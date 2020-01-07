package com.sava.application;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.sava.entity.User;
import com.sava.ui.MainWindow;
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
        Session session = factory.getCurrentSession();

        User user = new User("testUser", "pass");
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        MainWindow mainWindow = context.getBean("mainWindow", MainWindow.class);
        mainWindow.signIn();

        context.close();
        factory.close();
    }
}
