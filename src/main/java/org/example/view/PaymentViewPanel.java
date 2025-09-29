package org.example.view;

import org.example.model.Payment;
import org.example.model.Student;
import org.example.controller.PaymentController;
import org.example.service.PaymentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class PaymentViewPanel extends JPanel {

    private JTextField searchField;
    private JPanel studentListPanel;
    // Track student payments
    private Map<String, Double> studentPayments = new HashMap<>();
    // Track students added to queue
    private static Set<String> studentsInQueue = new HashSet<>();
    private PaymentController controller;

    public PaymentViewPanel() {
        super(new BorderLayout(10, 10));
        setOpaque(false);

        // Initialize controller
        controller = new PaymentController(this);

        // Initialize sample data
        studentPayments.put("S001", 1000.00);
        studentPayments.put("S002", 700.00);
        studentPayments.put("S003", 0.00);

        // --- Modern Gradient Header ---
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 102, 204);
                Color color2 = new Color(0, 180, 255);
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, color1, w, h, color2));
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setBorder(new EmptyBorder(18, 28, 18, 28));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Student Payment Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);
        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Search by ID or Name...");
        JButton searchButton = new JButton("Search");
        searchButton.putClientProperty("JButton.buttonType", "roundRect");
        searchButton.setBackground(new Color(255, 255, 255, 220));
        searchButton.setForeground(new Color(0, 102, 204));
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Hover effect
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                searchButton.setBackground(new Color(220, 240, 255));
            }
            public void mouseExited(MouseEvent e) {
                searchButton.setBackground(new Color(255, 255, 255, 220));
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        studentListPanel = new JPanel();
        studentListPanel.setLayout(new BoxLayout(studentListPanel, BoxLayout.Y_AXIS));
        studentListPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        studentListPanel.setOpaque(false);

        // Fill the cards using linked list
        loadStudentsFromLinkedList();

        JScrollPane scrollPane = new JScrollPane(studentListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> controller.handleSearch());
    }

    /**
     * Load students from the LinkedStudentList and display them as cards
     */
    private void loadStudentsFromLinkedList() {
        studentListPanel.removeAll();

        if (PaymentService.isStudentListEmpty()) {
            // Show empty state
            JLabel emptyLabel = new JLabel("No students found. Register students to see them here.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setForeground(new Color(120, 120, 120));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            studentListPanel.add(Box.createVerticalGlue());
            studentListPanel.add(emptyLabel);
            studentListPanel.add(Box.createVerticalGlue());
        } else {
            // Get all students from the linked list
            Student[] allStudents = PaymentService.getStudentsAsArray();

            // Add each student as a card
            for (Student student : allStudents) {
                addStudentCardFromLinkedList(student);
            }
        }

        studentListPanel.revalidate();
        studentListPanel.repaint();
    }

    /**
     * Add a student card using Student object from LinkedStudentList
     */
    private void addStudentCardFromLinkedList(Student student) {
        boolean isEligible = student.isEligible();
        boolean isInQueue = studentsInQueue.contains(student.getStudentId());

        JPanel card = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = isEligible ? new Color(230, 255, 240) : new Color(255, 240, 240);
                Color color2 = isEligible ? new Color(210, 255, 230) : new Color(255, 220, 220);
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, color1, w, h, color2));
                g2d.fillRoundRect(0, 0, w, h, 18, 18);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(isEligible ? new Color(40, 167, 69, 120) : new Color(220, 53, 69, 120), 2, true),
                new EmptyBorder(15, 20, 15, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Drop shadow effect
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 8, 0, new Color(0,0,0,18)),
                card.getBorder()
        ));

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(isEligible ? new Color(40, 167, 69) : new Color(220, 53, 69), 3, true),
                        new EmptyBorder(15, 20, 15, 20)
                ));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(isEligible ? new Color(40, 167, 69, 120) : new Color(220, 53, 69, 120), 2, true),
                        new EmptyBorder(15, 20, 15, 20)
                ));
            }
        });

        // Student information from linked list
        JLabel nameLabel = new JLabel(String.format("%s (%s)", student.getFullName(), student.getStudentId()));
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(new Color(30, 30, 30));

        JLabel totalFeesLabel = new JLabel(String.format("Total Fees: %.2f LKR", student.getTotalFees()));
        totalFeesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        totalFeesLabel.setForeground(new Color(80, 80, 80));

        JLabel paidLabel = new JLabel(String.format("Paid: %.2f LKR", student.getPaidAmount()));
        paidLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        paidLabel.setForeground(new Color(0, 102, 204));

        JLabel statusLabel = new JLabel(isEligible ? "✅ ELIGIBLE" : "❌ NOT ELIGIBLE");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(isEligible ? new Color(40, 167, 69) : new Color(220, 53, 69));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 2));
        infoPanel.setOpaque(false);
        infoPanel.add(nameLabel);
        infoPanel.add(totalFeesLabel);
        infoPanel.add(paidLabel);

        // Button panel for payment actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setOpaque(false);

        // Set Payment button
        JButton setPaymentBtn = createModernButton("💰 Set Payment", new Color(255, 152, 0));
        setPaymentBtn.addActionListener(e -> controller.handleSetPayment(student.getStudentId(), student.getFullName()));

        buttonPanel.add(setPaymentBtn);

        // Add to Queue button (only show if eligible and not already in queue)
        if (isEligible && !isInQueue) {
            JButton addToQueueBtn = createModernButton("✅ Add to Queue", new Color(76, 175, 80));
            addToQueueBtn.addActionListener(e -> {
                controller.handleAddToQueue(student.getStudentId(), student.getFullName());
                // Add student to queue tracking set
                studentsInQueue.add(student.getStudentId());
                // Refresh the view to hide the button
                refreshStudentCards();
            });
            buttonPanel.add(addToQueueBtn);
        } else if (isEligible && isInQueue) {
            // Show "In Queue" status instead of button
            JLabel inQueueLabel = new JLabel("🎯 In Queue");
            inQueueLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            inQueueLabel.setForeground(new Color(76, 175, 80));
            inQueueLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
            buttonPanel.add(inQueueLabel);
        }

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(statusLabel, BorderLayout.WEST);
        card.add(buttonPanel, BorderLayout.EAST);

        studentListPanel.add(card);
        studentListPanel.add(Box.createVerticalStrut(16));
    }

    /**
     * Public method to mark a student as added to queue (called from controller)
     */
    public static void markStudentInQueue(String studentId) {
        studentsInQueue.add(studentId);
    }

    /**
     * Public method to remove a student from queue tracking (if needed)
     */
    public static void removeStudentFromQueue(String studentId) {
        studentsInQueue.remove(studentId);
    }

    /**
     * Check if a student is already in queue
     */
    public static boolean isStudentInQueue(String studentId) {
        return studentsInQueue.contains(studentId);
    }

    /**
     * Show search results in the student list panel
     */
    public void showSearchResults(Student[] searchResults) {
        studentListPanel.removeAll();

        if (searchResults.length == 0) {
            JLabel noResultsLabel = new JLabel("No students found matching your search.");
            noResultsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            noResultsLabel.setForeground(new Color(120, 120, 120));
            noResultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            studentListPanel.add(Box.createVerticalGlue());
            studentListPanel.add(noResultsLabel);
            studentListPanel.add(Box.createVerticalGlue());
        } else {
            for (Student student : searchResults) {
                addStudentCardFromLinkedList(student);
            }
        }

        studentListPanel.revalidate();
        studentListPanel.repaint();
    }

    /**
     * Refresh the student list and update header count
     */
    public void refreshStudentCards() {
        // Update to use linked list instead of hardcoded data
        loadStudentsFromLinkedList();

        // Clear search field to show we're showing all students again
        searchField.setText("");
    }

    /**
     * Public method to refresh the view (can be called from outside)
     */
    public void refreshView() {
        SwingUtilities.invokeLater(() -> {
            refreshStudentCards();
        });
    }

    // Getter methods for controller access
    public JTextField getSearchField() {
        return searchField;
    }

    public Map<String, Double> getStudentPayments() {
        return studentPayments;
    }

    public void showPaymentDialog(String studentId, String studentName) {
        // Temporarily set JOptionPane button colors for visibility
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("Button.background", new Color(240, 240, 240));
        UIManager.put("Button.foreground", new Color(50, 50, 50));
        UIManager.put("Button.select", new Color(200, 220, 255));

        JDialog paymentDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Set Payment", true);
        paymentDialog.setSize(400, 250);
        paymentDialog.setLocationRelativeTo(this);
        paymentDialog.setLayout(new BorderLayout(10, 10));

        // Header panel
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 152, 0);
                Color color2 = new Color(255, 193, 7);
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, color1, w, h, color2));
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel headerLabel = new JLabel("💰 Set Payment for " + studentName + " (" + studentId + ")");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel currentLabel = new JLabel("Current Payment: " + String.format("%.2f LKR", PaymentService.getPaymentAmount(studentId)));
        currentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contentPanel.add(currentLabel, gbc);

        JLabel newLabel = new JLabel("New Payment Amount:");
        newLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        contentPanel.add(newLabel, gbc);

        JTextField paymentField = new JTextField(15);
        paymentField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        paymentField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 2, true),
                new EmptyBorder(12, 15, 12, 15)
        ));
        paymentField.setPreferredSize(new Dimension(200, 40));
        paymentField.setMinimumSize(new Dimension(180, 40));

        // Add focus effects to make it more visible
        paymentField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                paymentField.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(25, 118, 210), 2, true),
                        new EmptyBorder(12, 15, 12, 15)
                ));
                paymentField.setBackground(Color.WHITE);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                paymentField.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 2, true),
                        new EmptyBorder(12, 15, 12, 15)
                ));
                paymentField.setBackground(new Color(250, 250, 250));
            }
        });

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(paymentField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton confirmBtn = createModernButton("✅ Confirm", new Color(76, 175, 80));
        JButton cancelBtn = createModernButton("❌ Cancel", new Color(244, 67, 54));

        confirmBtn.addActionListener(e -> {
            if (controller.handlePaymentConfirmation(studentId, studentName, paymentField.getText(), paymentDialog)) {
                // Dialog is closed by controller if successful
            }
        });

        cancelBtn.addActionListener(e -> paymentDialog.dispose());

        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);

        paymentDialog.add(headerPanel, BorderLayout.NORTH);
        paymentDialog.add(contentPanel, BorderLayout.CENTER);
        paymentDialog.add(buttonPanel, BorderLayout.SOUTH);

        paymentDialog.setVisible(true);

        // Reset UIManager properties after dialog
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Button.select", null);
        });
    }


    private JButton createModernButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                Color color1 = getBackground();
                Color color2 = getBackground().darker();
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, h, color2));
                g2d.fillRoundRect(0, 0, w, h, 8, 8);

                // Ensure text is properly rendered
                g2d.setColor(getForeground());
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                String buttonText = getText();
                if (buttonText != null && !buttonText.isEmpty()) {
                    int x = (w - fm.stringWidth(buttonText)) / 2;
                    int y = (h - fm.getHeight()) / 2 + fm.getAscent();
                    g2d.drawString(buttonText, x, y);
                }
                g2d.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 30));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.brighter());
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
                button.repaint();
            }
        });

        return button;
    }
}
