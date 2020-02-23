package com.sava.authenticator;

import com.sava.exception.AuthenticatorException;

public interface Authenticator {
    /**
     * Checks a password & a captcha and grants access if everything is right
     *
     * @param captcha
     *         must be a sum of num1 & num2, sent by  user
     * @param login
     *         is used to find a user record in a DB or an XML file, sent by  user
     * @param password
     *         a user password, sent by  user
     * @param num1
     *         first number to calculate captcha, sent by  user
     * @param num2
     *         second number to calculate captcha, sent by  user
     *
     * @return {@code true} when authentication has been successful, {@code false} otherwise
     * @throws AuthenticatorException
     *         when an authentication goes wrong
     */
    boolean authenticate(String captcha, String login, char[] password, int num1, int num2)
            throws AuthenticatorException;
}
