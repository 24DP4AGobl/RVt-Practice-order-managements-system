package rvt.service;

import java.sql.SQLException;
import java.util.List;

import rvt.dao.DelivererDAO;
import rvt.model.Deliverer;
import rvt.model.Employee;

public class DelivererService {
    
    private DelivererDAO delivererDAO = new DelivererDAO();

    public List<Deliverer> getAllDeliverers() throws SQLException {
        return delivererDAO.getAllDeliverers();
    }

    public void addDeliverer(Deliverer deliverer) throws SQLException {
        if (deliverer.getName().isBlank()) {
            throw new IllegalArgumentException("Vārds ir obligāts");
        }

        if (!deliverer.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Nepareizs epasts");
        }

        if (!deliverer.getPhone().matches("\\d{8}")) {
            throw new IllegalArgumentException("Tālrunim jābūt 8 cipariem");
        }

        delivererDAO.addDeliverer(deliverer);
    }

    public void updateDeliverer(Deliverer deliverer) throws SQLException {
        if (deliverer.getName().isBlank()) {
            throw new IllegalArgumentException("Vārds ir obligāts");
        }

        if (!deliverer.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Nepareizs epasts");
        }

        if (!deliverer.getPhone().matches("\\d{8}")) {
            throw new IllegalArgumentException("Tālrunim jābūt 8 cipariem");
        }

        delivererDAO.updateDeliverer(deliverer);
    }

    public void removeDeliverer(int id) throws SQLException {
        delivererDAO.removerDeliverer(id);
    }

    public Deliverer getDelivererById(int id) throws SQLException {
        return delivererDAO.getDelivererById(id);
    }
}