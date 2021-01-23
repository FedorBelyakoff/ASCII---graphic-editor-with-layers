package classes;

import controllers.TextUiController;
import controllers.WorkspaceFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ui.UiLocator;

import javax.swing.*;

import java.awt.*;

import static enumerations.WindowComponents.*;

public class TextInputAreaFactoryImplTest {

    private TextInputAreaFactoryImpl factory;

    @Before
    public void setUp() throws Exception {
        UiLocator locator = UiLocator.getInstance();
        updateLocator(locator);
        factory = new TextInputAreaFactoryImpl(locator, Mockito.mock(TextUiController.class));
    }

    private void updateLocator(UiLocator locator) {
        locator.add(getFrame(), FRAME);
        locator.add(getArea(), AREA);
        locator.add(new WorkspaceFactory().getWorkspace(), WORKSPACE);
    }

    private JTextArea getArea() {
        JTextArea area = Mockito.mock(JTextArea.class);
        Mockito.when(area.getInsets()).thenReturn(WorkspaceFactory.ZERO_INSETS);
        return area;
    }

    private JFrame getFrame() {
        JFrame frame = Mockito.mock(JFrame.class);
        Mockito.when(frame.getLayeredPane()).thenReturn(new JLayeredPane());
        return frame;
    }

    @Test
    public void createComponents() {
    }

    @Test
    public void getRect() {
//        Rectangle actualDimension = factory.getRect(new TextPosition(1, 1), 1, 1);
//        Rectangle expectedDimension = new Rectangle(105, 105, 20, 20);
//        Assert.assertEquals(expectedDimension, actualDimension);
    }

    @Test
    public void getInputArea() {
    }

    @Test
    public void getAreaMouseController() {
    }

    @Test
    public void removeComponents() {
    }

    @Test
    public void isInputAreaExist() {
    }

}