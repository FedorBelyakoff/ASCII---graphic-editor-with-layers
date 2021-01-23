package controllers;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.Workspace;
import ui.WorkspaceHighlighter;

import java.util.*;

public class LineTextHighlighter {
    private final WorkspaceHighlighter highlighter;
    private final Workspace workspace;
    private TextPixel maxColumn;
    private TextPixel minColumn;
    private TextPixel minRow;
    private TextPixel maxRow;
    private Set<TextPixel> textPixels;

    public LineTextHighlighter(WorkspaceHighlighter highlighter,
                     Workspace workspace) {
        this.highlighter = highlighter;
        this.workspace = workspace;
    }

    public void highlight(Set<TextPixel> textPixels) {
        this.textPixels = textPixels;
        determineExtremePositions();

        int lengthHorizontally = maxColumn.getColumn() - minColumn.getColumn();
        int lengthVertically = maxRow.getRow() - minRow.getRow();

        if (lengthHorizontally > lengthVertically) {
            highlight(minColumn, maxColumn, lengthHorizontally);
        } else {
            highlight(minRow, maxRow, lengthVertically);
        }
    }

    private void highlight(TextPixel minPos, TextPixel maxPos, int length) {
        Set<TextPosition> highlights = new HashSet<>();
        TextPosition initPos = new TextPosition(minPos.getColumn(), minColumn.getRow());
        highlights.add(initPos);

        for (TextPixel p : textPixels) {
            if (p.getColumn() == length / 2) {
                TextPosition middlePos = p.getPosition();
                highlights.add(middlePos);
            }
        }

        TextPosition endPos = new TextPosition(maxPos.getPosition());
        highlights.add(endPos);

        highlighter.highlight(highlights);
    }

    private void determineExtremePositions() {
        Comparator<TextPixel> columnComparator = (o1, o2) ->
                         o1.getColumn() > o2.getColumn() ? 1 : 0;
        maxColumn = Collections.max(textPixels, columnComparator);
        minColumn = Collections.min(textPixels, columnComparator);

        Comparator<TextPixel> rowComparator = (o1, o2) ->
                         o1.getRow() > o2.getRow() ? 1 : 0;
        minRow = Collections.min(textPixels, rowComparator);
        maxRow = Collections.max(textPixels, rowComparator);
    }
}
