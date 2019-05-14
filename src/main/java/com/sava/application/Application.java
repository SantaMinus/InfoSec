package com.sava.application;

import com.sava.ui.MainWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.debug("App started");
        MainWindow mainWindow = new MainWindow();
        mainWindow.signIn();
    }
}