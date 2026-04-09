package rvt;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

public class EmployeeInterface extends ElementFormatting{
    public EmployeeInterface() {

    }
    /* ===================== ADD AN ORDER WINDOW ===================== */
    public void orderWindow(String currentlyEmployeeID) {
        JFrame frame = WindowFormat("Pasūtījumi", false);
        frame.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        
        DefaultListModel<String> model = new DefaultListModel<>();

        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM orders")) {
        
                boolean empty = true;
                while (rs.next()) {
                    empty = false;
                    String text = rs.getInt("pasutijuma_id") + " - " +
                            rs.getString("datums") + " | " +
                            rs.getString("summa") + " | " +
                            rs.getString("statuss") + " | " +
                            rs.getString("darbinieks_id");
                    model.addElement(text);
                }
        
                if (empty) {
                    JLabel label = new JLabel("Pasūtījumu nav :(");
                    label.setFont(new Font("Serif", Font.BOLD, 25));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    frame.add(label);
                } else {
                    JList<String> list = new JList<>(model);
                    list.setFont(new Font("Arial", Font.PLAIN, 30));

                    frame.add(new JScrollPane(list), BorderLayout.CENTER);

                    JButton removeBtn = listRemoveButton();
                    removeBtn.addActionListener(e -> {
                        String selected = list.getSelectedValue();
                        if (selected != null) {
                            Integer currentOrderID = Integer.parseInt(selected.split(" - ")[0]);
                            removeOrderFromDb(currentOrderID, list, model);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please select an order first.");
                        }
                    });

                    JButton editBtn = listEditBtn();

                    editBtn.addActionListener(e -> {
                        String selected = list.getSelectedValue();
                        if (selected != null) {
                            Integer currentOrderID = Integer.parseInt(selected.split(" - ")[0]);
                            orderEditingMenu(currentOrderID, currentlyEmployeeID);
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please select an order first.");
                        }
                    });

                    bottomPanel.add(editBtn);
                    bottomPanel.add(removeBtn);
                }

                JButton addBtn = listAddBtn();
                addBtn.addActionListener(e -> {addOrderWindow(currentlyEmployeeID); frame.dispose();});
        
                JButton cancelBtn = listCancelBtn();
                cancelBtn.addActionListener(e -> frame.dispose());
    
                bottomPanel.add(addBtn);
                bottomPanel.add(cancelBtn);

                frame.add(bottomPanel, BorderLayout.SOUTH);
        
            } catch (Exception e) {
                e.printStackTrace();
            }
        frame.setVisible(true);
    }
    
    public void addOrderWindow(String currentEmployeeID) {
        JFrame frame = WindowFormat("Pievienot pasūtījumu", false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Pievienojiet jaunu pasūtījumu");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel inputGroup = new JPanel();
        inputGroup.setLayout(new BoxLayout(inputGroup, BoxLayout.Y_AXIS));

        JTextField orderIDField = new JTextField();
        inputGroup.add(inputGroup("Pasutijuma_id", orderIDField));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextField sumField = new JTextField();
        inputGroup.add(inputGroup("Summa", sumField));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

        String[] statusOptions = {"Izpildīts", "Procesā"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        inputGroup.add(comboBoxGroup("Statuss", statusComboBox));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel bottomBtnGroup = new JPanel(new BorderLayout(5, 5));
        JButton confirm = confirmBtn("Confirm");
        confirm.addActionListener(e -> {addOrderToDb(orderIDField, sumField, statusComboBox, currentEmployeeID); frame.dispose();});

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

    public void orderEditingMenu(Integer currentOrderID, String currentEmployeeID) { //😊🥸✅💹✔️ efektīvā manierē pievieno pasutijumu logu
        JFrame frame = WindowFormat("rediģēt pasūtījumu id: " + currentOrderID, false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Pievienojiet jaunu pasūtījumu");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel inputGroup = new JPanel();
        inputGroup.setLayout(new BoxLayout(inputGroup, BoxLayout.Y_AXIS));

        JTextField sumField = new JTextField();
        inputGroup.add(inputGroup("Summa", sumField));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

        String[] statusOptions = {"Izpildīts", "Procesā"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        inputGroup.add(comboBoxGroup("Statuss", statusComboBox));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel bottomBtnGroup = new JPanel(new BorderLayout(5, 5));
        JButton confirm = confirmBtn("Confirm");
        confirm.addActionListener(e -> {updateOrderToDb(currentOrderID, sumField, statusComboBox, currentEmployeeID); frame.dispose();});

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

    public void addOrderToDb(JTextField orderIDField, JTextField sumField, JComboBox<String> statusOptions, String currentEmployeeID) {
        try (Connection dbConn = Database.connect()) {
            String sql = "INSERT INTO orders(pasutijuma_id, datums, summa, statuss, darbinieks_id) VALUES(?,?,?,?,?)";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String sqliteDateTime = now.format(formatter);

            PreparedStatement pstmt = dbConn.prepareStatement(sql);

            pstmt.setInt(1, Integer.parseInt(orderIDField.getText()));
            pstmt.setString(2, sqliteDateTime);
            pstmt.setString(3, sumField.getText());
            pstmt.setString(4, String.valueOf(statusOptions.getSelectedItem()));
            pstmt.setInt(5, Integer.valueOf(currentEmployeeID));
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Saglabāts!");
            orderWindow(currentEmployeeID);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kļūda: " + e.getMessage());
        }
    }

    public void updateOrderToDb(Integer currentOrderID, JTextField sumField, JComboBox<String> statusOptions, String currentEmployeeID) {
        try (Connection conn3 = Database.connect()) {
            String sql = "UPDATE orders SET summa=?, statuss=? WHERE pasutijuma_id=?";
            PreparedStatement pstmt = conn3.prepareStatement(sql);

            try {
                String input = sumField.getText().trim().replace(",", ".");
                BigDecimal sum = new BigDecimal(input);
                pstmt.setBigDecimal(1, sum);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "invalid number");
            }

            pstmt.setString(2, String.valueOf(statusOptions.getSelectedItem()));
            pstmt.setInt(3, currentOrderID);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Pasūtījums atjaunināts!");
            orderWindow(currentEmployeeID);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Kļūda: " + ex.getMessage());
        }
    }

    public void removeOrderFromDb(Integer currentOrderID, JList<String> list, DefaultListModel<String> model) {
        int index = list.getSelectedIndex();

        if (index != -1) {
            try (Connection dbConn2 = Database.connect()) {
            String sql = "DELETE FROM orders WHERE pasutijuma_id=?";
            PreparedStatement pstmt = dbConn2.prepareStatement(sql);
            pstmt.setInt(1, currentOrderID);
            pstmt.executeUpdate();
            model.remove(index);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Kļūda: " + ex.getMessage());
            }
        }
    }
}
