package controllers;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.Workspace;

import java.util.HashSet;
import java.util.Set;

public class BrushTextUi {
    private Set<TextPixel> pixels;
    private Workspace workspace;
    private int size;
    private char ch;

    public BrushTextUi(Workspace workspace) {
        this.workspace = workspace;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public char getChar() {
        return ch;
    }

    public void setChar(char ch) {
        this.ch = ch;
    }

    public Set<TextPixel> getPixels() {
        return pixels;
    }

    public void draw(TextPosition center) {
        pixels = new HashSet<>();
        drawText(center);
    }

    private void drawText(TextPosition center) {
        int rowSize;
        if (size == 2 || size == 1) {
            rowSize = size;
        } else {
            rowSize = 2 * size / 3;
        }

        int leftColumn = center.getColumn() - size / 3;
        int topRow = center.getRow() - rowSize / 3;

        for (int column = leftColumn;
             column < leftColumn + size; column++) {
            for (int row = topRow; row < topRow + rowSize; row++) {
                pixels.add(new TextPixel(column, row, ch));
            }
        }
    }


}
