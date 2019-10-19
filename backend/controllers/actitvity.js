const config = require('../config/config');
const mysql = require('mysql');
const dbConfig = require('../config/db');
const activityDao = require('../dao/activiySqlMapping');
const environment = process.env.NODE_ENV;
const stage = require('../config/config')[environment];

// Use connection pool, improve the performance
const pool = mysql.createPool(dbConfig.mysql);

module.exports = {
    getActivity: (req, res) => {
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
            }
        })
    },
};