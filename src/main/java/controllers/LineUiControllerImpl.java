package controllers;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.Model;
import interfaces.UILineController;
import interfaces.Workspace;
import ui.WorkspaceHighlighter;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LineUiControllerImpl implements UILineController {
    private LineMouseController mouseController;
    private Workspace workspace;
    private LineTextUi textUi;
    private Model model;
    private WorkspaceHighlighter highlighter;
    private int currentLayer;

    public LineUiControllerImpl(Model model,
                                LineTextUi textUi,
                                Workspace workspace,
                                WorkspaceHighlighter highlighter) {
        this.highlighter = highlighter;
        this.workspace = workspace;
        this.textUi = textUi;
        this.model = model;
    }

    @Override
    public void setMouseController(LineMouseController mouseController) {
        this.mouseController = mouseController;
    }

    @Override
    public void drawLine() {
        TextPosition firstPos = mouseController.getFirstPosition();
        TextPosition secondPos = mouseController.getSecondPosition();

        textUi.draw(firstPos, secondPos);

        Set<TextPixel> textPixels = textUi.getTextPixels();
        mouseController.setLinePixels(textPixels);
        workspace.setText(model.getText(currentLayer));
        workspace.show(textPixels);
        highlightEnds(firstPos, secondPos);
    }

    @Override
    public void removeLine() {
        mouseController.setLinePixels(Collections.emptySet());
        workspace.setText(model.getText(currentLayer));
        highlighter.removeHighlight();
    }

    @Override
    public void setCursor(Cursor cursor) {

    }

    @Override
    public void setChar(char c) {
        textUi.setChar(c);
    }

    private void highlightEnds(TextPosition firstPos,
                               TextPosition secondPos) {
        workspace.removeHighlight();
        HashSet<TextPosition> highlights = new HashSet<>();
        highlights.add(firstPos);
        highlights.add(secondPos);
        highlighter.highlight(highlights);
    }
}
