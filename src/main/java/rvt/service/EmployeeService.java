package rvt.service;

import java.sql.SQLException;
import java.util.List;

import rvt.dao.EmployeeDAO;
import rvt.model.Employee;

public class EmployeeService {
    
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    public void addEmployee(Employee employee) throws SQLException {
        if (employee.getName().isBlank()) {
            throw new IllegalArgumentException("Vārds ir obligāts");
        }

        if (employee.getSurname().isBlank()) {
            throw new IllegalArgumentException("Uzvārds ir obligāts");
        }

        if (Character.isUpperCase(employee.getRole().charAt(0))) {
            employee.setRole(employee.getRole().toLowerCase());
        }

        employeeDAO.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) throws SQLException {
        if (employee.getName().isBlank()) {
            throw new IllegalArgumentException("Vārds ir obligāts");
        }

        if (employee.getSurname().isBlank()) {
            throw new IllegalArgumentException("Uzvārds ir obligāts");
        }

        if (Character.isUpperCase(employee.getRole().charAt(0))) {
            employee.setRole(employee.getRole().toLowerCase());
        }

        employeeDAO.updateEmployee(employee);
    }

    public void removeEmployee(int id) throws SQLException {
        employeeDAO.removeEmployee(id);
    }

    public Employee login(String username, String password) throws SQLException {
        Employee emp = employeeDAO.findByUsername(username);

        if (emp == null) return null;

        if (!emp.getPassword().equals(password)) return null;

        return emp;
    }

    public boolean isAdmin(String username) throws SQLException {
        Employee emp = employeeDAO.findByUsername(username);

        return emp.getRole().equals("admin");
    }

    public Employee getEmployeeById(int id) throws SQLException {
        return employeeDAO.getEmployeeById(id);
    }

    public List<Employee> getEmployeesByRole(String role) throws SQLException {
        return employeeDAO.getEmployeesByRole(role);
    }
}