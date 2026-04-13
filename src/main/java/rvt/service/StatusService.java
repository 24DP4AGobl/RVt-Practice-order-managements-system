package rvt.service;

import java.sql.SQLException;
import java.util.List;

import rvt.dao.StatusDAO;
import rvt.model.Status;

public class StatusService {
    
    private StatusDAO statusDAO = new StatusDAO();

    public List<Status> getAllStatuses() throws SQLException {
        return statusDAO.getAllStatuses();
    }

    public void addStatus(Status status) throws SQLException {
        if (status.getName().isBlank()) {
            throw new IllegalArgumentException("Nosaukums ir obligāts");
        }

        statusDAO.addStatus(status);
    }

    public void updateStatus(Status status) throws SQLException {
        if (status.getName().isBlank()) {
            throw new IllegalArgumentException("Nosaukums ir obligāts");
        }

        statusDAO.updateStatus(status);
    }

    public void removeStatus(int id) throws SQLException {
        statusDAO.removeStatus(id);
    }

    public Status getStatusById(int id) throws SQLException {
        return statusDAO.getStatusById(id);
    }
}