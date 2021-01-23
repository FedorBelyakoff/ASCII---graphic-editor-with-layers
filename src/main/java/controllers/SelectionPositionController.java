package controllers;

import ui.Selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SelectionPositionController extends ComponentAdapter {
    private final Selection selection;
    private final JComponent frame;
    private final JTextArea area;
    private Point prevAreaLocation;
    private boolean notShownBefore;

    public SelectionPositionController(
                     Selection selection,
                     JComponent frame,
                     JTextArea area) {
        this.selection = selection;
        this.frame = frame;
        this.area = area;
        notShownBefore = true;
    }


    @Override
    public void componentShown(ComponentEvent e) {
        if (frame == e.getComponent()) {
            prevAreaLocation = area.getLocation();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        if (area != e.getComponent()) {
            return;
        }

        Dimension frameSize = frame.getSize();
        Point areaLocation = area.getLocation();

        if (notShownBefore) {
            notShownBefore = false;
            prevAreaLocation = areaLocation;
        }
        int dx = prevAreaLocation.x - areaLocation.x;
        int dy = prevAreaLocation.y - areaLocation.y;
        int newX = selection.getX() - dx;
        int newY = selection.getY() - dy;
        selection.setLocation(newX, newY);

        prevAreaLocation = areaLocation;
    }
}
