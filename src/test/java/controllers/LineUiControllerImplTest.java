package controllers;

import classes.TextPixel;
import classes.TextPosition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class LineUiControllerImplTest {

    private LineMouseController mouseMock;
    private LineTextUi textUiMock;

    @Before
    public void setUp() {
        mouseMock = Mockito.mock(LineMouseController.class);
        Mockito.when(mouseMock.getFirstPosition())
                         .thenReturn(new TextPosition(0, 0));
        Mockito.when(mouseMock.getSecondPosition())
                         .thenReturn(new TextPosition(3, 3));

        textUiMock = Mockito.mock(LineTextUi.class);

        Set<TextPixel> pixels = new HashSet<>();
        char c = 'c';
        Collections.addAll(pixels, new TextPixel(0, 0, c),
                         new TextPixel(1, 1, c),
                         new TextPixel(2, 2, c),
                         new TextPixel(3, 3, c));
        Mockito.when(textUiMock.getTextPixels()).thenReturn(pixels);
    }

    @Test
    public void drawLine() {
        TextPosition expectedFirst = new TextPosition(0, 0);
        TextPosition expectedSecond = new TextPosition(3, 3);

        assertEquals(expectedFirst, mouseMock.getFirstPosition());
        assertEquals(expectedSecond, mouseMock.getSecondPosition());
    }

    @Test
    public void removeLine() {

    }
}