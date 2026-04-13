package rvt.dao;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:sqlite:data/sample.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {
        String darbiniekiTable = "CREATE TABLE IF NOT EXISTS darbinieki ("
                + "darbinieka_id INTEGER PRIMARY KEY,"
                + "vards TEXT,"
                + "uzvards TEXT,"
                + "amats TEXT,"
                + "lietotajvards TEXT UNIQUE NOT NULL,"
                + "parole TEXT NOT NULL,"
                + "loma TEXT)";

        String delivererTable = "CREATE TABLE IF NOT EXISTS piegadataji ("
                + "piegadataja_id INTEGER PRIMARY KEY,"
                + "nosaukums TEXT,"
                + "talrunis TEXT,"
                + "epasts TEXT)";

        String catergoryTable = "CREATE TABLE IF NOT EXISTS kategorijas ("
                + "kategorijas_id INTEGER PRIMARY KEY,"
                + "nosaukums TEXT)";

        String statusTable = "CREATE TABLE IF NOT EXISTS statuss ("
                + "statusa_id INTEGER PRIMARY KEY,"
                + "nosaukums TEXT)";

        String ordersTable = "CREATE TABLE IF NOT EXISTS pasutijumi ("
                + "pasutijuma_id INTEGER PRIMARY KEY,"
                + "datums DATETIME,"
                + "summa REAL NOT NULL,"
                + "gab INTEGER,"
                + "status_id INTEGER,"
                + "darbinieks_id INTEGER,"
                + "produkts_id INTEGER,"
                + "FOREIGN KEY(status_id) REFERENCES statuss(statusa_id),"
                + "FOREIGN KEY(darbinieks_id) REFERENCES darbinieki(darbinieks_id),"
                + "FOREIGN KEY(produkts_id) REFERENCES produkti(produkts_id)"
                + ")";

        String productTable = "CREATE TABLE IF NOT EXISTS produkti ("
                + "produkta_id INTEGER PRIMARY KEY,"
                + "nosaukums TEXT NOT NULL,"
                + "cena REAL NOT NULL,"
                + "daudzums INTEGER,"
                + "kategorijas_id INTEGER,"
                + "piegadatajs_id INTEGER,"
                + "FOREIGN KEY(kategorijas_id) REFERENCES kategorijas(kategorijas_id),"
                + "FOREIGN KEY (piegadatajs_id) REFERENCES piegadataji(piegadataja_id))"; 

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(darbiniekiTable);
            stmt.execute(delivererTable);
            stmt.execute(catergoryTable);
            stmt.execute(statusTable);
            stmt.execute(ordersTable);
            stmt.execute(productTable);
            //stmt.execute("DROP TABLE orders");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void seedData() {
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {

                // Employees
                stmt.executeUpdate("""
                INSERT INTO darbinieki(vards, uzvards, amats, lietotajvards, parole, loma)
                VALUES 
                ('Janis', 'Berzins', 'Admin', 'admin', '1234', 'admin'),
                ('Anna', 'Kalnina', 'Darbinieks', 'anna', '1234', 'employee')
                """);

                // Status
                stmt.executeUpdate("""
                INSERT INTO statuss(nosaukums)
                VALUES ('Jauns'), ('Procesā'), ('Pabeigts')
                """);

                // Category
                stmt.executeUpdate("""
                INSERT INTO kategorijas(nosaukums)
                VALUES ('Elektronika'), ('Pārtika')
                """);

                // Products
                stmt.executeUpdate("""
                INSERT INTO produkti(nosaukums, cena, daudzums)
                VALUES ('Telefons', 299.99, 10),
                        ('Maize', 1.20, 50)
                """);

                // Orders
                stmt.executeUpdate("""
                INSERT INTO pasutijumi(datums, summa, gab, status_id, darbinieks_id, produkts_id)
                VALUES 
                ('2026-04-12', 299.99, 1, 1, 1, 1),
                ('2026-04-12', 2.40, 2, 2, 2, 2)
                """);

        } catch (SQLException e) {
                e.printStackTrace();
        }
   }
}
