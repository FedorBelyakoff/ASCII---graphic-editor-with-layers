package ui;

import interfaces.DimensionsController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DimensionPanel extends SettingsPanel implements ActionListener {
    private final JSpinner widthSpinner;
    private final JSpinner heightSpinner;
    private DimensionsController controller;

    public DimensionPanel() {
        widthSpinner = new JSpinner(new SpinnerNumberModel());
        heightSpinner = new JSpinner(new SpinnerNumberModel());

        createGui();


    }

    public int getWidth() {
        return (int) widthSpinner.getValue();
    }

    public int getHeight() {
        return (int) heightSpinner.getValue();
    }

    public void setWidth(int width) {
        widthSpinner.setValue(width);
    }

    public void setHeight(int height) {
        heightSpinner.setValue(height);
    }

    public void setController(DimensionsController controller) {
        this.controller = controller;
    }

    @Override
    void createGui() {

        createTitlePanel(0, bundle.getString("dimensions"));

        JPanel widthPanel = createPanel(1, widthSpinner);
        createPanel(2, widthSpinner);

        createDescriptionLabel(bundle.getString("width"),
                         widthPanel, 0, 10);

        JPanel heightPanel = createPanel(2, heightSpinner);
        createDescriptionLabel(bundle.getString("height"),
                         heightPanel, 0, 10);

        createBottomSpacer(3);

        createApplyPanel(4, bundle.getString("applyButton"));

        addListeners();
    }

    private void addListeners() {
        getApplyButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller == null) {
            return;
        }

        if (getApplyButton() == e.getSource()) {
            controller.changeDimension(getWidth(), getHeight(),this);
        }
    }
}
