package rvt.ui.DatabaseFunctionality.Product;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.GridLayout;
import java.math.BigDecimal;

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

public class ProductEdit extends JPanel{
            
    ProductService service = new ProductService();
    CategoryService catService = new CategoryService();
    DelivererService delService = new DelivererService();
    
    private Runnable onSave;

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    private int id;

    private JTextField nameField = field.size2();
    private JTextField priceField = field.size2();
    private JTextField quantityField = field.size2();
    JComboBox<Category> catBox = new JComboBox<>();
    JComboBox<Deliverer> delBox = new JComboBox<>();

    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public ProductEdit() {
        setLayout(new GridLayout(6, 2, 80, 20));

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

        add(text.text2("Nosaukums:"));
        add(nameField);
        add(text.text2("Cena:"));
        add(priceField);
        add(text.text2("Kategorija:"));
        add(catBox);
        add(text.text2("Piegādātājs:"));
        add(delBox);
        add(text.text2("Daudzums:"));
        add(quantityField);

        cnfrmBtn.addActionListener(e -> {
            try {
                Category cCategory = (Category) catBox.getSelectedItem();
                Deliverer dDeliverer = (Deliverer) delBox.getSelectedItem();

                int catId = cCategory.getId();
                int delId = dDeliverer.getId();

                Product product = new Product(id,
                                nameField.getText(),
                                new BigDecimal(priceField.getText()),
                                catId,
                                Integer.valueOf(quantityField.getText()),
                                delId
                            );
                service.updateProduct(product);

                if (onSave != null) {
                    onSave.run();
                }
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot pasūtījumu", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void loadProduct(int id) {
        this.id = id;

        try {
            Product oldInfo = service.getProductById(id);

            nameField.setText(oldInfo.getName());
            priceField.setText(oldInfo.getPrice().toString());
            quantityField.setText(String.valueOf(oldInfo.getAmount()));

            for (int i = 0; i < catBox.getItemCount(); i++) {
                if (catBox.getItemAt(i).getId() == oldInfo.getCatId()) {
                    catBox.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < delBox.getItemCount(); i++) {
                if (delBox.getItemAt(i).getId() == oldInfo.getDelId()) {
                    delBox.setSelectedIndex(i);
                    break;
                }
            }

        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot produktu", e);
        }
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
