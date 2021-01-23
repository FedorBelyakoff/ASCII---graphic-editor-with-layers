package ui;

import javax.swing.*;
import java.awt.*;

import static ui.UiResources.*;

public class ToolWindowFactoryImpl implements ToolWindowFactory {
    private final ToolsWindow window;
    private final int buttonSize;

    private GridBagConstraints gbc;
    private JPanel toolButtonsPanel;

    public ToolWindowFactoryImpl(ToolsWindow window, int buttonSize) {
        this.window = window;
        this.buttonSize = buttonSize;
    }

    @Override
    public ToolsWindow createWindow() {
        setWindowSettings();


        createTopSpacer();

        createToolPanel();

        createButtons();

        createSeparator();
        return window;
    }

    private void createSeparator() {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = .1;
        window.add(new HorizontalLine(), gbc);
    }

    private void setWindowSettings() {
        window.setLayout(new GridBagLayout());
        window.setBounds(0, 0, 350, 300);
        window.setMinimumSize(new Dimension(330, 280));
        window.setResizable(true);
        window.setVisible(true);
        window.setTitle(bundle.getString("ToolSettingsWindow.title"));
    }

    private void createTopSpacer() {
        JPanel spacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 10;
        window.add(spacer, gbc);
    }

    private void createButtons() {
        createButton("/newButtons/select.png",
                         bundle.getString("ToolSettingsWindow.select"));
        createButton("/newButtons/brush.png",
                         bundle.getString("ToolSettingsWindow.brush"));
        createButton("/newButtons/line.png",
                         bundle.getString("ToolSettingsWindow.line"));
        createButton("/newButtons/oval.png",
                         bundle.getString("ToolSettingsWindow.oval"));
        createButton("/newButtons/fill.png",
                         bundle.getString("ToolSettingsWindow.fill"));
        createButton("/newButtons/eraser.png",
                         bundle.getString("ToolSettingsWindow.eraser"));
        createButton("/newButtons/zoom.png",
                         bundle.getString("ToolSettingsWindow.zoom"));
        createButton("/newButtons/text.png",
                         bundle.getString("ToolSettingsWindow.text"));
    }

    private void createToolPanel() {
        final MyLayoutManager panelLayout = new MyLayoutManager(10, 15,
                         new Insets(15, 20, 10, 20));
        toolButtonsPanel = new JPanel(panelLayout);

        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.ipady = 25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        window.add(toolButtonsPanel, gbc);
    }

    private void createButton(String defaultImage, String typeText) {
        final ToolButton button =
                         new ToolButton(buttonSize, new IconReader(defaultImage));
        button.setBackground(window.getBackground());

//        button.setRolloverImage(selectedImage);
//        button.setSelectedImage(selectedImage);

//        button.addItemListener(this);
        button.setToolTipText(typeText);
//        button.setFocusPainted(false);
//        button.setContentAreaFilled(false);

        toolButtonsPanel.add(button);
//        lineButton.setMaximumSize(new Dimension(buttonSize, buttonSize));

//        currentSelectedButton = button;
    }

}
