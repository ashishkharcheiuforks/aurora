const controller = require('../controllers/actitvity');
const validateToken = require('../utils').validateToken;
const getUserId = require('../utils').getUserId;
const connectDatabase = require('../utils').connectDatabase;

module.exports = (router) => {
    router.route('/activity').get(validateToken, connectDatabase, controller.getActivities);
    router.route('/activity/enroll').post(validateToken, connectDatabase, getUserId, controller.enroll);
};