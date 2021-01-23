package ui;

import enumerations.ToolTypes;
import interfaces.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static enumerations.ToolTypes.*;

public class ToolsWindow extends JInternalFrame {
    public static final int WINDOW_HEIGHT = 190;
    public static final int WINDOW_WIDTH = 320;
    private JPanel toolButtonsPanel;
    private MainController controller;


    private ToolButton currentSelectedButton;
    private GridBagConstraints gbc;
    private SettingsPanel settingsPanel;
    private final ResourceBundle bundle =
                     ResourceBundle.getBundle("Bundle", Locale.getDefault(), new Utf8Control());

    public ToolsWindow() {

        createWindow();
    }

    public ToolsWindow(String title, MainController controller) {
        super(title);
        this.controller = controller;

        createWindow();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }


    public void createWindow() {
        setWindowSettings();

        createTopSpacer();

        createSeparator();
    }

    public void setButtonsPanel(JPanel buttonsPanel) {
        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.ipady = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonsPanel, gbc);
    }

    private void createSeparator() {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = .1;
        add(new HorizontalLine(), gbc);
    }

    private void setWindowSettings() {
        setLayout(new GridBagLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setResizable(true);
        setVisible(true);
        setTitle(bundle.getString("ToolSettingsWindow.title"));
    }

    private void createTopSpacer() {
        JPanel spacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 10;
        add(spacer, gbc);
    }



    public void setSettingsPanel(SettingsPanel panel) {
        Objects.requireNonNull(panel);

        if (this.settingsPanel != null) {
            remove(this.settingsPanel);
        }
        this.settingsPanel = panel;
        gbc = new GridBagConstraints();
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.weighty = 0.1;
        gbc.ipady = panel.getPreferredHeight();
        gbc.fill = GridBagConstraints.BOTH;
        add(panel, gbc);
        setMinimumSize(new Dimension(WINDOW_WIDTH,
                         WINDOW_HEIGHT + panel.getPreferredHeight()));
        setSize(getMinimumSize());
    }


}
