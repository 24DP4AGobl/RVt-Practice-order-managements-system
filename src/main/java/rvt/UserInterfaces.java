package rvt;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserInterfaces {

    public void userSelect() {
        JFrame frame = new JFrame("Lietotāja izvēle");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
                if ("parole1".equals(password)) {
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
        JFrame frame = new JFrame("Darbinieki");
        frame.setSize(600, 400);

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
                    }
                });

                frame.setLayout(new BorderLayout());
                frame.add(new JScrollPane(list), BorderLayout.CENTER);
                frame.add(selectBtn, BorderLayout.SOUTH);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    public void mainMenu(boolean isAdmin) {
        JFrame frame = new JFrame("Pasūtījumu uzskaites sistēma");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(addOrder);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(viewOrders);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(editOrders);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(products);

        if (isAdmin) {
            JButton addEmployee = buttonFormat("Pievienot darbinieku");
            addEmployee.addActionListener(e -> addEmployeeWindow());

            JButton removeEmployee = buttonFormat("Noņemt darbinieku");
            removeEmployee.addActionListener(e -> removeEmployeeWindow());

            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(addEmployee);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(removeEmployee);
        }

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);
        frame.add(wrapper);
        frame.setVisible(true);
    }

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
            try {
                int id = Integer.parseInt(idField.getText());
                String sql = "INSERT INTO darbinieki(id, vards, uzvards, amats) VALUES(?,?,?,?)";

                try (Connection dbConn = Database.connect();
                     PreparedStatement pstmt = dbConn.prepareStatement(sql)) {

                    pstmt.setInt(1, id);
                    pstmt.setString(2, vardsField.getText());
                    pstmt.setString(3, uzvardsField.getText());
                    pstmt.setString(4, amatsField.getText());
                    pstmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Saglabāts!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Kļūda!");
            }
        }
    }

    private void removeEmployeeWindow() {
        JFrame frame = new JFrame("Noņemt darbinieku");
        frame.setSize(600, 400);

        DefaultListModel<String> model = new DefaultListModel<>();

        try (Connection dbConn = Database.connect();
             Statement stmt = dbConn.createStatement();
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
                JButton removeBtn = new JButton("Noņemt");

                removeBtn.addActionListener(e -> {
                    int index = list.getSelectedIndex();
                    if (index != -1) {
                        String selected = list.getSelectedValue();
                        int id = Integer.parseInt(selected.split(" - ")[0]);

                        try (Connection dbConn2 = Database.connect()) {
                            String sql = "DELETE FROM darbinieki WHERE id=?";
                            try (PreparedStatement pstmt = dbConn2.prepareStatement(sql)) {
                                pstmt.setInt(1, id);
                                pstmt.executeUpdate();
                                model.remove(index);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                frame.setLayout(new BorderLayout());
                frame.add(new JScrollPane(list), BorderLayout.CENTER);
                frame.add(removeBtn, BorderLayout.SOUTH);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        frame.setVisible(true);
    }

    private void windowAddOrder() {
        JFrame frame = new JFrame("Pievienot pasūtījumu");
        frame.setSize(1000, 1000);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Pievienojiet jaunu pasūtījumu");
        title.setFont(new Font("Serif", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel dataInputPanel = new JPanel();
        dataInputPanel.setLayout(new BoxLayout(dataInputPanel, BoxLayout.Y_AXIS));

        JPanel order_id = inputPanel("pasutijums_id");
        JPanel date = inputPanel("datums");
        JPanel summa = inputPanel("summa");

        JPanel panelStatus = new JPanel();
        JLabel labelStatus = new JLabel("statuss");
        labelStatus.setFont(new Font("Serif", Font.BOLD, 25));

        String[] statusOptions = {"Izpildīts", "Procesā"};
        JComboBox<String> statusBox = new JComboBox<>(statusOptions);

        panelStatus.add(labelStatus);
        panelStatus.add(statusBox);

        JPanel worker_id = inputPanel("darbinieka_id");

        dataInputPanel.add(order_id);
        dataInputPanel.add(date);
        dataInputPanel.add(summa);
        dataInputPanel.add(panelStatus);
        dataInputPanel.add(worker_id);

        mainPanel.add(title);
        mainPanel.add(dataInputPanel);

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.WEST);

        frame.setVisible(true);
    }

    private static JButton buttonFormat(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 100));
        button.setMaximumSize(new Dimension(200, 100));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private static JPanel inputPanel(String title) {
        Dimension dimension = new Dimension(150, 25);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Serif", Font.BOLD, 25));

        JTextArea area = new JTextArea();
        area.setFont(new Font("Serif", Font.PLAIN, 20));
        area.setPreferredSize(dimension);

        panel.add(label);
        panel.add(area);

        return panel;
    }
}