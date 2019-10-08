const controller = require('../controllers/users');
const validateToken = require('../utils').validateToken;
const connectDatabase =require('../utils').connectDatabase;

module.exports = (router) => {
  router.route('/register').post(controller.register);
  router.route('/login').post(controller.login);
};