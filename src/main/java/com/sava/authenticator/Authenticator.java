package com.sava.authenticator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Authenticator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Authenticator.class);
    private static final File FILE = new File("access.xml");
    private static final String FILE_NOT_FOUND_ERROR = "File not found";

    public boolean authenticate(String captcha, String login, String password, int c1, int c2) {
        int capt = Integer.parseInt(captcha);
        String[] arr;
        boolean access = false;

        Scanner scanner = null;
        try {
            scanner = new Scanner(FILE);
        } catch (FileNotFoundException e) {
            LOGGER.error(FILE_NOT_FOUND_ERROR, e);
        }
        String s;

        while (scanner.hasNext()) {
            s = scanner.nextLine();
            arr = s.split(" ");

            if (login.equals(arr[0]) && password.equals(arr[1]) && capt == c1 + c2) {
                File directory = new File("C:/root/" + login);
                access = true;
                if (!directory.exists())
                    directory.mkdir();
                try {
                    Runtime.getRuntime().exec("attrib +H " + "C:/root/" + login);
                } catch (IOException e) {
                    LOGGER.error("Failed to execute a command", e);
                }
                LOGGER.debug("{} signed in", login);
            }
        }
        scanner.close();
        return access;
    }
}