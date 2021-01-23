package interfaces;

import classes.TextPosition;

import java.awt.*;

public interface WorkspaceResolver {
    Rectangle getRect(int column, int row,
                      int widthInChars, int heightInChars);

    TextPosition resolveForPointRelativeWindow(int x, int y, Container topContainer);
}
