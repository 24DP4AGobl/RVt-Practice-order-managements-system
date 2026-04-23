package rvt.ui.DatabaseFunctionality.Product;

import java.awt.GridLayout;
import java.math.BigDecimal;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.ProductService;
import rvt.service.CategoryService;
import rvt.service.DelivererService;

import rvt.model.Product;
import rvt.model.Category;
import rvt.model.Deliverer;

public class ProductAdd extends JPanel{
    
    private Runnable onSave;

    ProductService service = new ProductService();
    CategoryService catService = new CategoryService();
    DelivererService delService = new DelivererService();

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();
    
    private JTextField idField = field.size2();
    private JTextField nameField = field.size2();
    private JTextField priceField = field.size2();
    private JTextField quantityField = field.size2();

    JComboBox<Category> catBox = new JComboBox<>();
    JComboBox<Deliverer> delBox = new JComboBox<>();

    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public ProductAdd(){
        setLayout(new GridLayout(7, 2, 80, 20));

        try {
            for (Category c : catService.getAllCategories()) {
                catBox.addItem(c);
            }

            for (Deliverer d : delService.getAllDeliverers()) {
                delBox.addItem(d);
            }
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
        }

        add(text.text2("Id:"));
        add(idField);
        add(text.text2("nosaukums:"));
        add(nameField);
        add(text.text2("cena:"));
        add(priceField);
        add(text.text2("kategorija:"));
        add(catBox);
        add(text.text2("piegadatajs:"));
        add(delBox);
        add(text.text2("daudzums:"));
        add(quantityField);

        cnfrmBtn.addActionListener(e -> {
            try {
                Category cCategory = (Category) catBox.getSelectedItem();
                int catId = cCategory.getId();

                Deliverer dDeliverer = (Deliverer) delBox.getSelectedItem();
                int delId = dDeliverer.getId();

                Product product = new Product(
                                        Integer.valueOf(idField.getText()),
                                        nameField.getText(),
                                        new BigDecimal(priceField.getText()),
                                        catId,
                                        Integer.valueOf(quantityField.getText()),
                                        delId
                                    );
                service.addProduct(product);

                if (onSave != null) {
                    onSave.run();
                }

                clearFields();
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot produktu", ex);
            }
        });

        add(cnfrmBtn);
    } 

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");

        if (catBox.getItemCount() > 0) {
            catBox.setSelectedIndex(0);
        }

        if (delBox.getItemCount() > 0) {
            delBox.setSelectedIndex(0);
        }
    }
}
