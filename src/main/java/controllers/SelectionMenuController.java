package controllers;

import interfaces.SelectionController;
import ui.UiLocator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SelectionMenuController extends EditMenuController implements ActionListener {

    private final SelectionController controller;

    public SelectionMenuController(UiLocator locator, SelectionController controller) {
        super(locator);
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();
        if (cut == source) {
            controller.cut();
        }

        if (copy == source) {
            controller.copy();
        }

        if (paste == source) {
            controller.paste();
        }

        if (deselect == source) {
            controller.deselect();
        }

        if (delete == source) {
            controller.delete();
        }

        if (flipHorizontally == source) {
            controller.flipHorizontally();
        }

        if (flipVertically == source) {
            controller.flipVertically();
        }

        if (right90 == source) {
            controller.rotateRight90();
        }

        if (right180 == source) {
            controller.rotateRight180();
        }

        if (left90 == source) {
            controller.rotateLeft90();
        }

        if (left180 == source) {
            controller.rotateLeft180();
        }
    }

    @Override
    public void select() {
        deselect.setEnabled(true);
        cut.setEnabled(true);
        copy.setEnabled(true);
        delete.setEnabled(true);
        flip.setEnabled(true);
        rotate.setEnabled(true);
    }

    @Override
    public void deselect() {
        deselect.setEnabled(false);
        cut.setEnabled(false);
        copy.setEnabled(false);
        delete.setEnabled(false);
        flip.setEnabled(false);
        rotate.setEnabled(false);
    }


    @Override
    public void cut() {
        deselect.setEnabled(false);
        cut.setEnabled(false);
        copy.setEnabled(false);
        flip.setEnabled(false);
        rotate.setEnabled(false);

        paste.setEnabled(true);
    }

    @Override
    public void copy() {
        copy.setEnabled(false);
        deselect.setEnabled(true);
        cut.setEnabled(true);
        paste.setEnabled(true);
    }

    @Override
    public void paste() {
        deselect.setEnabled(true);
        cut.setEnabled(true);
        copy.setEnabled(true);
        paste.setEnabled(true);
    }

    @Override
    public void delete() {
        deselect.setEnabled(false);
        cut.setEnabled(false);
        delete.setEnabled(false);
        copy.setEnabled(false);
        flip.setEnabled(false);
        rotate.setEnabled(false);
    }
}
