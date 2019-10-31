const controller = require('../controllers/actitvity');
const validateToken = require('../utils').validateToken;
const connectDatabase =require('../utils').connectDatabase;

module.exports = (router) => {
    router.route('/activity').get(validateToken, controller.getActivity);
    router.route('/activity/enroll').post(validateToken, controller.enroll);
};