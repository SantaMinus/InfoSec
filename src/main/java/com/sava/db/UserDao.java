package com.sava.db;

import com.sava.entity.User;

public interface UserDao extends Dao<User> {
    /**
     * Retrieves a User by its login
     */
    User getByLogin(String login);
}
