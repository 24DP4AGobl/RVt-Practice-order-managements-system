package rvt;

import java.sql.*;

public class Database {
    public static Connection connect() {
        String url = "jdbc:sqlite:data/sample.db";
    
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void createTables() {
        String darbiniekiTable = "CREATE TABLE IF NOT EXISTS darbinieki ("
                   + "id INTEGER PRIMARY KEY,"
                   + "vards TEXT,"
                   + "uzvards TEXT,"
                   + "amats TEXT)";
        String ordersTable = "CREATE TABLE IF NOT EXISTS darbinieki ("
                   + "pasutijuma_id INTEGER PRIMARY KEY,"
                   + "datums DATETIME,"
                   + "summa DECIMAL(10,2),"
                   + "statuss VARCHAR(20))";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(darbiniekiTable);
            stmt.execute(ordersTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}