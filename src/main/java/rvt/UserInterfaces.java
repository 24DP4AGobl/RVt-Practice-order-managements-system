package rvt;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserInterfaces extends ElementFormatting{
    private String currentEmployeeID;

    AdminInterface adminUI;
    EmployeeInterface employeeUI;

    public UserInterfaces() {
        adminUI = new AdminInterface();
        employeeUI = new EmployeeInterface();
    }

    /* ===================== USER SELECTION ===================== */
    public void userSelect() {
        JFrame frame = WindowFormat("Lietotāja izvēle", true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Izvēlieties lietotāju");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton darbinieki = buttonFormat("Darbinieki");
        JButton admin = buttonFormat("Administratori");

        darbinieki.addActionListener(e -> {
            currentEmployeeSelectionWindow();
            frame.dispose();
        });

        admin.addActionListener(e -> {
            JPasswordField pf = new JPasswordField();
            int ok = JOptionPane.showConfirmDialog(null, pf, "Ievadi paroli", JOptionPane.OK_CANCEL_OPTION);

            if (ok == JOptionPane.OK_OPTION) {
                String password = new String(pf.getPassword());
                if ("test".equals(password)) {
                    frame.dispose();
                    adminMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Nepareiza parole!");
                }
            }
        });

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(darbinieki);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(admin);

        frame.add(panel);
        frame.setVisible(true);
    }

    /* ===================== ADMIN MAIN MENU ===================== */
    public void adminMenu() {
        JFrame frame = WindowFormat("Pasūtījumu uzskaites sistēma", true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Pasūtījumu uzskaites sistēma(Admin panel)");
        title.setFont(new Font("Serif", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton UserReselection = buttonFormat("Mainīt lietotājus");
        UserReselection.addActionListener(e -> {userSelect(); frame.dispose();});

        JLabel warning = new JLabel("Brīdinājums");
        warning.setFont(new Font("Serif", Font.BOLD, 40));
        warning.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton productsBtn = buttonFormat("Produkti un krājumi");
        productsBtn.addActionListener(e -> adminUI.productWindow()); // Atver produktu logu

        JButton addEmployee = buttonFormat("Pievienot darbinieku");
        addEmployee.addActionListener(e -> adminUI.addEmployeeWindow());

        JButton removeEmployee = buttonFormat("Noņemt darbinieku");
        removeEmployee.addActionListener(e -> adminUI.employeeWindowFunctionality("remove"));

        JButton editEmployee = buttonFormat("Rediģēt darbinieku");
        editEmployee.addActionListener(e -> adminUI.employeeWindowFunctionality("edit"));

        JButton addDeliverer = buttonFormat("Pievienot piegādātāju");
        addDeliverer.addActionListener(e -> adminUI.delivererWindowFunctionality("add"));

        JButton removeDeliverer = buttonFormat("Noņemt piegādātāju");
        removeDeliverer.addActionListener(e -> adminUI.delivererWindowFunctionality("remove"));

        JButton editDeliverer = buttonFormat("Rediģēt piegādātāju");
        editDeliverer.addActionListener(e -> adminUI.delivererWindowFunctionality("edit"));

        JPanel warningContainer = new JPanel();
        warningContainer.add(warning);
        warningContainer.setBackground(Color.RED);

        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(productsBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(UserReselection);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(addEmployee);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(removeEmployee);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(editEmployee);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(addDeliverer);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(removeDeliverer);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(editDeliverer);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(warningContainer);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        frame.add(wrapper);
        frame.setVisible(true);
    }

    /* ===================== SELECT CURRENT EMPLOYEE ===================== */
    private void currentEmployeeSelectionWindow() {
        JFrame frame = WindowFormat("Darbinieki", false);

        DefaultListModel<String> model = new DefaultListModel<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM darbinieki")) {

            boolean empty = true;
            while (rs.next()) {
                empty = false;
                String text = rs.getInt("id") + " - " +
                        rs.getString("vards") + " " +
                        rs.getString("uzvards") + " (" +
                        rs.getString("amats") + ")";
                model.addElement(text);
            }

            if (empty) {
                JLabel label = new JLabel("Darbinieku nav :(");
                label.setFont(new Font("Serif", Font.BOLD, 25));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                frame.add(label);
            } else {
                JList<String> list = new JList<>(model);
                list.setFont(new Font("Arial", Font.PLAIN, 30));

                
                JButton selectBtn = new JButton("Izvēlēties");

                selectBtn.addActionListener(e -> {
                    if (!list.isSelectionEmpty()) {
                        frame.dispose();
                        mainMenu();

                        currentEmployeeID = list.getSelectedValue().split(" - ")[0];
                    }
                });

                frame.setLayout(new BorderLayout());
                frame.add(new JScrollPane(list), BorderLayout.CENTER);
                JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setPreferredSize(new Dimension(0, 50));
            cancelBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            cancelBtn.setBackground(new Color(224, 86, 76));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.addActionListener(e -> frame.dispose());

            selectBtn.setPreferredSize(new Dimension(0, 50));
            selectBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

            bottomPanel.add(selectBtn);
            bottomPanel.add(cancelBtn);

            frame.add(bottomPanel, BorderLayout.SOUTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    /* ===================== EMPLOYEE MAIN MENU ===================== */

    public void mainMenu() {
        JFrame frame = WindowFormat("Pasūtījumu uzskaites sistēma", true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Pasūtījumu uzskaites sistēma");
        title.setFont(new Font("Serif", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton manageOrders = buttonFormat("Pārvaldīt pasūtījumus");
        manageOrders.addActionListener(e -> employeeUI.orderWindow(currentEmployeeID));

        JButton products = buttonFormat("Apskatīt produktus");
        products.addActionListener(e -> employeeUI.productViewOnlyWindow());

        JButton UserReselection = buttonFormat("Mainīt lietotājus");
        UserReselection.addActionListener(e -> userSelect());

        JLabel warning = new JLabel("Brīdinājums");
        warning.setFont(new Font("Serif", Font.BOLD, 40));
        warning.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel warningContainer = new JPanel();
        warningContainer.add(warning);
        warningContainer.setBackground(Color.RED);

        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(manageOrders);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(products);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(UserReselection);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(warningContainer);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        frame.add(wrapper);
        frame.setVisible(true);
    }
}