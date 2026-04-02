package rvt;

import javax.swing.*;
import java.awt.*;

public class UserInterfaces{
    public void mainMenu(){
        JFrame frame = new JFrame("Pasūtījumu uzskaites sistēma");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(200, 0, 700, 900);

        BoxLayout boxLayoutManager = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayoutManager);

        JLabel title = new JLabel("Pasūtījumu uzskaites sistēma");
        title.setFont(new Font("Serif", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton addOrder = buttonFormat("Pievienot pasūtījumu");
        addOrder.addActionListener((e) -> {
            windowAddOrder();
        });

        JButton viewOrders = buttonFormat("Apskatīt visus pasūtījumus");
        JButton editOrders = buttonFormat("Rediģēt pasūtījumus");
        JButton products = buttonFormat("Apskatīt produktus");

        JLabel warning = new JLabel("Brīdinājums");
        warning.setFont(new Font("Serif", Font.BOLD, 40));
        warning.setAlignmentX(Component.CENTER_ALIGNMENT);
        Panel warningContainer = new Panel();
        warningContainer.add(warning);
        warningContainer.setBackground(Color.RED);

        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(addOrder);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(viewOrders);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(editOrders);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(products);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(warningContainer);
        
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        frame.add(wrapper);

        frame.setVisible(true);
    }

    private void windowAddOrder() {
        JFrame frame = new JFrame();
        frame.setSize(1000, 1000);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(200, 0, 700, 900);

        BoxLayout boxLayoutManager = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayoutManager);

        JLabel title = new JLabel("Pievienojiet jaunu pasūtījumu");
        title.setFont(new Font("Serif", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel dataInputPanel = new JPanel();
        dataInputPanel.setLayout(new BoxLayout(dataInputPanel, BoxLayout.Y_AXIS));

        JPanel order_id = inputPanel("pasutijums_id");
        JPanel date = inputPanel("datums");
        JPanel summa = inputPanel("summa");

        JPanel panelStatus = new JPanel();
        JLabel labelStatus = new JLabel("statuss");
        labelStatus.setFont(new Font("Serif", Font.BOLD, 25));

        String[] statusOptions = {"Izpildīts", "Procesā"};
        JComboBox<String> statusBox = new JComboBox<>(statusOptions);
        
        panelStatus.add(labelStatus);
        panelStatus.add(statusBox);

        JPanel worker_id = inputPanel("darbinieka_id");

        dataInputPanel.add(order_id);
        dataInputPanel.add(date);
        dataInputPanel.add(summa);
        dataInputPanel.add(panelStatus);
        dataInputPanel.add(worker_id);

        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(dataInputPanel);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        frame.add(wrapper);

        frame.setVisible(true);
    }

    private static JButton buttonFormat(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 100));
        button.setMaximumSize(new Dimension(200, 100));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    private static JPanel inputPanel(String title) {
        Dimension dimension = new Dimension(150, 25);
        JPanel panel = new JPanel();
        JLabel label = new JLabel(title);

        panel.add(label);
        label.setFont(new Font("Serif", Font.BOLD, 25));
        JTextArea area = new JTextArea();
        area.setFont(new Font("Serif", Font.PLAIN, 20));
        area.setPreferredSize(dimension);
        panel.add(area);

        return panel;
    }
}