package rvt.ui.adminPanels;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import rvt.model.Employee;
import rvt.service.EmployeeService;
import rvt.util.ErrorHandler;

public class EmployeePanel extends JPanel {
    
    private JTable table;
    private EmployeeService service = new EmployeeService();

    public EmployeePanel() {
        table = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Vards", "Uzvards", "Amats"}, 0
        ));

        add(new JScrollPane(table));

        loadData(); // 🔥 initial load
    }

    private void updateTable(List<Employee> employees) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);

        for (Employee o : employees) {
            model.addRow(new Object[]{
                o.getId(),
                o.getName(),
                o.getSurname(),
                o.getPosition()
            });
        }
    }

    private void loadData() {
        try {
            List<Employee> orders = service.getAllEmployees();
            updateTable(orders);
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }
    }
}
