package rvt.ui.adminPanels;

import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import rvt.model.Deliverer;
import rvt.model.Product;
import rvt.model.Category;
import rvt.service.ProductService;
import rvt.service.CategoryService;
import rvt.service.DelivererService;
import rvt.ui.DatabaseFunctionality.Product.ProductEdit;
import rvt.ui.DatabaseFunctionality.Product.ProductAdd;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;
import rvt.util.TextFormatting;

public class ProductPanel extends JPanel {

    private JTable table;
    private ProductService service = new ProductService();
    private CategoryService catService = new CategoryService();
    private DelivererService delService = new DelivererService();

    JComboBox<Category> catBox = new JComboBox<>();
    JComboBox<Deliverer> delBox = new JComboBox<>();

    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();
    TextFormatting text = new TextFormatting();

    public ProductPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
        
        table = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Nosaukums", "Cena", "Kategorija", "Piegadatajs", "Daudzums"}, 0
        ));
        
        filterButtons();

        add(new JScrollPane(table));

        loadData();

        JPanel buttonPanel = new JPanel();

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);

        content.add(new JPanel(), "nothing");
        ProductAdd productAdd = new ProductAdd();
        productAdd.setOnSave(() -> {
            loadData();
            cardLayout.show(content, "nothing");
        });
        content.add(productAdd, "add");
        ProductEdit productEdit = new ProductEdit();
        productEdit.setOnSave(() -> {
            loadData();
            cardLayout.show(content, "nothing");
        });
        content.add(productEdit, "edit");

        JButton addBtn = btn.tableOption("Pievienot", color.button2());
        addBtn.addActionListener(e -> cardLayout.show(content, "add"));

        JButton editBtn = btn.tableOption("Rediģēt", color.editButton());
        editBtn.addActionListener(e -> {
            try {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    int id = (int) table.getModel().getValueAt(modelRow, 0);
        
                    productEdit.loadProduct(id);

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
        
                    service.removeProduct(id);
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

        try {
            for (Product o : products) {
                model.addRow(new Object[]{
                    o.getId(),
                    o.getName(),
                    o.getPrice(),
                    catService.getCategoryById(o.getCatId()),
                    delService.getDelivererById(o.getDelId()),
                    o.getAmount()
                });
            }
        } catch (Exception e) {
            ErrorHandler.showError("kļūda ielādējot tabulu", e);
        }
    }

    private void loadData() {
        try {
            List<Product> products = service.getAllProducts();
            updateTable(products);
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot datus", e);
        }
    }

    private void filterButtons() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(1, 6, 10, 10));

        filterPanel.add(text.text2("Filtrs: "));
        filterPanel.add(new JPanel());
        filterPanel.add(new JPanel());

        catBox.addActionListener(e -> {
            try {
                Category cCategory = (Category) catBox.getSelectedItem();
                int catId = cCategory.getId();

                List<Product> products = service.getProducts(catId, null);
                updateTable(products);
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda", ex);
            }
        });

        delBox.addActionListener(e -> {
            try {
                Deliverer dDeliverer = (Deliverer) delBox.getSelectedItem();
                int delId = dDeliverer.getId();

                List<Product> products = service.getProducts(null, delId);
                updateTable(products);
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda", ex);
            }
        });

        try {
            for (Category c : catService.getAllCategories()) {
                catBox.addItem(c);
            }
            for(Deliverer d : delService.getAllDeliverers()) {
                delBox.addItem(d);
            }
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }

        filterPanel.add(catBox);
        filterPanel.add(delBox);
        add(filterPanel);
    }
}
