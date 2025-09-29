package org.example;

import org.example.view.EligibilityViewPanel;
import org.example.view.PaymentViewPanel;
import org.example.view.RegisterViewPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {

    /**
     * Utility method to temporarily fix JOptionPane button text visibility
     * Call this before showing any JOptionPane dialog to ensure buttons are visible
     */
    public static void setPopupButtonColors() {
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("Button.background", new Color(240, 240, 240));
        UIManager.put("Button.foreground", new Color(50, 50, 50));
        UIManager.put("Button.select", new Color(200, 220, 255));
        UIManager.put("Button.focus", new Color(180, 200, 255));
    }

    /**
     * Utility method to reset JOptionPane button colors back to theme defaults
     * Call this after showing JOptionPane dialogs to restore normal button styling
     */
    public static void resetPopupButtonColors() {
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Button.select", null);
            UIManager.put("Button.focus", null);
        });
    }

    public static void main(String[] args) {
        // --- ENHANCED THEME SETUP ---
        FlatLightLaf.setup();

        // --- MODERN CUSTOM THEME CONFIGURATION ---
        try {
            // Enhanced roundness and modern styling
            UIManager.put("Component.arc", 15);
            UIManager.put("Button.arc", 12);
            UIManager.put("TextComponent.arc", 8);
            UIManager.put("CheckBox.arc", 6);

            // Modern color palette
            Color primaryAccent = new Color(25, 118, 210);  // Modern blue
            Color secondaryAccent = new Color(156, 39, 176); // Modern purple
            Color successColor = new Color(46, 125, 50);     // Modern green

            UIManager.put("Component.accentColor", primaryAccent);
            UIManager.put("Button.default.background", primaryAccent);
            UIManager.put("Button.default.foreground", Color.WHITE);
            UIManager.put("Component.focusColor", primaryAccent);
            UIManager.put("Component.focusedBorderColor", primaryAccent);

            // Enhanced tab styling
            UIManager.put("TabbedPane.selectedBackground", new Color(240, 248, 255));
            UIManager.put("TabbedPane.hoverColor", new Color(230, 240, 255));
            UIManager.put("TabbedPane.focusColor", primaryAccent);
            UIManager.put("TabbedPane.underlineColor", primaryAccent);
            UIManager.put("TabbedPane.disabledUnderlineColor", new Color(200, 200, 200));
            UIManager.put("TabbedPane.tabHeight", 45);

            // Fixed Title Bar and Window Control Button Styling
            UIManager.put("TitlePane.background", primaryAccent);
            UIManager.put("TitlePane.foreground", Color.WHITE);
            UIManager.put("TitlePane.inactiveBackground", new Color(180, 180, 180));
            UIManager.put("TitlePane.inactiveForeground", new Color(100, 100, 100));

            // Window control buttons (minimize, maximize, close) - VISIBLE STYLING
            UIManager.put("TitlePane.buttonHoverBackground", new Color(255, 255, 255, 40));
            UIManager.put("TitlePane.buttonPressedBackground", new Color(255, 255, 255, 60));
            UIManager.put("TitlePane.closeHoverBackground", new Color(232, 17, 35));
            UIManager.put("TitlePane.closePressedBackground", new Color(200, 15, 30));

            // Ensure window control icons/text are visible
            UIManager.put("TitlePane.iconColor", Color.WHITE);
            UIManager.put("TitlePane.buttonForeground", Color.WHITE);
            UIManager.put("TitlePane.buttonDisabledForeground", new Color(255, 255, 255, 100));
            UIManager.put("TitlePane.closeIcon", null); // Use default close icon
            UIManager.put("TitlePane.iconifyIcon", null); // Use default minimize icon
            UIManager.put("TitlePane.maximizeIcon", null); // Use default maximize icon
            UIManager.put("TitlePane.restoreIcon", null); // Use default restore icon

        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("College Exam Management System - LEEMS") {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    // Add subtle gradient background to the frame
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.dispose();
                }
            };

            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Fix popup button colors for exit dialog
                    setPopupButtonColors();

                    int option = JOptionPane.showConfirmDialog(
                        mainFrame,
                        "Are you sure you want to exit LEEMS?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if (option == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }

                    // Reset popup button colors
                    resetPopupButtonColors();
                }
            });

            mainFrame.setSize(1100, 750);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setMinimumSize(new Dimension(900, 600));

            // Create modern tabbed pane with custom styling
            JTabbedPane tabbedPane = new JTabbedPane() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Subtle gradient background
                    Color color1 = new Color(250, 252, 255);
                    Color color2 = new Color(240, 245, 251);
                    int w = getWidth(), h = getHeight();
                    g2d.setPaint(new GradientPaint(0, 0, color1, 0, h, color2));
                    g2d.fillRect(0, 0, w, h);
                }
            };

            tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 15));
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Create panel instances
            RegisterViewPanel registerPanel = new RegisterViewPanel();
            PaymentViewPanel paymentPanel = new PaymentViewPanel();
            EligibilityViewPanel eligibilityPanel = new EligibilityViewPanel();

            // Add tabs with modern icons and styling
            tabbedPane.addTab("   🎓 Student Registration   ", registerPanel);
            tabbedPane.addTab("   💳 Payment Management   ", paymentPanel);
            tabbedPane.addTab("   ✅ Eligibility Queue   ", eligibilityPanel);

            // Set custom colors for each tab
            tabbedPane.setBackgroundAt(0, new Color(232, 245, 233));
            tabbedPane.setBackgroundAt(1, new Color(255, 243, 224));
            tabbedPane.setBackgroundAt(2, new Color(237, 247, 237));

            // Add tab change listener to refresh eligibility table when switching to that tab
            tabbedPane.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    int selectedIndex = tabbedPane.getSelectedIndex();

                    // If Eligibility Queue tab is selected (index 2), refresh the table
                    if (selectedIndex == 2) {
                        System.out.println("Switching to Eligibility Queue tab - refreshing table...");
                        eligibilityPanel.refreshEligibilityTable();
                    }
                }
            });

            // Create main content panel with gradient background
            JPanel mainPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    Color color1 = new Color(245, 250, 255);
                    Color color2 = new Color(225, 240, 255);
                    int w = getWidth(), h = getHeight();
                    g2d.setPaint(new GradientPaint(0, 0, color1, w, h, color2));
                    g2d.fillRect(0, 0, w, h);
                }
            };
            mainPanel.setOpaque(false);
            mainPanel.add(tabbedPane, BorderLayout.CENTER);

            mainFrame.add(mainPanel, BorderLayout.CENTER);
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainFrame.setVisible(true);

            // Show welcome message
            SwingUtilities.invokeLater(() -> {
                // Fix popup button colors for welcome dialog
                setPopupButtonColors();

                JOptionPane.showMessageDialog(
                    mainFrame,
                    "Welcome to LEEMS - Learning Enhancement & Exam Management System\n\nA modern solution for student registration, payment tracking, and exam eligibility management.",
                    "Welcome to LEEMS",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Reset popup button colors
                resetPopupButtonColors();
            });
        });
    }
}

