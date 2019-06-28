package com.sava.authenticator;

import com.sava.db.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAuthenticator implements Authenticator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBAuthenticator.class);

    @Override
    public boolean authenticate(String captcha, String login, char[] password, int c1, int c2) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection con = connection.connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT LOGIN, PASSWORD FROM USERS");
            if (res.next()) {
                LOGGER.debug("login: {}, pass: {}", res.getString("LOGIN"), res.getString("PASSWORD"));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL query", e);
        }
        return false;
    }
}
