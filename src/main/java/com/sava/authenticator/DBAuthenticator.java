package com.sava.authenticator;

import com.sava.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//@Slf4j
public class DBAuthenticator implements Authenticator {
    @Override
    public boolean authenticate(String captcha, String login, char[] password, int c1, int c2) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection con = connection.connect();

        try (Statement stmt = con.createStatement()) {
            ResultSet res = stmt.executeQuery("SELECT LOGIN, PASSWORD FROM USERS");
            if (res.next()) {
//                log.debug("login: {}, pass: {}", res.getString("LOGIN"), res.getString("PASSWORD"));
                if (res.getString("LOGIN").equals(login)
                        && res.getString("PASSWORD").equals(String.valueOf(password))) {
                    return true;
                }
            }
        } catch (SQLException e) {
//            log.error("Failed to execute SQL query", e);
        }
        return false;
    }
}
