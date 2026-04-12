package rvt.ui;

import javax.swing.*;
import java.awt.*;

import rvt.ui.adminPanels.*;
import rvt.util.ButtonFormatting;
import rvt.util.UIColors;

public class AdminPanel extends JPanel {

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

        JButton ordersBtn = btn.sideBarBtn("Pasūtījumi");
        JButton productsBtn = btn.sideBarBtn("Produkti");
        JButton employeesBtn = btn.sideBarBtn("Darbinieki");

        sideBar.add(ordersBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(productsBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(employeesBtn);

        // 🔹 Content
        //JPanel content = new JPanel(new BorderLayout());

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);

        content.add(new OrderPanel(), "orders");
        ordersBtn.addActionListener(e -> cardLayout.show(content, "orders"));
        content.add(new ProductPanel(), "products");
        productsBtn.addActionListener(e -> cardLayout.show(content, "products"));
        content.add(new EmployeePanel(), "employees");
        employeesBtn.addActionListener(e -> cardLayout.show(content, "employees"));

        // 🔹 Top filter bar
        //JPanel filterPanel = new JPanel();

        //JButton allBtn = new JButton("Visi");
        //JButton activeBtn = new JButton("Aktīvie");
        //JButton doneBtn = new JButton("Pabeigti");

        //filterPanel.add(allBtn);
        //filterPanel.add(activeBtn);
        //filterPanel.add(doneBtn);

        // 🔹 Table (placeholder)
        //String[] columns = {"ID", "Datums", "Summa", "Statuss"};
        //Object[][] data = {};

        //JTable table = new JTable(data, columns);
        //JScrollPane scrollPane = new JScrollPane(table);

        //content.add(filterPanel, BorderLayout.NORTH);
        //content.add(scrollPane, BorderLayout.CENTER);

        add(sideBar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        // 🔥 Filter logic (mock)
        //allBtn.addActionListener(e -> System.out.println("Visi"));
        //activeBtn.addActionListener(e -> System.out.println("Aktīvie"));
        //doneBtn.addActionListener(e -> System.out.println("Pabeigti"));
    }
}
