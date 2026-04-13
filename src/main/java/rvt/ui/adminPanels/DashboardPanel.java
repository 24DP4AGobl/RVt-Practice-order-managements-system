package rvt.ui.adminPanels;

import java.util.List;
import javax.swing.*;
import java.awt.*;

import rvt.model.Order;
import rvt.service.OrderService;
import rvt.util.ErrorHandler;
import rvt.util.ButtonFormatting;
import rvt.util.TextFormatting;
import rvt.util.UIColors;

public class DashboardPanel extends JPanel {
            
    JTable table;
    OrderService service = new OrderService();
    ButtonFormatting btn = new ButtonFormatting();
    TextFormatting text = new TextFormatting();
    UIColors color = new UIColors();

    public DashboardPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));

        //add(new JScrollPane(table));

        JLabel orderLabel = text.text1("Pasūtījumi");
        JLabel orderAmount = text.text2("Kopā: " + getTotalOrders());
        JLabel activeOrderAmount = text.text2("Aktīvi: " + getTotalActiveOrders());
        JLabel completeOrderAmount = text.text2("Izpildīti: " + getTotalCompleteOrders());

        add(orderLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(orderAmount);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(completeOrderAmount);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(activeOrderAmount);
    }

    private int getTotalOrders() {
        try {
            List<Order> orders = service.getAllOrders();
            return orders.size();
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
            return 0;
        }
    }

    private int getTotalCompleteOrders() {
        try {
            List<Order> orders = service.getOrders(2, null);
            return orders.size();
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
            return 0;
        }
    }

    private int getTotalActiveOrders() {
        try {
            List<Order> orders = service.getOrders(1, null);
            return orders.size();
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot", e);
            return 0;
        }
    }
}
