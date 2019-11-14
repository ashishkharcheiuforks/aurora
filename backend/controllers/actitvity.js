const mysql = require('mysql');
const dbConfig = require('../config/db');
const activityDao = require('../dao/activiySqlMapping');
const userDao = require('../dao/userSqlMapping');
const util = require('util');

// Use connection pool, improve the performance
const pool = mysql.createPool(dbConfig.mysql);

module.exports = {
    getActivities: (req, res) => {
        let response = {};
        pool.getConnection(async function (error, connection) {
            try {
                const query = util.promisify(connection.query).bind(connection);
                const result = await query(activityDao.queryAll);
                response.status = 200;
                response.data = result;
                res.status(response.status).send(response);
                pool.releaseConnection(connection);
            } catch (e) {
                response.status = 400;
                response.message = e.message;
                res.status(response.status).send(response);
                pool.releaseConnection(connection)
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
                    res.status(response.status).send(response);
                    pool.releaseConnection(connection)
                } else {
                    const user = await query(userDao.getUser, username);
                    if (!user) {
                        response.status = 400;
                        response.message = "User not exist, please check your account and try again";
                        res.status(response.status).send(response);
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
                            res.status(response.status).send(response);
                        } else {
                            response.status = 200;
                            response.message = "You have successfully enrolled this event";
                            response.data = {
                                user_id: userId,
                                activity_id: activityId
                            };
                            res.status(response.status).send(response);
                        }
                    }
                }
            } catch (e) {
                response.status = 500;
                response.message = e.message;
                res.status(response.status).send(response);
                pool.releaseConnection(connection)
            }
        })
    }
};