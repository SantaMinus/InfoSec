package com.sava.db;

import com.sava.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class UserDao implements Dao<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    private Session session;

    private void createSession() {
        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        session = factory.getCurrentSession();
    }

    @Override
    public User getById(int id) {
        createSession();
        session.beginTransaction();
        return session.get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return Collections.emptyList();
    }

    @Override
    public User create() {
        return null;
    }

    @Override
    public User update() {
        return null;
    }
}
