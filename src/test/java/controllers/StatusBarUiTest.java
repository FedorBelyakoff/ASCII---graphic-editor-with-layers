package controllers;

import com.formdev.flatlaf.IntelliJTheme;
import interfaces.Model;
import interfaces.UILineController;
import ui.StatusBar;
import ui.TextWorkspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatusBarUiTest extends JFrame {

    private JLayeredPane pane;
    private JTextArea area;
    private TextWorkspace workspace;
    private Model model;
    private LineTextUi textUi;
    private UILineController controller;
    private StatusBar statusBar;
    private JPanel workspacePanel;
    private GridBagConstraints gbc;

    public StatusBarUiTest() throws HeadlessException {
        setWindowSettings();

        createPane();

        createWorkspacePanel();

        createWorkspace();


        createStatusBar();

        createMouseController();
    }



    private void createMouseController() {
        area.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                statusBar.setMousePosition(workspace.resolve(e.getX(), e.getY()));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                statusBar.setMousePosition(workspace.resolve(e.getX(), e.getY()));
            }
        });
    }

    private void createStatusBar() {

        statusBar = new StatusBar();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                displayStatusBar();
            }
        });

        pane.add(statusBar, JLayeredPane.PALETTE_LAYER);

        displayStatusBar();
    }

    private void displayStatusBar() {
        int y = getHeight() - statusBar.getSize().height - ScrollPaneTest.BOTTOM_INDENT;
        statusBar.setLocation(0, y);
    }

    private void createWorkspace() {


        workspace = new TextWorkspace(area);
        Dimension size = area.getPreferredSize();
        area.setBounds(50, 20, size.width, size.height);


//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 1;
//        pane.add(area, JLayeredPane.DEFAULT_LAYER);
    }

    private void createPane() {
        pane = getLayeredPane();
    }

    private void setWindowSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
    }


    private void createWorkspacePanel() {
        addWorkspacePanel();

        createHeightSpacer(0);

        createHeightSpacer(2);

        createWidthSpacer(0);

        createWidthSpacer(2);

        createArea();
    }

    private void createWidthSpacer(int gridy) {
        JPanel leftSpacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = gridy;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        workspacePanel.add(leftSpacer, gbc);
    }

    private void addWorkspacePanel() {
//        workspacePanel = new JPanel(new GridBagLayout());
        workspacePanel = new JPanel(new GridBagLayout());

        setWorkspacePanelSettings();

        JScrollPane scrollPane = new JScrollPane(workspacePanel);
        pane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);

//        scrollPane.add(workspacePanel);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.setSize(getWidth(), getHeight());
            }
        });
    }

    private void setWorkspacePanelSettings() {
        workspacePanel.setLocation(0, 0);
        workspacePanel.setSize(getWidth(), getHeight());
        workspacePanel.setBackground(Color.LIGHT_GRAY);
        workspacePanel.setSize(1000, 1000);
        workspacePanel.setMinimumSize(new Dimension(1000, 1000));
    }

    private void createHeightSpacer(int gridy) {
        JPanel bottomSpacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = gridy;
        gbc.weighty = 0.1;
        workspacePanel.add(bottomSpacer, gbc);
    }

    private void createArea() {
        area = new JTextArea(20, 40);
        area.setFocusable(false);
        area.setEditable(false);
        area.setMinimumSize(area.getPreferredSize());
        area.setBackground(Color.WHITE);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
//        gbc.weightx = 0.1;
        workspacePanel.add(area, gbc);
    }


    public static void main(String[] args) {
        IntelliJTheme.install(StatusBarUiTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
//        IntelliJTheme.install(SelectionUiTest.class.getResourceAsStream("/Solarized Light.theme.json"));
        SwingUtilities.invokeLater(StatusBarUiTest::new);
    }
}
