package rvt.dao;

import rvt.model.Employee;
import  rvt.model.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class OrderDAO {

    public List<Order> getAllOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pasutijumi")) {

            while (rs.next()) {
                Order order = new Order(rs.getInt("pasutijuma_id"),
                                        rs.getString("datums"),
                                        new BigDecimal(rs.getString("summa")),
                                        rs.getInt("gab"),
                                        rs.getInt("status_id"),
                                        rs.getInt("darbinieks_id"),
                                        rs.getInt("produkts_id")
                                        );
                orders.add(order);
            }
        }

        return orders;
    }

    public void addOrder(Order order) throws SQLException {
        try (Connection dbConn = Database.connect()) {
            String sql = "INSERT INTO pasutijumi(pasutijuma_id, datums, summa, gab, status_id, darbinieks_id, produkts_id) VALUES(?,?,?,?,?,?,?)";

            PreparedStatement pstmt = dbConn.prepareStatement(sql);

            pstmt.setInt(1, order.getId());
            pstmt.setString(2, order.getDate());
            pstmt.setBigDecimal(3, order.getTotal());
            pstmt.setInt(4, order.getAmount());
            pstmt.setInt(5, order.getStatId());
            pstmt.setInt(6, order.getEmplId());
            pstmt.setInt(7, order.getProdlId());
            pstmt.executeUpdate();

        }
    }

    public void updateOrder(Order order) throws SQLException {
        try (Connection conn3 = Database.connect()) {
            String sql = "UPDATE pasutijumi SET summa=?, status_id=?, produkts_id=?, gab=? WHERE pasutijuma_id=?";
            PreparedStatement pstmt = conn3.prepareStatement(sql);

            String input = String.valueOf(order.getTotal()).trim().replace(",", ".");
            BigDecimal sum = new BigDecimal(input);
            pstmt.setBigDecimal(1, sum);

            pstmt.setInt(2, order.getStatId());
            pstmt.setInt(3, order.getProdlId());
            pstmt.setInt(4, order.getAmount());
            pstmt.setInt(5, order.getId());

            pstmt.executeUpdate();

        }
    }

    public void removeOrder(int Id) throws SQLException {
        try (Connection dbConn2 = Database.connect()) {
            String sql = "DELETE FROM pasutijumi WHERE pasutijuma_id=?";
            PreparedStatement pstmt = dbConn2.prepareStatement(sql);
            pstmt.setInt(1, Id);
            pstmt.executeUpdate();
        }
    }

    public List<Order> getOrders(Integer statusId, Integer employeeId) throws SQLException {

        List<Order> orders = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM pasutijumi WHERE 1=1");

        if (statusId != null) {
            sql.append(" AND status_id = ?");
        }

        if (employeeId != null) {
            sql.append(" AND darbinieks_id = ?");
        }

        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if (statusId != null) {
                pstmt.setInt(index++, statusId);
            }

            if (employeeId != null) {
                pstmt.setInt(index++, employeeId);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("pasutijuma_id"),
                    rs.getString("datums"),
                    rs.getBigDecimal("summa"),
                    rs.getInt("gab"),
                    rs.getInt("status_id"),
                    rs.getInt("darbinieks_id"),
                    rs.getInt("produkts_id")
                );

                orders.add(order);
            }
        }

        return orders;
    }

    public Order getOrderById(Integer id) throws SQLException {
        String sql = "SELECT * FROM pasutijumi WHERE pasutijuma_id = ?";

        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Order(
                    rs.getInt("pasutijuma_id"),
                    rs.getString("datums"),
                    rs.getBigDecimal("summa"),
                    rs.getInt("gab"),
                    rs.getInt("status_id"),
                    rs.getInt("darbinieks_id"),
                    rs.getInt("produkts_id"));
            }
        }

        return null; // not found
    }
}