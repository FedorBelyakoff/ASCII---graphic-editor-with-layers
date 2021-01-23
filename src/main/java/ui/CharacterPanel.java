package ui;

import interfaces.CharController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterPanel extends SettingsPanel implements ActionListener {
    private final JTextField textField;
    private CharController controller;

    public CharacterPanel(int fontSize) {
        super(fontSize);
        textField = new JTextField();
        createGui();
        addListeners();
    }

    public char getChar() {
        return textField.getText().charAt(0);
    }

    public void setChar(char ch) {
        textField.setText(String.valueOf(ch));
    }

    public void setController(CharController controller) {
        this.controller = controller;
    }

    @Override
    void createGui() {
        createTitlePanel(0, bundle.getString("settingsTitle"));
        JPanel panel = createPanel(1, textField);
        createDescriptionLabel(bundle.getString("ToolSettingsWindow.character"),
                         panel, 0, 10);
        createBottomSpacer(4);
        createApplyPanel(5, bundle.getString("applyButton"));
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
            controller.changeChar(this);
        }
    }
}
