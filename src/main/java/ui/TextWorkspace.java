package ui;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.Workspace;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.Arrays;
import java.util.Set;

public class TextWorkspace implements Workspace {
    private static final int FONT_HEIGHT_100PX = 114;
    private static final int FONT_WIDTH_100PX = 60;
    private int charWidth;
    private int charHeight;
    private Font font;

    //    private final StringBuilder displayedCharacters;
    private final JTextArea area;

    public TextWorkspace(JTextArea area) {
        this.area = area;
        setAreaSettings(area);
        setFontSize(18);
        fillEmpty('x');
    }

    private void setAreaSettings(JTextArea area) {
        area.setLineWrap(true);
        area.setEditable(false);
        area.setBackground(Color.WHITE);
    }

    @Override
    public int getColumns() {
        return area.getColumns();
    }

    @Override
    public int getRows() {
        return area.getRows();
    }

    @Override
    public int getX() {
        return area.getX();
    }

    @Override
    public int getY() {
        return area.getY();
    }

    @Override
    public int getXRelative(Container container) {
        ContainerResolver resolver = ContainerResolver.getXResolver(area);

        return resolver.resolveRelative(container);
    }

    @Override
    public int getYRelative(Container container) {
        ContainerResolver resolver = ContainerResolver.getYResolver(area);

        return resolver.resolveRelative(container);
    }

    @Override
    public Insets getInsets() {
        return area.getInsets();
    }

    @Override
    public int getCharWidth() {
        return charWidth;
    }

    /**
     * @return character size in workspace, in pixels
     */
    @Override
    public int getCharHeight() {
        return charHeight;
    }

    /**
     * Returns the text contained in this {@link Workspace}.
     */
    @Override
    public String getText() {
        return area.getText();
    }

    @Override
    public int getPosition(int column, int row) {
        int nCharsBeforeTargetRow = row * area.getColumns();
        return (nCharsBeforeTargetRow - 1) + (column + 1);
    }

    @Override
    public void setDimensions(int columns, int rows) {
        area.setColumns(columns);
        area.setRows(rows);
        area.setMinimumSize(area.getPreferredSize());
    }

    public void fillEmpty(char ch) {
        int areaCapacity = area.getRows() * area.getColumns();
        char[] chars = new char[areaCapacity];
        Arrays.fill(chars, ch);
        String backgroundFilling = String.valueOf(chars);
        area.setText(backgroundFilling);
    }

    @Override
    public void show(Set<TextPixel> textPixels) {
        for (TextPixel p : textPixels) {
            int column = p.getColumn();
            int row = p.getRow();
            if (!checkReplaceInArea(column, row)) {
                continue;
            }

            int txtPos = getPosition(column, row);
            String replaceStr = String.valueOf(p.getSymbol());
            area.replaceRange(replaceStr, txtPos, txtPos + 1);
        }
    }

    public void replace(int column, int row, char replace) {
        if (!checkReplaceInArea(column, row)) {
            return;
        }

        String replaceStr = String.valueOf(replace);
        int txtPos = getPosition(column, row);
        area.replaceRange(replaceStr, txtPos, txtPos + 1);
        area.select(txtPos, txtPos); // Снятие выделения

    }

    @Override
    public boolean checkReplaceInArea(int column, int row) {
        boolean columnLessThanRightBorder = column < area.getColumns() - 1;
        boolean rowLessThanBottomBorder = row < area.getRows() - 1;

        return columnLessThanRightBorder && rowLessThanBottomBorder
                         && row >= 0
                         && column >= 0;
    }

    /**
     * Calculates a text position for the coordinates
     * relative to app window.
     *
     * @param x coordinate relative {@link Workspace}
     * @param y coordinate relative {@link Workspace}
     * @param topContainer
     * @return position in columns and rows
     * relative to app window
     * @see #resolveForPointRelativeWindow
     */

    @Override
    public TextPosition resolveForPointRelativeWindow(int x, int y, Container topContainer) {
        int xRelativeWorkspace = x - getXRelative(topContainer);
        int yRelativeWorkspace = y - getYRelative(topContainer);

        return resolve(xRelativeWorkspace, yRelativeWorkspace);
    }

    /**
     * Calculates a text position for the coordinates
     * relative to the workspace.
     *
     * @param x coordinate relative {@link Workspace}
     * @param y coordinate relative {@link Workspace}
     * @return position in columns and rows
     * relative to the workspace
     * @see #resolveForPointRelativeWindow
     */
    @Override
    public TextPosition resolve(int x, int y) {
        int xOnTextArea = x - area.getInsets().left;
        int yOnTextArea = y - area.getInsets().top;
        int column = xOnTextArea / charWidth;
        int row = yOnTextArea / charHeight;

        return new TextPosition(column, row);
    }

    @Override
    public Rectangle getRect(int column, int row,
                             int widthInChars, int heightInChars) {
        Insets areaInsets = area.getInsets();
        int x = getX() + areaInsets.left + column * charWidth;
        int y = getY() + areaInsets.top + row * charHeight;
        int width = widthInChars * charWidth;
        int height = heightInChars * charHeight;

        return new Rectangle(x, y, width, height);
    }

    /**
     * Sets the size of the workspace font in Pt.
     *
     * @param size in Pt.
     *            Should be more or equal than 8 and less or equal than 72.
     */
    @Override
    public void setFontSize(int size) {
        assert size >= 8 : "Size should be more or equal than 8";
        assert size >= 72 : "Size should be less or equal than 72";

        font = new Font("Courier New", Font.PLAIN, size);
        area.setFont(font);
        charHeight = Math.round((float) FONT_HEIGHT_100PX
                         / 100 * size);
        charWidth = Math.round((float) FONT_WIDTH_100PX
                         / 100 * size);
    }
    @Override
    public void setText(String text) {
        area.setText(text);
    }
//    @Override
//    public void highlight(Set<TextPosition> positions) {
//        for (TextPosition p : positions) {
//            if (!checkReplaceInArea(p.getColumn(), p.getRow())) {
//                continue;
//            }
//            int position = getPosition(p.getColumn(), p.getRow());

//            addHighlight(position);

//        }

//    }

    private void addHighlight(int position) {
        Highlighter h = area.getHighlighter();
        try {
            h.addHighlight(position, position + 1,
                             DefaultHighlighter.DefaultPainter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeHighlight() {
        area.getHighlighter().removeAllHighlights();
    }

    private static abstract class ContainerResolver {
        private final JTextArea area;

        static ContainerResolver getXResolver(JTextArea area) {
            return new ContainerResolver(area) {
                @Override
                protected int positionRelativeParentFor(Container child) {
                    return child.getX();
                }
            };
        }

        static ContainerResolver getYResolver(JTextArea area) {
            return new ContainerResolver(area) {
                @Override
                protected int positionRelativeParentFor(Container child) {
                    return child.getY();
                }
            };
        }

        private ContainerResolver(JTextArea area) {
            this.area = area;
        }

        protected abstract int positionRelativeParentFor(Container child);

        int resolveRelative(Container container) {
             int position = 0;
             Container current = area;
             while (current != container) {
                 position += positionRelativeParentFor(current);
                 current = current.getParent();
             }
             return position;
         }

    }
}
