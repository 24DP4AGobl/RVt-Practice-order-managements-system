package rvt.ui.DatabaseFunctionality.Employee;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.GridLayout;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.EmployeeService;
import rvt.model.Employee;

public class EmployeeEdit extends JPanel{

    EmployeeService service = new EmployeeService();

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    private Runnable onSave;

    private int id;

    private JTextField positionField = field.size2();
    private JTextField usernameField = field.size2();
    private JTextField passwordField = field.size2();
    private JTextField roleField = field.size2();

    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public EmployeeEdit() {
        setLayout(new GridLayout(5, 2, 80, 20));

        add(text.text2("amats:"));
        add(positionField);
        add(text.text2("lietotajvards:"));
        add(usernameField);
        add(text.text2("parole:"));
        add(passwordField);
        add(text.text2("loma:"));
        add(roleField);

        cnfrmBtn.addActionListener(e -> {
            try {

                Employee oldInfo = service.getEmployeeById(id);

                Employee employee = new Employee(id, oldInfo.getName(), oldInfo.getSurname(), positionField.getText(), usernameField.getText(), passwordField.getText(), roleField.getText());
                service.updateEmployee(employee);

                if (onSave != null) {
                    onSave.run();
                }
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot darbinieku", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void loadEmployee(int id) {
        this.id = id;

        try {
            Employee oldInfo = service.getEmployeeById(id);

            positionField.setText(oldInfo.getPosition());
            usernameField.setText(oldInfo.getUsername());
            passwordField.setText(oldInfo.getPassword());
            roleField.setText(oldInfo.getRole());

        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot produktu", e);
        }
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
