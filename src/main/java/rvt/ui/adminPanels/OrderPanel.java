package rvt.ui.adminPanels;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import rvt.model.Order;
import rvt.service.OrderService;
import rvt.ui.DatabaseFunctionality.Order.OrderAdd;
import rvt.ui.DatabaseFunctionality.Order.OrderEdit;

import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

public class OrderPanel extends JPanel {
    
    private JTable table;
    private OrderService service = new OrderService();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    public OrderPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
        table = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Datums", "Summa", "Statuss"}, 0
        ));

        add(new JScrollPane(table));

        loadData();

        JPanel buttonPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);

        content.add(new JPanel(), "nothing");
        content.add(new OrderAdd(), "add");
        OrderEdit edit = new OrderEdit();
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
        
                    edit.setOrderId(id); 

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
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);

        for (Order o : orders) {
            model.addRow(new Object[]{
                o.getId(),
                o.getDate(),
                o.getTotal(),
                o.getStatId()
            });
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
}
