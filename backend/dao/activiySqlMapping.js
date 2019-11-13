let activity = {
    queryAll: 'select * from activity',
    getActivity: 'select * from activity where id =?',
    enroll: 'insert into activity_enrolment(user_id, activity_id, create_at, update_at) values(?,?,?,?);',
    queryEnrollRecord: 'select * from activity_enrolment where user_id=? and activity_id=?',
};

module.exports = activity;