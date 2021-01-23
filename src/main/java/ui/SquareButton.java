package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SquareButton extends JButton {

    private final int size;
    private final IconReader reader;

    public SquareButton(int size, IconReader reader) {
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
