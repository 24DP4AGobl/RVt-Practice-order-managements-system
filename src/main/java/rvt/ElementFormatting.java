package rvt;

import javax.swing.*;
import java.awt.*;

public class ElementFormatting {
    /* ===================== UI HELPERS ===================== */
    public static JButton buttonFormat(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 100));
        button.setMaximumSize(new Dimension(200, 100));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    public static JPanel inputGroup(String title, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 40));

        field.setFont(new Font("Arial", Font.PLAIN, 40));
        field.setPreferredSize(new Dimension(200, 50));

        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.EAST);

        return panel;
    }

    public static JPanel comboBoxGroup(String title, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 40));

        panel.add(label, BorderLayout.WEST);
        panel.add(comboBox, BorderLayout.EAST);

        return panel;
    }

    public static JButton confirmBtn(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 50));
        button.setBackground(new Color(66, 201, 87));
        button.setForeground(Color.white);

        return button;
    }
    public static JButton cancelBtn(String text, Frame frame) {
        JButton button = new JButton(text);
        button.addActionListener(e -> frame.dispose());
        button.setPreferredSize(new Dimension(150, 50));
        button.setBackground(new Color(224, 86, 76));
        button.setForeground(Color.white);

        return button;
    }

    public static JButton listAddBtn() {
        JButton addBtn = new JButton("Pievienot");

        addBtn.setPreferredSize(new Dimension(0, 50));
        addBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        addBtn.setBackground(new Color(66, 201, 87));
        addBtn.setForeground(Color.WHITE);

        return addBtn;
    }

    public static JButton listCancelBtn() {
        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.setPreferredSize(new Dimension(0, 50));
        cancelBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        return cancelBtn;
    }

    public static JButton listRemoveButton() {
        JButton removeBtn = new JButton("Noņemt");

        removeBtn.setPreferredSize(new Dimension(0, 50));
        removeBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        removeBtn.setBackground(new Color(224, 86, 76));
        removeBtn.setForeground(Color.WHITE);

        return removeBtn;
    }

    public static JButton listEditBtn() {
        JButton editBtn = new JButton("Rediģēt");
        editBtn.setPreferredSize(new Dimension(0, 50));
        editBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        return editBtn;
    }

    public static JFrame WindowFormat(String title, Boolean exitOnClose) {
        JFrame frame = new JFrame(title);
        frame.pack();
        frame.setSize(1000, 1000);
        if(exitOnClose){
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        frame.setLocationRelativeTo(null);
        return frame;
    }
}
