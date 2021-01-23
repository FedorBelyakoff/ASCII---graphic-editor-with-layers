package controllers;

import interfaces.DimensionsController;
import interfaces.SelectionController;
import ui.DimensionPanel;


public class SelectionControllerImpl implements SelectionController, DimensionsController {

    private final SelectionMenuController menuController;
    private final SelectionMenuController popupMenuController;
    private final SelectionWorkspaceController workspaceController;
    private final DimensionPanel dimensionPanel;


    public SelectionControllerImpl(SelectionMenuController menuController,
                                   SelectionMenuController popupMenuController, SelectionWorkspaceController workspaceController,
                                   DimensionPanel dimensionPanel) {
        this.menuController = menuController;
        this.popupMenuController = popupMenuController;

        this.workspaceController = workspaceController;
        this.dimensionPanel = dimensionPanel;
    }

    @Override
    public void changeDimension(int widthInChars,
                                int heightInChars,
                                Object source) {
        if (dimensionPanel == source) {
            workspaceController.resizeInChars(widthInChars, heightInChars);
        }
        if (workspaceController == source) {
            dimensionPanel.setWidth(widthInChars);
            dimensionPanel.setHeight(heightInChars);
        }
    }


    @Override
    public void select() {
        menuController.select();
        popupMenuController.select();
    }

    @Override
    public void deselect() {
        workspaceController.removeSelection();
        menuController.deselect();
        popupMenuController.deselect();
    }

    @Override
    public void cut() {

        workspaceController.cut();
        menuController.cut();
        popupMenuController.cut();
    }

    @Override
    public void copy() {
        workspaceController.copy();
        menuController.copy();
        popupMenuController.copy();
    }

    private void removeSelection() {
        workspaceController.removeSelection();
    }

    @Override
    public void paste() {
        workspaceController.paste();
        menuController.paste();
        popupMenuController.paste();

    }

    @Override
    public void delete() {
        workspaceController.delete();
        menuController.delete();
        popupMenuController.delete();
    }

    @Override
    public void flipHorizontally() {
        workspaceController.flipHorizontally();
    }

    @Override
    public void flipVertically() {
        workspaceController.flipVertically();
    }


    @Override
    public void rotateRight90() {
        workspaceController.rotateRight90();
    }

    @Override
    public void rotateRight180() {
        workspaceController.rotateRight180();
    }

    @Override
    public void rotateLeft90() {
        workspaceController.rotateLeft90();
    }

    @Override
    public void rotateLeft180() {
        workspaceController.rotateRight180();
    }



}
