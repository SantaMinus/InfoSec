package com.sava.application;

import com.sava.ui.MainWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.debug("App started");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        MainWindow mainWindow = context.getBean("mainWindow", MainWindow.class);
        mainWindow.signIn();

        context.close();
    }
}
