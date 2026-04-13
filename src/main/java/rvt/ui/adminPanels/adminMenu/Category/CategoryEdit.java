package rvt.ui.adminPanels.adminMenu.Category;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.CategoryService;
import rvt.model.Category;

public class CategoryEdit extends JPanel{
        
    CategoryService service = new CategoryService();

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    private JTextField nameField = field.size2();
    private int id;
    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public CategoryEdit() {
        setLayout(new GridLayout(3, 2, 80, 20));

        add(text.text2("Nosaukums:"));
        add(nameField);

        cnfrmBtn.addActionListener(e -> {
            try {
                Category cat = new Category(id, nameField.getText());
                service.updateCategory(cat);
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot kategoriju", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void setCategoryId(int id) {
        this.id = id;
    }
}
