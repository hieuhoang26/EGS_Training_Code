package org.example.dao;


import org.example.model.User;

public class UserDao extends BaseDao {

    public User saveUser(User user) {
        return save(user);
    }

    public User findUser(Long id) {
        return find(User.class, id);
    }
}
