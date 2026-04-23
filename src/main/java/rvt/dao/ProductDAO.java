package rvt.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import rvt.model.Order;
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
                                        rs.getInt("kategorijas_id"),
                                        rs.getInt("daudzums"),
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

    public Product getProductById(int Id) throws SQLException {
        String sql = "SELECT * FROM produkti WHERE produkta_id = ?";

        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Product(
                    rs.getInt("produkta_id"),
                    rs.getString("nosaukums"),
                    new BigDecimal(rs.getString("cena")),
                    rs.getInt("kategorijas_id"),
                    rs.getInt("daudzums"),
                    rs.getInt("piegadatajs_id")
                );
            }
        }

        return null; // not found
    }

    public List<Product> getProducts(Integer catId, Integer delId) throws SQLException {

        List<Product> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM produkti WHERE 1=1");

        if (catId != null) {
            sql.append(" AND kategorijas_id = ?");
        }

        if (delId != null) {
            sql.append(" AND piegadatajs_id = ?");
        }

        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if (catId != null) {
                pstmt.setInt(index++, catId);
            }

            if (delId != null) {
                pstmt.setInt(index++, delId);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getInt("produkta_id"),
                                rs.getString("nosaukums"),
                                new BigDecimal(rs.getString("cena")),
                                rs.getInt("kategorijas_id"),
                                rs.getInt("daudzums"),
                                rs.getInt("piegadatajs_id")
                                );
                products.add(product);
            }
        }

        return products;
    }
}