package rvt;

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

    public void productWindow() {
        JFrame frame = WindowFormat("Produkti un krājumi", true);
    
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = null;
    
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT p.*, d.nosaukums AS piegadatajs " +
                     "FROM products p LEFT JOIN deliverer d " +
                     "ON p.piegadatajs_id = d.piegadataja_id")) {
    
            boolean empty = true;
    
            while (rs.next()) {
                empty = false;
    
                String deliverer = rs.getString("piegadatajs");
                if (deliverer == null) deliverer = "Nav";
    
                String text = rs.getInt("produkts_id") + " - " +
                        rs.getString("nosaukums") + " | " +
                        rs.getBigDecimal("cena") + " | " +
                        rs.getString("kategorija") + " | " +
                        rs.getInt("daudzums") + " gab | " +
                        deliverer;
    
                model.addElement(text);
            }
    
            frame.setLayout(new BorderLayout());
    
            JComponent centerComponent;
    
            if (empty) {
                JLabel label = new JLabel("Produktu nav :(");
                label.setFont(new Font("Serif", Font.BOLD, 25));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                centerComponent = label;
            } else {
                list = new JList<>(model);
                list.setFont(new Font("Arial", Font.PLAIN, 25));
                centerComponent = new JScrollPane(list);
            }
    
            JButton addBtn = buttonFormat("Pievienot");
            JButton editBtn = buttonFormat("Rediģēt");
            JButton removeBtn = buttonFormat("Dzēst");
            JButton cancelBtn = buttonFormat("Atcelt");
    
            JList<String> finalList = list;
    
            // ADD
            addBtn.addActionListener(e -> {
                JTextField idField = new JTextField();
                JTextField nameField = new JTextField();
                JTextField priceField = new JTextField();
                JTextField categoryField = new JTextField();
                JTextField quantityField = new JTextField();
                JTextField delivererIdField = new JTextField();
    
                Object[] fields = {
                        "Produkts ID:", idField,
                        "Nosaukums:", nameField,
                        "Cena:", priceField,
                        "Kategorija:", categoryField,
                        "Daudzums:", quantityField,
                        "Piegādātāja ID:", delivererIdField
                };
    
                int option = JOptionPane.showConfirmDialog(null, fields, "Pievienot produktu", JOptionPane.OK_CANCEL_OPTION);
    
                if (option == JOptionPane.OK_OPTION) {
                    try (Connection dbConn = Database.connect()) {
                        String sql = "INSERT INTO products(produkts_id, nosaukums, cena, kategorija, daudzums, piegadatajs_id) VALUES(?,?,?,?,?,?)";
                        PreparedStatement pstmt = dbConn.prepareStatement(sql);
    
                        pstmt.setInt(1, Integer.parseInt(idField.getText()));
                        pstmt.setString(2, nameField.getText());
                        pstmt.setBigDecimal(3, new java.math.BigDecimal(priceField.getText()));
                        pstmt.setString(4, categoryField.getText());
                        pstmt.setInt(5, Integer.parseInt(quantityField.getText()));
                        pstmt.setInt(6, Integer.parseInt(delivererIdField.getText()));
    
                        pstmt.executeUpdate();
    
                        JOptionPane.showMessageDialog(null, "Saglabāts!");
                        frame.dispose();
                        productWindow();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Kļūda! Pārbaudi datus.");
                    }
                }
            });
    
            // EDIT
            editBtn.addActionListener(e -> {
                if (finalList == null || finalList.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nav izvēlēts produkts!");
                    return;
                }
            
                int id = Integer.parseInt(finalList.getSelectedValue().split(" - ")[0]);
                editProduct(id, frame);
            });
    
            // REMOVE
            removeBtn.addActionListener(e -> {
                if (finalList == null || finalList.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nav izvēlēts produkts!");
                    return;
                }
    
                String selected = finalList.getSelectedValue();
                int id = Integer.parseInt(selected.split(" - ")[0]);
    
                try (Connection dbConn = Database.connect()) {
                    String sql = "DELETE FROM products WHERE produkts_id=?";
                    PreparedStatement pstmt = dbConn.prepareStatement(sql);
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
    
                    JOptionPane.showMessageDialog(null, "Dzēsts!");
                    frame.dispose();
                    productWindow();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Kļūda dzēšanā!");
                }
            });
    
            cancelBtn.addActionListener(e -> frame.dispose());
    
            if (empty) {
                editBtn.setEnabled(false);
                removeBtn.setEnabled(false);
            }
    
            JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 5, 5));
            bottomPanel.add(addBtn);
            bottomPanel.add(editBtn);
            bottomPanel.add(removeBtn);
            bottomPanel.add(cancelBtn);
    
            frame.add(centerComponent, BorderLayout.CENTER);
            frame.add(bottomPanel, BorderLayout.SOUTH);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        frame.setVisible(true);
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
