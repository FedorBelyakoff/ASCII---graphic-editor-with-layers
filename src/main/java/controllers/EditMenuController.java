package controllers;

import classes.EditMenuItems;
import enumerations.WindowComponents;
import ui.UiLocator;

import javax.swing.*;

import static classes.EditMenuItems.*;

public abstract class EditMenuController {
    protected JMenuItem undo;
    protected JMenuItem redo;
    protected JMenuItem cut;
    protected JMenuItem copy;
    protected JMenuItem paste;
    protected JMenuItem deselect;
    protected JMenuItem delete;
    protected JMenu flip;
    protected JMenuItem flipHorizontally;
    protected JMenuItem flipVertically;
    protected JMenu rotate;
    protected JMenuItem right90;
    protected JMenuItem right180;
    protected JMenuItem left90;
    protected JMenuItem left180;

    public EditMenuController(UiLocator locator) {
        setFields(locator);

        setInitialSettings();
    }

    private void setFields(UiLocator locator) {
        undo = (JMenuItem) locator.get(UNDO);
        redo = (JMenuItem) locator.get(REDO);
        deselect = (JMenuItem) locator.get(DESELECT);
        cut = (JMenuItem) locator.get(CUT);
        copy = (JMenuItem) locator.get(COPY);
        paste = (JMenuItem) locator.get(PASTE);
        delete = (JMenuItem) locator.get(DELETE);
        flip = (JMenu) locator.get(FLIP);
        flipHorizontally = (JMenuItem) locator.get(FLIP_HORIZONTALLY);
        flipVertically = (JMenuItem) locator.get(FLIP_VERTICALLY);
        rotate = (JMenu) locator.get(ROTATE);
        right90 = (JMenuItem) locator.get(ROTATE_RIGHT90);
        right180 = (JMenuItem) locator.get(ROTATE_RIGHT180);
        left90 = (JMenuItem) locator.get(ROTATE_LEFT90);
        left180 = (JMenuItem) locator.get(ROTATE_LEFT180);
    }

    private void setInitialSettings() {
        flip.setEnabled(false);
        rotate.setEnabled(false);

        deselect.setEnabled(false);
        cut.setEnabled(false);
        copy.setEnabled(false);
        paste.setEnabled(false);
        delete.setEnabled(false);
    }

    public void setUndoEnabled(boolean enabled) {
        undo.setEnabled(enabled);
    }

    public void setRedoEnabled(boolean enabled) {
        redo.setEnabled(enabled);
    }

    public void select() {
    }

    public void deselect() {
    }

    public void cut() {
    }

    public void copy() {
    }

    public void paste() {
    }

    public void delete() {

    }
}
