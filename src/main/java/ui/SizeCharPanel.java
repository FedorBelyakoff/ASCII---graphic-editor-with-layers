package ui;

import interfaces.SizeCharController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SizeCharPanel extends SettingsPanel implements ItemListener, ActionListener {
    private final JSpinner sizeSpinner;
    private final JTextField charField;

    private SizeCharController controller;

    public SizeCharPanel(int fontSize) {
        super(fontSize);
        sizeSpinner = new JSpinner(new SpinnerNumberModel());
        charField = new JTextField();

        createGui();

    }

    private void addListeners() {
        getApplyButton().addActionListener(this);
    }

    public int getsSize() {
        return (int) sizeSpinner.getValue();
    }


    public char getChar() {
        return charField.getText().charAt(0);
    }


    public void setSize(int size) {
        sizeSpinner.setValue(size);
    }

    public void setChar(char ch) {
        charField.setText(String.valueOf(ch));
    }

    public void setController(SizeCharController controller) {
        this.controller = controller;
    }

    @Override
    void createGui() {

        createTitlePanel(0, bundle.getString("settingsTitle"));

        JPanel charPanel = createPanel(1, charField);
        createDescriptionLabel(bundle.getString("ToolSettingsWindow.character"),
                         charPanel, 0, 10);
        JPanel sizePanel = createPanel(2, sizeSpinner);
        createDescriptionLabel(bundle.getString("ToolSettingsWindow.size"),
                         sizePanel, 0, 10);


        createBottomSpacer(3);
        createApplyPanel(4, bundle.getString("applyButton"));

        addListeners();
    }




    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller == null) {
            return;
        }

        if (getApplyButton() == e.getSource()) {
            controller.changeChar(this);
            controller.changeSize(this);
        }
    }
}
