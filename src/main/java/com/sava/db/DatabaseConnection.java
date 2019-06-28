package com.sava.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);

    public Connection connect() {
        Connection con = null;

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/savadb", "SA", "");
            if (con != null) {
                LOGGER.debug("Connection created successfully");
            } else {
                LOGGER.error("Problem with creating connection");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error("JDBCDriver not found", e);
        } catch (SQLException e) {
            LOGGER.error("Failed to create a DB connection", e);
        }

        return con;
    }
}
