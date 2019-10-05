const users = require('./users');
const activity = require('./activity');

module.exports = (router) => {
  users(router);
  activity(router);
  return router;
};