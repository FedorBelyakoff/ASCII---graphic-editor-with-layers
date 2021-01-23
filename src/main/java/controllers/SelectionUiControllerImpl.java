package controllers;

import classes.TextPosition;
import interfaces.Workspace;
import ui.SelectionAlgorithm;
import ui.WorkspaceHighlighter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class SelectionUiControllerImpl extends MouseAdapter{
    private final SelectionAlgorithm algorithm;
    private final TextBoundsRounder rounder;
    private final JComponent selection;
    private final Workspace workspace;
    private final WorkspaceHighlighter highlighter;

    public SelectionUiControllerImpl(SelectionAlgorithm algorithm,
                                     TextBoundsRounder rounder,
                                     JComponent selection, Workspace workspace,
                                     WorkspaceHighlighter highlighter) {
        this.selection = selection;
        this.algorithm = algorithm;
        this.rounder = rounder;
        this.workspace = workspace;
        this.highlighter = highlighter;
    }

    public int getColumnPosition() {
        return rounder.getColumnPosition();
    }

    public int getRowPosition() {
        return rounder.getRowPosition();
    }

    public int getWidthInChars() {
        return rounder.getWidthInChars();
    }

    public int getHeightInChars() {
        return rounder.getHeightInChars();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        selection.setVisible(true);
        algorithm.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        algorithm.mouseMoved(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selection.setVisible(false);

        algorithm.mouseReleased(e);
        rounder.update(e);
        highlightSelection();
    }

    private void highlightSelection() {
        workspace.removeHighlight();
        Set<TextPosition> highlightPositions = new HashSet<>();
        int rightBorderColumn = getColumnPosition() + getWidthInChars();
        int bottomBorderRow = getRowPosition() + getHeightInChars();

        for (int i = getColumnPosition(); i < rightBorderColumn; i++) {
            for (int j = getRowPosition(); j < bottomBorderRow; j++) {
                highlightPositions.add(new TextPosition(i, j));
            }
        }
        highlighter.highlight(highlightPositions);
    }
}
