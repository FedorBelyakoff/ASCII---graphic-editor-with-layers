package controllers;

import classes.TextPosition;
import classes.TextSelectionBoundsResolver;
import com.formdev.flatlaf.IntelliJTheme;
import interfaces.Model;
import interfaces.TextInputAreaFactory;
import interfaces.WorkspaceResolver;
import org.mockito.Mockito;
import ui.*;
import ui.Selection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

import static ui.UiConstants.*;

public class TextTest extends JFrame {

    public static final Dimension STROKE_DIMENSION = new Dimension(20, 3);
    private JLayeredPane pane;
    private JTextArea area;
    private TextWorkspace workspace;
    private Model model;
    private Selection selection;
    private TextMouseController mouse;
    private TextKeyboard keyboard;
    private TextUiController textUiController;

    public TextTest() throws HeadlessException {
        setWindowSettings();

        initPane();

        createWorkspace();


        createModel();

        createTextUiController();

    }


    private void setWindowSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setSize(500, 500);
        setVisible(true);
    }

    private void initPane() {
        pane = getLayeredPane();
    }

    private void createWorkspace() {
        area = new JTextArea(20, 40);
        area.setEditable(false);


        workspace = new TextWorkspace(area);
        Dimension size = area.getPreferredSize();
        area.setBounds(20, 20, size.width, size.height);
        area.removeMouseMotionListener((MouseMotionListener) area.getCaret());
        area.removeMouseListener((MouseListener) area.getCaret());
        pane.add(area, JLayeredPane.DEFAULT_LAYER);
    }

    private void createModel() {
        model = Mockito.mock(Model.class);

        Mockito.when(model.getText(0)).thenReturn(fillEmpty('_'));
    }

    private void createTextUiController() {
        textUiController = new TextUiController();

        createMouse(textUiController);
        createKeyboard(textUiController);

        registerComponents(textUiController);
    }

    private void registerComponents(TextUiController controller) {
        WorkspaceHighlighter highlighter = new WorkspaceHighlighter(area, workspace);
        controller.register(mouse);
        controller.register(keyboard);
        controller.register(highlighter);
        controller.register(new TextUi(workspace, highlighter));
        controller.register(new TextComponentsFactory());
    }

    private void createKeyboard(TextUiController controller) {
        keyboard = new TextKeyboard(controller);
        area.addKeyListener(keyboard);
    }

    private void createMouse(TextUiController controller) {
        mouse = new TextMouseController(controller, workspace);

        area.addMouseListener(mouse);
        area.addMouseMotionListener(mouse);
    }


    private String fillEmpty(char ch) {
        char[] chars = new char[(area.getRows())
                         * (area.getColumns())];
        Arrays.fill(chars, ch);
        String str = String.valueOf(chars);
//        displayedCharacters.insert(0, str);
        return str;
    }

    public static void main(String[] args) {
        IntelliJTheme.install(TextTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
//        IntelliJTheme.install(SelectionUiTest.class.getResourceAsStream("/Solarized Light.theme.json"));
        SwingUtilities.invokeLater(TextTest::new);
    }

    private class TextComponentsFactory implements TextInputAreaFactory {

        private SelectionAlgorithm algorithm;
        private TextInputArea inputArea;
        private InputAreaMouseController mouseController;
        private TextBoundsRounder rounder;
        private InputAreaMouseAdapter adapter;

        @Override
        public void createComponents(TextPosition location) {
            createSelection(location);
            createAlgorithm();
            createInputArea(textUiController);
            createMouseController(textUiController);
            setAlgorithmSettings();
            addSelectionListeners();
        }

        private void createSelection(TextPosition location) {
            selection = new Selection(STROKE_DIMENSION,
                             SELECTION_BORDER_SIZE,
                             INPUT_AREA_SELECTION_COLOR);
            selection.setBounds(new TextSelectionBoundsResolver(workspace, pane).getBounds(
                             location.getColumn(), location.getRow(),
                             1, 1));
            selection.setVisible(true);
            pane.add(selection, JLayeredPane.PALETTE_LAYER);
        }

        private void setAlgorithmSettings() {
            algorithm.setMoveEnabled(false);
        }

        @Override
        public void removeComponents() {
            pane.remove(selection);
            selection.removeMouseListener(adapter);
            selection.removeMouseMotionListener(adapter);

            nullifyLinks();
        }

        @Override
        public boolean isInputAreaExist() {
            return inputArea != null;
        }

        @Override
        public TextInputArea getInputArea() {
            if (inputArea == null) {
                throw new IllegalStateException("Components were not created");
            }
            return inputArea;
        }

        @Override
        public InputAreaMouseController getAreaMouseController() {
            if (mouseController == null) {
                throw new IllegalStateException("Components were not created");
            }

            return mouseController;
        }

        private void createAlgorithm() {
            algorithm = new SelectionAlgorithm(selection, SELECTION_BORDER_SIZE);
        }

        private void createInputArea(TextUiController controller) {

            rounder = new TextBoundsRounder(new TextSelectionBoundsResolver(workspace, pane), workspace, selection,
                             pane);
            inputArea = new TextInputArea(algorithm, rounder);
            inputArea.setController(controller);
        }

        private WorkspaceResolver getRounderResolver() {
            return new WorkspaceResolver() {
                @Override
                public Rectangle getRect(int column, int row,
                                         int widthInChars, int heightInChars) {
                    Insets areaInsets = area.getInsets();
                    int charWidth = workspace.getCharWidth();
                    int charHeight = workspace.getCharHeight();

                    int x = workspace.getX() + areaInsets.left +
                                     column * charWidth - SELECTION_BORDER_SIZE;
                    int y = workspace.getY() + areaInsets.top +
                                     row * charHeight - SELECTION_BORDER_SIZE;
                    int width = widthInChars * charWidth + SELECTION_BORDER_SIZE * 2;
                    int height = heightInChars * charHeight + charHeight + SELECTION_BORDER_SIZE * 2;

                    return new Rectangle(x, y, width, height);
                }

                @Override
                public TextPosition resolveForPointRelativeWindow(int x, int y, Container topContainer) {

                    Insets insets = area.getInsets();
                    int charWidth = workspace.getCharWidth();
                    int charHeight = workspace.getCharHeight();

                    int xOnTextArea = x - area.getX() - insets.left;
                    int yOnTextArea = y - area.getY() - insets.top;
                    int column = xOnTextArea / charWidth + 1;
                    int row = yOnTextArea / charHeight + 1;

                    return new TextPosition(column, row);
                }
            };
        }

        private void addSelectionListeners() {
            adapter = new InputAreaMouseAdapter(mouseController, algorithm, inputArea, rounder);
            selection.addMouseMotionListener(adapter);
            selection.addMouseListener(adapter);
        }

        private void createMouseController(TextUiController controller) {
            mouseController = new InputAreaMouseController(
                             workspace, algorithm, controller, selection, pane);
        }



        private void nullifyLinks() {
            algorithm = null;
            inputArea = null;
            mouseController = null;
            rounder = null;
            adapter = null;
        }
    }
}
