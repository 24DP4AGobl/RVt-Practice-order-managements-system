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


        JLabel Rediģēt = text.text3("Rediģēt");
        Rediģēt.setForeground(Color.white);

        JButton dashboardBtn = btn.sideBarBtn("Galvenā");
        JButton productsBtn = btn.sideBarBtn("Produkti");
        JButton employeesBtn = btn.sideBarBtn("Darbinieki");
        JButton catBtn = btn.sideBarBtn("Kategorijas");
        JButton statusBtn = btn.sideBarBtn("Statusi");


        JLabel Apskatīt = text.text3("Apskatīt");
        Apskatīt.setForeground(Color.white);

        JButton ordersBtn = btn.sideBarBtn("Pasūtījumi");

        sideBar.add(dashboardBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 20)));
        sideBar.add(productsBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(employeesBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(catBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 10)));
        sideBar.add(statusBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0, 30)));
        sideBar.add(Apskatīt);
        sideBar.add(Box.createRigidArea(new Dimension(0, 5)));
        sideBar.add(ordersBtn);

        // 🔹 Content
        //JPanel content = new JPanel(new BorderLayout());

        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);
        JScrollPane scroll = new JScrollPane();

        content.add(new ProductPanel(),  "products");
        productsBtn.addActionListener(e -> cardLayout.show(content, "products"));
        content.add(new EmployeePanel(), "employees");
        employeesBtn.addActionListener(e -> cardLayout.show(content, "employees"));
        content.add(new CategoryPanel(), "categories");
        catBtn.addActionListener(e -> cardLayout.show(content, "categories"));
        //content.add(new StatusPanel(), "statuses");
        //employeesBtn.addActionListener(e -> cardLayout.show(content, "statuses"));
        //content.add(new DelivererPanel(), "deliverers");
        employeesBtn.addActionListener(e -> cardLayout.show(content, "deliverers"));
        content.add(new OrderPanel(), "orders");
        ordersBtn.addActionListener(e -> cardLayout.show(content, "orders"));

        scroll.add(content);

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
