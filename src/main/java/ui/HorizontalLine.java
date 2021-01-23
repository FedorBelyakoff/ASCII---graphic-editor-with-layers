package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HorizontalLine extends JComponent {
    private final int size;
    private Color color;

    public HorizontalLine() {
        this(1, Color.LIGHT_GRAY);
    }

    public HorizontalLine(int size, Color color) {
        if (size < 1) {
            throw new IndexOutOfBoundsException("Size should be more than zero!");
        }
        this.size = size;
        this.color = Objects.requireNonNull(color);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(size));
        g2d.setColor(color);
        g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
    }
}
