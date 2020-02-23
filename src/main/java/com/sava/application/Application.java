package com.sava.application;

import com.sava.db.UserDao;
import com.sava.entity.User;
import com.sava.ui.MainWindow;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.debug("App started");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        UserDao userDao = new UserDao();
        User newUser = new User("testUser", "pass");
        User created = userDao.create(newUser);
        User usr = userDao.getByLogin("testUser");

        MainWindow mainWindow = context.getBean("mainWindow", MainWindow.class);
        mainWindow.signIn();

        context.close();
        factory.close();
    }
}
