package controllers;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.Model;
import interfaces.TextController;
import interfaces.TextInputAreaFactory;
import interfaces.Workspace;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class TextControllerImpl implements TextController {
    TextMenuController menuController;
    TextMenuController popupMenuController;
    TextUiController workspaceController;
    Model model;
    Workspace workspace;
    TextUi textUi;
    TextInputAreaFactory factory;
    private TextInputArea inputArea;
    private int currentLayer;
    private char bgSymbol;

    public TextControllerImpl() {
    }

    public TextControllerImpl(TextMenuController menuController,
                              TextMenuController popupMenuController,
                              TextUiController workspaceController,
                              TextInputArea inputArea, Model model,
                              Workspace workspace, TextUi textUi,
                              TextInputAreaFactory factory) {
        this.menuController = menuController;
        this.popupMenuController = popupMenuController;
        this.workspaceController = workspaceController;
        this.inputArea = inputArea;
        this.model = model;
        this.workspace = workspace;
        this.textUi = textUi;
        this.factory = factory;
    }


    @Override
    public void select() {
        menuController.select();
        popupMenuController.select();
    }

    @Override
    public void deselect() {
        workspaceController.removeInputArea();
        menuController.deselect();
        popupMenuController.deselect();
    }

    @Override
    public void cut() {
        if (!factory.isInputAreaExist()) {
            return;
        }
        Set<TextPosition> selected = textUi.getSelectedPositions();
        Set<TextPixel> cutPixels = getCutPixels(selected);
        saveTextInBuffer(cutPixels);

        removeSelectedText(selected);

        menuController.cut();
        popupMenuController.cut();
    }

    private void removeSelectedText(Set<TextPosition> selected) {
        Set<TextPixel> replacement = getReplacementCutPixels(selected);
        workspace.show(replacement);

        textUi.removeSelection();
    }

    private void saveTextInBuffer(Set<TextPixel> cutPixels) {
        String strToSave = getStringToSave(cutPixels);
        StringSelection stringSelection = new StringSelection(strToSave);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        clipboard.setContents(stringSelection, null);
    }

    private String  getStringToSave(Set<TextPixel> cutPixels) {
        ArrayList<TextPixel> stringPixels = new ArrayList<>(cutPixels);
        stringPixels.sort(new TextPixelComparator());

        StringBuilder resultStr = new StringBuilder();
        for (TextPixel pixel : stringPixels) {
            resultStr.append(pixel.getSymbol());
        }

        return resultStr.toString();
    }

    private Set<TextPixel> getReplacementCutPixels(Set<TextPosition> positions) {
        Set<TextPixel> replacement = new HashSet<>();
        for (TextPosition position : positions) {
            replacement.add(new TextPixel(position, bgSymbol));
        }
        return replacement;
    }

    private Set<TextPixel> getCutPixels(Set<TextPosition> positions) {
        Set<TextPixel> layerPixels = model.getTextPixels(currentLayer);
        Set<TextPixel> cutPixels = new HashSet<>();
        for (TextPixel pixel : layerPixels) {
            if (positions.contains(pixel.getPosition())) {
                cutPixels.add(pixel);
            }
        }
        return cutPixels;
    }

    @Override
    public void copy() {
        Set<TextPosition> selected = textUi.getSelectedPositions();
        Set<TextPixel> cutPixels = getCutPixels(selected);
        saveTextInBuffer(cutPixels);

        menuController.copy();
        popupMenuController.copy();
    }

    @Override
    public void paste() {
        if (!factory.isInputAreaExist()) {
            return;
        }
        inputBufferPixels();
        menuController.paste();
        popupMenuController.paste();

    }

    private void inputBufferPixels() {
        String bufferString = getBufferString();

        TextPosition initialCursorPos = textUi.getCursorPosition();
        for (int i = 0; i < bufferString.length(); i++) {
            char c = bufferString.charAt(i);
            input(c, textUi.getCursorPosition());
        }

        textUi.setSelection(initialCursorPos, textUi.getCursorPosition());

    }

    private void input(char c, TextPosition cursorPosition) {
        TextPosition cursor = textUi.getCursorPosition();
        Set<TextPosition> areaPositions = inputArea.getAreaPositions();

        if (!areaPositions.contains(cursor)) {
            cursor = new TextPosition(inputArea.getLeft(),
                             cursor.getRow() + 1);
        }
        textUi.input(c, cursor);
    }

    private String getBufferString() {
        Clipboard systemClipboard = Toolkit.getDefaultToolkit()
                         .getSystemClipboard();
        DataFlavor dataFlavor = DataFlavor.stringFlavor;

        if (systemClipboard.isDataFlavorAvailable(dataFlavor)) {
            Object text = null;
            try {
                text = systemClipboard.getData(dataFlavor);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
            return (String) text;
        }
        throw new InternalError("Data Flavor Not Available");

    }

    @Override
    public void delete() {
        Set<TextPosition> selectedPositions = textUi.getSelectedPositions();
        removeSelectedText(selectedPositions);

        menuController.delete();
        popupMenuController.delete();
    }

    private static class TextPixelComparator implements Comparator<TextPixel> {
        @Override
        public int compare(TextPixel o1, TextPixel o2) {
            boolean inGreaterColumn = o1.getColumn() > o2.getColumn();
            boolean inSameRow = o1.getRow() == o2.getRow();
            boolean inGreaterRow = o1.getRow() > o2.getRow();

            if ((inGreaterColumn && inSameRow)) {
                return 1;
            } else if (inGreaterRow) {
                return 1;
            } else {
                return 0;
            }
        }

    }

}
