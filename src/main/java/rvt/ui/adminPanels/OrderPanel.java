package rvt.ui.adminPanels;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import rvt.model.Order;
import rvt.service.OrderService;
import rvt.util.ErrorHandler;

public class OrderPanel extends JPanel {
    
    private JTable table;
    private OrderService service = new OrderService();

    public OrderPanel() {
        table = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Datums", "Summa", "Statuss"}, 0
        ));

        add(new JScrollPane(table));

        loadData(); // 🔥 initial load
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
