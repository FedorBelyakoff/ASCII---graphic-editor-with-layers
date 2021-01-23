package controllers;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.UILineController;
import interfaces.Workspace;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Set;

public class LineMouseController extends MouseAdapter {
    private static final double TAN_67 = Math.tan(3 * Math.PI / 8);
    private static final double TAN_22 = Math.tan(Math.PI / 8);
    private static final double TAN_45 = Math.tan(Math.PI / 4);

    private final Workspace workspace;
    private final UILineController controller;

    private boolean clickedOnce;
    private boolean clickedTwice;
    private int mouseX;
    private int mouseY;
    private boolean dragged;
    private TextPosition firstPosition;
    private TextPosition secondPosition;
    private Set<TextPixel> textPixels;

    public LineMouseController(Workspace workspace, UILineController controller) {
        this.workspace = workspace;
        this.controller = controller;
        textPixels = Collections.emptySet();
    }

    public boolean isClickedOnce() {
        return clickedOnce;
    }

    public TextPosition getCurrentPosition() {
        return currentPosition;
    }
    public TextPosition getPrevPosition() {
        return prevPosition;
    }
    private TextPosition currentPosition;

    private TextPosition prevPosition;


    public TextPosition getFirstPosition() {
        return firstPosition;
    }

    public TextPosition getSecondPosition() {
        return secondPosition;
    }

    public void setLinePixels(Set<TextPixel> textPixels) {
        this.textPixels = textPixels;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();
        mouseX = e.getX();
        mouseY = e.getY();
        currentPosition = workspace.resolve(mouseX, mouseY);

        if (button == MouseEvent.BUTTON1) {
            if (clickedOnce) {
                processClickedTwice();
            } else if (clickedTwice) {
                removeLine();
            } else {
                processClickedOnce();
            }
        } else if (button == MouseEvent.BUTTON2) {
            removeLine();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentPosition = workspace.resolve(e.getX(),e.getY());

        if (clickedOnce) {
            if (e.isControlDown()) {
                stretchAlignedLine();
            } else {
                stretchLine();
            }

        } else if (clickedTwice) {
            updateCursor();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentPosition = workspace.resolve(e.getX(),e.getY());

        if (!dragged) {
            dragged = true;
            prevPosition = currentPosition;
        }

        if (clickedTwice) {
            updateCursor();

            if (firstPosition.equals(prevPosition)) {
                dragStart();
            } else if (secondPosition.equals(prevPosition)) {
                dragEnd();
            } else if (lineContains(prevPosition)) {
                dragAll();
            }
            prevPosition = currentPosition;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragged) {
            dragged = false;
        }
    }

    private void updateCursor() {
        if (positionNearEnds()) {
            controller.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        } else if (lineContains(currentPosition)) {
            controller.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
    }

    private void removeLine() {
        controller.removeLine();
        clickedOnce = clickedTwice = false;
    }

    private void processClickedOnce() {
        firstPosition = new TextPosition(currentPosition);
        clickedOnce = true;
    }

    private void processClickedTwice() {
        clickedOnce = false;
        clickedTwice = true;
    }

    private boolean positionNearEnds() {
        return firstPosition.equals(currentPosition)
                         || secondPosition.equals(currentPosition);
    }



    private void stretchAlignedLine() {
        int dColumn = currentPosition.getColumn() - firstPosition.getColumn();
        int dRow = currentPosition.getRow() - firstPosition.getRow();

        int columnLength = Math.abs(dColumn);
        int rowLength = Math.abs(dRow);

        int column, row;

        double tg = ((double) rowLength) / columnLength;
        if (TAN_22 > tg) {
            column = currentPosition.getColumn();
            row = firstPosition.getRow();
        } else if (TAN_67 > tg) {
            column = currentPosition.getColumn();
            row = firstPosition.getRow();

            int alignedRow = (int) Math.round(columnLength * TAN_45);
            if (dRow < 0) {
                row -= alignedRow;
            } else {
                row += alignedRow;
            }
        } else {
            column = firstPosition.getColumn();
            row = currentPosition.getRow();
        }

        secondPosition = new TextPosition(column, row);

        controller.drawLine();
    }

    private void stretchLine() {
        secondPosition = new TextPosition(currentPosition);
        controller.drawLine();
    }

    private boolean lineContains(TextPosition position) {
        for (TextPixel p : textPixels) {
            if (p.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    private void dragAll() {
        int dColumn = currentPosition.getColumn() - prevPosition.getColumn();
        int dRow = currentPosition.getRow() - prevPosition.getRow();

        firstPosition = new TextPosition(firstPosition.getColumn() + dColumn,
                         firstPosition.getRow() + dRow);
        secondPosition = new TextPosition(secondPosition.getColumn() + dColumn,
                         secondPosition.getRow() + dRow);

        controller.drawLine();


    }

    private void dragStart() {
        int dColumn = currentPosition.getColumn() - prevPosition.getColumn();
        int dRow = currentPosition.getRow() - prevPosition.getRow();

        firstPosition = new TextPosition(firstPosition.getColumn() + dColumn,
                         firstPosition.getRow() + dRow);


        controller.drawLine();
    }


    private void dragEnd() {
        int dColumn = currentPosition.getColumn() - prevPosition.getColumn();
        int dRow = currentPosition.getRow() - prevPosition.getRow();

        secondPosition = new TextPosition(secondPosition.getColumn() + dColumn,
                         secondPosition.getRow() + dRow);

        controller.drawLine();

    }

}
