package classes;

import controllers.*;
import interfaces.TextInputAreaFactory;
import interfaces.Workspace;
import ui.HorizontalLine;
import ui.Selection;
import ui.SelectionAlgorithm;
import ui.UiLocator;

import javax.swing.*;

import java.awt.*;

import static enumerations.WindowComponents.*;
import static ui.UiConstants.INPUT_AREA_SELECTION_COLOR;
import static ui.UiConstants.SELECTION_BORDER_SIZE;

public class TextInputAreaFactoryImpl implements TextInputAreaFactory {
    private static final Dimension STROKE_DIMENSION = new Dimension(20, 3);
    private static final Integer SELECTION_LAYER = JLayeredPane.PALETTE_LAYER - 10;
    private final JLayeredPane pane;
    private final JTextArea area;
    private final Workspace workspace;
    private final TextUiController inputAreaController;
    private Selection selection;
    private InputAreaMouseController mouseController;
    private TextInputArea inputArea;
    private InputAreaMouseAdapter adapter;
    private TextSelectionBoundsResolver resolver;

    public TextInputAreaFactoryImpl(
                     UiLocator locator,
                     TextUiController inputAreaController) {
        this.inputAreaController = inputAreaController;
        pane = ((JFrame) locator.get(FRAME)).getLayeredPane();
        area = (JTextArea) locator.get(AREA);
        workspace = (Workspace) locator.get(WORKSPACE);

    }

    @Override
    public boolean isInputAreaExist() {
        return inputArea != null;
    }

    @Override
    public TextInputArea getInputArea() {
        if (inputArea == null) {
            throw new IllegalStateException("Components were not created");
        }
        return inputArea;
    }

    @Override
    public InputAreaMouseController getAreaMouseController() {
        if (mouseController == null) {
            throw new IllegalStateException("Components were not created");
        }
        return mouseController;
    }

    @Override
    public void createComponents(TextPosition location) {
        selection = createSelection(location);
        SelectionAlgorithm algorithm = new SelectionAlgorithm(selection, SELECTION_BORDER_SIZE);


        resolver = new TextSelectionBoundsResolver(workspace, pane);
        TextBoundsRounder rounder = new TextBoundsRounder(
                         resolver, workspace, selection, pane);
        inputArea = new TextInputArea(algorithm, rounder);

        mouseController = new InputAreaMouseController(
                         workspace, algorithm, inputAreaController, selection, pane);
        adapter = new InputAreaMouseAdapter(mouseController, algorithm, inputArea,
                         rounder);

        inputArea.setController(inputAreaController);
        addSelectionListeners(selection, adapter);
        setAlgorithmSettings(algorithm);

        drawStrokes();
    }

    private void drawStrokes() {
        for (int i = 0; i < workspace.getRows(); i++) {
            Rectangle strokeBounds = resolver.getBounds(
                             0, i, 1000, 1);

            HorizontalLine line = new HorizontalLine();
            int y = strokeBounds.y;
            System.out.println(y);
            line.setBounds(0, y, 1000, 3);
            pane.add(line, JLayeredPane.PALETTE_LAYER);

        }
    }

    @Override
    public void removeComponents() {
        removeSelection();
        removeListeners();

        nullifyLinks();
    }

    private Selection createSelection(TextPosition location) {
        assert location.getColumn() > 0;
        assert location.getRow() > 0;

        Selection selection = new Selection(STROKE_DIMENSION,
                         SELECTION_BORDER_SIZE,
                         INPUT_AREA_SELECTION_COLOR);
        Rectangle selectionBounds = new TextSelectionBoundsResolver(workspace, pane)
                         .getBounds(location, 1, 1);
        selection.setBounds(selectionBounds);
        selection.setVisible(true);

        area.addComponentListener(new SelectionPositionController(selection, pane, area));

        pane.add(selection, SELECTION_LAYER, 10);


        return selection;
    }

    private void addSelectionListeners(
                     Selection selection,
                     InputAreaMouseAdapter adapter) {
        selection.addMouseListener(adapter);
        selection.addMouseMotionListener(adapter);
    }

    private void setAlgorithmSettings(SelectionAlgorithm algorithm) {
        algorithm.setMoveEnabled(false);
    }

    private void removeSelection() {
        pane.remove(selection);
        pane.repaint();
    }

    private void removeListeners() {
        selection.removeMouseListener(adapter);
        selection.removeMouseMotionListener(adapter);
    }

    private void nullifyLinks() {
        mouseController = null;
        inputArea = null;
    }



}
