package rvt.util;

import javax.swing.JOptionPane;

public class ErrorHandler {

    public static void showError(String userMessage, Exception e) {
        String technical = e.getMessage();

        JOptionPane.showMessageDialog(
            null,
            userMessage + "\n\nDetaļas: " + technical,
            "Kļūda",
            JOptionPane.ERROR_MESSAGE
        );

        e.printStackTrace(); // still useful for you
    }

    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(
            null,
            message,
            "Info",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}