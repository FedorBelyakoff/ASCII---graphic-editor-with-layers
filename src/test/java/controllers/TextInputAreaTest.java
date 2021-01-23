package controllers;

import classes.TextPosition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ui.SelectionAlgorithm;

import java.util.HashSet;
import java.util.Set;

public class TextInputAreaTest {

    private TextInputArea inputArea;

    @Before
    public void setUp() throws Exception {
        SelectionAlgorithm algorithm = Mockito.mock(SelectionAlgorithm.class);
        TextBoundsRounder rounder = Mockito.mock(TextBoundsRounder.class);
        TextUiController controller = Mockito.mock(TextUiController.class);

        inputArea = new TextInputArea(algorithm, rounder);
    }

    @Test
    public void updateBounds() {
        inputArea.setLeft(0);
        inputArea.setRight(1);
        inputArea.setTop(0);
        inputArea.setBottom(1);

        Set<TextPosition> positions = new HashSet<>();
        positions.add(new TextPosition(0, 0));
        positions.add(new TextPosition(1, 0));
        positions.add(new TextPosition(0, 1));
        positions.add(new TextPosition(1, 1));

        Assert.assertEquals(positions, inputArea.getAreaPositions());
    }
}