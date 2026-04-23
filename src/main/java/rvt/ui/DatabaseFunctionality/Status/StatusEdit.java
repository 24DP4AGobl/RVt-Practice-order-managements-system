package rvt.ui.DatabaseFunctionality.Status;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.StatusService;
import rvt.model.Status;

public class StatusEdit extends JPanel{
        
    StatusService service = new StatusService();

    private Runnable onSave;

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    private JTextField nameField = field.size2();
    private int id;
    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public StatusEdit() {
        setLayout(new GridLayout(3, 2, 80, 20));

        add(text.text2("Nosaukums:"));
        add(nameField);

        cnfrmBtn.addActionListener(e -> {
            try {
                Status cat = new Status(id, nameField.getText());
                service.updateStatus(cat);

                if (onSave != null) {
                    onSave.run();
                }
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot kategoriju", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void loadStatus(int id) {
        this.id = id;

        try {
            Status oldInfo = service.getStatusById(id);

            nameField.setText(oldInfo.getName());

        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot kategoriju", e);
        }
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
