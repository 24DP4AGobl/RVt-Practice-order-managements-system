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
            bottomBtnGroup.add(cancelBtn("Cancel", frame), BorderLayout.EAST);

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
        
                    frame.setLayout(new BorderLayout());
                    frame.add(new JScrollPane(list), BorderLayout.CENTER);
                    frame.add(bottomPanel, BorderLayout.SOUTH);
                }
        
            } catch (Exception e) {
                e.printStackTrace();
            }
        
            frame.setVisible(true);
        }
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
                } else if (function.equals("edit")) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }
}
