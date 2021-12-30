package com.sava.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DatabaseConnection {
   public Connection connect() {
        String dbUrl = "jdbc:h2:file:/D:/Programs/H2/bin/infosec;AUTO_SERVER=TRUE";
        String user = "sava";
        String password = "12345";
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            Connection con = DriverManager.getConnection(dbUrl, user, password);
            log.debug("Connection created successfully");
            return con;
        } catch (ClassNotFoundException e) {
            log.error("JDBCDriver not found", e);
        } catch (SQLException e) {
            log.error("Failed to create a DB connection", e);
        }
        return null;
    }
}
