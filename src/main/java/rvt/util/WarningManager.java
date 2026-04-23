package rvt.util;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import java.util.ArrayList;

import rvt.service.ProductService;
import rvt.model.Product;

public class WarningManager extends JPanel{

    ProductService service = new ProductService();

    TextFormatting text = new TextFormatting();
    UIColors color = new UIColors();
    ButtonFormatting btn = new ButtonFormatting();

    List<Integer> productQuantities = new ArrayList<>();
    private JPanel messagePanel;

    public WarningManager() {
        setLayout(new BorderLayout());
        setBackground(color.low());

        JButton reloadBtn = btn.tableOption("Atjaunot datus", color.editButton());
        reloadBtn.addActionListener(e -> {
            RefreshData();
        });

        messagePanel = new JPanel();

        add(reloadBtn, BorderLayout.EAST);
        add(messagePanel, BorderLayout.WEST);

        RefreshData();
    }

    private void WarningMessage(String option) {
        if(option == "low") {
            messagePanel.setBackground(color.low());
            messagePanel.add(text.text2("Viens vai vairāki produkti ir palikuši maz"));
        } else if(option == "medium") {
            messagePanel.setBackground(color.medium());
            messagePanel.add(text.text2("Viens vai vairāki produkti drīz paliks ļoti maz"));
        } else if(option == "high") {
            messagePanel.setBackground(color.high());
            messagePanel.add(text.text2("Produktu ir pietiekami"));
        }

        add(messagePanel);
    }

    private void quantityLoader(List<Product> products) {
        try {
            for (Product o : products) {
                productQuantities.add(o.getAmount());
            }
        } catch (Exception e) {
            ErrorHandler.showError("kļūda ielādējot tabulu", e);
        }
    }

    private void loadData() {
        try {
            List<Product> products = service.getAllProducts();
            quantityLoader(products);
        } catch (Exception e) {
            ErrorHandler.showError("Kļūda ielādējot datus", e);
        }
    }

    private void RefreshData() {
        productQuantities.clear();
        messagePanel.removeAll();
        
        loadData();

        if (!productQuantities.isEmpty()) {
            int minimum = productQuantities.stream().min(Integer::compare).get();
            
            String level = (minimum < 4) ? "low" : (minimum < 25) ? "medium" : "high";
            WarningMessage(level);
        }

        messagePanel.revalidate();
        messagePanel.repaint();
    }
}
