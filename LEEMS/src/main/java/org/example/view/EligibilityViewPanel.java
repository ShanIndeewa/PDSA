package org.example.view;

import org.example.controller.EligibilityController;
import org.example.model.Student;
import org.example.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EligibilityViewPanel extends JPanel {

    private JTable eligibilityTable;
    private DefaultTableModel tableModel;
    private JButton removeButton;
    private EligibilityController controller;
    private StudentService studentService;

    public EligibilityViewPanel() {
        super(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(20, 20, 20, 20));
        setOpaque(false);

        // Initialize controller
        controller = new EligibilityController(this);
        studentService = new StudentService();

        JLabel titleLabel = new JLabel("Exam Eligibility Queue");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 18, 0));

        String[] columnNames = {"#", "Student ID", "Student Name", "Date Eligible"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        eligibilityTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 250, 255) : new Color(230, 240, 255));
                } else {
                    c.setBackground(new Color(200, 220, 255));
                }
                c.setForeground(new Color(40, 40, 40));
                return c;
            }
        };
        eligibilityTable.setRowHeight(36);
        eligibilityTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        eligibilityTable.setShowHorizontalLines(true);
        eligibilityTable.setShowVerticalLines(false);
        eligibilityTable.setGridColor(new Color(200, 220, 255));
        eligibilityTable.setSelectionBackground(new Color(180, 210, 255));
        eligibilityTable.setSelectionForeground(new Color(0, 60, 120));
        eligibilityTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = eligibilityTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(eligibilityTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 250, 255));

        // Create modern remove button panel
        JPanel buttonPanel = createButtonPanel();

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Load sample students when we move to this view
        loadSampleStudents();
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setOpaque(false);

        removeButton = createModernButton("🗑️ Remove Selected Student", new Color(244, 67, 54));

        // Use controller for button click
        removeButton.addActionListener(e -> controller.handleRemoveSelectedStudent());

        buttonPanel.add(removeButton);
        return buttonPanel;
    }

    // Getter methods for controller access
    public JTable getEligibilityTable() {
        return eligibilityTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    private JButton createModernButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color color1 = getBackground();
                Color color2 = getBackground().darker();
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, h, color2));
                g2d.fillRoundRect(0, 0, w, h, 12, 12);

                // Button text
                g2d.setColor(getForeground());
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (w - fm.stringWidth(getText())) / 2;
                int y = (h + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 40));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    /**
     * Load sample students into the queue and display them in the table
     */
    private void loadSampleStudents() {
        // Add some sample eligible students to the service queue

//        Student student2 = new Student("S002", "Jane", "Smith", "jane.smith@email.com",
//                                     "0779876543", "Information Technology", 14000.0, 10500.0);
//        Student student3 = new Student("S004", "Sarah", "Williams", "sarah.williams@email.com",
//                                     "0772223333", "Data Science", 17000.0, 15300.0);
//        Student student4 = new Student("S007", "Alex", "Wilson", "alex.wilson@email.com",
//                                     "0776667777", "Information Technology", 14000.0, 11200.0);
//        Student student5 = new Student("S008", "Lisa", "Taylor", "lisa.taylor@email.com",
//                                     "0773334444", "Software Engineering", 16000.0, 12800.0);

        // Add eligible students to the service queue

//        if (student2.isEligible()) studentService.addStudent(student2);
//        if (student3.isEligible()) studentService.addStudent(student3);
//        if (student4.isEligible()) studentService.addStudent(student4);
//        if (student5.isEligible()) studentService.addStudent(student5);

        // Now populate the table by calling addStudentToTable
        addStudentToTable();
    }

    /**
     * Add students from the service queue to the table
     */
    public void addStudentToTable() {
        // Clear existing table data first
        tableModel.setRowCount(0);

        // Get all students from the queue at once
        Student[] students = studentService.getAllStudents();
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // Add each student to the table
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            tableModel.addRow(new Object[]{
                i + 1,  // Position number (1-based)
                student.getStudentId(),
                student.getFullName(),
                currentDate
            });
        }

        System.out.println("Loaded " + students.length + " eligible students into the eligibility table.");
    }

    /**
     * Refresh the eligibility table - called when switching to this tab
     */
    public void refreshEligibilityTable() {
        System.out.println("Refreshing eligibility table with fresh data...");

        // Clear the current queue and reload fresh sample data
        loadSampleStudents();

        System.out.println("Eligibility table refreshed successfully!");
    }

    public void refreshTable() {
        addStudentToTable();
    }
}