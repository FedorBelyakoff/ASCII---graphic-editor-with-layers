package controllers;

import classes.TextPosition;
import interfaces.Model;
import interfaces.Workspace;
import ui.WorkspaceHighlighter;

public class BrushUiController {
    private final Model model;
    private final Workspace workspace;
    private final BrushTextUi textUi;
    private final WorkspaceHighlighter highlighter;
    private BrushMouseController mouseController;

    private int currentLayer;

    public BrushUiController(Model model,
                             Workspace workspace,
                             BrushTextUi textUi,
                             WorkspaceHighlighter highlighter) {
        this.model = model;
        this.workspace = workspace;
        this.textUi = textUi;
        this.highlighter = highlighter;
    }

    public void setLayer(int layer) {
        this.currentLayer = layer;
    }

    public void show(TextPosition center) {
        workspace.setText(model.getText(currentLayer));
        draw(center);
    }

    public void draw(TextPosition center) {
        textUi.draw(center);
        workspace.show(textUi.getPixels());
        highlight();
    }

    private void highlight() {
        highlighter.removeHighlight();
        highlighter.highlightPixels(textUi.getPixels());
    }

    public void save() {
        model.setText(workspace.getText(), currentLayer);
    }

    public void setSize(int size) {
        textUi.setSize(size);
    }


    public void setChar(char c) {
        textUi.setChar(c);
    }
}

