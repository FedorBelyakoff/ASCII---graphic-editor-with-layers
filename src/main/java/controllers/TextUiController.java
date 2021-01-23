package controllers;

import classes.TextPosition;
import interfaces.Model;
import interfaces.TextInputAreaFactory;
import ui.WorkspaceHighlighter;

import java.util.Set;

public class TextUiController {
    // TODO: 09.01.2021 Исправить ответственности, оставить только
    //  обмен информацией между компонентами.
    private TextMouseController mouse;
    private InputAreaMouseController inputAreaMouse;
    private TextKeyboard keyboard;
    private TextInputArea inputArea;
    private WorkspaceHighlighter highlighter;
    private TextInputAreaFactory factory;
    private TextUi textUi;

    private TextPosition cursorPosition;


    public TextPosition getCursorPosition() {
        return cursorPosition;
    }

    public void register(Object component) {
        if (component instanceof TextMouseController) {
            mouse = (TextMouseController) component;
        } else if (component instanceof TextKeyboard) {
            keyboard = (TextKeyboard) component;
        } else if (component instanceof WorkspaceHighlighter) {
            highlighter = (WorkspaceHighlighter) component;
        } else if (component instanceof TextInputAreaFactory) {
            factory = (TextInputAreaFactory) component;
        }  else if (component instanceof TextUi) {
            textUi = (TextUi) component;
        } else {
            throw new IllegalArgumentException("Unknown component");
        }
    }

    public void createInputArea(TextPosition location) {
        if (inputArea != null) {
            removeArea();
        }
        factory.createComponents(location);

        inputArea = factory.getInputArea();
        inputAreaMouse = factory.getAreaMouseController();

        initAreaBounds(location);

        textUi.setInputArea(inputArea);
        cursorPosition = location;
        textUi.setCursorPosition(location);
    }


    public void removeInputArea() {
        removeArea();
    }

    public void updateBounds(Object source) {
        Set<TextPosition> areaPositions = inputArea.getAreaPositions();
        if (!areaPositions.contains(cursorPosition)) {
            TextPosition newPosition = new TextPosition(
                             inputArea.getLeft(), inputArea.getTop());
            moveCursor(newPosition);
        }
        mouse.setInputAreaPositions(areaPositions);
    }

    public void moveCursor(TextPosition newPosition) {
        cursorPosition = newPosition;
        updateAreaBounds(newPosition);
        updateBounds(this);
        textUi.setCursorPosition(newPosition);
    }

    public void lineBreak() {
        int column = inputArea.getLeft();
        int row = getCursorPosition().getRow() + 1;

        moveCursor(new TextPosition(column, row));
    }

    public void select(TextPosition firstPos,
                       TextPosition secondPos) {

        updateAreaBounds(secondPos);

        textUi.setSelection(firstPos, secondPos);
    }

    public void input(char ch) {
        if (cursorPosition == null) {
            return;
        }

        textUi.input(ch, cursorPosition);
        int column = cursorPosition.getColumn() + 1;
        int row = cursorPosition.getRow();
        moveCursor(new TextPosition(column, row));
    }



    private void initAreaBounds(TextPosition location) {

        inputArea.setLeft(location.getColumn());
        inputArea.setRight(location.getColumn() + 10);
        inputArea.setTop(location.getRow() + 1);
        inputArea.setBottom(location.getRow() + 1);
        inputArea.resizeArea();
    }

    private void removeArea() {
        factory.removeComponents();
    }

    private void updateAreaBounds(TextPosition current) {
        int currentColumn = current.getColumn();
        int currentRow = current.getRow();

        if (inputArea.getLeft() > currentColumn) {
            inputArea.setLeft(currentColumn);
        }

        if (inputArea.getTop() > currentRow) {
            inputArea.setTop(currentRow);
        }

        if (inputArea.getRight() < currentColumn + 1) {
            inputArea.setRight(currentColumn + 1);
        }

        if (inputArea.getBottom() < currentRow + 1) {
            inputArea.setBottom(currentRow + 1);
        }

        inputArea.resizeArea();
    }

}
