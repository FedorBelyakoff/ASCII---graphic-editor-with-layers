package controllers;

import classes.MenuFactory;
import classes.TextInputAreaFactoryImpl;
import classes.TextPosition;
import classes.UiFactory;
import com.formdev.flatlaf.IntelliJTheme;
import enumerations.WindowComponents;
import interfaces.TextInputAreaFactory;
import interfaces.Workspace;
import org.mockito.Mockito;
import ui.StatusBar;
import ui.UiLocator;
import ui.WorkspaceHighlighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import static enumerations.WindowComponents.*;
import static enumerations.WindowComponents.WORKSPACE;

public class TextInputAreaFactoryTest {
    public static final int INIT_INPUT_FIELD_WIDTH = 10;
    private UiLocator locator;
    private JFrame frame;
    private StatusBar statusBar;
    private Workspace workspace;
    private JTextArea area;
    private TextInputAreaFactory areaFactory;

    public TextInputAreaFactoryTest() {
        createUi();
        setFields();
        addMouseController();
        createPositionController();
    }

    private void createUi() {
        locator = UiLocator.getInstance();
        UiFactory uiFactory = new UiFactory(locator);
        uiFactory.createAllElements();


        MenuFactory menuFactory = new MenuFactory(locator);
        menuFactory.createAllElements();
    }

    private void setFields() {
        frame = (JFrame) locator.get(FRAME);
        statusBar = (StatusBar) locator.get(STATUS_BAR);
        workspace = (Workspace) locator.get(WORKSPACE);
        area = (JTextArea) locator.get(AREA);
        areaFactory = new TextInputAreaFactoryImpl(locator, Mockito.mock(TextUiController.class));
    }

    private void addMouseController() {
        area.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (areaFactory.isInputAreaExist()) {
                    areaFactory.removeComponents();
//                    return;
                }
                TextPosition clickPosition = workspace.resolve(e.getX(), e.getY());
                createInputArea(clickPosition);
            }
        });
    }

    private void createInputArea(TextPosition location) {
//        System.out.println("Workspace pane indent: " + getWorkspacePaneYIndent() );
////        int absoluteY = area.getLocationOnScreen().y - frame.getLocationOnScreen().y;
////        System.out.println(absoluteY - area.getY());
        areaFactory.createComponents(location);

        TextInputArea inputArea = areaFactory.getInputArea();
        setInitInputAreaBounds(location, inputArea);
        highlightInputArea(location);
    }

    private void highlightInputArea(TextPosition initLocation) {
        WorkspaceHighlighter highlighter = new WorkspaceHighlighter(area, workspace);
        Set<TextPosition> positions = new HashSet<>();

        int initColumn = initLocation.getColumn();
        for (int i = initColumn;
             i < initColumn + INIT_INPUT_FIELD_WIDTH;
             i++) {
            TextPosition highlightPos = new TextPosition(i, initLocation.getRow());
            positions.add(highlightPos);
        }
        highlighter.highlight(positions);
    }

    private int getWorkspacePaneYIndent() {
        JPanel workspacePanel = (JPanel) locator.get(WORKSPACE_PANEl);

        int indent = 0;
        Container current = workspacePanel;
        while (current != frame.getLayeredPane()) {
            indent += current.getY();
            current = current.getParent();
        }
        return indent;
    }

    private void setInitInputAreaBounds(TextPosition location, TextInputArea inputArea) {
        inputArea.setLeft(location.getColumn());
        inputArea.setRight(location.getColumn() + INIT_INPUT_FIELD_WIDTH);
        inputArea.setTop(location.getRow());
        inputArea.setBottom(location.getRow() + 1);
        inputArea.resizeArea();
    }

    private void createPositionController() {
        TextPositionLabelController labelController = new TextPositionLabelController(statusBar, workspace);
        PositionLabelMouse mouse = new PositionLabelMouse(labelController);
        JLayeredPane layeredPane = frame.getLayeredPane();
        area.addMouseListener(mouse);
        area.addMouseMotionListener(mouse);
    }


    public static void main(String[] args) {
        IntelliJTheme.install(ScrollPaneTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
        SwingUtilities.invokeLater(TextInputAreaFactoryTest::new);
    }
}

