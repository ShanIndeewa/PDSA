package org.example.view;

import org.example.controller.RegisterController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

public class RegisterViewPanel extends JPanel {

    private final JTextField idField, nameField, emailField, phoneField;
    private JButton registerButton, clearButton;
    private final JLabel statusLabel;
    private RegisterController controller;

    public RegisterViewPanel() {
        super(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Initialize controller
        controller = new RegisterController(this);

        // Create main content panel with modern card-like design
        JPanel mainCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Modern card background with subtle gradient
                Color color1 = new Color(255, 255, 255, 250);
                Color color2 = new Color(248, 252, 255, 250);
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, h, color2));
                g2d.fillRoundRect(0, 0, w, h, 20, 20);

                // Subtle shadow effect
                g2d.setColor(new Color(0, 0, 0, 15));
                g2d.fillRoundRect(5, 5, w - 5, h - 5, 20, 20);
            }
        };
        mainCard.setOpaque(false);
        mainCard.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainCard.setPreferredSize(new Dimension(600, 650));

        // Modern header section
        JPanel headerPanel = createHeaderPanel();

        // Form section with modern styling
        JPanel formPanel = createFormPanel();

        // Initialize form fields with modern styling
        idField = createModernTextField("Enter student ID (e.g., S001)");
        nameField = createModernTextField("Enter full name");
        emailField = createModernTextField("Enter email address");
        phoneField = createModernTextField("Enter phone number");

        // Add form fields to the form panel
        addFormFieldToPanel(formPanel, "🆔 Student ID:", idField, 0);
        addFormFieldToPanel(formPanel, "👤 Full Name:", nameField, 1);
        addFormFieldToPanel(formPanel, "📧 Email Address:", emailField, 2);
        addFormFieldToPanel(formPanel, "📱 Phone Number:", phoneField, 3);

        // Button panel with modern design
        JPanel buttonPanel = createButtonPanel();

        // Status label for feedback
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Assemble the main card
        mainCard.add(headerPanel, BorderLayout.NORTH);
        mainCard.add(formPanel, BorderLayout.CENTER);
        mainCard.add(buttonPanel, BorderLayout.SOUTH);

        // Center the card in the main panel
        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.setOpaque(false);
        centeringPanel.add(mainCard);

        this.add(centeringPanel, BorderLayout.CENTER);
        this.add(statusLabel, BorderLayout.SOUTH);

        // Add action listeners
        setupEventHandlers();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color color1 = new Color(25, 118, 210);
                Color color2 = new Color(33, 150, 243);
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0, 0, color1, w, h, color2));
                g2d.fillRoundRect(0, 0, w, h, 15, 15);
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("✨ New Student Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Please fill in all required information");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(255, 255, 255, 180));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(30, 20, 20, 20));
        return formPanel;
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !hasFocus()) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(150, 150, 150));
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = getInsets().left;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2d.drawString(placeholder, x, y);
                }
            }
        };

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 2, true),
                new EmptyBorder(12, 15, 12, 15)
        ));
        field.setBackground(new Color(250, 250, 250));

        // Add focus effects
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(25, 118, 210), 2, true),
                        new EmptyBorder(12, 15, 12, 15)
                ));
                field.setBackground(Color.WHITE);
                field.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                validateField(field);
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(220, 220, 220), 2, true),
                        new EmptyBorder(12, 15, 12, 15)
                ));
                field.setBackground(new Color(250, 250, 250));
                field.repaint();
            }
        });

        return field;
    }

    private void addFormFieldToPanel(JPanel panel, String labelText, JTextField field, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(70, 70, 70));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        registerButton = createModernButton("🎓 Register Student", new Color(25, 118, 210), true);
        clearButton = createModernButton("🗑️ Clear Form", new Color(158, 158, 158), false);

        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    private JButton createModernButton(String text, Color baseColor, boolean isPrimary) {
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

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(baseColor);
        button.setForeground(isPrimary ? Color.WHITE : new Color(80, 80, 80));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 45));

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

    private void setupEventHandlers() {
        registerButton.addActionListener(e -> controller.handleRegisterStudent());
        clearButton.addActionListener(e -> controller.handleClearForm());
    }

    // Getter methods for controller access
    public JTextField getIdField() {
        return idField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    // Keep field validation method in view since it's UI-related
    private void validateField(JTextField field) {
        String text = field.getText().trim();
        boolean isValid = true;

        if (field == emailField && !text.isEmpty()) {
            Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
            isValid = emailPattern.matcher(text).matches();
        } else if (field == phoneField && !text.isEmpty()) {
            Pattern phonePattern = Pattern.compile("^[0-9]{10}$");
            isValid = phonePattern.matcher(text).matches();
        }

        if (!isValid) {
            field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(244, 67, 54), 2, true),
                    new EmptyBorder(12, 15, 12, 15)
            ));
        }
    }
}