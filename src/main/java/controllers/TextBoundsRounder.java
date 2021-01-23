package controllers;

import classes.TextPosition;
import classes.TextSelectionBoundsResolver;
import interfaces.Workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TextBoundsRounder {
    private static final int ADDITIVE_TO_THE_INPUT_AREA = 1;
    private final TextSelectionBoundsResolver boundsResolver;
    private final Workspace workspace;
    private final JComponent selection;
    private int columnPosition;
    private int rowPosition;
    private int widthInChars;
    private int heightInChars;
    private final Container topContainer;

    public TextBoundsRounder(TextSelectionBoundsResolver boundsResolver,
                             Workspace workspace,
                             JComponent selection,
                             Container topContainer) {
        this.selection = selection;
        this.workspace = workspace;
        this.boundsResolver = boundsResolver;
        this.topContainer = topContainer;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getWidthInChars() {
        return widthInChars;
    }

    public int getHeightInChars() {
        return heightInChars;
    }


    public void update(MouseEvent e) {
        definePositionsInCharacters();
        defineSizeInCharacters();

        resizeSelection();
    }

    public void moveInChars(int column, int row) {
        assert column > 0;
        assert row > 0;
        assert column < workspace.getColumns() - 1;
        assert row < workspace.getRows() - 1;
        assert widthInChars + column < workspace.getColumns() - 1;
        assert heightInChars + row < workspace.getRows() - 1;

        columnPosition = column;
        rowPosition = row;
        resizeSelection();
    }

    public void resizeInChars(int width, int height) {
        assert width > 0;
        assert height > 0;
        assert width + columnPosition < workspace.getColumns() - 1;
        assert height + rowPosition < workspace.getRows() - 1;

        widthInChars = width;
        heightInChars = height;
        resizeSelection();
    }

    private void definePositionsInCharacters() {
        int selectionX = selection.getX();
        int selectionY = selection.getY();

        TextPosition topLeftCorner = workspace.resolveForPointRelativeWindow(
                         selectionX, selectionY, topContainer);

        columnPosition = topLeftCorner.getColumn() + ADDITIVE_TO_THE_INPUT_AREA;
        if (columnPosition < 0) {
            columnPosition = 0;
        }
        if (columnPosition > workspace.getColumns() - 1) {
            columnPosition = workspace.getColumns() - 1;
        }

        rowPosition = topLeftCorner.getRow() + ADDITIVE_TO_THE_INPUT_AREA;
        if (rowPosition < 0) {
            rowPosition = 0;
        }
        if (rowPosition > workspace.getRows() - 1) {
            rowPosition = workspace.getRows() - 1;
        }
    }

    private void defineSizeInCharacters() {
        int rightBorder = selection.getX() + selection.getWidth();
        int bottomBorder = selection.getY() + selection.getHeight();

        TextPosition bottomRightChar =
                         workspace.resolveForPointRelativeWindow(
                                          rightBorder, bottomBorder, topContainer);

        //Check whether the right border goes beyond the workspace.
        widthInChars = bottomRightChar.getColumn() - columnPosition;
        int workspaceRightBorder = workspace.getColumns() - 1;
        if (widthInChars + columnPosition > workspaceRightBorder) {
            widthInChars = workspaceRightBorder - columnPosition;
        }

        //Check whether the bottom border goes beyond the workspace.
        heightInChars = bottomRightChar.getRow() - rowPosition;
        int workspaceBottomBorder = workspace.getRows() - 1;
        if (heightInChars + rowPosition > workspaceBottomBorder) {
            heightInChars = workspaceBottomBorder - rowPosition;
        }
    }

    private void resizeSelection() {
        Rectangle roundedBounds = boundsResolver.getBounds(columnPosition, rowPosition,
                         widthInChars, heightInChars);
        selection.setBounds(roundedBounds);
    }
}
