package rvt;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:sqlite:data/sample.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {
        String darbiniekiTable = "CREATE TABLE IF NOT EXISTS darbinieki ("
                + "id INTEGER PRIMARY KEY,"
                + "vards TEXT,"
                + "uzvards TEXT,"
                + "amats TEXT)";

        // String ordersTable = "CREATE TABLE IF NOT EXISTS orders ("
        //        + "pasutijuma_id INTEGER PRIMARY KEY,"
        //        + "datums DATETIME,"
        //        + "summa DECIMAL(10,2),"
        //        + "statuss VARCHAR(20))";

        String delivererTable = "CREATE TABLE IF NOT EXISTS deliverer ("
                + "piegadataja_id INTEGER PRIMARY KEY,"
                + "nosaukums VARCHAR(100),"
                + "talrunis VARCHAR(15),"
                + "emails VARCHAR(100))";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(darbiniekiTable);
        //    stmt.execute(ordersTable);
            stmt.execute(delivererTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}