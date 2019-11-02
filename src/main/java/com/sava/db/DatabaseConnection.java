package com.sava.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);

    public Connection connect() {
        String dbUrl = "jdbc:h2:file:/D:/Programs/H2/bin/infosec;AUTO_SERVER=TRUE";
        String user = "sava";
        String password = "12345";
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            Connection con = DriverManager.getConnection(dbUrl, user, password);
            LOGGER.debug("Connection created successfully");
            return con;
        } catch (ClassNotFoundException e) {
            LOGGER.error("JDBCDriver not found", e);
        } catch (SQLException e) {
            LOGGER.error("Failed to create a DB connection", e);
        }
        return null;
    }
}
