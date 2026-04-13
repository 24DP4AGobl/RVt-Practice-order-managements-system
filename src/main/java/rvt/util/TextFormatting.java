package rvt.util;

import javax.swing.*;
import java.awt.Font;

public class TextFormatting {

    public JLabel Label(String text) {
        JLabel label = new JLabel(text);

        label.setFont(new Font("Arial", Font.PLAIN, 40));

        return label;
    }

    public JLabel text1(String text) {
        JLabel label = new JLabel(text);

        label.setFont(new Font("Arial", Font.PLAIN, 30));

        return label;
    }

    public JLabel text2(String text) {
        JLabel label = new JLabel(text);

        label.setFont(new Font("Arial", Font.PLAIN, 20));

        return label;
    }

    public JLabel text3(String text) {
        JLabel label = new JLabel(text);

        label.setFont(new Font("Arial", Font.PLAIN, 15));

        return label;
    }

    public JLabel text4(String text) {
        JLabel label = new JLabel(text);

        label.setFont(new Font("Arial", Font.PLAIN, 10));

        return label;
    }
}