package controllers;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.Workspace;
import ui.WorkspaceHighlighter;

import java.util.HashSet;
import java.util.Set;

public class TextUi {
    private final Workspace workspace;
    private final WorkspaceHighlighter highlighter;
    private TextInputArea inputArea;
    private TextPosition cursorPosition;
    private Set<TextPosition> selectedPositions;

    public TextUi(Workspace workspace, WorkspaceHighlighter highlighter) {
        this.workspace = workspace;
        this.highlighter = highlighter;
    }

    public TextPosition getCursorPosition() {
        return cursorPosition;
    }

    public Set<TextPosition> getSelectedPositions() {
        return selectedPositions;
    }

    public void setInputArea(TextInputArea inputArea) {
        this.inputArea = inputArea;
    }

    public void input(char ch, TextPosition position) {
        HashSet<TextPixel> pixels = new HashSet<>();
        pixels.add(new TextPixel(position, ch));
        workspace.show(pixels);
    }

    public void setCursorPosition(TextPosition position) {
        highlightCursor(position);
        cursorPosition = position;
    }

    public void setSelection(TextPosition firstPos, TextPosition currentPos) {
        selectedPositions = calculateSelectedPositions(firstPos, currentPos);

        highlightSelection(selectedPositions);
    }

    public void removeSelection() {
        highlighter.removeHighlight();
        selectedPositions = null;
    }

    private void highlightCursor(TextPosition position) {
        highlighter.removeHighlight();
        HashSet<TextPosition> cursor = new HashSet<>();
        cursor.add(position);
        highlighter.highlight(cursor);
    }

    private void highlightSelection(Set<TextPosition> positions) {
        highlighter.removeHighlight();
        highlighter.highlight(positions);
    }

    private Set<TextPosition> calculateSelectedPositions(TextPosition firstPos, TextPosition currentPos) {
        if (firstPos.getRow() == currentPos.getRow()) {
            return calculateUp(firstPos, currentPos);
        } else if (firstPos.getRow() < currentPos.getRow()) {
            return calculateDown(firstPos, currentPos);
        } else {
            return calculateDirectly(firstPos, currentPos);
        }
    }

    private Set<TextPosition> calculateDirectly(TextPosition firstPos, TextPosition currentPos) {
        Set<TextPosition> positions = new HashSet<>();
        for (int column = firstPos.getColumn();
             column <= currentPos.getColumn(); column++) {
            positions.add(new TextPosition(column, currentPos.getRow()));
        }
        return positions;
    }

    private Set<TextPosition> calculateUp(TextPosition firstPos, TextPosition currentPos) {
        Set<TextPosition> positions = new HashSet<>();
        int row = firstPos.getRow();

        for (int column = firstPos.getColumn();
             column >= inputArea.getLeft(); column--) {
            positions.add(new TextPosition(column, row));
        }

        row--;
        for (; row > currentPos.getRow(); row--) {
            for (int column = inputArea.getLeft();
                 column <= inputArea.getRight(); column++) {
                positions.add(new TextPosition(column, row));
            }
        }


        for (int column = inputArea.getLeft();
             column <= currentPos.getColumn(); column++) {
            positions.add(new TextPosition(column, row));
        }

        return positions;
    }

    private Set<TextPosition> calculateDown(TextPosition firstPos, TextPosition currentPos) {
        HashSet<TextPosition> positions = new HashSet<>();
        int row = firstPos.getRow();

        for (int column = firstPos.getColumn();
             column <= inputArea.getRight(); column++) {
            positions.add(new TextPosition(column, row));
        }

        row++;
        for (; row < currentPos.getRow(); row++) {
            for (int column = inputArea.getLeft();
                 column <= inputArea.getRight(); column++) {
                positions.add(new TextPosition(column, row));
            }
        }

        int column = firstPos.getColumn();
        for (; column <= currentPos.getColumn(); column++) {
            positions.add(new TextPosition(column, row));
        }
        return positions;
    }

}
