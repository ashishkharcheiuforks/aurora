let user = {
    register: 'insert into user(username, password, nick_name, create_at, update_at) values(?,?,?,?,?);',
    getUsers: 'select * from user where username=?;',
    login: 'select distinct * from user where username=?;',
    queryAll: 'select * from user;',
};

module.exports = user;