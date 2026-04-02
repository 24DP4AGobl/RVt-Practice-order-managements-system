package rvt;

import javax.swing.*;
import javax.swing.text.AbstractDocument.Content;

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

        BoxLayout boxLayoutManager = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayoutManager);

        JLabel title = new JLabel("Pievienojiet jaunu pasūtījumu");
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setSize(300, 30);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);

        JPanel inputGroup = new JPanel();
        inputGroup.setSize(300, 30);
        inputGroup.setLayout(new BoxLayout(inputGroup, BoxLayout.Y_AXIS));

        inputGroup.add(inputGroup("Pasutijuma_id"));
        inputGroup.add(inputGroup("Datums"));
        inputGroup.add(inputGroup("Summa"));

        String[] statusOptions = {"izpildīts", "Procesā"};
        inputGroup.add(comboBoxGroup("Statuss", statusOptions));

        String[] delivererOptions = {""};
        inputGroup.add(comboBoxGroup("Piegadataja_id", delivererOptions));

        String[] workersOptions = {""};
        inputGroup.add(comboBoxGroup("Darbinieka_id", workersOptions));

        mainPanel.add(inputGroup);

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

    private static JPanel inputGroup(String title) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setSize(100, 50);
        inputPanel.add(label);

        JTextArea area = new JTextArea();
        area.setFont(new Font("Arial", Font.PLAIN, 40));
        area.setLineWrap(true);
        area.setSize(200, 50);
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(area);

        return inputPanel;
    }

    private static JPanel comboBoxGroup(String title, String[] options) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setSize(100, 50);
        panel.add(label);

        JComboBox<String> comboBox = new JComboBox<>(options);
        panel.add(comboBox);

        return panel;
    }
}