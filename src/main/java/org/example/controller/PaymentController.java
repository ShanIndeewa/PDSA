package org.example.controller;

import org.example.model.Student;
import org.example.service.PaymentService;
import org.example.service.StudentService;
import org.example.view.PaymentViewPanel;
import javax.swing.*;
import java.awt.*;

public class PaymentController {

    private PaymentViewPanel view;
    private StudentService studentService;

    public PaymentController(PaymentViewPanel view) {
        this.view = view;
        this.studentService = new StudentService();
    }

    /**
     * Handle search button click
     */
    public void handleSearch() {
        String searchText = view.getSearchField().getText().trim();

        if (searchText.isEmpty()) {
            // If search is empty, show all students
            view.refreshStudentCards();
            return;
        }

        // Use PaymentService to search in the LinkedStudentList
        Student[] results = PaymentService.searchStudents(searchText);

        if (results.length == 0) {
            JOptionPane.showMessageDialog(view, "No students found for: " + searchText, "Search Results", JOptionPane.INFORMATION_MESSAGE);
            // Still refresh to show current state
            view.refreshStudentCards();
        } else {
            // Update the view to show only search results
            view.showSearchResults(results);
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
                // Update payment in the LinkedStudentList
                boolean success = PaymentService.updateStudentPayment(studentId, newPayment);

                if (success) {
                    dialog.dispose();
                    view.refreshStudentCards(); // Refresh to show updated data

                    // Check if student is now eligible (60% of total fees)
                    Student updatedStudent = PaymentService.findStudentById(studentId);
                    boolean isNowEligible = updatedStudent != null && updatedStudent.isEligible();

                    // Show success message
                    String message = "Payment updated successfully!\n\n" +
                                   "Student: " + studentName + " (" + studentId + ")\n" +
                                   "New Payment: " + String.format("%.2f LKR", newPayment);

                    if (isNowEligible) {
                        message += "\n\n🎉 Student is now eligible for exam queue!";
                        message += "\nThe eligibility queue will be updated automatically.";
                    }

                    JOptionPane.showMessageDialog(view, message, "Payment Updated", JOptionPane.INFORMATION_MESSAGE);

                    // Auto-refresh eligibility table if student became eligible
                    if (isNowEligible) {
                        System.out.println("Student " + studentId + " became eligible - triggering eligibility table refresh");
                        // Note: In a real application, you might use an observer pattern or event system
                        // For now, we rely on the tab change listener in Main.java to refresh when user switches tabs
                    }

                    return true;
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update payment. Student not found.", "Update Failed", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
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
            // Get the student from PaymentService
            Student student = PaymentService.findStudentById(studentId);

            if (student != null && student.isEligible()) {
                // Add student to the LinkedQueue in StudentService
                studentService.addStudent(student);

                JOptionPane.showMessageDialog(
                    view,
                    studentName + " has been added to the eligibility queue!\n" +
                    "Queue size: " + studentService.getQueueSize() + " students",
                    "Added to Queue",
                    JOptionPane.INFORMATION_MESSAGE
                );

                System.out.println("Student " + studentId + " (" + studentName + ") added to LinkedQueue");
                System.out.println("Current queue size: " + studentService.getQueueSize());

            } else {
                JOptionPane.showMessageDialog(
                    view,
                    "Student is not eligible or not found.",
                    "Cannot Add to Queue",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }

        // Reset UIManager properties
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Button.select", null);
        });
    }
}
