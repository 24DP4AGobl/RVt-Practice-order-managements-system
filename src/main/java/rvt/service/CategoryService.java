package rvt.service;

import java.sql.SQLException;
import java.util.List;

import rvt.dao.CategoryDAO;
import rvt.model.Category;

public class CategoryService {
    
    private CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAllCategories();
    }

    public void addCategory(Category category) throws SQLException {
        if (category.getName().isBlank()) {
            throw new IllegalArgumentException("Vārds ir obligāts");
        }

        categoryDAO.addCategory(category);
    }

    public void updateCategory(Category category) throws SQLException {
        if (category.getName().isBlank()) {
            throw new IllegalArgumentException("Vārds ir obligāts");
        }

        categoryDAO.updateCategory(category);
    }

    public void removeCategory(int id) throws SQLException {
        categoryDAO.removeCategory(id);
    }

    public Category getCategoryById(int Id) throws SQLException {
        return categoryDAO.getCategoryById(Id);
    }
}