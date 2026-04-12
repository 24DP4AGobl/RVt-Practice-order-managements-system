package rvt.util;

import javax.swing.*;
import java.awt.*;

public class ButtonFormatting {

    UIColors color = new UIColors();

    public JButton loginButton() {
        JButton loginBtn = new JButton("Login");

        loginBtn.setFont(new Font("Arial", Font.BOLD, 30));
        loginBtn.setForeground(Color.white);
        loginBtn.setBackground(color.button2());

        return loginBtn;
    }

    public JButton sideBarBtn(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.white);
        button.setBackground(color.sideBarBtn());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    public JButton topBarBtn(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.white);
        button.setBackground(color.button2());
        button.setMaximumSize(new Dimension(75, Integer.MAX_VALUE));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }
}
