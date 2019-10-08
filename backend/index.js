require('dotenv').config(); // Sets up dotenv as soon as our application starts

const express = require('express');
const logger = require('morgan');
const bodyParser = require('body-parser');
const fs = require('fs');

const https = require('https');

const app = express();
const router = express.Router();

const environment = process.env.NODE_ENV; // development
const stage = require('./config/config')[environment];
const routes = require('./routes/index.js');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

if (environment !== 'production') {
    app.use(logger('dev'));
}

app.use('/api/v1', routes(router));

// Enable https
let privateKey = fs.readFileSync('privatekey.pem').toString();
let certificate = fs.readFileSync('certificate.pem').toString();
let credentials = {key: privateKey, cert: certificate};
let handler = function (req, res) {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Hello World\n');
};

let httpsServer = https.createServer(credentials, app);

httpsServer.listen("3000", () => {
    console.log("Server now listening at localhost: 3000");
});

module.exports = app;