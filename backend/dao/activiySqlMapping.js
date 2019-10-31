let activity = {
    queryAll: 'select * from activity',
    getActivity: 'select * from activity where id =?',
    enroll: 'insert into activity_enrolment(user_id, activity_id, create_at, update_at) values(?,?,?,?);',
};

module.exports = activity;