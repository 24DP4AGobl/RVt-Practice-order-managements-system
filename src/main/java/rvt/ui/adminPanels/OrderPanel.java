package rvt.ui.adminPanels;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import rvt.model.Order;
import rvt.service.OrderService;
import rvt.service.StatusService;
import rvt.model.Status;
import rvt.ui.DatabaseFunctionality.Order.OrderAdd;
import rvt.ui.DatabaseFunctionality.Order.OrderEdit;

import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;
import rvt.util.TextFormatting;

public class OrderPanel extends JPanel {
    
    private JTable table;

    private OrderService service = new OrderService();
    StatusService statService = new StatusService();

    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();
    TextFormatting text = new TextFormatting();

    public OrderPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
        table = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Datums", "Summa", "Statuss"}, 0
        ));

        filterButtons();

        add(new JScrollPane(table));

        loadData();

        JPanel buttonPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);

        content.add(new JPanel(), "nothing");
        OrderAdd add = new OrderAdd();
        add.setOnSave(() -> {
            loadData();
            cardLayout.show(content, "nothing");
        });
        content.add(add, "add");

        OrderEdit edit = new OrderEdit();
        edit.setOnSave(() -> {
            loadData();
            cardLayout.show(content, "nothing");
        });
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
        
                    edit.loadOrder(id);

                    cardLayout.show(content, "edit");
                    System.out.println("Selected ID: " + id);
                } else {
                    System.out.println("No row selected");
                }

                loadData();
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda dzēšot", ex);
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
        
                    service.removeOrder(id);
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

        JButton reloadBtn = btn.tableOption("pārlādēt", color.button2());
        reloadBtn.addActionListener(e -> loadData());

        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(reloadBtn);
        buttonPanel.add(removeBtn);

        add(buttonPanel);
        add(content);
    }

    private void updateTable(List<Order> orders) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

                for (Order o : orders) {
                    model.addRow(new Object[]{
                        o.getId(),
                        o.getDate(),
                        o.getTotal(),
                        statService.getStatusById(o.getStatId())
                    });
                }
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }
    }

    private void loadData() {
        try {
            List<Order> orders = service.getAllOrders();
            updateTable(orders);
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }
    }

    private void filterButtons() {
        JPanel filterPanel = new JPanel(new GridLayout(1, 3, 20, 10));

        JComboBox<Status> statusBox = new JComboBox<>();
        JButton resetButton = btn.tableOption("Atcelt", color.editButton());

        statusBox.addActionListener(e -> {
            try {
                Status sStatus = (Status) statusBox.getSelectedItem();
                int StatusId = sStatus.getId();

                List<Order> orders = service.getOrders(StatusId, null);
                updateTable(orders);
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda", ex);
            }
        });

        resetButton.addActionListener(e -> {
            loadData();
        });

        try {
            for (Status s : statService.getAllStatuses()) {
                statusBox.addItem(s);
            }
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }

        filterPanel.add(text.text2("Filtrēt pēc:"));
        filterPanel.add(resetButton);
        filterPanel.add(statusBox);
        add(filterPanel);
    }
}
