package rvt.util;

import javax.swing.*;
import java.awt.Font;

public class FieldFormatting {

    public JTextField size1() {
        JTextField field = new JTextField();

        field.setFont(new Font("Arial", Font.PLAIN, 30));

        return field;
    }

    public JTextField size2() {
        JTextField field = new JTextField();

        field.setFont(new Font("Arial", Font.PLAIN, 20));

        return field;
    }

    public JPasswordField passize1() {
        JPasswordField field = new JPasswordField();

        field.setFont(new Font("Arial", Font.PLAIN, 30));

        return field;
    }

    public JPasswordField passize2() {
        JPasswordField field = new JPasswordField();

        field.setFont(new Font("Arial", Font.PLAIN, 20));

        return field;
    }

}
