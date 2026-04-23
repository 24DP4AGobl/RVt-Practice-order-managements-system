package rvt.ui.DatabaseFunctionality.Deliverer;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.GridLayout;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.DelivererService;
import rvt.model.Deliverer;

public class DelivererEdit extends JPanel{
    
    DelivererService service = new DelivererService();

    private Runnable onSave;

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    private int id;
    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    private JTextField nameField = field.size2();
    private JTextField phoneField = field.size2();
    private JTextField emailField = field.size2();

    public DelivererEdit() {
        setLayout(new GridLayout(5, 2, 80, 20));

        add(text.text2("nosaukums:"));
        add(nameField);
        add(text.text2("talrunis:"));
        add(phoneField);
        add(text.text2("epasts:"));
        add(emailField);

        cnfrmBtn.addActionListener(e -> {
            try {

                //Deliverer oldInfo = service.getDelivererById(id);

                Deliverer deliverer = new Deliverer(id, nameField.getText(), phoneField.getText(), emailField.getText());
                service.updateDeliverer(deliverer);

                if (onSave != null) {
                    onSave.run();
                }
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot piegadataju", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void loadDeliverer(int id) {
        this.id = id;

        try {
            Deliverer oldInfo = service.getDelivererById(id);

            nameField.setText(oldInfo.getName());
            phoneField.setText(oldInfo.getPhone());
            emailField.setText(oldInfo.getEmail());
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot datus par darbinieku", e);
        }
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
