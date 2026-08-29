package com.sunrisedental.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Global UI Theme and Component Styling Utility for Sunrise Dental System.
 * Provides high-contrast colors, modern Segoe UI typography, and polished Swing widgets.
 */
public class UITheme {

    private UITheme() {}

    // Color Palette - High Contrast & Modern Aesthetics
    public static final Color BG_LIGHT = new Color(248, 250, 252);     // Crisp slate background
    public static final Color CARD_BG = Color.WHITE;                   // Clean white card background
    public static final Color PRIMARY = new Color(14, 116, 144);       // Deep Cyan / Medical Blue
    public static final Color PRIMARY_DARK = new Color(8, 80, 100);    // Hover state for primary
    public static final Color SUCCESS = new Color(16, 149, 103);       // Emerald Green
    public static final Color SUCCESS_DARK = new Color(13, 115, 80);
    public static final Color WARNING = new Color(217, 119, 6);        // Amber / Orange
    public static final Color DANGER = new Color(225, 29, 72);         // Crimson Red
    public static final Color DANGER_DARK = new Color(190, 18, 60);

    // Text & Border Colors
    public static final Color TEXT_MAIN = new Color(15, 23, 42);       // Dark Slate (High Contrast)
    public static final Color TEXT_MUTED = new Color(100, 116, 139);   // Medium Gray for labels
    public static final Color TEXT_LIGHT = Color.WHITE;                // Light text on dark buttons
    public static final Color BORDER = new Color(203, 213, 225);       // Soft slate border
    public static final Color INPUT_BG = Color.WHITE;

    // Typography
    public static final Font FONT_HEADER_LARGE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADER_MED = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BTN = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 13);

    /**
     * Creates a styled Primary action button (Medical Blue with white text).
     */
    public static JButton createPrimaryButton(String text) {
        return createStyledButton(text, PRIMARY, PRIMARY_DARK, TEXT_LIGHT);
    }

    /**
     * Creates a styled Success action button (Emerald Green with white text).
     */
    public static JButton createSuccessButton(String text) {
        return createStyledButton(text, SUCCESS, SUCCESS_DARK, TEXT_LIGHT);
    }

    /**
     * Creates a styled Secondary/Outline button (White background with dark border & text).
     */
    public static JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BTN);
        btn.setBackground(Color.WHITE);
        btn.setForeground(TEXT_MAIN);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            new EmptyBorder(10, 18, 10, 18)
        ));
        btn.getModel().addChangeListener(e -> {
            if (btn.getModel().isRollover()) {
                btn.setBackground(new Color(241, 245, 249));
            } else {
                btn.setBackground(Color.WHITE);
            }
        });
        return btn;
    }

    /**
     * Creates a styled Danger button (Crimson Red with white text).
     */
    public static JButton createDangerButton(String text) {
        return createStyledButton(text, DANGER, DANGER_DARK, TEXT_LIGHT);
    }

    private static JButton createStyledButton(String text, Color normalColor, Color hoverColor, Color textColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(normalColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(textColor);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Applies rounded borders, padding, and readable font to text fields.
     */
    public static void styleTextField(JTextField field) {
        field.setFont(FONT_INPUT);
        field.setForeground(TEXT_MAIN);
        field.setBackground(INPUT_BG);
        field.setCaretColor(TEXT_MAIN);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
    }

    /**
     * Applies styling to JComboBox dropdowns.
     */
    public static void styleComboBox(JComboBox<?> combo) {
        combo.setFont(FONT_INPUT);
        combo.setForeground(TEXT_MAIN);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            new EmptyBorder(4, 6, 4, 6)
        ));
    }

    /**
     * Styles JTable with dark high-contrast headers, alternating row colors, and row height.
     */
    public static void styleTable(JTable table) {
        table.setFont(FONT_INPUT);
        table.setForeground(TEXT_MAIN);
        table.setBackground(Color.WHITE);
        table.setRowHeight(36);
        table.setGridColor(new Color(226, 232, 240));
        table.setSelectionBackground(new Color(224, 242, 254));
        table.setSelectionForeground(new Color(12, 74, 96));
        table.setShowVerticalLines(false);

        // Header Custom Renderer (guarantees dark slate background with bold white text on all L&Fs)
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 42));
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setBackground(new Color(15, 23, 42)); // Deep Slate Dark Header
                lbl.setForeground(Color.WHITE);           // Bright Pure White Text
                lbl.setOpaque(true);
                lbl.setHorizontalAlignment(JLabel.LEFT);
                lbl.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(51, 65, 85)),
                    new EmptyBorder(8, 12, 8, 12)
                ));
                return lbl;
            }
        });

        // Cell Renderer for clear text and padding
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (comp instanceof JLabel) {
                    JLabel lbl = (JLabel) comp;
                    lbl.setBorder(new EmptyBorder(6, 12, 6, 12));
                    lbl.setForeground(isSelected ? new Color(12, 74, 96) : TEXT_MAIN);
                    if (!isSelected) {
                        lbl.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                    }
                }
                return comp;
            }
        });
    }

    /**
     * Creates a standard styled White Card Panel with a soft border and padding.
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            new EmptyBorder(20, 24, 20, 24)
        ));
        return panel;
    }
}
