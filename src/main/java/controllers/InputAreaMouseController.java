package controllers;

import classes.TextPosition;
import interfaces.Workspace;
import ui.Selection;
import ui.SelectionAlgorithm;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputAreaMouseController extends MouseAdapter {
    private final Workspace workspace;
    private final SelectionAlgorithm algorithm;
    private final TextUiController controller;
    private final Selection selection;
    private final Container topContainer;
    private boolean dragged;
    private TextPosition startSelection;


    public InputAreaMouseController(Workspace workspace,
                                    SelectionAlgorithm algorithm,
                                    TextUiController controller,
                                    Selection selection,
                                    Container topContainer) {
        this.workspace = workspace;
        this.algorithm = algorithm;
        this.controller = controller;
        this.selection = selection;
        this.topContainer = topContainer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.moveCursor(resolvePosition(e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        TextPosition currentPosition = resolvePosition(e);
        if (!dragged) {
            startSelection = currentPosition;
            dragged = true;
        }
        controller.select(startSelection, currentPosition);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragged = false;
    }

    private TextPosition resolvePosition(MouseEvent e) {
        int xRelativeWindow = selection.getX() + e.getX();
        int yRelativeWindow = selection.getY() + e.getY();
        return workspace.resolveForPointRelativeWindow(
                         xRelativeWindow, yRelativeWindow, topContainer);
    }
}
