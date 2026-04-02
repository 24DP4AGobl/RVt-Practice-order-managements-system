package rvt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connect() {
        String url = "jdbc:sqlite:sample.db";
    
        try {
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connected to SQLite successfully!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection failed:");
            System.out.println(e.getMessage());
            return null;
        }
    }
}