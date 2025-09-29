package org.example.controller;

import org.example.model.Student;
import org.example.service.StudentService;
import org.example.view.PaymentViewPanel;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentController {

    private PaymentViewPanel view;
    private StudentService studentService;

    public PaymentController(PaymentViewPanel view) {
        this.view = view;
        studentService = new StudentService();
    }

    /**
     * Handle search button click
     */
    public void handleSearch() {
        String searchText = view.getSearchField().getText();
        java.util.List<org.example.model.Student> results = studentService.searchStudents(searchText);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No students found for: " + searchText, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder sb = new StringBuilder();
            for (org.example.model.Student s : results) {
                sb.append("ID: ").append(s.getStudentId())
                  .append(", Name: ").append(s.getFullName())
                  .append(", Course: ").append(s.getCourse())
                  .append(", Paid: ").append(s.getPaidAmount())
                  .append("\n");
            }
            JOptionPane.showMessageDialog(view, sb.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handle set payment button click
     */
    public void handleSetPayment(String studentId, String studentName) {
        view.showPaymentDialog(studentId, studentName);
    }

    /**
     * Handle payment confirmation
     */
    public boolean handlePaymentConfirmation(String studentId, String studentName, String paymentText, JDialog dialog) {
        try {
            double newPayment = Double.parseDouble(paymentText.trim());
            if (newPayment >= 0) {
                // TODO: Add actual payment update logic here
                view.getStudentPayments().put(studentId, newPayment);
                dialog.dispose();
                view.refreshStudentCards();

                // Show success message
                String message = "Payment updated successfully!\n\n" +
                               "Student: " + studentName + " (" + studentId + ")\n" +
                               "New Payment: " + String.format("%.2f LKR", newPayment);

                if (newPayment >= 1000) {
                    message += "\n\n🎉 Student is now eligible for exam queue!";
                }

                JOptionPane.showMessageDialog(view, message, "Payment Updated", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid positive amount.", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Please enter a valid numeric amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Handle add to eligibility queue button click
     */
    public void handleAddToQueue(String studentId, String studentName) {
        // Temporarily set JOptionPane button colors for visibility
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("Button.background", new Color(240, 240, 240));
        UIManager.put("Button.foreground", new Color(50, 50, 50));
        UIManager.put("Button.select", new Color(200, 220, 255));

        int option = JOptionPane.showConfirmDialog(
            view,
            "Add " + studentName + " (" + studentId + ") to the eligibility queue?",
            "Add to Eligibility Queue",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            Student student = new Student();
            student.setStudentId(studentId);
            student.setFirstName(studentName);
            student.setPaymentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            studentService.addStudent(student);

            JOptionPane.showMessageDialog(
                view,
                "✅ " + studentName + " (" + studentId + ") has been added to the eligibility queue!\n\n" +
                "Date Added: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                "Added to Queue",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        // Reset UIManager properties after dialogs
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Button.select", null);
        });
    }
}
