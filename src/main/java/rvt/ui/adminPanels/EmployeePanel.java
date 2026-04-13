package rvt.ui.adminPanels;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import rvt.model.Employee;
import rvt.model.Order;
import rvt.service.EmployeeService;
import rvt.ui.DatabaseFunctionality.Employee.EmployeeAdd;
import rvt.ui.DatabaseFunctionality.Employee.EmployeeEdit;

import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.TextFormatting;
import rvt.util.UIColors;

public class EmployeePanel extends JPanel {

    private JTable table;
    private EmployeeService service = new EmployeeService();
    
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();
    TextFormatting text = new TextFormatting();

    public EmployeePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));

        table = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Vārds", "Uzvārds", "Lietotājvārds", "Loma"}, 0
        ));

        filterButtons();

        add(new JScrollPane(table));

        loadData();

        JPanel buttonPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);

        content.add(new JPanel(), "nothing");
        content.add(new EmployeeAdd(), "add");
        EmployeeEdit edit = new EmployeeEdit();
        content.add(edit, "edit");

        JButton addBtn = btn.tableOption("Pievienot", color.button2());
        addBtn.addActionListener(e -> cardLayout.show(content, "add"));

        
        JButton editBtn = btn.tableOption("Rediģēt", color.editButton());
        editBtn.addActionListener(e -> {
            try {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    int id = (int) table.getModel().getValueAt(modelRow, 0);
        
                    edit.setEmployeeId(id); 

                    cardLayout.show(content, "edit");
                    System.out.println("Selected ID: " + id);
                } else {
                    System.out.println("No row selected");
                }

                loadData();
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda Rediģējot", ex);
            }
        });

        JButton cancelBtn = btn.tableOption("Atcelt", color.editButton());
        

        JButton removeBtn = btn.tableOption("Noņemt", color.removeButton());

        removeBtn.addActionListener(e -> {
            try {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    int id = (int) table.getModel().getValueAt(modelRow, 0);
        
                    service.removeEmployee(id);
                    System.out.println("Selected ID: " + id);
                } else {
                    System.out.println("No row selected");
                }

                loadData();
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda dzēšot", ex);
            }
        });
        cancelBtn.addActionListener(e -> cardLayout.show(content, "nothing"));

        JButton reloadBtn = btn.tableOption("Pārlādēt", color.button2());
        reloadBtn.addActionListener(e -> loadData());

        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(reloadBtn);
        buttonPanel.add(removeBtn);

        add(buttonPanel);
        add(content);
    }

    private void updateTable(List<Employee> employees) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Employee e : employees) {
            model.addRow(new Object[]{
                e.getId(),
                e.getName(),
                e.getSurname(),
                e.getUsername(),
                e.getRole()
            });
        }
    }

    private void loadData() {
        try {
            List<Employee> employees = service.getAllEmployees();
            updateTable(employees);
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }
    }

    private void filterButtons() {
        JPanel filterPanel = new JPanel(new GridLayout(1, 3, 20, 10));

        JTextField roleField = new JTextField();
        JButton buttonField = btn.tableOption("Meklēt", color.editButton());
        JButton resetButton = btn.tableOption("Atcelt", color.editButton());

        buttonField.addActionListener(e -> {
            try {
                String role = roleField.getText();

                List<Employee> employee = service.getEmployeesByRole(role);
                updateTable(employee);
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda", ex);
            }
        });

        resetButton.addActionListener(e -> {
            loadData();
        });

        filterPanel.add(text.text2("Filtrēt pēc:"));
        filterPanel.add(resetButton);
        filterPanel.add(roleField);
        filterPanel.add(buttonField);
        add(filterPanel);
    }
}
