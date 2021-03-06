package com.sava.authenticator;

import com.sava.exception.AuthenticatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class XMLAuthenticator implements Authenticator {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLAuthenticator.class);
    private static final File FILE = new File("access.xml");
    private static final String FILE_NOT_FOUND_ERROR = "File not found";

    @Override
    public boolean authenticate(String captcha, String login, char[] password, int num1, int num2) throws AuthenticatorException {
        int capt = Integer.parseInt(captcha);
        String[] arr;
        boolean access = false;

        Scanner scanner = null;
        try {
            scanner = new Scanner(FILE);
        } catch (FileNotFoundException e) {
            LOGGER.error(FILE_NOT_FOUND_ERROR, e);
        }
        if (scanner == null) {
            throw new AuthenticatorException("Failed to create a file scanner");
        }
        String s;

        while (scanner.hasNext()) {
            s = scanner.nextLine();
            arr = s.split(" ");

            if (login.equals(arr[0]) && Arrays.equals(password, arr[1].toCharArray()) && capt == num1 + num2) {
                File directory = new File("F:/root/" + login);
                access = true;
                if (!directory.exists())
                    directory.mkdir();
                try {
                    Runtime.getRuntime().exec("attrib +H " + "F:/root/" + login);
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
