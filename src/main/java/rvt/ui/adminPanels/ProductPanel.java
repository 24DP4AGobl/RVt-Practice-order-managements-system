package rvt.ui.adminPanels;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import rvt.model.Product;
import rvt.service.ProductService;
import rvt.util.ErrorHandler;

public class ProductPanel extends JPanel {

    private JTable table;
    private ProductService service = new ProductService();

    public ProductPanel() {
        table = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Nosaukums", "Cena", "Kategorija", "Daudzums", "Piegadatajs"}, 0
        ));

        add(new JScrollPane(table));

        loadData(); // 🔥 initial load
    }

    private void updateTable(List<Product> products) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Product o : products) {
            model.addRow(new Object[]{
                o.getId(),
                o.getName(),
                o.getPrice(),
                o.getCatId(),
                o.getAmount(),
                o.getDelId()
            });
        }
    }

    private void loadData() {
        try {
            List<Product> orders = service.getAllProducts();
            updateTable(orders);
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }
    } 
}
