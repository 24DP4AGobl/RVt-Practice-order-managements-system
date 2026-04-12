package rvt.oldVersion;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminInterface extends ElementFormatting{
    /* ===================== ADMIN FUNCTIONS ===================== */
    public AdminInterface() {

    }

    public void delivererWindowFunctionality(String function) {
        if (function.equals("add")) {
            JFrame frame = WindowFormat("Pievienot piegādātāju", false);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JLabel title = new JLabel("Pievienojiet jaunu piegādātāju");
            title.setFont(new Font("Arial", Font.BOLD, 50));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel inputGroup = new JPanel();
            inputGroup.setLayout(new BoxLayout(inputGroup, BoxLayout.Y_AXIS));

            JTextField delivererField = new JTextField();
            inputGroup.add(inputGroup("Piegādātāja_id", delivererField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

            JTextField nameField = new JTextField();
            inputGroup.add(inputGroup("Nosaukums", nameField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

            JTextField numField = new JTextField();
            inputGroup.add(inputGroup("Talrunis", numField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

            JTextField gmailField = new JTextField();
            inputGroup.add(inputGroup("E-pasts", gmailField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

            JPanel bottomBtnGroup = new JPanel(new BorderLayout(5, 5));
            JButton confirm = confirmBtn("Confirm");
            confirm.addActionListener(e -> {addDelivererToDb(delivererField, nameField, numField, gmailField); frame.dispose();});
            

            bottomBtnGroup.add(confirm, BorderLayout.WEST);
            bottomBtnGroup.add(cancelBtn("Atcelt", frame), BorderLayout.EAST);

            inputGroup.add(Box.createRigidArea(new Dimension(0, 30)));
            inputGroup.add(bottomBtnGroup);

            mainPanel.add(title);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            mainPanel.add(inputGroup);

            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.add(mainPanel);

            frame.add(wrapper);
            frame.setVisible(true);
        }   

        if (function.equals("remove")) {
            JFrame frame = WindowFormat("Piegādātāji", false);
        
            DefaultListModel<String> model = new DefaultListModel<>();
        
            try (Connection conn = Database.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM deliverer")) {
        
                boolean empty = true;
                while (rs.next()) {
                    empty = false;
                    String text = rs.getInt("piegadataja_id") + " - " +
                            rs.getString("nosaukums") + " | " +
                            rs.getString("talrunis") + " | " +
                            rs.getString("emails");
                    model.addElement(text);
                }
        
                if (empty) {
                    JLabel label = new JLabel("Piegādātāju nav :(");
                    label.setFont(new Font("Serif", Font.BOLD, 25));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    frame.add(label);
                } else {
                    JList<String> list = new JList<>(model);
                    list.setFont(new Font("Arial", Font.PLAIN, 30));
        
                    JButton removeBtn = new JButton("Noņemt");
        
                    removeBtn.addActionListener(e -> {
                        int index = list.getSelectedIndex();
                        if (index != -1) {
                            String selected = list.getSelectedValue();
                            int id = Integer.parseInt(selected.split(" - ")[0]);
        
                            try (Connection dbConn2 = Database.connect()) {
                                String sql = "DELETE FROM deliverer WHERE piegadataja_id=?";
                                PreparedStatement pstmt = dbConn2.prepareStatement(sql);
                                pstmt.setInt(1, id);
                                pstmt.executeUpdate();
        
                                model.remove(index);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
        
                    JButton cancelBtn = new JButton("Atcelt");
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
        
                    frame.setLayout(new BorderLayout());
                    frame.add(new JScrollPane(list), BorderLayout.CENTER);
                    frame.add(bottomPanel, BorderLayout.SOUTH);
                }
        
            } catch (Exception e) {
                e.printStackTrace();
            }
        
            frame.setVisible(true);
        }
        
        if (function.equals("edit")) {
            JFrame frame = WindowFormat("Piegādātāji", false);
        
            DefaultListModel<String> model = new DefaultListModel<>();
        
            try (Connection conn = Database.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM deliverer")) {
        
                boolean empty = true;
                while (rs.next()) {
                    empty = false;
                    String text = rs.getInt("piegadataja_id") + " - " +
                            rs.getString("nosaukums") + " | " +
                            rs.getString("talrunis") + " | " +
                            rs.getString("emails");
                    model.addElement(text);
                }
        
                if (empty) {
                    JLabel label = new JLabel("Piegādātāju nav :(");
                    label.setFont(new Font("Serif", Font.BOLD, 25));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    frame.add(label);
                } else {
                    JList<String> list = new JList<>(model);
                    list.setFont(new Font("Arial", Font.PLAIN, 30));
        
                    JButton editBtn = new JButton("Rediģēt");
                    editBtn.setPreferredSize(new Dimension(0, 50));
                    editBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
                    JButton cancelBtn = new JButton("atcelt");
                    cancelBtn.setPreferredSize(new Dimension(0, 50));
                    cancelBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                    cancelBtn.setBackground(new Color(224, 86, 76));
                    cancelBtn.setForeground(Color.WHITE);
                    cancelBtn.addActionListener(e -> frame.dispose());
        
                    JPanel bottomPanel = new JPanel();
                    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
                    bottomPanel.add(editBtn);
                    bottomPanel.add(cancelBtn);
        
                    frame.setLayout(new BorderLayout());
                    frame.add(new JScrollPane(list), BorderLayout.CENTER);
                    frame.add(bottomPanel, BorderLayout.SOUTH);
        
                    editBtn.addActionListener(e -> {
                        if (!list.isSelectionEmpty()) {
                            String selected = list.getSelectedValue();
                            int id = Integer.parseInt(selected.split(" - ")[0]);
        
                            JTextField nameField = new JTextField();
                            JTextField phoneField = new JTextField();
                            JTextField emailField = new JTextField();
        
                            // Ielādē esošos datus
                            try (Connection conn2 = Database.connect()) {
                                String sql = "SELECT * FROM deliverer WHERE piegadataja_id=?";
                                PreparedStatement pstmt = conn2.prepareStatement(sql);
                                pstmt.setInt(1, id);
                                ResultSet rs2 = pstmt.executeQuery();
        
                                if (rs2.next()) {
                                    nameField.setText(rs2.getString("nosaukums"));
                                    phoneField.setText(rs2.getString("talrunis"));
                                    emailField.setText(rs2.getString("emails"));
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
        
                            Object[] fields = {
                                    "Nosaukums:", nameField,
                                    "Talrunis:", phoneField,
                                    "E-pasts:", emailField
                            };
        
                            int option = JOptionPane.showConfirmDialog(null, fields, "Rediģēt piegādātāju", JOptionPane.OK_CANCEL_OPTION);
        
                            if (option == JOptionPane.OK_OPTION) {
                                String name = nameField.getText();
                                String phone = phoneField.getText();
                                String email = emailField.getText();
        
                                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Aizpildi visus laukus!");
                                    return;
                                }
        
                                try (Connection conn3 = Database.connect()) {
                                    String sql = "UPDATE deliverer SET nosaukums=?, talrunis=?, emails=? WHERE piegadataja_id=?";
                                    PreparedStatement pstmt = conn3.prepareStatement(sql);
        
                                    pstmt.setString(1, name);
                                    pstmt.setString(2, phone);
                                    pstmt.setString(3, email);
                                    pstmt.setInt(4, id);
        
                                    pstmt.executeUpdate();
        
                                    JOptionPane.showMessageDialog(null, "Piegādātājs atjaunināts!");
        
                                    // Atjauno sarakstu
                                    model.set(list.getSelectedIndex(),
                                            id + " - " + name + " | " + phone + " | " + email);
        
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

    }

    public boolean isLowStock() {
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT daudzums FROM products")) {
    
            while (rs.next()) {
                if (rs.getInt("daudzums") < 20) {
                    return true;
                }
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return false;
    }

    public void productWindow() {
        JFrame frame = WindowFormat("Produkti un krājumi", true);
    
        JList<String> list = new JList<>();
        list.setFont(new Font("Arial", Font.PLAIN, 25));
    
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(list), BorderLayout.CENTER);
    
        // ===================== FILTER STATE =====================
        StringBuilder where = new StringBuilder();
        java.util.List<Object> params = new java.util.ArrayList<>();
    
        Runnable refresh = () -> {
            String sqlWhere = where.length() == 0 ? null : "WHERE " + where.toString();
            list.setModel(loadProducts(sqlWhere, params.toArray()));
        };
    
        refresh.run();
    
        // ===================== FILTER BUTTONS =====================
        JButton filterPrice = buttonFormat("Cena");
        JButton filterAmount = buttonFormat("Daudzums");
        JButton filterDeliverer = buttonFormat("Piegādātājs");
        JButton resetBtn = buttonFormat("Reset");
    
        Dimension smallFilterSize = new Dimension(0, 40);
    
        filterPrice.setPreferredSize(smallFilterSize);
        filterAmount.setPreferredSize(smallFilterSize);
        filterDeliverer.setPreferredSize(smallFilterSize);
        resetBtn.setPreferredSize(smallFilterSize);
    
        filterPrice.setMaximumSize(smallFilterSize);
        filterAmount.setMaximumSize(smallFilterSize);
        filterDeliverer.setMaximumSize(smallFilterSize);
        resetBtn.setMaximumSize(smallFilterSize);
    
        // ===================== FILTERS =====================
        filterPrice.addActionListener(e -> {
            JTextField min = new JTextField();
            JTextField max = new JTextField();
    
            Object[] fields = {"Min cena:", min, "Max cena:", max};
    
            int option = JOptionPane.showConfirmDialog(null, fields, "Cena", JOptionPane.OK_CANCEL_OPTION);
    
            if (option == JOptionPane.OK_OPTION) {
                try {
                    if (where.length() > 0) where.append(" AND ");
                    where.append("p.cena BETWEEN ? AND ?");
                    params.add(Double.parseDouble(min.getText()));
                    params.add(Double.parseDouble(max.getText()));
                    refresh.run();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nepareizi dati!");
                }
            }
        });
    
        filterAmount.addActionListener(e -> {
            JTextField min = new JTextField();
            JTextField max = new JTextField();
    
            Object[] fields = {"Min daudzums:", min, "Max daudzums:", max};
    
            int option = JOptionPane.showConfirmDialog(null, fields, "Daudzums", JOptionPane.OK_CANCEL_OPTION);
    
            if (option == JOptionPane.OK_OPTION) {
                try {
                    if (where.length() > 0) where.append(" AND ");
                    where.append("p.daudzums BETWEEN ? AND ?");
                    params.add(Integer.parseInt(min.getText()));
                    params.add(Integer.parseInt(max.getText()));
                    refresh.run();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nepareizi dati!");
                }
            }
        });
    
        filterDeliverer.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Piegādātāja ID:");
    
            if (input != null && !input.isEmpty()) {
                try {
                    if (where.length() > 0) where.append(" AND ");
                    where.append("p.piegadatajs_id = ?");
                    params.add(Integer.parseInt(input));
                    refresh.run();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Nepareizs ID!");
                }
            }
        });
    
        resetBtn.addActionListener(e -> {
            where.setLength(0);
            params.clear();
            list.clearSelection();
            refresh.run();
        });
    
        // ===================== FILTER UI =====================
        JLabel filterTitle = new JLabel("Filtrēšana");
        filterTitle.setFont(new Font("Arial", Font.BOLD, 18));
        filterTitle.setHorizontalAlignment(SwingConstants.CENTER);
    
        JPanel topPanel = new JPanel(new BorderLayout());
    
        JPanel filterButtons = new JPanel(new GridLayout(1, 4, 5, 5));
        filterButtons.add(filterPrice);
        filterButtons.add(filterAmount);
        filterButtons.add(filterDeliverer);
        filterButtons.add(resetBtn);
    
        topPanel.add(filterTitle, BorderLayout.NORTH);
        topPanel.add(filterButtons, BorderLayout.CENTER);
    
        frame.add(topPanel, BorderLayout.NORTH);
    
        // ===================== BOTTOM BUTTONS =====================
        JButton addBtn = buttonFormat("Pievienot");
        JButton editBtn = buttonFormat("Rediģēt");
        JButton removeBtn = buttonFormat("Dzēst");
        JButton cancelBtn = buttonFormat("Atcelt");
    
        // ===================== ADD PRODUCT (FIXED PROPER UI) =====================
        addBtn.addActionListener(e -> {
    
            JFrame addFrame = WindowFormat("Pievienot produktu", false);
    
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    
            JLabel title = new JLabel("Pievienojiet jaunu produktu");
            title.setFont(new Font("Arial", Font.BOLD, 40));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
    
            JPanel inputGroup = new JPanel();
            inputGroup.setLayout(new BoxLayout(inputGroup, BoxLayout.Y_AXIS));
    
            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField categoryField = new JTextField();
            JTextField amountField = new JTextField();
            JTextField delivererField = new JTextField();
    
            inputGroup.add(inputGroup("Produkts ID", idField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 10)));
    
            inputGroup.add(inputGroup("Nosaukums", nameField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 10)));
    
            inputGroup.add(inputGroup("Cena", priceField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 10)));
    
            inputGroup.add(inputGroup("Kategorija", categoryField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 10)));
    
            inputGroup.add(inputGroup("Daudzums", amountField));
            inputGroup.add(Box.createRigidArea(new Dimension(0, 10)));
    
            inputGroup.add(inputGroup("Piegādātāja ID", delivererField));
    
            JPanel buttons = new JPanel(new BorderLayout(5, 5));
    
            JButton confirm = confirmBtn("Saglabāt");
            JButton cancel = cancelBtn("Atcelt", addFrame);
    
            confirm.addActionListener(ev -> {
                try (Connection conn = Database.connect()) {
    
                    String sql = "INSERT INTO products(produkts_id, nosaukums, cena, kategorija, daudzums, piegadatajs_id) VALUES(?,?,?,?,?,?)";
    
                    PreparedStatement ps = conn.prepareStatement(sql);
    
                    ps.setInt(1, Integer.parseInt(idField.getText()));
                    ps.setString(2, nameField.getText());
                    ps.setBigDecimal(3, new java.math.BigDecimal(priceField.getText()));
                    ps.setString(4, categoryField.getText());
                    ps.setInt(5, Integer.parseInt(amountField.getText()));
                    ps.setInt(6, Integer.parseInt(delivererField.getText()));
    
                    ps.executeUpdate();
    
                    addFrame.dispose();
                    refresh.run();
    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Kļūda: " + ex.getMessage());
                }
            });
    
            buttons.add(confirm, BorderLayout.WEST);
            buttons.add(cancel, BorderLayout.EAST);
    
            mainPanel.add(title);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(inputGroup);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(buttons);
    
            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.add(mainPanel);
    
            addFrame.add(wrapper);
            addFrame.setVisible(true);
        });
    
        // ===================== EDIT =====================
        editBtn.addActionListener(e -> {
            if (list.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav izvēlēts produkts!");
                return;
            }
    
            int id = Integer.parseInt(list.getSelectedValue().split(" - ")[0]);
            editProduct(id, frame);
        });
    
        // ===================== DELETE =====================
        removeBtn.addActionListener(e -> {
            if (list.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null, "Nav izvēlēts produkts!");
                return;
            }
    
            int id = Integer.parseInt(list.getSelectedValue().split(" - ")[0]);
    
            try (Connection conn = Database.connect()) {
                PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM products WHERE produkts_id=?"
                );
                ps.setInt(1, id);
                ps.executeUpdate();
    
                refresh.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Kļūda dzēšanā!");
            }
        });
    
        cancelBtn.addActionListener(e -> frame.dispose());
    
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        bottomPanel.add(addBtn);
        bottomPanel.add(editBtn);
        bottomPanel.add(removeBtn);
        bottomPanel.add(cancelBtn);
    
        frame.add(bottomPanel, BorderLayout.SOUTH);
    
        frame.setVisible(true);
    }
    private DefaultListModel<String> loadProducts(String where, Object[] params) {
        DefaultListModel<String> model = new DefaultListModel<>();
    
        String sql = "SELECT p.*, d.nosaukums AS piegadatajs " +
                "FROM products p LEFT JOIN deliverer d " +
                "ON p.piegadatajs_id = d.piegadataja_id ";
    
        if (where != null) sql += where;
    
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
    
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                String deliverer = rs.getString("piegadatajs");
                if (deliverer == null) deliverer = "Nav";
    
                model.addElement(
                        rs.getInt("produkts_id") + " - " +
                        rs.getString("nosaukums") + " | " +
                        rs.getBigDecimal("cena") + " | " +
                        rs.getString("kategorija") + " | " +
                        rs.getInt("daudzums") + " gab | " +
                        deliverer
                );
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return model;
    }
    
    public void addDelivererToDb(JTextField delivererField, JTextField nameField, JTextField numField, JTextField gmailField) {
        try (Connection dbConn = Database.connect()) {
            String sql = "INSERT INTO deliverer(piegadataja_id, nosaukums, talrunis, emails) VALUES(?,?,?,?)";
            PreparedStatement pstmt = dbConn.prepareStatement(sql);

            pstmt.setInt(1, Integer.parseInt(delivererField.getText()));
            pstmt.setString(2, nameField.getText());
            pstmt.setString(3, numField.getText());
            pstmt.setString(4, gmailField.getText());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Saglabāts!");
       } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ID jau tiek izmantots, vai ir ievadīts!");
        }
    }



    public void addEmployeeWindow() {
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

    private void editProduct(int id, JFrame frame) {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField delivererIdField = new JTextField();
    
        try {
            Connection conn = Database.connect();
            String sql = "SELECT * FROM products WHERE produkts_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                nameField.setText(rs.getString("nosaukums"));
                priceField.setText(rs.getString("cena"));
                categoryField.setText(rs.getString("kategorija"));
                quantityField.setText(rs.getString("daudzums"));
                delivererIdField.setText(rs.getString("piegadatajs_id"));
            }
    
            rs.close();
            pstmt.close();
            conn.close();
    
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
        Object[] fields = {
                "Nosaukums:", nameField,
                "Cena:", priceField,
                "Kategorija:", categoryField,
                "Daudzums:", quantityField,
                "Piegādātāja ID:", delivererIdField
        };
    
        int option = JOptionPane.showConfirmDialog(null, fields, "Rediģēt produktu", JOptionPane.OK_CANCEL_OPTION);
    
        if (option == JOptionPane.OK_OPTION) {
            try {
                Connection conn = Database.connect();
                String sql = "UPDATE products SET nosaukums=?, cena=?, kategorija=?, daudzums=?, piegadatajs_id=? WHERE produkts_id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
    
                pstmt.setString(1, nameField.getText());
                pstmt.setBigDecimal(2, new java.math.BigDecimal(priceField.getText()));
                pstmt.setString(3, categoryField.getText());
                pstmt.setInt(4, Integer.parseInt(quantityField.getText()));
                pstmt.setInt(5, Integer.parseInt(delivererIdField.getText()));
                pstmt.setInt(6, id);
    
                pstmt.executeUpdate();
    
                pstmt.close();
                conn.close();
    
                JOptionPane.showMessageDialog(null, "Atjaunināts!");
                frame.dispose();
                productWindow();
    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Kļūda atjaunināšanā!");
            }
        }
    }

    public void employeeWindowFunctionality(String function) {
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
                list.setFont(new Font("Arial", Font.PLAIN, 30));

                if (function.equals("remove")) {
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
                    JButton cancelBtn = new JButton("Atcelt");
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
                } else if (function.equals("edit")) {
                    JButton selectBtn = new JButton("Rediģēt");
                    selectBtn.setPreferredSize(new Dimension(0, 50));
                    selectBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

                    JButton cancelBtn = new JButton("Atcelt");
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }
}
