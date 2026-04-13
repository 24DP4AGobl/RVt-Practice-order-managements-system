package rvt.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import rvt.model.Category;

public class CategoryDAO {
    
    public List<Category> getAllCategories() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM kategorijas")) {

            while (rs.next()) {
                Category category = new Category(rs.getInt("kategorijas_id"),
                                        rs.getString("nosaukums")
                                        );
                categories.add(category);
            }
        }

        return categories;
    }

    public void addCategory(Category category) throws SQLException {
        try (Connection dbConn = Database.connect()) {
            String sql = "INSERT INTO kategorijas(kategorijas_id, nosaukums) VALUES(?,?)";

            PreparedStatement pstmt = dbConn.prepareStatement(sql);

            pstmt.setInt(1, category.getId());
            pstmt.setString(2, category.getName());
            pstmt.executeUpdate();

        }
    }

    public void updateCategory(Category category) throws SQLException {
        try (Connection conn3 = Database.connect()) {
            String sql = "UPDATE kategorijas SET nosaukums=? WHERE kategorijas_id=?";
            PreparedStatement pstmt = conn3.prepareStatement(sql);

            pstmt.setString(1, category.getName());
            pstmt.setInt(2, category.getId());

            pstmt.executeUpdate();

        }
    }

    public void removeCategory(int Id) throws SQLException {
        try (Connection dbConn2 = Database.connect()) {
            String sql = "DELETE FROM kategorijas WHERE kategorijas_id=?";
            PreparedStatement pstmt = dbConn2.prepareStatement(sql);
            pstmt.setInt(1, Id);
            pstmt.executeUpdate();
        }
    }
}