package controllers;

import classes.TextPixel;
import classes.TextPosition;
import interfaces.Workspace;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class LineTextUiTest {
    LineTextUi textUi;

    @Before
    public void setUp() throws Exception {
//        Workspace workspace = new WorkspaceMock();
        textUi = new LineTextUi();
    }

    @Test
    public void draw() {
        char c = 'c';
        textUi.setChar(c);

        textUi.draw(new TextPosition(0, 0),
                         new TextPosition(3, 3));

        Set<TextPixel> expectedPixels = new HashSet<>();

        Collections.addAll(expectedPixels, new TextPixel(0, 0, c),
                         new TextPixel(1, 1, c),
                         new TextPixel(2, 2, c),
                         new TextPixel(3, 3, c));

        assertArrayEquals(expectedPixels.toArray(),textUi.getTextPixels().toArray() );
    }
}