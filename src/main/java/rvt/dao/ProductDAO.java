package rvt.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import rvt.model.Product;

public class ProductDAO {
    
    public List<Product> getAllProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM produkti")) {

            while (rs.next()) {
                Product product = new Product(rs.getInt("produkta_id"),
                                        rs.getString("nosaukums"),
                                        new BigDecimal(rs.getString("cena")),
                                        rs.getInt("daudzums"),
                                        rs.getInt("kategorijas_id"),
                                        rs.getInt("piegadatajs_id")
                                        );
                products.add(product);
            }
        }

        return products;
    }

    public void addProduct(Product product) throws SQLException {
        try (Connection dbConn = Database.connect()) {
            String sql = "INSERT INTO produkti(produkta_id, nosaukums, cena, daudzums, kategorijas_id, piegadatajs_id) VALUES(?,?,?,?,?,?)";

            PreparedStatement pstmt = dbConn.prepareStatement(sql);

            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setBigDecimal(3, product.getPrice());
            pstmt.setInt(4, product.getAmount());
            pstmt.setInt(5, product.getCatId());
            pstmt.setInt(6, product.getDelId());
            pstmt.executeUpdate();

        }
    }

    public void updateProduct(Product product) throws SQLException {
        try (Connection conn3 = Database.connect()) {
            String sql = "UPDATE produkti SET nosaukums=?, cena=?, daudzums=?, kategorijas_id=?, piegadatajs_id=? WHERE produkta_id=?";
            PreparedStatement pstmt = conn3.prepareStatement(sql);

            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());
            pstmt.setInt(3, product.getAmount());
            pstmt.setInt(4, product.getCatId());
            pstmt.setInt(5, product.getDelId());
             pstmt.setInt(6, product.getId());

            pstmt.executeUpdate();

        }
    }

    public void removeProduct(int Id) throws SQLException {
        try (Connection dbConn2 = Database.connect()) {
            String sql = "DELETE FROM produkti WHERE produkta_id=?";
            PreparedStatement pstmt = dbConn2.prepareStatement(sql);
            pstmt.setInt(1, Id);
            pstmt.executeUpdate();
        }
    }
}