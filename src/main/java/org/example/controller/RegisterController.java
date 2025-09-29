package org.example.controller;

import org.example.view.RegisterViewPanel;
import org.example.service.PaymentService;
import org.example.model.Student;
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
            try {
                // Create Student object from form data
                Student newStudent = createStudentFromForm();

                // Add student to the static linked list
                PaymentService.addStudentLast(newStudent);

                showSuccessMessage();
                clearForm();

                // Optional: Display current list size for confirmation
                System.out.println("Student registered successfully. Total students in list: " +
                                 PaymentService.getStudentListSize());

            } catch (Exception e) {
                showErrorMessage("Failed to register student: " + e.getMessage());
            }
        } else {
            showErrorMessage("Please fill in all required fields correctly.");
        }
    }

    /**
     * Create Student object from form data
     */
    private Student createStudentFromForm() {
        String studentId = view.getIdField().getText().trim();
        String fullName = view.getNameField().getText().trim();
        String email = view.getEmailField().getText().trim();
        String phone = view.getPhoneField().getText().trim();
        String selectedCourse = (String) view.getCourseComboBox().getSelectedItem();
        String totalFeesText = view.getTotalFeesField().getText().trim();

        // Check if student ID already exists
        if (PaymentService.containsStudent(studentId)) {
            throw new RuntimeException("Student ID already exists in the system");
        }

        // Validate course selection
        if (selectedCourse == null || selectedCourse.equals("Select Course")) {
            throw new RuntimeException("Please select a valid course");
        }

        // Parse and validate total fees
        double totalFees = 0.0;
        if (!totalFeesText.isEmpty()) {
            try {
                totalFees = Double.parseDouble(totalFeesText);
                if (totalFees < 0) {
                    throw new RuntimeException("Total fees cannot be negative");
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Please enter a valid amount for total fees");
            }
        }

        // Split full name into first and last name
        String[] nameParts = fullName.split("\\s+", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        // Default paid amount for new registration
        double paidAmount = 0.0;

        return new Student(studentId, firstName, lastName, email, phone,
                          selectedCourse, totalFees, paidAmount);
    }

    /**
     * Validate the registration form
     */
    private boolean validateForm() {
        String totalFeesText = view.getTotalFeesField().getText().trim();
        String selectedCourse = (String) view.getCourseComboBox().getSelectedItem();

        boolean basicValidation = !view.getIdField().getText().trim().isEmpty() &&
                !view.getNameField().getText().trim().isEmpty() &&
                !view.getEmailField().getText().trim().isEmpty() &&
                !view.getPhoneField().getText().trim().isEmpty() &&
                Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(view.getEmailField().getText()).matches() &&
                Pattern.compile("^[0-9]{10}$").matcher(view.getPhoneField().getText()).matches();

        boolean courseValid = selectedCourse != null && !selectedCourse.equals("Select Course");

        boolean feesValid = true;
        if (!totalFeesText.isEmpty()) {
            try {
                double fees = Double.parseDouble(totalFeesText);
                feesValid = fees >= 0;
            } catch (NumberFormatException e) {
                feesValid = false;
            }
        }

        return basicValidation && courseValid && feesValid;
    }

    /**
     * Clear all form fields
     */
    private void clearForm() {
        view.getIdField().setText("");
        view.getNameField().setText("");
        view.getEmailField().setText("");
        view.getPhoneField().setText("");
        view.getTotalFeesField().setText("");
        view.getCourseComboBox().setSelectedIndex(0); // Reset to "Select Course"
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

    /**
     * Handle clear button click
     */
    public void handleClearForm() {
        clearForm();
    }
}
