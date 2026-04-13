package rvt.dao;

import  rvt.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class EmployeeDAO {

    public List<Employee> getAllEmployees() throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM darbinieki")) {

            while (rs.next()) {
                Employee employee = new Employee(rs.getInt("darbinieka_id"),
                                        rs.getString("vards"),
                                        rs.getString("uzvards"),
                                        rs.getString("amats"),
                                        rs.getString("lietotajvards"),
                                        rs.getString("parole"),
                                        rs.getString("amats")
                                        );
                employees.add(employee);
            }
        }

        return employees;
    }

    public void addEmployee(Employee employee) throws SQLException {
        try (Connection dbConn = Database.connect()) {
            String sql = "INSERT INTO darbinieki(darbinieka_id, vards, uzvards, amats, lietotajvards, parole, loma) VALUES(?,?,?, ?)";

            PreparedStatement pstmt = dbConn.prepareStatement(sql);

            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getSurname());
            pstmt.setString(4, employee.getPosition());
            pstmt.setString(5, employee.getUsername());
            pstmt.setString(6, employee.getPassword());
            pstmt.setString(7, employee.getRole());
            pstmt.executeUpdate();

        }
    }

    public void updateEmployee(Employee employee) throws SQLException {
        try (Connection conn3 = Database.connect()) {
            String sql = "UPDATE darbinieki SET vards=?, uzvards=?, amats=?, lietotajvards=?, parole=?, loma=? WHERE darbinieka_id=?";
            PreparedStatement pstmt = conn3.prepareStatement(sql);

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getSurname());
            pstmt.setString(3, employee.getPosition());
            pstmt.setString(4, employee.getUsername());
            pstmt.setString(5, employee.getPassword());
            pstmt.setString(6, employee.getRole());
            pstmt.setInt(3, employee.getId());
            pstmt.executeUpdate();

        }
    }

    public void removeEmployee(int id) throws SQLException {
        try (Connection dbConn2 = Database.connect()) {
            String sql = "DELETE FROM darbinieki WHERE darbinieka_id=?";
            PreparedStatement pstmt = dbConn2.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Employee findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM darbinieki WHERE lietotajvards=?";

        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                    rs.getInt("darbinieka_id"),
                    rs.getString("vards"),
                    rs.getString("uzvards"),
                    rs.getString("amats"),
                    rs.getString("lietotajvards"),
                    rs.getString("parole"),
                    rs.getString("loma")
                );
            }
        }

        return null;
    }
}
