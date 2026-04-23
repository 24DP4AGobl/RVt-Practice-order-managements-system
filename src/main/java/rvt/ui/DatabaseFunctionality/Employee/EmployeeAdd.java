package rvt.ui.DatabaseFunctionality.Employee;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import rvt.util.FieldFormatting;
import rvt.util.TextFormatting;
import rvt.util.ButtonFormatting;
import rvt.util.ErrorHandler;
import rvt.util.UIColors;

import rvt.service.EmployeeService;
import rvt.model.Employee;

public class EmployeeAdd extends JPanel{
    
    EmployeeService service = new EmployeeService();

    private Runnable onSave;

    TextFormatting text = new TextFormatting();
    FieldFormatting field = new FieldFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();
    
    private JTextField idField = field.size2();
    private JTextField nameField = field.size2();
    private JTextField surnameField = field.size2();
    private JTextField positionField = field.size2();
    private JTextField usernameField = field.size2();
    private JTextField passwordField = field.size2();
    private JTextField roleField = field.size2();

    private JButton cnfrmBtn = btn.tableOption("Akceptēt", color.button2());

    public EmployeeAdd(){
        setLayout(new GridLayout(8, 2, 80, 20));

        add(text.text2("Id:"));
        add(idField);
        add(text.text2("vārds:"));
        add(nameField);
        add(text.text2("uzvārds:"));
        add(surnameField);
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

                Employee employee = new Employee(Integer.valueOf(idField.getText()),
                                        nameField.getText(),
                                        surnameField.getText(),
                                        positionField.getText(),
                                        usernameField.getText(),
                                        passwordField.getText(),
                                        roleField.getText()
                                    );
                service.addEmployee(employee);

                if (onSave != null) {
                    onSave.run();
                }
            } catch (Exception ex) {
                ErrorHandler.showError("Kļūda saglabājot darbinieku", ex);
            }
        });

        add(cnfrmBtn);
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }
}
