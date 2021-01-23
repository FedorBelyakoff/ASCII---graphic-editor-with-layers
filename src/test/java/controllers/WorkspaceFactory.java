package controllers;

import classes.TextPosition;
import interfaces.Workspace;
import org.mockito.Mockito;

import java.awt.*;

public class WorkspaceFactory {
    public static final int CHAR_WIDTH = 10;
    public static final int CHAR_HEIGHT = 10;
    public static final int X = 100;
    public static final int Y = 100;
    public static final Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
    public static final TextPosition RESOLVED_POSITION = new TextPosition(1, 1);

    public Workspace getWorkspace() {
        Workspace workspace = Mockito.mock(Workspace.class);
        Mockito.when(workspace.getCharWidth()).thenReturn(CHAR_WIDTH);
        Mockito.when(workspace.getCharHeight()).thenReturn(CHAR_HEIGHT);
        Mockito.when(workspace.getX()).thenReturn(X);
        Mockito.when(workspace.getY()).thenReturn(Y);
        Mockito.when(workspace.getInsets()).thenReturn(ZERO_INSETS);

        return workspace;
    }

}
