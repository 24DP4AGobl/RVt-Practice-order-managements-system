package rvt.ui;

import javax.swing.*;

import rvt.model.Employee;
import rvt.service.EmployeeService;
import rvt.util.*;

import java.awt.*;

public class LoginInterface extends JFrame{

    JTextField usernameField;
    JPasswordField passwordField;
    EmployeeService service = new EmployeeService();
    UIColors color = new UIColors();
    ButtonFormatting btnFormat = new ButtonFormatting();
    TextFormatting textFormat = new TextFormatting();
    FieldFormatting fieldFormat = new FieldFormatting();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension screenSizeFormated = new Dimension((int)Math.round(screenSize.getWidth()/1.5), (int)Math.round(screenSize.getHeight()/1.5));
    Dimension panelSize = new Dimension((int)Math.round(screenSize.getWidth()/3), (int)Math.round(screenSize.getHeight()/3));

    public LoginInterface(){
        setTitle("Pasutijumu sistēma --> Login");
        getContentPane().setBackground(color.loginBakground());
        setSize(screenSizeFormated);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel centerPanel = new RoundedPanel(color.panelBackground(), 50);
        centerPanel.setPreferredSize(panelSize);
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createLineBorder(color.panelBackground(), 50, true));

        usernameField = fieldFormat.size2();
        passwordField = fieldFormat.passize2();
        JButton loginBtn = btnFormat.loginButton();

        passwordField.addActionListener(e -> login());
        loginBtn.addActionListener(e -> login());

        centerPanel.setLayout(new GridLayout(3, 2, (int)screenSize.getWidth()/80, (int)screenSize.getHeight()/20));
        centerPanel.add(textFormat.text2("Username:"));
        centerPanel.add(usernameField);
        centerPanel.add(textFormat.text2("Password:"));
        centerPanel.add(passwordField);
        centerPanel.add(loginBtn);

        add(centerPanel);

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword()); 

        try {
            Employee emp = service.login(username, password);

            if (emp != null) {
                dispose(); // close login
                new MainFrame(emp); // open main UI
            } else {
                ErrorHandler.showError("Nepareizs lietotājvārds vai parole", new Exception());
            }
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda pieslēdzoties", e);
        }
    }
}
