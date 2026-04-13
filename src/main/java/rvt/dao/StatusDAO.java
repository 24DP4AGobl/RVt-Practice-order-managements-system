package rvt.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import rvt.model.Status;

public class StatusDAO {

    public List<Status> getAllStatuses() throws SQLException {
        ArrayList<Status> statuses = new ArrayList<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM statuss")) {

            while (rs.next()) {
                Status status = new Status(rs.getInt("statuss_id"),
                                        rs.getString("nosaukums")
                                        );
                statuses.add(status);
            }
        }

        return statuses;
    }

    public void addStatus(Status status) throws SQLException {
        try (Connection dbConn = Database.connect()) {
            String sql = "INSERT INTO statuss(statuss_id, nosaukums) VALUES(?,?)";

            PreparedStatement pstmt = dbConn.prepareStatement(sql);

            pstmt.setInt(1, status.getId());
            pstmt.setString(2, status.getName());
            pstmt.executeUpdate();

        }
    }

    public void updateStatus(Status status) throws SQLException {
        try (Connection conn3 = Database.connect()) {
            String sql = "UPDATE statuss SET nosaukums=? WHERE statusa_id=?";
            PreparedStatement pstmt = conn3.prepareStatement(sql);

            pstmt.setString(1, status.getName());
            pstmt.setInt(2, status.getId());

            pstmt.executeUpdate();

        }
    }

    public void removeStatus(int Id) throws SQLException {
        try (Connection dbConn2 = Database.connect()) {
            String sql = "DELETE FROM statuss WHERE statusa_id=?";
            PreparedStatement pstmt = dbConn2.prepareStatement(sql);
            pstmt.setInt(1, Id);
            pstmt.executeUpdate();
        }
    }
}
