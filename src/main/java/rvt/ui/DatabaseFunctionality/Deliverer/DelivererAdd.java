package rvt.ui.DatabaseFunctionality.Deliverer;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.DelivererService;
import rvt.model.Deliverer;

public class DelivererAdd extends JPanel{

    DelivererService service = new DelivererService();

    private Runnable onSave;

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();
    
    private JTextField idField = field.size2();
    private JTextField nameField = field.size2();
    private JTextField phoneField = field.size2();
    private JTextField emailField = field.size2();

    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public DelivererAdd(){
        setLayout(new GridLayout(8, 2, 80, 20));

        add(text.text2("Id:"));
        add(idField);
        add(text.text2("nosaukums:"));
        add(nameField);
        add(text.text2("tālrunis:"));
        add(phoneField);
        add(text.text2("epasts:"));
        add(emailField);

        cnfrmBtn.addActionListener(e -> {
            try {

                Deliverer deliverer = new Deliverer(Integer.valueOf(idField.getText()),
                                        nameField.getText(),
                                        phoneField.getText(),
                                        emailField.getText()
                                    );
                service.addDeliverer(deliverer);
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot kategoriju", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
