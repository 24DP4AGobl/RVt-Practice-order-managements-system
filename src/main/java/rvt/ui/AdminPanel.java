package rvt.ui;

import javax.swing.*;
import java.awt.*;

import rvt.ui.adminPanels.*;
import rvt.util.ButtonFormatting;
import rvt.util.TextFormatting;
import rvt.util.UIColors;

public class AdminPanel extends JPanel {

    TextFormatting text = new TextFormatting();
    ButtonFormatting btn = new ButtonFormatting();
    UIColors color = new UIColors();

    public AdminPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // 🔹 Sidebar
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(color.sideBar());
        sideBar.setPreferredSize(new Dimension(170, 0));
        sideBar.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));


        JLabel Misc = text.text3("Misc");
        Misc.setForeground(Color.white);

        JButton dashboardBtn = btn.sideBarBtn("Galvenā");
        JButton productsBtn = btn.sideBarBtn("Produkti");
        JButton employeesBtn = btn.sideBarBtn("Darbinieki");
        JButton delivererBtn = btn.sideBarBtn("Piegādātāji");
        JButton ordersBtn = btn.sideBarBtn("Pasūtījumi");
        JButton catBtn = btn.sideBarBtn("Kategorijas");
        JButton statusBtn = btn.sideBarBtn("Statusi");


        sideBar.add(dashboardBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));
        sideBar.add(productsBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(employeesBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(delivererBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 30)));

        sideBar.add(Misc);
        sideBar.add(Box.createRigidArea(new Dimension(0, 5)));
        sideBar.add(catBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(statusBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 30)));

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);
        JScrollPane scroll = new JScrollPane();

        content.add(new DashboardPanel(),  "dashboard");
        dashboardBtn.addActionListener(e -> cardLayout.show(content, "dashboard"));

        content.add(new ProductPanel(),  "products");
        productsBtn.addActionListener(e -> cardLayout.show(content, "products"));

        content.add(new EmployeePanel(), "employees");
        employeesBtn.addActionListener(e -> cardLayout.show(content, "employees"));

        content.add(new DelivererPanel(), "deliverers");
        delivererBtn.addActionListener(e -> cardLayout.show(content, "deliverers"));

        content.add(new CategoryPanel(), "categories");
        catBtn.addActionListener(e -> cardLayout.show(content, "categories"));

        content.add(new StatusPanel(), "statuses");
        statusBtn.addActionListener(e -> cardLayout.show(content, "statuses"));

        scroll.add(content);

        add(sideBar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);
    }
}
