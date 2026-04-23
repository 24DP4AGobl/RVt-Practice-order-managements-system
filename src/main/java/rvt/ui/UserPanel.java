package rvt.ui;

import javax.swing.*;
import java.awt.*;

import rvt.ui.adminPanels.*;
import rvt.util.ButtonFormatting;
import rvt.util.TextFormatting;
import rvt.util.UIColors;

public class UserPanel extends JPanel {
    
    TextFormatting text = new TextFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    public UserPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(color.sideBar());
        sideBar.setPreferredSize(new Dimension(170, 0));
        sideBar.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));


        JLabel Misc = text.text3("Misc");
        Misc.setForeground(Color.white);

        JButton dashboardBtn = btn.sideBarBtn("Galvenā");
        JButton ordersBtn = btn.sideBarBtn("Pasūtījumi");

        sideBar.add(dashboardBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));

        sideBar.add(ordersBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);
        JScrollPane scroll = new JScrollPane();

        content.add(new DashboardPanel(),  "dashboard");
        dashboardBtn.addActionListener(e -> cardLayout.show(content, "dashboard"));

        content.add(new OrderPanel(), "orders");
        ordersBtn.addActionListener(e -> cardLayout.show(content, "orders"));

        scroll.add(content);

        add(sideBar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);
    }
}
