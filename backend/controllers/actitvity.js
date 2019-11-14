const mysql = require('mysql');
const dbConfig = require('../config/db');
const activityDao = require('../dao/activiySqlMapping');
const userDao = require('../dao/userSqlMapping');
const util = require('util');

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
                        res.status(200).send(response);
                    } else {
                        // Create response
                        response.status = 200;
                        response.data = result;
                        res.status(200).send(response)
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
                        res.status(200).send(response);
                        pool.releaseConnection(connection);
                    } else {
                        let {id} = req.body;
                        if (!id) {
                            response.status = 400;
                            response.message = "Activity id could not be blank";
                            res.status(200).send(response);
                            pool.releaseConnection(connection);
                        } else {
                            connection.query(activityDao.getActivity, [id],
                                function (error, result) {
                                    if (error) {
                                        response.status = 500;
                                        response.message = "Unable to get activity information, please try again later";
                                        res.status(200).send(response);
                                    } else {
                                        if (!result) {
                                            response.status = 400;
                                            response.message = "Activity not exist";
                                            res.status(200).send(response);
                                        } else {
                                            response.status = 200;
                                            response.message = "Activity found";
                                            response.data = result;
                                            res.status(200).send(response);
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
    enroll: async (req, res) => {
        let response = {};
        pool.getConnection(async function (error, connection) {
            try {
                const query = util.promisify(connection.query).bind(connection);
                const {username, activity_id: activityId} = req.body;
                if (!username || !activityId) {
                    response.status = 400;
                    response.message = "User or activity could not be blank";
                    res.status(200).send(response);
                    pool.releaseConnection(connection)
                } else {
                    const user = await query(userDao.getUser, username);
                    if (!user) {
                        response.status = 400;
                        response.message = "User not exist, please check your account and try again";
                        res.status(200).send(response);
                        pool.releaseConnection(connection)
                    } else {
                        const userId = user[0].id;
                        const enrollCount = await query(activityDao.queryEnrollRecord, [userId, activityId]);
                        if (enrollCount.length > 0) {
                            response.status = 200;
                            response.message = "You have have already enrolled in this activity, you do not need to do it again.";
                            response.data = {
                                user_id: userId,
                                activity_id: activityId
                            };
                            res.status(200).send(response);
                        } else {
                            response.status = 200;
                            response.message = "You have successfully enrolled this event";
                            response.data = {
                                user_id: userId,
                                activity_id: activityId
                            };
                            res.status(200).send(response);
                        }
                    }
                }
            } catch (e) {
                response.status = 400;
                response.message = e.message;
                res.status(200).send(response);
                pool.releaseConnection(connection)
            }
        })
    }
};