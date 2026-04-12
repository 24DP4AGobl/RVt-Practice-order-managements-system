package rvt.util;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;

import rvt.model.Employee;

public class UtilPanels {

    ButtonFormatting text = new ButtonFormatting();

    public UtilPanels() {
    }

    public JPanel userPanel(Employee employee) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);

        String displayName = String.format("%s (%s %s)", 
                        employee.getUsername(), 
                        employee.getName(), 
                        employee.getSurname());

        JButton button = text.topBarBtn(displayName);

        button.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panel.add(button);

        return panel;
    }
}
