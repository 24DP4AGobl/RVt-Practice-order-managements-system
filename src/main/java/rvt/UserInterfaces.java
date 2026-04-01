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
        JButton viewOrders = buttonFormat("Apskatīt visus pasūtījumus");
        JButton manageWarehouse = buttonFormat("Pārvaldīt noliktavu");
        
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
        mainPanel.add(manageWarehouse);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(warningContainer);
        
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
}