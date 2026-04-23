package rvt.ui.DatabaseFunctionality.Status;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.StatusService;
import rvt.model.Status;

public class StatusAdd extends JPanel {
    
    StatusService service = new StatusService();

    private Runnable onSave;

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    private JTextField idField = field.size2();
    private JTextField nameField = field.size2();
    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public StatusAdd(){
        setLayout(new GridLayout(3, 2, 80, 20));

        add(text.text2("Id:"));
        add(idField);
        add(text.text2("Nosaukums:"));
        add(nameField);

        cnfrmBtn.addActionListener(e -> {
            try {
                Status status = new Status(Integer.valueOf(idField.getText()), nameField.getText());
                service.addStatus(status);

                if (onSave != null) {
                    onSave.run();
                }
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
