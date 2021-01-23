package ui;

import classes.TextPosition;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class TextWorkspaceTest {

    private TextWorkspace workspace;
    private JTextArea area;

    @Before
    public void setUp() throws Exception {
        area = new JTextArea(50, 50);
        workspace = new TextWorkspace(area);
    }

    @Test
    public void getTextPosition() {
        int actualPosition = workspace.getPosition(2, 1);
        assertEquals(52, actualPosition);
    }

    @Test
    public void positionRelateWindow() {
        workspace.setFontSize(15);
        TextPosition expectedPos = new TextPosition(1, 1);

        TextPosition actualPos = workspace.resolveForPointRelativeWindow(15 + area.getInsets().left,
                         20 + area.getInsets().top, new JLayeredPane());
        assertEquals(expectedPos, actualPos);
    }
}