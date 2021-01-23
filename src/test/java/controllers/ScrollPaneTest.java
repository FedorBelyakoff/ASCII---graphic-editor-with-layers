package controllers;

import com.formdev.flatlaf.IntelliJTheme;
import interfaces.Model;
import interfaces.UILineController;
import ui.Selection;
import ui.StatusBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class ScrollPaneTest extends JFrame {

    public static final int BOTTOM_INDENT = 40;
    public static final int RIGHT_INDENT = 17;
    private Model model;
    private LineTextUi textUi;
    private UILineController controller;
    private StatusBar statusBar;
    private GridBagConstraints gbc;
    private JPanel workspacePanel;
    private JScrollPane scrollPane;
    private Selection selection;
    private JPanel topPanel;

    public ScrollPaneTest() throws HeadlessException {
        setWindowSettings();

        createTopPanel();
//
        createWorkspacePanel();

        createScrollPane();


    }

    private void createTopPanel() {
        topPanel = new JPanel();
        topPanel.setBounds(getBounds());
        topPanel.setLayout(null);
        getLayeredPane().add(topPanel, DEFAULT_LAYER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                topPanel.setBounds(0, 0,
                                 getWidth() - RIGHT_INDENT,
                                 getHeight() - BOTTOM_INDENT);
            }
        });

    }

    private void createWorkspacePanel() {
        workspacePanel = new JPanel();
        workspacePanel.setLayout(new GridBagLayout());
        Rectangle panelBounds = new Rectangle(0, 0, 500, 500);

        Selection selection = getSelection(panelBounds);

        workspacePanel.setPreferredSize(panelBounds.getSize());
//        workspacePanel.setBounds(panelBounds);
        JTextArea area = new JTextArea(20, 50);
        Dimension areaPreferredSize = area.getPreferredSize();
//        area.setBounds(0, 0, areaPreferredSize.width, areaPreferredSize.height);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        workspacePanel.add(area, gbc);


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                workspacePanel.setPreferredSize(area.getPreferredSize());
            }
        });
    }

    private void createScrollPane() {
        scrollPane = new JScrollPane(workspacePanel, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(getBounds());

        topPanel.add(scrollPane);


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.setBounds(0, 0,
                                 getWidth() - RIGHT_INDENT, getHeight() - BOTTOM_INDENT);
            }
        });
    }


    private void setWindowSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }

    private Selection getSelection(Rectangle bounds) {
        Selection selection = new Selection(new Dimension(10, 3), 5, Color.blue);
        selection.setVisible(true);
        selection.setBounds(bounds);
        return selection;
    }


    public static void main(String[] args) {
        IntelliJTheme.install(ScrollPaneTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
        SwingUtilities.invokeLater(ScrollPaneTest::new);
    }
}
