package ui;

import enumerations.ToolTypes;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

class ToolButton extends JToggleButton {
    private final IconReader reader;
    private final int size;

    public ToolButton(int size,
                      IconReader reader) {
        this.size = size;
        this.reader = Objects.requireNonNull(reader);
        createGui();
    }

    private void createGui() {
        setIcon(reader.readDefault(size));
        setMaximumSize(new Dimension(size, size));
        setBorderPainted(false);
    }




}
