package interfaces;

import classes.TextPixel;
import classes.TextPosition;

import java.awt.*;
import java.util.Set;

public interface Workspace extends WorkspaceResolver {
    TextPosition resolve(int x, int y);

    void show(Set<TextPixel> textPixels);

    int getPosition(int column, int row);

    TextPosition resolveForPointRelativeWindow(int x, int y, Container topContainer);

    void setFontSize(int size);

    int getX();

    int getY();

    int getXRelative(Container container);

    int getYRelative(Container container);

    int getCharWidth();

    int getCharHeight();

    String getText();

    void setText(String text);

    boolean checkReplaceInArea(int column, int row);


//    void highlight(Set<TextPosition> positions);

    void removeHighlight();

    Insets getInsets();

    void setDimensions(int columns, int rows);

    int getColumns();

    int getRows();
}
