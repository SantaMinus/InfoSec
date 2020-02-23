package com.sava.authenticator;

import com.sava.db.UserDao;
import com.sava.entity.User;
import com.sava.exception.AuthenticatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("authenticator")
public class DBAuthenticator implements Authenticator {
    private final UserDao userDao;

    @Autowired
    public DBAuthenticator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String captcha, String login, char[] password, int num1, int num2)
            throws AuthenticatorException {
        int capt = Integer.parseInt(captcha);
        User user = userDao.getByLogin(login);

        if (user == null) {
            throw new AuthenticatorException(String.format("User %s is not found", login));
        }
        return user.getPassword().equals(String.valueOf(password)) && capt == num1 + num2;
    }
}
