package rvt.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import rvt.dao.ProductDAO;
import rvt.model.Product;

public class ProductService {
    
    private ProductDAO productDAO = new ProductDAO();

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public void addProduct(Product product) throws SQLException {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Nosaukums ir obligāts");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cena jābūt > 0");
        }

        if (product.getAmount() < 0) {
            throw new IllegalArgumentException("Daudzums nevar būt negatīvs");
        }

        if (product.getCatId() == 0) {
            throw new IllegalArgumentException("Izvēlieties kategoriju");
        }

        productDAO.addProduct(product);
    }

    public void updateProduct(Product product) throws SQLException {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Nosaukums ir obligāts");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cena jābūt > 0");
        }

        if (product.getAmount() < 0) {
            throw new IllegalArgumentException("Daudzums nevar būt negatīvs");
        }

        if (product.getCatId() == 0) {
            throw new IllegalArgumentException("Izvēlieties kategoriju");
        }

        productDAO.updateProduct(product);
    }

    public void removeProduct(int id) throws SQLException {
        productDAO.removeProduct(id);
    }

    public Product getProductById(int id) throws SQLException {
        return productDAO.getProductById(id);
    }

    public List<Product> getProducts(Integer catId, Integer delId) throws SQLException{
        return productDAO.getProducts(catId, delId);
    }
}