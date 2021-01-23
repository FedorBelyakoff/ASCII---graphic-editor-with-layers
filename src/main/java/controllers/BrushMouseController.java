package controllers;

import classes.TextPosition;
import interfaces.Workspace;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BrushMouseController extends MouseAdapter {
    private TextPosition prevPosition;
    private BrushUiController controller;
    private Workspace workspace;

    public BrushMouseController(BrushUiController controller,
                                Workspace workspace) {
        this.controller = controller;
        this.workspace = workspace;
        prevPosition = new TextPosition(0, 0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        draw(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        draw(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        show(e);
    }

    private void draw(MouseEvent e) {
        TextPosition currentPos = workspace.resolve(e.getX(), e.getY());
        if (prevPosition.equals(currentPos)) {
            return;
        }
        controller.draw(currentPos);

        prevPosition = currentPos;
    }

    private void show(MouseEvent e) {
        TextPosition currentPos = workspace.resolve(e.getX(), e.getY());
        if (prevPosition.equals(currentPos)) {
            return;
        }
        controller.show(currentPos);

        prevPosition = currentPos;
    }
}
