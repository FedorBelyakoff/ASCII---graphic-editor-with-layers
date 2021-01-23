package controllers;

import ui.SelectionAlgorithm;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputAreaMouseAdapter extends MouseAdapter {
    private final InputAreaMouseController mouseController;
    private final SelectionAlgorithm algorithm;
    private final TextInputArea inputArea;
    private final TextBoundsRounder rounder;

    public InputAreaMouseAdapter(InputAreaMouseController mouseController,
                                 SelectionAlgorithm algorithm,
                                 TextInputArea inputArea,
                                 TextBoundsRounder rounder) {
        this.inputArea = inputArea;
        this.mouseController = mouseController;
        this.algorithm = algorithm;
        this.rounder = rounder;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseController.mouseClicked(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        algorithm.mouseDragged(e);

        if (!algorithm.isCloseToTheSides()) {
            mouseController.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        algorithm.mouseMoved(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        algorithm.mouseReleased(e);
        mouseController.mouseReleased(e);
        rounder.update(e);
        inputArea.updateAreaBounds();
    }
}
