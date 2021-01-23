package ui;

import classes.ControllerBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static ui.UiResources.*;

public class ToolButtonsPanel extends JPanel implements ActionListener, ItemListener {
    private static final int BUTTON_SIZE = 35;
    private JToggleButton selection;
    private JToggleButton brush;
    private JToggleButton line;
    private JToggleButton fill;
    private JToggleButton eraser;
    private JToggleButton text;
    private ControllerBuilder builder;
    private JToggleButton selectedItem;

    public ToolButtonsPanel() {
        setUiSettings();

        createButtons();
    }

    private void setUiSettings() {
        final MyLayoutManager buttonLayout = new MyLayoutManager(10, 15,
                         new Insets(15, 20, 10, 20));
        setLayout(buttonLayout);
    }


    private void createButtons() {
        selection = createButton("/newButtons/select.png",
                         bundle.getString("ToolSettingsWindow.select"));
        brush = createButton("/newButtons/brush.png",
                         bundle.getString("ToolSettingsWindow.brush"));
        line = createButton("/newButtons/line.png",
                         bundle.getString("ToolSettingsWindow.line"));
        fill = createButton("/newButtons/fill.png",
                         bundle.getString("ToolSettingsWindow.fill"));
        eraser = createButton("/newButtons/eraser.png",
                         bundle.getString("ToolSettingsWindow.eraser"));

        text = createButton("/newButtons/text.png",
                         bundle.getString("ToolSettingsWindow.text"));
    }

    private ToolButton createButton(
                                    String defaultImageUrl, String typeText) {
        final ToolButton button =
                         new ToolButton(BUTTON_SIZE, new IconReader(defaultImageUrl));
        button.setBackground(getBackground()); // TODO: 19.12.2020 Подумать над передачей фона
        button.setToolTipText(typeText);
//        button.addActionListener(this);
        button.addItemListener(this);
        add(button);
        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object item = e.getItem();

        if (selectedItem != null) {
            selectedItem.setSelected(false);
        }

        selectedItem = (JToggleButton) item;

        if (selection == item) {
            builder.buildSelectionController();
        }
        if (brush == item) {
            builder.buildBrushController();
        }
        if (line == item) {
            builder.buildLineController();
        }
        if (fill == item) {
            builder.buildFillController();
        }
        if (eraser == item) {
            builder.buildEraserController();
        }
        if (text == item) {
            builder.buildTextController();
        }

    }
}
