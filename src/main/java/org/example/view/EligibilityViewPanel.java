package org.example.view;

import org.example.controller.EligibilityController;
import org.example.model.Student;
import org.example.service.PaymentService;
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
import java.util.List;

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

        String[] columnNames = {"#", "Student ID", "Student Name", "Total Fees", "Paid Amount", "Date Eligible"};
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

        // Load eligible students from the linked list
//        loadEligibleStudentsFromLinkedList();
        addStudentToTable();
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setOpaque(false);

        removeButton = createModernButton("🗑️ Remove Selected Student", new Color(244, 67, 54));
        JButton refreshButton = createModernButton("🔄 Refresh Queue", new Color(76, 175, 80));

        // Use controller for button clicks
        removeButton.addActionListener(e -> controller.handleRemoveSelectedStudent());
        refreshButton.addActionListener(e -> refreshEligibilityTable());

        buttonPanel.add(refreshButton);
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
     * Load eligible students from the LinkedStudentList and display them in the table
     */
    private void loadEligibleStudentsFromLinkedList() {
        // Clear existing table data first
        tableModel.setRowCount(0);

        // Get eligible students from PaymentService
        List<Student> eligibleStudents = PaymentService.getEligibleStudents();
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        if (eligibleStudents.isEmpty()) {
            // Show message when no eligible students
            System.out.println("No eligible students found in the linked list.");
            return;
        }

        // Add each eligible student to the table
        for (int i = 0; i < eligibleStudents.size(); i++) {
            Student student = eligibleStudents.get(i);
            tableModel.addRow(new Object[]{
                i + 1,  // Position number (1-based)
                student.getStudentId(),
                student.getFullName(),
                String.format("%.2f LKR", student.getTotalFees()),
                String.format("%.2f LKR", student.getPaidAmount()),
                currentDate
            });
        }

        System.out.println("Loaded " + eligibleStudents.size() + " eligible students into the eligibility table.");
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
                String.format("%.2f LKR", student.getTotalFees()),
                String.format("%.2f LKR", student.getPaidAmount()),
                currentDate
            });
        }

        System.out.println("Loaded " + students.length + " students into the eligibility table.");
    }

    /**
     * Refresh the eligibility table - called when switching to this tab or when payments are updated
     */
    public void refreshEligibilityTable() {
        System.out.println("Refreshing eligibility table with fresh data from LinkedStudentList...");

        // Load eligible students from the linked list
//        loadEligibleStudentsFromLinkedList();
        addStudentToTable();

        // Update the title to show current count
        Component[] components = getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                JLabel titleLabel = (JLabel) comp;
                if (titleLabel.getText().contains("Eligibility Queue")) {
                    int eligibleCount = PaymentService.countEligibleStudents();
                    titleLabel.setText("Exam Eligibility Queue (" + eligibleCount + " eligible students)");
                    break;
                }
            }
        }

        System.out.println("Eligibility table refreshed successfully!");
    }

    public void refreshTable() {
        refreshEligibilityTable();
    }
}