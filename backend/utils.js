const jwt = require('jsonwebtoken');
const config = require('./config/config');
const dbConfig = require('./config/db');
const mysql = require('mysql');

// Use connection pool, improve the performance
const pool = mysql.createPool(dbConfig.mysql);

module.exports = {
    validateToken: (req, res, next) => {
        const authorizationHeader = req.headers.authorization;
        let result;
        if (authorizationHeader) {
            const token = req.headers.authorization.split(' ')[1]; // Bearer <token>
            const options = {
                expiresIn: '2d',
                issuer: config.serverUri
            };
            try {
                // verify makes sure that the token hasn't expired and has been issued by us
                result = jwt.verify(token, config.jwtSecret, options);

                // Let's pass back the decoded token to the request object
                req.decode = result;

                // We call next to pass execution to the subsequent middleware
                next();
            } catch (err) {
                // Throw an error just in case anything goes wrong with verification
                throw new Error(err);
            }
        } else {
            result = { 
                error: `Authentication error. Token required.`,
                status: 401
              };
              res.status(401).send(result);        
        }
    },

    connectDatabase: (req, res, next) => {
        pool.getConnection(function (error, connection) {
            let response = {};
            if (error) {
                // If connection error, return error message
                response.status = 500;
                response.message = "Database connection error";
                res.status(response.status).send(response);
            } else {
                next();
            }
        })
    },
};