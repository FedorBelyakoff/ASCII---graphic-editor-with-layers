package ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;
import static ui.UiLocatorTest.En.ELEM_1;

public class UiLocatorTest {

    private UiLocator locator;

    @Before
    public void setUp() throws Exception {
        locator = UiLocator.getInstance();

    }

    @Test
    public void add() {
        locator.add(1, ELEM_1);
        Object expected = 1;
        Object actual = locator.resources.get(ELEM_1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get() {
        JMenuItem elem = new JMenuItem();
        locator.add(elem, ELEM_1);

        Assert.assertEquals(elem, locator.get(ELEM_1));
    }

    enum En {
        ELEM_1
    }

}