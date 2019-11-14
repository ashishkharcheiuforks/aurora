const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const config = require('../config/config');
const mysql = require('mysql');
const dbConfig = require('../config/db');
const userDao = require('../dao/userSqlMapping');
const environment = process.env.NODE_ENV;
const stage = require('../config/config')[environment];

// Use connection pool, improve the performance
const pool = mysql.createPool(dbConfig.mysql);

module.exports = {
    register: (req, res) => {
        pool.getConnection(function (error, connection) {
            let response = {};
            if (error) {
                // If connection error, return error message
                response.status = 500;
                response.message = "Database connection error";
                res.status(response.status).send(response);
                pool.releaseConnection(connection);
            } else {
                // Grab the input from request
                let {username, password, nick_name} = req.body;

                // Hash the password
                bcrypt.hash(password, stage.saltingRounds, function (error, hash) {
                    if (error) {
                        // Hash error
                        response.status = 500;
                        response.message = "Unable to register, please change password and try again";
                        res.status(response.status).send(response);
                        pool.releaseConnection(connection);
                    } else {
                        connection.query(userDao.getUsers, username, function (error, result) {
                            if (result.length > 0) {
                                // Hash error
                                response.status = 400;
                                response.message = "User already exist, please change username and try again";
                                res.status(response.status).send(response);
                                pool.releaseConnection(connection)
                            } else {
                                password = hash;
                                // Save the record to the database
                                let timestamp = Date.now();
                                connection.query(userDao.register, [username, password, nick_name, timestamp, timestamp],
                                    function (error, result) {
                                        if (error) {
                                            response.status = 500;
                                            response.message = "Unable to register, please try again later";
                                            res.status(response.status).send(response);
                                        } else {
                                            response.status = 200;
                                            response.message = "Account successfully registered";
                                            response.data = {
                                                username: username,
                                                nick_name: nick_name
                                            };
                                            res.status(response.status).send(response);
                                        }
                                        pool.releaseConnection(connection);
                                    });
                            }
                        });

                    }
                })
            }
        })
    },

    login: (req, res) => {
        pool.getConnection(function (error, connection) {
            let response = {};
            let {username, password} = req.body;
            if (error) {
                // If connection error, return error message
                response.status = 500;
                response.message = "Database connection error";
                res.status(response.status).send(response);
            } else {
                if (!username) {
                    response.status = 400;
                    response.message = "Username could not be blank";
                    res.status(response.status).send(response);
                } else if (!password) {
                    response.status = 401;
                    response.message = "Password could not be blank";
                    res.status(response.status).send(response);
                } else {
                    connection.query(userDao.getUsers, username, function (error, result) {
                        if (error) {
                            response.status = 500;
                            response.message = "Database connection error";
                            res.status(response.status).send(response);
                        } else if (result.length === 0) {
                            response.status = 400;
                            response.message = "User not exist, please check your username";
                            res.status(response.status).send(response);
                        } else {
                            bcrypt.compare(password, result[0].password).then(match => {
                                if (match) {
                                    response.status = 200;
                                    // create a token
                                    const payload = {user: username};
                                    const options = {
                                        expiresIn: '2d',
                                        issuer: config.serverUri
                                    };
                                    const secret = config.jwtSecret;
                                    const token = jwt.sign(payload, secret, options);
                                    response.status = 200;
                                    response.message = "Login successful!";
                                    response.data = {
                                        username: username,
                                        token: token
                                    };
                                    res.status(response.status).send(response)
                                } else {
                                    response.status = 400;
                                    response.message = "Password is not correct, please check and try again.";
                                    res.status(response.status).send(response);
                                }
                            })
                        }
                    })
                }

            }
        })
    },
};