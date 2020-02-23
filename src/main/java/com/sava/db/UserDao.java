package com.sava.db;

import com.sava.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserDao implements Dao<User> {

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
    public User getByLogin(String login) {
        createSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User u where u.login = :login")
                .setParameter("login", login)
                .getResultList();
        return users.get(0);
    }

    @Override
    public List<User> getAll() {
        return Collections.emptyList();
    }

    @Override
    public User create(User newUser) {
        createSession();
        session.beginTransaction();
        session.save(newUser);
        session.getTransaction().commit();
        return getById(newUser.getPk());
    }

    @Override
    public User update() {
        return null;
    }
}
