package org.example.controller;

import org.example.service.StudentService;
import org.example.view.EligibilityViewPanel;
import javax.swing.*;
import java.awt.*;

public class EligibilityController {

    private EligibilityViewPanel view;
    private StudentService studentService;


    public EligibilityController(EligibilityViewPanel view) {
        this.view = view;
        studentService = new StudentService();
    }

    /**
     * Handle remove selected student button click
     */
    public void handleRemoveSelectedStudent() {
        studentService.removeStudent();
        view.refreshEligibilityTable();
        int selectedRow = view.getEligibilityTable().getSelectedRow();
        if (selectedRow != -1) {
            String studentId = (String) view.getTableModel().getValueAt(selectedRow, 1);
            String studentName = (String) view.getTableModel().getValueAt(selectedRow, 2);

            // Temporarily set JOptionPane button colors for visibility
            UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
            UIManager.put("Button.background", new Color(240, 240, 240));
            UIManager.put("Button.foreground", new Color(50, 50, 50));
            UIManager.put("Button.select", new Color(200, 220, 255));

            // Show confirmation dialog
            int option = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to remove " + studentName + " (" + studentId + ") from the eligibility queue?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (option == JOptionPane.YES_OPTION) {

                JOptionPane.showMessageDialog(
                    view,
                    "Remove button clicked for: " + studentName + " (" + studentId + ")\n\nRemoval logic not implemented yet.",
                    "Button Click Event",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }

            // Reset UIManager properties after dialogs
            SwingUtilities.invokeLater(() -> {
                UIManager.put("Button.background", null);
                UIManager.put("Button.foreground", null);
                UIManager.put("Button.select", null);
            });
        } else {
            // Temporarily set JOptionPane button colors for visibility
            UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
            UIManager.put("Button.background", new Color(240, 240, 240));
            UIManager.put("Button.foreground", new Color(50, 50, 50));
            UIManager.put("Button.select", new Color(200, 220, 255));

            JOptionPane.showMessageDialog(
                view,
                "Please select a student from the eligibility queue to remove.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );

            // Reset UIManager properties after dialog
            SwingUtilities.invokeLater(() -> {
                UIManager.put("Button.background", null);
                UIManager.put("Button.foreground", null);
                UIManager.put("Button.select", null);
            });
        }
    }
}
