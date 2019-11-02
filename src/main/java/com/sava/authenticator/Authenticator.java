package com.sava.authenticator;

import com.sava.exception.AuthenticatorException;

public interface Authenticator {
    boolean authenticate(String captcha, String login, char[] password, int c1, int c2) throws AuthenticatorException;
}
