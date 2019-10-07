const controller = require('../controllers/users');
const validateToken = require('../utils').validateToken;
const connectDatabase =require('../utils').connectDatabase;

module.exports = (router) => {
  router.route('/register').post(controller.add);
  router.route('/login').post(controller.login);
};