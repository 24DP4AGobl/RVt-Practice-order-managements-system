package rvt.dao;

import rvt.model.Deliverer;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DelivererDAO {
    
    
    public List<Deliverer> getAllDeliverers() throws SQLException {
        ArrayList<Deliverer> deliverers = new ArrayList<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pasutijumi")) {

            while (rs.next()) {
                Deliverer deliverer = new Deliverer(rs.getInt("piegadataja_id"),
                                        rs.getString("nosaukums"),
                                        rs.getString("talrunis"),
                                        rs.getString("epasts")
                                        );
                deliverers.add(deliverer);
            }
        }

        return deliverers;
    }

    public void addDeliverer(Deliverer deliverer) throws SQLException {
        try (Connection dbConn = Database.connect()) {
            String sql = "INSERT INTO piegadataji(piegadataja_id, nosaukums, talrunis, epasts) VALUES(?,?,?,?)";

            PreparedStatement pstmt = dbConn.prepareStatement(sql);

            pstmt.setInt(1, deliverer.getId());
            pstmt.setString(2, deliverer.getName());
            pstmt.setString(3, deliverer.getPhone());
            pstmt.setString(4, deliverer.getEmail());
            pstmt.executeUpdate();

        }
    }

    public void updateDeliverer(Deliverer deliverer) throws SQLException {
        try (Connection conn3 = Database.connect()) {
            String sql = "UPDATE piegadataji SET nosaukums=?, talrunis=?, email=? WHERE piegadataja_id=?";
            PreparedStatement pstmt = conn3.prepareStatement(sql);

            pstmt.setString(1, deliverer.getName());
            pstmt.setString(2, deliverer.getPhone());
            pstmt.setString(3, deliverer.getEmail());
            pstmt.setInt(3, deliverer.getId());

            pstmt.executeUpdate();

        }
    }

    public void removerDeliverer(int Id) throws SQLException {
        try (Connection dbConn2 = Database.connect()) {
            String sql = "DELETE FROM piegadataji WHERE piegadataja_id=?";
            PreparedStatement pstmt = dbConn2.prepareStatement(sql);
            pstmt.setInt(1, Id);
            pstmt.executeUpdate();
        }
    }
}
