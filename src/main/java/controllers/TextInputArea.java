package controllers;

import classes.TextPosition;
import ui.SelectionAlgorithm;

import java.awt.event.MouseAdapter;
import java.util.HashSet;
import java.util.Set;

public class TextInputArea extends MouseAdapter {
    private final SelectionAlgorithm algorithm;
    private final TextBoundsRounder rounder;
    private TextUiController controller;
    private int left;
    private int right;
    private int top;
    private int bottom;

    public TextInputArea(SelectionAlgorithm algorithm,
                         TextBoundsRounder rounder) {
        this.algorithm = algorithm;
        this.rounder = rounder;
    }

    public void setController(TextUiController controller) {
        this.controller = controller;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }


    public void resizeArea() {
        rounder.moveInChars(left, top);
        int width = right - left;
        int height = bottom - top;
        rounder.resizeInChars(width, height);
    }

    public void updateAreaBounds() {
        int left = rounder.getColumnPosition();
        int top = rounder.getRowPosition();

        setLeft(left);
        setTop(top);
        setRight(left + rounder.getWidthInChars());
        setBottom(top + rounder.getHeightInChars());

        controller.updateBounds(this);
    }

    public Set<TextPosition> getAreaPositions() {
        HashSet<TextPosition> positions = new HashSet<>();
        for (int column = left; column <= right; column++) {
            for (int row = top; row <= bottom; row++) {
                positions.add(new TextPosition(column, row));
            }
        }
        return positions;
    }
}
