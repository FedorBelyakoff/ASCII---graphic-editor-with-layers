package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Selection extends JComponent {
    private final Image horizontalImage;
    private final Image verticalImage;
    private final Dimension horizontalStrokeDimension;
    private final Dimension verticalStrokeDimension;
    private final Color color;
    private final int indentBetweenStrokes;
    private boolean visible;

    public Selection(Dimension horizontalStrokeDim, int indent,
                     Color color) {
        assert indent > 0;

        this.indentBetweenStrokes = indent;
        this.color = Objects.requireNonNull(color);
        this.horizontalStrokeDimension = Objects.requireNonNull(horizontalStrokeDim);
        this.verticalStrokeDimension = new Dimension(horizontalStrokeDim.height,
                         horizontalStrokeDim.width);

        horizontalImage = drawStrokeImage(horizontalStrokeDim);
        verticalImage = rotateOn90(horizontalImage);

        setVisible(true);
    }

    private Image rotateOn90(Image image) {
        final int bufWidth = image.getHeight(null);
        final int bufHeight = image.getWidth(null);
        final BufferedImage buffer = new BufferedImage(bufWidth,
                         bufHeight, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g = buffer.createGraphics();
        g.rotate(Math.PI / 2, bufWidth, 0);
        g.drawImage(image, bufWidth, 0, null);
        return buffer;
    }

    private Image drawStrokeImage(Dimension strokeDimension) {
        int width = strokeDimension.width;
        int height = strokeDimension.height;

        //Here we multiply the size,
        // because we can't draw a small image correctly
        width *= 100;
        height *= 100;
        final BufferedImage image = new BufferedImage(width,
                         height, BufferedImage.TYPE_INT_ARGB);
        final Graphics g = image.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, height, height);
        g.fillOval(width - height, 0,
                         height, height);
        g.fillRect(height / 2, 0, width - height, height);

        //here the image is compressed back
        width /= 100;
        height /= 100;

        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    public void setVisible(boolean aFlag) {
        visible = aFlag;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!visible) {
            return;
        }
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        drawHorizontalStrokes(g2d, 0);
        drawHorizontalStrokes(g2d, getHeight()
                         - horizontalStrokeDimension.height);
        drawVerticalStrokes(g2d, 0);
        drawVerticalStrokes(g2d, getWidth()
                         - verticalStrokeDimension.width);

    }

    private void drawVerticalStrokes(Graphics2D g, int x) {
        for (int y = 0; y < getHeight(); ) {

            g.drawImage(verticalImage,
                             x, y, null);

            y += verticalStrokeDimension.height + indentBetweenStrokes;
        }
    }


    private void drawHorizontalStrokes(Graphics2D g, int y) {
        for (int x = 0; x < getWidth(); ) {
            g.drawImage(horizontalImage, x, y, null);

//            g.drawLine(i, y, i + imgDim.width, y);
            x += horizontalStrokeDimension.width + indentBetweenStrokes;
        }
    }


}
