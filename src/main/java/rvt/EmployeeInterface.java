package rvt;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeInterface extends ElementFormatting{
    public EmployeeInterface() {

    }
    /* ===================== ADD AN ORDER WINDOW ===================== */
    public void windowAddOrder() {
        JFrame frame = WindowFormat("Pievienot pasūtījumu", false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Pievienojiet jaunu pasūtījumu");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel inputGroup = new JPanel();
        inputGroup.setLayout(new BoxLayout(inputGroup, BoxLayout.Y_AXIS));

        JTextField orderField = new JTextField();
        inputGroup.add(inputGroup("Pasutijuma_id", orderField));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextField sumField = new JTextField();
        inputGroup.add(inputGroup("Summa", sumField));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

        String[] statusOptions = {"Izpildīts", "Procesā"};
        inputGroup.add(comboBoxGroup("Statuss", statusOptions));
        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));

        inputGroup.add(Box.createRigidArea(new Dimension(0, 20)));
        inputGroup.add(comboBoxGroup("Piegadataja_id", new String[]{""}));

        JPanel bottomBtnGroup = new JPanel(new BorderLayout(5, 5));
        JButton confirm = confirmBtn("Confirm");
        //confirm.addActionListener(e -> addOrderToDb());

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

    //public void addOrderToDb() {
    //    try (Connection dbConn = Database.connect()) {
    //        String sql = "INSERT INTO darbinieki(id, datums, summa, statuss) VALUES(?,?,?,?)";
    //        PreparedStatement pstmt = dbConn.prepareStatement(sql);

    //        pstmt.setInt(1, Integer.parseInt(idField.getText()));
    //        pstmt.setString(2, vardsField.getText());
    //        pstmt.setString(3, datums.generate); //generate it
    //        pstmt.setString(4, amatsField.getText());
    //        pstmt.executeUpdate();

    //        JOptionPane.showMessageDialog(null, "Saglabāts!");
    //    } catch (Exception e) {
    //        JOptionPane.showMessageDialog(null, "ID jau tiek izmantots, vai ir ievadīts!");
    //    }
    //}
}
