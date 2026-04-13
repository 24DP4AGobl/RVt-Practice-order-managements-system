package rvt.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import rvt.model.Order;
import rvt.dao.OrderDAO;

public class OrderService {
    
    private OrderDAO orderDAO = new OrderDAO();

    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    public List<Order> getOrders(Integer statusId, Integer employeeId) throws SQLException {
        return orderDAO.getOrders(statusId, employeeId);
    }

    public void addOrder(Order order) throws SQLException {
            if (order.getDate() == null || order.getDate().isEmpty()) {
                throw new IllegalArgumentException("Datums ir obligāts");
            }

            if (order.getTotal() == null || order.getTotal().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Summa jābūt ≥ 0");
            }

            if (order.getAmount() <= 0) {
                throw new IllegalArgumentException("Daudzumam jābūt > 0");
            }

            if (order.getStatId() <= 0) {
                throw new IllegalArgumentException("Statuss nav izvēlēts");
            }

        orderDAO.addOrder(order);
    }

    public Order getOrderById(int id) throws SQLException {
        return orderDAO.getOrderById(id);
    }

    public void updateOrder(Order order) throws SQLException {
        if (order.getDate() == null || order.getDate().isEmpty()) {
           throw new IllegalArgumentException("Datums ir obligāts");
        }

        if (order.getTotal() == null || order.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Summa jābūt ≥ 0");
        }

        if (order.getAmount() <= 0) {
            throw new IllegalArgumentException("Daudzumam jābūt > 0");
        }

        if (order.getStatId() <= 0) {
            throw new IllegalArgumentException("Statuss nav izvēlēts");
        }

        orderDAO.updateOrder(order);
    }

    public void removeOrder(int id) throws SQLException {
        orderDAO.removeOrder(id);
    }
}