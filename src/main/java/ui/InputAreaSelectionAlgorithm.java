package ui;

import javax.swing.*;
import java.awt.*;

import static java.awt.Cursor.*;

public class InputAreaSelectionAlgorithm extends SelectionAlgorithm {

    public InputAreaSelectionAlgorithm(JComponent selection, int borderSize) {
        super(selection, borderSize);
    }


    protected void resizeSelection(int dx, int dy) {
        int xPos = selection.getX();
        int yPos = selection.getY();
        int width = selection.getWidth();
        int height = selection.getHeight();

        if (onLeft) {
            xPos += dx;
            width -= dx;
        } else if (onRight) {
            width += dx;
            cursorX += dx;
        }

        if (onTop) {
            yPos += dy;
            height -= dy;
        } else if (onBottom) {
            height += dy;
            cursorY += dy;
        }


        selection.setBounds(xPos, yPos, width, height);
    }

    protected void updateCursor() {
        if (onTop && onLeft) {
            selection.setCursor(getPredefinedCursor(NW_RESIZE_CURSOR));
        } else if (onBottom && onLeft) {
            selection.setCursor(getPredefinedCursor(SW_RESIZE_CURSOR));
        } else if (onTop && onRight) {
            selection.setCursor(getPredefinedCursor(NE_RESIZE_CURSOR));
        } else if (onBottom && onRight) {
            selection.setCursor(getPredefinedCursor(SE_RESIZE_CURSOR));
        } else if (onLeft) {
            selection.setCursor(getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        } else if (onRight) {
            selection.setCursor(getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        } else if (onTop) {
            selection.setCursor(getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        } else if (onBottom) {
            selection.setCursor(getPredefinedCursor(S_RESIZE_CURSOR));
        }
    }
}
