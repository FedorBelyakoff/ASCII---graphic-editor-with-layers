package controllers;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.Workspace;

import java.awt.*;
import java.util.Set;

public class WorkspaceMock implements Workspace {
    @Override
    public TextPosition resolve(int x, int y) {
        return null;
    }

    @Override
    public void show(Set<TextPixel> textPixels) {
    }

    @Override
    public int getPosition(int column, int row) {
        return 0;
    }

    @Override
    public TextPosition resolveForPointRelativeWindow(int x, int y, Container topContainer) {
        int column = (x - getX()) / getCharWidth();
        int row = (y - getY()) / getCharHeight();
        return new TextPosition(column, row);

    }

    @Override
    public Rectangle getRect(int column, int row,
                             int widthInChars, int heightInChars) {
        return null;
    }

    @Override
    public void setFontSize(int size) {

    }

    @Override
    public int getX() {
        return 15;
    }

    @Override
    public int getY() {
        return 34;
    }

    @Override
    public int getXRelative(Container container) {
        return 0;
    }

    @Override
    public int getYRelative(Container container) {
        return 0;
    }

    @Override
    public int getCharWidth() {
        return 10;
    }

    @Override
    public int getCharHeight() {
        return 15;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void setText(String text) {

    }

    @Override
    public boolean checkReplaceInArea(int column, int row) {
        return false;
    }


//    @Override
//    public void highlight(Set<TextPosition> positions) {
//
//    }

    @Override
    public void removeHighlight() {

    }

    @Override
    public Insets getInsets() {
        return null;
    }

    @Override
    public void setDimensions(int columns, int rows) {

    }

    @Override
    public int getColumns() {
        return 0;
    }

    @Override
    public int getRows() {
        return 0;
    }

}
