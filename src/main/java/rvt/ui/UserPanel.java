package rvt.ui;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {
    
    public UserPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Darbinieka panelis", JLabel.CENTER);

        JPanel filterPanel = new JPanel();

        JButton myOrdersBtn = new JButton("Mani pasūtījumi");
        JButton pendingBtn = new JButton("Procesā");
        JButton doneBtn = new JButton("Pabeigti");

        filterPanel.add(myOrdersBtn);
        filterPanel.add(pendingBtn);
        filterPanel.add(doneBtn);

        String[] columns = {"ID", "Datums", "Summa"};
        JTable table = new JTable(new Object[][]{}, columns);

        add(title, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(new JScrollPane(table), BorderLayout.SOUTH);

        // 🔥 Filter actions
        myOrdersBtn.addActionListener(e -> System.out.println("Mani"));
        pendingBtn.addActionListener(e -> System.out.println("Procesā"));
        doneBtn.addActionListener(e -> System.out.println("Pabeigti"));
    }
}
