package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PositionLabelMouse extends MouseAdapter {
    private final TextPositionLabelController controller;

    public PositionLabelMouse(TextPositionLabelController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updateController(e);
    }

    private void updateController(MouseEvent e) {
        controller.updatePosition(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateController(e);
    }
}
