package controllers;

import interfaces.TextController;
import ui.UiLocator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextMenuController extends EditMenuController implements ActionListener {
    private final TextController controller;

    public TextMenuController(UiLocator locator, TextController controller) {
        super(locator);
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (deselect == source) {
            controller.deselect();
        }
        if (cut == source) {
            controller.cut();
        }
        if (copy == source) {
            controller.copy();
        }
        if (paste == source) {
            controller.paste();
        }
        if (delete == source) {
            controller.delete();
        }
    }

    @Override
    public void select() {
        deselect.setEnabled(true);
        cut.setEnabled(true);
        copy.setEnabled(true);
        delete.setEnabled(true);
    }

    @Override
    public void deselect() {
        deselect.setEnabled(false);
        cut.setEnabled(false);
        copy.setEnabled(false);
    }

    @Override
    public void cut() {
        deselect.setEnabled(false);
        delete.setEnabled(false);
        cut.setEnabled(false);
        copy.setEnabled(false);
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
        super.delete();
    }

    public void addListeners() {
        deselect.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        delete.addActionListener(this);
        paste.addActionListener(this);
    }
}
