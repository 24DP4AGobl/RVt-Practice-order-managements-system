package rvt.ui;

import javax.swing.*;
import java.awt.*;

import rvt.util.UIColors;
import rvt.model.Employee;
import rvt.util.UtilPanels;

public class MainFrame extends JFrame{

    UIColors color = new UIColors();
    UtilPanels panels = new UtilPanels();
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    public MainFrame(Employee employee) {
        setLayout(new BorderLayout());
        setTitle("System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.add(Box.createHorizontalGlue());
        topBar.setBackground(color.topBar());
        topBar.setPreferredSize(new Dimension(0, 50));
        topBar.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        topBar.add(panels.userPanel(employee));
        add(topBar, BorderLayout.NORTH);

        if (employee.getRole().equals("admin")) {
            mainPanel.add(new AdminPanel(), "ADMIN");
            cardLayout.show(mainPanel, "ADMIN");
        } else {
            mainPanel.add(new UserPanel(), "EMPLOYEE");
            cardLayout.show(mainPanel, "EMPLOYEE");
        }

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}