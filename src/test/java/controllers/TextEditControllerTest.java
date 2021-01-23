package controllers;

import classes.*;
import com.formdev.flatlaf.IntelliJTheme;
import enumerations.WindowComponents;
import interfaces.Model;
import interfaces.SelectionController;
import interfaces.Workspace;
import org.mockito.Mockito;
import ui.UiLocator;
import ui.WorkspaceHighlighter;

import javax.swing.*;
import java.util.Arrays;

public class TextEditControllerTest {

    private SelectionMenuController menuController;
    private TextControllerImpl controller;
    private UiLocator locator;
    private TextInputAreaFactoryImpl inputAreaFactory;
    private TextUi textUi;
    private TextUiController workspaceController;

    public TextEditControllerTest() {
        createUi();
        createUiController();
        createTextEditController();

    }

    private void createUiController() {
        workspaceController = new TextUiController();
        Workspace workspace = (Workspace) locator.get(WindowComponents.WORKSPACE);
        inputAreaFactory = new TextInputAreaFactoryImpl(locator, workspaceController);
        WorkspaceHighlighter highlighter = new WorkspaceHighlighter((JTextArea) locator.get(WindowComponents.AREA),
                         workspace);
        textUi = new TextUi(workspace, highlighter);

        workspaceController.register(getMouseController(workspace));
        workspaceController.register(new TextKeyboard(workspaceController));
        workspaceController.register(highlighter);
        workspaceController.register(inputAreaFactory);
        workspaceController.register(textUi);
    }

    private TextMouseController getMouseController(Workspace workspace) {
        JTextArea area = (JTextArea) locator.get(WindowComponents.AREA);
        TextMouseController mouseController = new TextMouseController(workspaceController, workspace);
        area.addMouseListener(mouseController);
        area.addMouseMotionListener(mouseController);
        return mouseController;
    }

    private void createTextEditController() {
        controller = new TextControllerImpl();

        controller.factory = inputAreaFactory;
        controller.menuController = createMenuController();
        controller.popupMenuController = createMenuController();
        controller.model = createModel();
        controller.textUi = textUi;
        controller.workspaceController = workspaceController;
    }

    private Model createModel() {
        Model model = Mockito.mock(Model.class);

        Mockito.when(model.getText(0)).thenReturn(fillEmpty('_'));
        return model;
    }

    private String fillEmpty(char ch) {

        JTextArea area = (JTextArea) locator.get(WindowComponents.AREA);
        int nCharsInArea = area.getRows() * area.getColumns();

        char[] chars = new char[nCharsInArea];
        Arrays.fill(chars, ch);
        return String.valueOf(chars);
    }

    private TextMenuController createMenuController() {
        TextMenuController menuController = new TextMenuController(locator, controller);
        menuController.addListeners();
        return menuController;
    }

    private void createUi() {
        locator = UiLocator.getInstance();
        UiFactory uiFactory = new UiFactory(locator);
        uiFactory.createAllElements();

        MenuFactory menuFactory = new MenuFactory(locator);
        menuFactory.createAllElements();
    }


    public static void main(String[] args) {
        IntelliJTheme.install(TextEditControllerTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
        SwingUtilities.invokeLater(TextEditControllerTest::new);
    }

    private class SelectionControllerMock implements SelectionController {


        @Override
        public void select() {

        }

        @Override
        public void deselect() {
            menuController.deselect();
        }

        @Override
        public void cut() {
            menuController.cut();
        }

        @Override
        public void copy() {
            menuController.copy();
        }

        @Override
        public void paste() {
            menuController.paste();
        }

        @Override
        public void delete() {
            menuController.delete();
        }

        @Override
        public void flipHorizontally() {

        }

        @Override
        public void flipVertically() {

        }


        @Override
        public void rotateRight90() {

        }

        @Override
        public void rotateRight180() {

        }

        @Override
        public void rotateLeft90() {

        }

        @Override
        public void rotateLeft180() {

        }


    }


}
