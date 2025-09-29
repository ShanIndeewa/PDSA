package org.example.controller;

import org.example.view.RegisterViewPanel;
import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class RegisterController {

    private RegisterViewPanel view;

    public RegisterController(RegisterViewPanel view) {
        this.view = view;
    }

    /**
     * Handle register button click
     */
    public void handleRegisterStudent() {
        if (validateForm()) {
            // TODO: Add actual registration logic here
            showSuccessMessage();
            clearForm();
        } else {
            showErrorMessage("Please fill in all required fields correctly.");
        }
    }

    /**
     * Handle clear button click
     */
    public void handleClearForm() {
        clearForm();
    }

    /**
     * Validate the registration form
     */
    private boolean validateForm() {
        return !view.getIdField().getText().trim().isEmpty() &&
                !view.getNameField().getText().trim().isEmpty() &&
                !view.getEmailField().getText().trim().isEmpty() &&
                !view.getPhoneField().getText().trim().isEmpty() &&
                Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(view.getEmailField().getText()).matches() &&
                Pattern.compile("^[0-9]{10}$").matcher(view.getPhoneField().getText()).matches();
    }

    /**
     * Clear all form fields
     */
    private void clearForm() {
        view.getIdField().setText("");
        view.getNameField().setText("");
        view.getEmailField().setText("");
        view.getPhoneField().setText("");
        view.getStatusLabel().setText(" ");
        view.getIdField().requestFocus();
    }

    /**
     * Show success message
     */
    private void showSuccessMessage() {
        // Temporarily set JOptionPane button colors for visibility
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("Button.background", new Color(240, 240, 240));
        UIManager.put("Button.foreground", new Color(50, 50, 50));
        UIManager.put("Button.select", new Color(200, 220, 255));

        view.getStatusLabel().setText("✅ Student registered successfully!");
        view.getStatusLabel().setForeground(new Color(46, 125, 50));

        Timer timer = new Timer(3000, e -> view.getStatusLabel().setText(" "));
        timer.setRepeats(false);
        timer.start();

        // Reset UIManager properties after a delay
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Button.select", null);
        });
    }

    /**
     * Show error message
     */
    private void showErrorMessage(String message) {
        // Temporarily set JOptionPane button colors for visibility
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("Button.background", new Color(240, 240, 240));
        UIManager.put("Button.foreground", new Color(50, 50, 50));
        UIManager.put("Button.select", new Color(200, 220, 255));

        view.getStatusLabel().setText("❌ " + message);
        view.getStatusLabel().setForeground(new Color(244, 67, 54));

        Timer timer = new Timer(5000, e -> view.getStatusLabel().setText(" "));
        timer.setRepeats(false);
        timer.start();

        // Reset UIManager properties after a delay
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Button.select", null);
        });
    }
}
