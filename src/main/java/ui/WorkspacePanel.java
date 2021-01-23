package ui;

import javax.swing.*;
import java.awt.*;

public class WorkspacePanel extends JPanel {

    private final JTextArea area;
    private GridBagConstraints gbc;

    public WorkspacePanel(JTextArea area) {
        this.area = area;
        createGui();

    }

    private void createGui() {
        setUiSettings();

        createHeightSpacer(0);

        createHeightSpacer(2);

        createWidthSpacer(0);

        createWidthSpacer(2);

        addArea();
    }

    private void setUiSettings() {
        setLayout(new GridBagLayout());
    }

    private void createHeightSpacer(int gridy) {
        JPanel heightSpacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = gridy;
        gbc.weighty = 0.1;
        add(heightSpacer, gbc);
    }

    private void createWidthSpacer(int gridx) {
        JPanel widthSpacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        add(widthSpacer, gbc);
    }

    private void addArea() {
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(area, gbc);
    }
}
