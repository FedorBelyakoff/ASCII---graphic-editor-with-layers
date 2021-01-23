package controllers;

import classes.TextPosition;
import edu.emory.mathcs.backport.java.util.Collections;
import interfaces.TextInputAreaFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ui.WorkspaceHighlighter;

import java.util.HashSet;

public class TextUiControllerTest {

    private TextUiController controller;
    private TextInputAreaFactory factory;
    private TextInputArea inputArea;
    private WorkspaceHighlighter highlighter;

    @Before
    public void setUp() throws Exception {
        controller = new TextUiController();
        highlighter = Mockito.mock(WorkspaceHighlighter.class);
        factory = Mockito.mock(TextInputAreaFactory.class);

        inputArea = Mockito.mock(TextInputArea.class);
        Mockito.when(factory.getAreaMouseController())
                         .thenReturn(Mockito.mock(InputAreaMouseController.class));

        Mockito.when(factory.getInputArea())
                         .thenReturn(inputArea);

        registerComponents();

    }

    private void registerComponents() {
        controller.register(highlighter);
        controller.register(factory);
        controller.register(Mockito.mock(TextMouseController.class));
        controller.register(Mockito.mock(TextKeyboard.class));
        controller.register(Mockito.mock(WorkspaceHighlighter.class));
        controller.register(Mockito.mock(TextUi.class));
    }


    @Test
    public void createInputArea() {
    }

    @Test
    public void updateBounds() {
    }

    @Test
    public void replaceCursor() {

    }

    @Test
    public void lineBreak() {
    }

    @Test
    public void select() {
    }

    @Test
    public void input() {
        HashSet<TextPosition> positions = new HashSet<>();
        positions.add(new TextPosition(0, 0));
        positions.add(new TextPosition(1, 0));
        Mockito.when(inputArea.getAreaPositions()).thenReturn(positions);

        controller.createInputArea(new TextPosition(0, 0));
        controller.input('c');

        Mockito.verify(inputArea, Mockito.times(1))
                         .setTop(0);
        Mockito.verify(inputArea, Mockito.times(1))
                         .setBottom(0);
        Mockito.verify(inputArea, Mockito.times(1))
                         .setRight(1);
        Mockito.verify(inputArea, Mockito.times(1))
                         .setLeft(0);
    }
}