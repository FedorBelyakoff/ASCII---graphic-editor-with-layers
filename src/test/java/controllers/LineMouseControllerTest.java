package controllers;

import classes.TextPosition;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import static org.junit.Assert.*;

public class LineMouseControllerTest {
    LineMouseController controller;

    @Before
    public void setUp() {
        WorkspaceMock workspaceMock = new WorkspaceMock();
        controller = new LineMouseController(workspaceMock, new DrawingLineControllerMock());
    }

    @Test
    public void mouseClicked() {
        controller.mouseClicked(new MouseEvent(new JComponent() {
        }, 10, 33, MouseEvent.BUTTON1_MASK,
                         20, 55, 1, false, MouseEvent.BUTTON1));

        TextPosition expectedFirstPos = new TextPosition(0, 1);
        assertEquals(expectedFirstPos, controller.getFirstPosition());
    }

    @Test
    public void mouseMoved() {
        controller.mouseClicked(new MouseEvent(new JComponent() {
        }, 10, 33, MouseEvent.BUTTON1_MASK,
                         20, 55, 1, false, MouseEvent.BUTTON1));

        controller.mouseMoved(new MouseEvent(new JComponent() {
        }, 10, 33, InputEvent.META_MASK,
                         36, 76, 1, false, MouseEvent.NOBUTTON));
        TextPosition expectedFirstPos = new TextPosition(0, 1);
        TextPosition expectedSecondPos = new TextPosition(2, 2);

        assertEquals(expectedFirstPos, controller.getFirstPosition());
        assertEquals(expectedSecondPos, controller.getSecondPosition());
    }

    @Test
    public void mouseMovedWithCtrl() {
        controller.mouseClicked(new MouseEvent(new JComponent() {
        }, 10, 33, MouseEvent.BUTTON1_MASK,
                         20, 183, 1, false, MouseEvent.BUTTON1));

        controller.mouseMoved(new MouseEvent(new JComponent() {
        }, 10, 33, InputEvent.CTRL_DOWN_MASK,
                         26, 123, 1, false, MouseEvent.NOBUTTON));

        TextPosition expectedFirstPos = new TextPosition(0, 9);
        TextPosition expectedSecondPos = new TextPosition(0, 5);

        assertEquals(expectedFirstPos, controller.getFirstPosition());
        assertEquals(expectedSecondPos, controller.getSecondPosition());
    }

    @Test
    public void mouseDragged() {

    }
}