package classes;

import interfaces.Workspace;

import java.awt.*;

import static ui.UiConstants.SELECTION_BORDER_SIZE;

public class TextSelectionBoundsResolver {
    private final Workspace workspace;
    private final Container topContainer;

    public TextSelectionBoundsResolver(Workspace workspace, Container topContainer) {
        this.workspace = workspace;
        this.topContainer = topContainer;
    }

    public Rectangle getBounds(TextPosition location, int widthInChars, int heightInChars) {
       return getBounds(location.getColumn(), location.getRow(), widthInChars, heightInChars);
    }

    public Rectangle getBounds(int columnPosition, int rowPosition,
                               int widthInChars, int heightInChars) {
        Insets areaInsets = workspace.getInsets();

        int charWidth = workspace.getCharWidth();
        int charHeight = workspace.getCharHeight();

        int entryFieldX = workspace.getXRelative(topContainer) + areaInsets.left +
                         columnPosition * charWidth;
        int entryFieldWidth = widthInChars * charWidth;


        //If the width of the selection boundary is greater than
        // the width of the symbol - make the indentation of the floor of the symbol.
        int x;
        int width;
        if (SELECTION_BORDER_SIZE < charWidth) {
            x = entryFieldX - SELECTION_BORDER_SIZE;
            width = entryFieldWidth + SELECTION_BORDER_SIZE * 2;
        } else {
            x = entryFieldX - charWidth / 2;
            width = entryFieldWidth + charWidth;
        }

        int entryFieldY = workspace.getYRelative(topContainer) + areaInsets.top +
                         rowPosition * charHeight;
        int entryFieldHeight = heightInChars * charHeight;

        //Similar to the previous block.
        int y;
        int height;
        if (SELECTION_BORDER_SIZE < charHeight) {
            y = entryFieldY - SELECTION_BORDER_SIZE;
            height = entryFieldHeight  + SELECTION_BORDER_SIZE * 2;
        } else {
            y = entryFieldY - charHeight / 2;
            height = entryFieldHeight + charHeight;
        }


        return new Rectangle(x, y, width, height);
    }
}
