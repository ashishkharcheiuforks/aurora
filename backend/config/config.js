const connUri = "mongodb://127.0.0.1:27017/node-jwt";
const serverUri = "http://localhost:3000";
const jwtSecret = "cheng2277980";
const development = {
    port: process.env.PORT || 3000,
    saltingRounds: 10
};

module.exports = {
    connUri,
    serverUri,
    jwtSecret,
    development
};