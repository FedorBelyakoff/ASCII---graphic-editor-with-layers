package ui;

import interfaces.LayerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Objects;

import static classes.LogicConstraints.*;
import static ui.UiResources.*;

public class NewLayerDialog extends JInternalFrame implements ActionListener {
    private final LayerController layerController;
    private JSpinner heightSpinner;
    private JSpinner widthSpinner;
    private JTextField nameField;
    private JTextField fontField;
    private JButton okButton;
    private JButton cancelButton;

    public NewLayerDialog(LayerController layerController) {
        super(bundle.getString("NewLayerDialog.windowTitle"), true);
        this.layerController = Objects.requireNonNull(layerController);
        createGui();
    }

    private void createGui() {
        setVisible(true);
        setLayout(new GridBagLayout());
        createPanel();
    }

    private void createPanel() {
        final NewLayerDialogPanel dialogPanel = new NewLayerDialogPanel();
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        add(dialogPanel, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();
        if (source == okButton) {
            processFields();
        }
        if (source == cancelButton) {
            closeWindow();
        }
    }

    private void processFields() {
        if (checkNameNotEmpty()) {
            showEmptyNameError();
            return;
        }

        if (!checkWidth()) {
            showIncorrectWidthError();
            return;
        }

        if (!checkHeight()) {
            showIncorrectHeightError();
            return;
        }
        addLayerToController();
        closeWindow();
    }

    private boolean checkNameNotEmpty() {
        return nameField.getText().isEmpty();
    }

    private boolean checkWidth() {
        int value = (int) widthSpinner.getValue();
        return value >= MIN_LAYER_WIDTH &&
                         value <= MAX_LAYER_WIDTH;
    }

    private boolean checkHeight() {
        int value = (int) heightSpinner.getValue();
        return value >= MIN_LAYER_HEIGHT && value <= MAX_LAYER_HEIGHT;
    }

    private void addLayerToController() {
        layerController.addLayer(nameField.getText(),
                         ((int) widthSpinner.getValue()),
                         ((int) heightSpinner.getValue()));
    }

    private void closeWindow() {
        try {
            setClosed(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private void showEmptyNameError() {
        showError(bundle.getString("NewLayer.nameError"));
    }

    private void showIncorrectHeightError() {
        final String s = String.format(
                         bundle.getString("NewLayer.heightError"),
                         MIN_LAYER_HEIGHT, MAX_LAYER_HEIGHT);
        showError(s);
    }

    private void showIncorrectWidthError() {
        final String s = String.format(
                         bundle.getString("NewLayer.widthError"),
                         MIN_LAYER_WIDTH, MAX_LAYER_WIDTH);
        showError(s);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(getParent(), message,
                         bundle.getString("error"),
                         JOptionPane.ERROR_MESSAGE);
    }

    private class NewLayerDialogPanel extends SettingsPanel {
        @Override
        void createGui() {
            widthSpinner = new JSpinner();
            heightSpinner = new JSpinner();
            nameField = new JTextField();
            fontField = new JTextField();
            okButton = new JButton(bundle.getString("ok"));
            cancelButton = new JButton(bundle.getString("cancel"));

            createTitlePanel(0, bundle.getString("NewLayerDialog.title"));
            JPanel panel = createPanel(1, nameField);
            createDescriptionLabel(bundle.getString("name"), panel,
                             0, 10);


            panel = createPanel(2, widthSpinner);
            createDescriptionLabel(bundle.getString("width"),
                             panel, 0, 10);


            panel = createPanel(3, heightSpinner);
            createDescriptionLabel(bundle.getString("height"), panel,
                             0, 10);


            panel = createPanel(4, fontField);
            createDescriptionLabel(bundle.getString("char"), panel,
                             0, 10);

            createHSpacer(0, 5, 0);

            createOkCancelPanel(6, okButton, cancelButton);
        }


    }

}
