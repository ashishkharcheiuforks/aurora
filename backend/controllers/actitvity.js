const config = require('../config/config');
const mysql = require('mysql');
const dbConfig = require('../config/db');
const activityDao = require('../dao/activiySqlMapping');
const environment = process.env.NODE_ENV;
const stage = require('../config/config')[environment];

// Use connection pool, improve the performance
const pool = mysql.createPool(dbConfig.mysql);

module.exports = {
    getActivities: (req, res) => {
        pool.getConnection(function (error, connection) {
            let response = {};
            if (error) {
                // If connection error, return error message
                response.status = 500;
                response.message = "Database connection error";
                res.status(response.status).send(response);
            } else {
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
                })
            }
        })
    }
};