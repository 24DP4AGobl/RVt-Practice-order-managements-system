package rvt.ui.adminPanels;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import rvt.model.Product;
import rvt.service.ProductService;
import rvt.ui.DatabaseFunctionality.Category.CategoryAdd;
import rvt.ui.DatabaseFunctionality.Category.CategoryEdit;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

public class ProductPanel extends JPanel {

    private JTable table;
    private ProductService service = new ProductService();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    public ProductPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));

        table = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Nosaukums", "Cena", "Kategorija", "Daudzums", "Piegadatajs"}, 0
        ));

        add(new JScrollPane(table));

        loadData();

        JPanel buttonPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);

        content.add(new JPanel(), "nothing");
        content.add(new CategoryAdd(), "add");
        CategoryEdit categoryEdit = new CategoryEdit();
        content.add(categoryEdit, "edit");

        JButton addBtn = btn.tableOption("Pievienot", color.button2());
        addBtn.addActionListener(e -> cardLayout.show(content, "add"));

        JButton editBtn = btn.tableOption("Rediģēt", color.editButton());
        editBtn.addActionListener(e -> {
            try {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    int id = (int) table.getModel().getValueAt(modelRow, 0);
        
                    categoryEdit.setCategoryId(id); 

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
        
                    //service.removeCategory(id);
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
