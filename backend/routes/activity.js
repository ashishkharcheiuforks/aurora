const controller = require('../controllers/actitvity');
const validateToken = require('../utils').validateToken;
const connectDatabase =require('../utils').connectDatabase;

module.exports = (router) => {
    router.route('/activity').get(validateToken, controller.getAll);
    router.route('/activity/specific').get(validateToken, controller.getActivity);
};