package rvt;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserInterfaces {

    String currentEmployeeID;

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
            frame.dispose();
            selectEmployee();
        });

        admin.addActionListener(e -> {
            JPasswordField pf = new JPasswordField();
            int ok = JOptionPane.showConfirmDialog(null, pf, "Ievadi paroli", JOptionPane.OK_CANCEL_OPTION);

            if (ok == JOptionPane.OK_OPTION) {
                String password = new String(pf.getPassword());
                if ("test".equals(password)) {
                    frame.dispose();
                    mainMenu(true);
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

    private void selectEmployee() {
        JFrame frame = WindowFormat("Darbinieki", true);

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
                JButton selectBtn = new JButton("Izvēlēties");

                selectBtn.addActionListener(e -> {
                    if (!list.isSelectionEmpty()) {
                        frame.dispose();
                        mainMenu(false);

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

    /* ===================== MAIN MENU ===================== */

    public void mainMenu(boolean isAdmin) {
        JFrame frame = WindowFormat("Pasūtījumu uzskaites sistēma", true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Pasūtījumu uzskaites sistēma");
        title.setFont(new Font("Serif", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton addOrder = buttonFormat("Pievienot pasūtījumu");
        addOrder.addActionListener(e -> windowAddOrder());

        JButton viewOrders = buttonFormat("Apskatīt visus pasūtījumus");
        JButton editOrders = buttonFormat("Rediģēt pasūtījumus");
        JButton products = buttonFormat("Apskatīt produktus");
        JButton UserReselection = buttonFormat("Mainīt lietotājus");
        UserReselection.addActionListener(e -> {userSelect(); frame.dispose();});

        JLabel warning = new JLabel("Brīdinājums");
        warning.setFont(new Font("Serif", Font.BOLD, 40));
        warning.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel warningContainer = new JPanel();
        warningContainer.add(warning);
        warningContainer.setBackground(Color.RED);

        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(addOrder);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(viewOrders);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(editOrders);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(products);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(UserReselection);


        if (isAdmin) {
            JButton addEmployee = buttonFormat("Pievienot darbinieku");
            addEmployee.addActionListener(e -> addEmployeeWindow());

            JButton removeEmployee = buttonFormat("Noņemt darbinieku");
            removeEmployee.addActionListener(e -> removeEmployeeWindow());

            JButton editEmployee = buttonFormat("Rediģēt darbinieku");
            editEmployee.addActionListener(e -> editEmployeeWindow());

            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(addEmployee);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(removeEmployee);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(editEmployee);
        }

        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(warningContainer);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        frame.add(wrapper);
        frame.setVisible(true);
    }

    /* ===================== ADD AN ORDER WINDOW ===================== */

    private void windowAddOrder() {
        JFrame frame = WindowFormat("Pievienot pasūtījumu", false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Pievienojiet jaunu pasūtījumu");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputGroup = new JPanel();
        inputGroup.setLayout(new BoxLayout(inputGroup, BoxLayout.Y_AXIS));

        inputGroup.add(inputGroup("Pasutijuma_id"));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));
        inputGroup.add(inputGroup("Datums"));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));
        inputGroup.add(inputGroup("Summa"));

        String[] statusOptions = {"Izpildīts", "Procesā"};
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));
        inputGroup.add(comboBoxGroup("Statuss", statusOptions));

        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));
        inputGroup.add(comboBoxGroup("Piegadataja_id", new String[]{""}));

        JPanel confirmGroup = confirmCancelGroup("Cancel", frame);

        inputGroup.add(Box.createRigidArea(new Dimension(0, 30)));
        inputGroup.add(confirmGroup);

        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(inputGroup);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        frame.add(wrapper);
        frame.setVisible(true);
    }

    /* ===================== ADMIN FUNCTIONS ===================== */

    private void addEmployeeWindow() {
        JTextField idField = new JTextField();
        JTextField vardsField = new JTextField();
        JTextField uzvardsField = new JTextField();
        JTextField amatsField = new JTextField();

        Object[] fields = {
                "ID:", idField,
                "Vārds:", vardsField,
                "Uzvārds:", uzvardsField,
                "Amats:", amatsField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Jauns darbinieks", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try (Connection dbConn = Database.connect()) {
                String sql = "INSERT INTO darbinieki(id, vards, uzvards, amats) VALUES(?,?,?,?)";
                PreparedStatement pstmt = dbConn.prepareStatement(sql);

                pstmt.setInt(1, Integer.parseInt(idField.getText()));
                pstmt.setString(2, vardsField.getText());
                pstmt.setString(3, uzvardsField.getText());
                pstmt.setString(4, amatsField.getText());
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Saglabāts!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ID jau tiek izmantots, vai ir ievadīts!");
            }
        }
    }

    private void removeEmployeeWindow() {
        JFrame frame = WindowFormat("Noņemt darbinieku", false);
        DefaultListModel<String> model = new DefaultListModel<>();

        try (Connection dbConn = Database.connect();
             Statement stmt = dbConn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM darbinieki")) {

            while (rs.next()) {
                String text = rs.getInt("id") + " - " +
                        rs.getString("vards") + " " +
                        rs.getString("uzvards") + " (" +
                        rs.getString("amats") + ")";
                model.addElement(text);
            }

            JList<String> list = new JList<>(model);
            JButton removeBtn = new JButton("Noņemt");

            removeBtn.addActionListener(e -> {
                int index = list.getSelectedIndex();
                if (index != -1) {
                    String selected = list.getSelectedValue();
                    int id = Integer.parseInt(selected.split(" - ")[0]);

                    try (Connection dbConn2 = Database.connect()) {
                        String sql = "DELETE FROM darbinieki WHERE id=?";
                        PreparedStatement pstmt = dbConn2.prepareStatement(sql);
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();
                        model.remove(index);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
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

            removeBtn.setPreferredSize(new Dimension(0, 50));
            removeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

            bottomPanel.add(removeBtn);
            bottomPanel.add(cancelBtn);

            frame.add(bottomPanel, BorderLayout.SOUTH);
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        frame.setVisible(true);
    }

    private void editEmployeeWindow() {
        JFrame frame = WindowFormat("Darbinieki", true);
    
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
    
                JButton selectBtn = new JButton("Rediģēt");
                selectBtn.setPreferredSize(new Dimension(0, 50));
                selectBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

                JButton cancelBtn = new JButton("Cancel");
                cancelBtn.setPreferredSize(new Dimension(0, 50));
                cancelBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                cancelBtn.setBackground(new Color(224, 86, 76));
                cancelBtn.setForeground(Color.WHITE);
                cancelBtn.addActionListener(e -> frame.dispose());
    
                JPanel bottomPanel = new JPanel();
                bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
                bottomPanel.add(selectBtn);
                bottomPanel.add(cancelBtn);
    
                frame.setLayout(new BorderLayout());
                frame.add(new JScrollPane(list), BorderLayout.CENTER);
                frame.add(bottomPanel, BorderLayout.SOUTH);
    
                selectBtn.addActionListener(e -> {
                    if (!list.isSelectionEmpty()) {
                        String selected = list.getSelectedValue();
                        int id = Integer.parseInt(selected.split(" - ")[0]);
    
                        JTextField vardsField = new JTextField();
                        JTextField uzvardsField = new JTextField();
                        JTextField amatsField = new JTextField();
    
                        try (Connection conn2 = Database.connect()) {
                            String sql = "SELECT * FROM darbinieki WHERE id=?";
                            PreparedStatement pstmt = conn2.prepareStatement(sql);
                            pstmt.setInt(1, id);
                            ResultSet rs2 = pstmt.executeQuery();
    
                            if (rs2.next()) {
                                vardsField.setText(rs2.getString("vards"));
                                uzvardsField.setText(rs2.getString("uzvards"));
                                amatsField.setText(rs2.getString("amats"));
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
    
                        Object[] fields = {
                                "Vārds:", vardsField,
                                "Uzvārds:", uzvardsField,
                                "Amats:", amatsField
                        };
    
                        int option = JOptionPane.showConfirmDialog(null, fields, "Rediģēt darbinieku", JOptionPane.OK_CANCEL_OPTION);
    
                        if (option == JOptionPane.OK_OPTION) {
                            String vards = vardsField.getText();
                            String uzvards = uzvardsField.getText();
                            String amats = amatsField.getText();
    
                            if (vards.isEmpty() || uzvards.isEmpty() || amats.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Aizpildi visus laukus!");
                                return;
                            }
    
                            try (Connection conn3 = Database.connect()) {
                                String sql = "UPDATE darbinieki SET vards=?, uzvards=?, amats=? WHERE id=?";
                                PreparedStatement pstmt = conn3.prepareStatement(sql);
    
                                pstmt.setString(1, vards);
                                pstmt.setString(2, uzvards);
                                pstmt.setString(3, amats);
                                pstmt.setInt(4, id);
    
                                pstmt.executeUpdate();
    
                                JOptionPane.showMessageDialog(null, "Darbinieks atjaunināts!");
    
                                model.set(list.getSelectedIndex(),
                                        id + " - " + vards + " " + uzvards + " (" + amats + ")");
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        frame.setVisible(true);
    }


    /* ===================== UI HELPERS ===================== */

    private static JButton buttonFormat(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 100));
        button.setMaximumSize(new Dimension(200, 100));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private static JPanel inputGroup(String title) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 40));

        JTextField area = new JTextField();
        area.setFont(new Font("Arial", Font.PLAIN, 40));
        area.setPreferredSize(new Dimension(200, 50));

        panel.add(label, BorderLayout.WEST);
        panel.add(area, BorderLayout.EAST);

        return panel;
    }

    private static JPanel comboBoxGroup(String title, String[] options) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 40));

        JComboBox<String> comboBox = new JComboBox<>(options);

        panel.add(label, BorderLayout.WEST);
        panel.add(comboBox, BorderLayout.EAST);

        return panel;
    }

    private static JPanel confirmCancelGroup(String secButtonName, Frame frame) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        Dimension buttonSize = new Dimension(150, 50);
        Color secColor = new Color(224, 86, 76);

        JButton confirm = new JButton("confirm");
        confirm.setPreferredSize(buttonSize);

        JButton secButton = new JButton(secButtonName);
        secButton.addActionListener(e -> frame.dispose());
        secButton.setPreferredSize(buttonSize);
        secButton.setBackground(secColor);
        secButton.setForeground(Color.white);

        panel.add(confirm, BorderLayout.WEST);
        panel.add(secButton, BorderLayout.EAST);

        return panel;
    }
        private static JFrame WindowFormat(String title, Boolean exitOnClose) {
            JFrame frame = new JFrame(title);
            frame.pack();
            frame.setSize(1000, 1000);
            if(exitOnClose){
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            frame.setLocationRelativeTo(null);
            return frame;
    }
}