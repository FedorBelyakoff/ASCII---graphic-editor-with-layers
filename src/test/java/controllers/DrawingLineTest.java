package controllers;

import com.formdev.flatlaf.IntelliJTheme;
import interfaces.Model;
import interfaces.UILineController;
import org.mockito.Mockito;
import ui.TextWorkspace;
import ui.WorkspaceHighlighter;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DrawingLineTest extends JFrame {

    private JLayeredPane pane;
    private JTextArea area;
    private TextWorkspace workspace;
    private Model model;
    private  LineTextUi textUi;
    private UILineController controller;

    public DrawingLineTest() throws HeadlessException {
        setWindowSettings();

        createPane();

        createWorkspace();

        createTextUi();

        createModel();

        createController();

        createMouseController();

    }

    private void createMouseController() {
        LineMouseController mouseController = new LineMouseController(workspace, controller);
        area.addMouseListener(mouseController);
        area.addMouseMotionListener(mouseController);
        controller.setMouseController(mouseController);
    }

    private void createController() {
        controller = new LineUiControllerImpl(model, textUi, workspace,
                         new WorkspaceHighlighter(area, workspace)
        );
    }

    private void createTextUi() {
        textUi = new LineTextUi();
        textUi.setChar('c');
    }

    private void createPane() {
        pane = getLayeredPane();
    }

    private void setWindowSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setSize(500, 500);
        setVisible(true);
    }

    private void createModel() {
        model = Mockito.mock(Model.class);

        Mockito.when(model.getText(0)).thenReturn(fillEmpty('_'));
    }

    private String fillEmpty(char ch) {
        char[] chars = new char[(area.getRows())
                         * (area.getColumns())];
        Arrays.fill(chars, ch);
        String str = String.valueOf(chars);
//        displayedCharacters.insert(0, str);
        return str;
    }

    private void createWorkspace() {
        area = new JTextArea(20, 40);
        area.setEditable(false);


        workspace = new TextWorkspace(area);
        Dimension size = area.getPreferredSize();
        area.setBounds(20, 20, size.width, size.height);
        pane.add(area, JLayeredPane.DEFAULT_LAYER);
    }

    public static void main(String[] args) {
        IntelliJTheme.install(DrawingLineTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
//        IntelliJTheme.install(SelectionUiTest.class.getResourceAsStream("/Solarized Light.theme.json"));
        SwingUtilities.invokeLater(DrawingLineTest::new);
    }
}
