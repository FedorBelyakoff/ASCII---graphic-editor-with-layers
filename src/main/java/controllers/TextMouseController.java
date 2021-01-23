package controllers;

import classes.TextPosition;
import interfaces.Workspace;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Set;

public class TextMouseController extends MouseAdapter {
    private final TextUiController controller;
    private final Workspace workspace;
    private Set<TextPosition> inputAreaPositions;
    private TextPosition currentPosition;
    private TextPosition firstPosition;

    public TextMouseController(TextUiController controller, Workspace workspace) {
        this.controller = controller;
        this.workspace = workspace;
        inputAreaPositions = Collections.emptySet();
    }

    public TextPosition getCurrentPosition() {
        return currentPosition;
    }

    public TextPosition getFirstPosition() {
        return firstPosition;
    }

    public void setInputAreaPositions(Set<TextPosition> inputAreaPositions) {
        this.inputAreaPositions = inputAreaPositions;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        currentPosition = workspace.resolve(e.getX(), e.getY());
        if (inputAreaPositions.contains(currentPosition)) {
            controller.moveCursor(currentPosition);
        } else {
            controller.createInputArea(currentPosition);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentPosition = workspace.resolve(e.getX(), e.getY());


    }
}
