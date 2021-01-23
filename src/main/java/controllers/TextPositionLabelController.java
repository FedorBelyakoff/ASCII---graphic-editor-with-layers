package controllers;

import classes.TextPosition;
import interfaces.Workspace;
import ui.StatusBar;

public class TextPositionLabelController {
    private final StatusBar statusBar;
    private final Workspace workspace;

    public TextPositionLabelController(
                     StatusBar statusBar,
                     Workspace workspace) {
        this.statusBar = statusBar;
        this.workspace = workspace;
    }

    public void updatePosition(int x, int y) {
        TextPosition position = workspace.resolve(x, y);
        statusBar.setMousePosition(position);
    }
}
