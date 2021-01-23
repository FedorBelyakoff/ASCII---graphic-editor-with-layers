package controllers;

import classes.TextPosition;
import classes.TextSelectionBoundsResolver;
import interfaces.Workspace;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import ui.Selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TextBoundsRounderTest {
    private TextBoundsRounder rounder;
    private Selection selection;
    private WorkspaceFactory workspaceFactory;


    @Before
    public void setUp() throws Exception {
        selection = new Selection(new Dimension(10, 3), 10, Color.blue);
        workspaceFactory = new WorkspaceFactory();
        Workspace workspace = workspaceFactory.getWorkspace();
        JLayeredPane topContainer = new JLayeredPane();
        rounder = new TextBoundsRounder(new TextSelectionBoundsResolver(workspace, topContainer), workspace, selection, topContainer);
        Mockito.when(workspace.resolveForPointRelativeWindow(109, 109, topContainer))
                         .thenReturn(new TextPosition(0, 0));
        Mockito.when(workspace.resolveForPointRelativeWindow(124, 124, topContainer))
                         .thenReturn(new TextPosition(2, 2));
    }


    @Test
    public void resizeInChars() {
        selection.setBounds(105, 105, 10, 10);
        rounder.resizeInChars(1, 1);

        int actualWidthInChars = rounder.getWidthInChars();
        assertEquals(1, actualWidthInChars);
        int actualHeightInChars = rounder.getHeightInChars();
        assertEquals(1, actualHeightInChars);

        int actualWidth = selection.getWidth();
        int actualHeight = selection.getHeight();
        int expectedWidth = 20;
        int expectedHeight = 20;

        assertEquals(expectedWidth, actualWidth);
        assertEquals(expectedHeight, actualHeight);
    }

    @Test
    public void roundBounds() {
        selection.setBounds(109, 109,
                         15, 15);

        rounder.update(Mockito.mock(MouseEvent.class));
        int actualWidth = selection.getWidth();
        int actualHeight = selection.getHeight();

        assertEquals(20, actualWidth);
        assertEquals(20, actualHeight);

    }

    @Test
    public void resizeSelection() {
    }
}