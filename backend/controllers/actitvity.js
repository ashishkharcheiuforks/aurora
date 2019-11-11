const config = require('../config/config');
const mysql = require('mysql');
const dbConfig = require('../config/db');
const activityDao = require('../dao/activiySqlMapping');
const userDao = require('../dao/userSqlMapping');
const environment = process.env.NODE_ENV;
const stage = require('../config/config')[environment];

// Use connection pool, improve the performance
const pool = mysql.createPool(dbConfig.mysql);

module.exports = {
    getActivity: (req, res) => {
        pool.getConnection(function (error, connection) {
            let response = {};
            let {id} = req.body;
            if (!id) {
                // If id not provided, return all
                connection.query(activityDao.queryAll, function (error, result) {
                    if (error) {
                        response.status = 500;
                        response.message = "Database connection error";
                        res.status(response.status).send(response);
                    } else {
                        // Create response
                        response.status = 200;
                        response.data = result;
                        res.status(response.status).send(response)
                    }
                    pool.releaseConnection(connection);
                })
            } else {
                // If id provided, return the specific id
                pool.getConnection(function (error, connection) {
                    let response = {};
                    if (error) {
                        // If connection error, return error message
                        response.status = 500;
                        response.message = "Database connection error";
                        res.status(response.status).send(response);
                        pool.releaseConnection(connection);
                    } else {
                        let {id} = req.body;
                        if (!id) {
                            response.status = 400;
                            response.message = "Activity id could not be blank";
                            pool.releaseConnection(connection);
                        } else {
                            connection.query(activityDao.getActivity, [id],
                                function (error, result) {
                                    if (error) {
                                        response.status = 500;
                                        response.message = "Unable to get activity information, please try again later";
                                        res.status(response.status).send(response);
                                    } else {
                                        if (!result) {
                                            response.status = 400;
                                            response.message = "Activity not exist";
                                            res.status(response.status).send(response);
                                        } else {
                                            response.status = 200;
                                            response.message = "Activity found";
                                            response.data = result;
                                            res.status(response.status).send(response);
                                        }
                                    }
                                    pool.releaseConnection(connection);
                                })
                        }
                    }
                })
            }
        })
    },

    enroll: (req, res) => {
        pool.getConnection(function (error, connection) {
            let response = {};
            let {username, activity_id} = req.body;
            if (!username || !activity_id) {
                response.status = 400;
                response.message = "User or activity could not be blank";
                res.status(response.status).send(response);
                pool.releaseConnection(connection)
            } else {
                // Find uer based on username
                connection.query(userDao.getUser, username, function (error, result) {
                    if (error) {
                        response.status = 400;
                        response.message = "User not exist";
                        res.status(response.status).send(response);
                        pool.releaseConnection(connection)
                    } else {
                        let timestamp = Date.now();
                        let user_id = result.id;
                        connection.query(activityDao.enroll, [user_id, activity_id, timestamp, timestamp],
                            function (error, result) {
                                if (error) {
                                    response.status = 500;
                                    response.message = "Unable to get activity information, please try again later";
                                    res.status(response.status).send(response);
                                } else {
                                    response.status = 200;
                                    response.message = "You have successfully enrolled this event";
                                    response.data = {
                                        user_id: user_id,
                                        activity_id: activity_id
                                    };
                                    res.status(response.status).send(response);
                                }
                            });
                    }

                });


            }

        })
    }
};