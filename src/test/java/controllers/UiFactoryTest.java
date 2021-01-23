package controllers;

import classes.UiFactory;
import com.formdev.flatlaf.IntelliJTheme;
import ui.UiLocator;

import javax.swing.*;

public class UiFactoryTest {
    public static void main(String[] args) {
        IntelliJTheme.install(ScrollPaneTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
        SwingUtilities.invokeLater(new UiFactory(UiLocator.getInstance())::createAllElements);
    }
}
