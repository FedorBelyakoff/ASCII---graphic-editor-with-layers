package controllers;

import classes.TextPosition;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public class TextKeyboard extends KeyAdapter {

    private static final String AUXILIARY_CHARACTERS = " ~`!@#$%^&*()_+-=:\"|\\;',./<>?";
    private final TextUiController controller;
    private TextPosition cursorPosition;

    public TextKeyboard(TextUiController controller) {
        this.controller = controller;
    }

    public TextPosition getCursorPosition() {
        return cursorPosition;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        updateCursor(e);

        processCommands(e);

        inputText(e);
    }

    private void updateCursor(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case VK_UP:
                moveCursor(0, -1);
                break;
            case VK_DOWN:
                moveCursor(0, 1);
                break;
            case VK_LEFT:
                moveCursor(-1, 0);
                break;
            case VK_RIGHT:
                moveCursor(1, 0);
                break;
        }
    }

    private void processCommands(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (VK_ENTER == keyCode) {
            controller.lineBreak();
        }
    }

    private void inputText(KeyEvent e) {
        char keyChar = e.getKeyChar();

        if (checkNotCommand(keyChar)) {
            controller.input(keyChar);
        }

    }

    private void moveCursor(int column, int row) {
        TextPosition cursor = controller.getCursorPosition();
        cursorPosition = new TextPosition(cursor.getColumn() + column,
                         cursor.getRow() + row);
        controller.moveCursor(cursorPosition);
    }

    private boolean checkNotCommand(char keyChar) {
        boolean auxiliaryCharacter = AUXILIARY_CHARACTERS.indexOf(keyChar) != -1;
        return Character.isLetterOrDigit(keyChar) || auxiliaryCharacter;
    }


}
