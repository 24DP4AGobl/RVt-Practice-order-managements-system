package rvt;

import java.sql.*;

public class Database {
    public static Connection connect() {
        String url = "jdbc:sqlite:sample.db";
    
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS darbinieki ("
                   + "id INTEGER PRIMARY KEY,"
                   + "vards TEXT,"
                   + "uzvards TEXT,"
                   + "amats TEXT)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}