package controllers;

import com.formdev.flatlaf.IntelliJTheme;
import interfaces.Model;
import org.mockito.Mockito;
import ui.TextWorkspace;
import ui.WorkspaceHighlighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class BrushUiTest extends JFrame {

    private final int size;
    private JLayeredPane pane;
    private JTextArea area;
    private TextWorkspace workspace;
    private Model model;

    public BrushUiTest() throws HeadlessException {
        setWindowSettings();


        pane = getLayeredPane();

        createWorkspace();


        createModel();

        size = 3;


        BrushUiController controller = new BrushUiController(model, workspace,
                         new BrushTextUi(workspace),new WorkspaceHighlighter(area, workspace));
        controller.setSize(size);
        controller.setChar('c');

        BrushMouseController mouseController = new BrushMouseController(controller, workspace);
        area.addMouseListener(mouseController);
        area.addMouseMotionListener(mouseController);
//        controller.setMouseController(mouseController);

    }

    private void setWindowSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setSize(500, 500);
        setVisible(true);
    }

    private void createCircle() {
        Circle circle = new Circle();
        int circleWidth = 2 * size * workspace.getCharWidth();
        int circleHeight = size * workspace.getCharHeight();
        circle.setBounds(0, 0, circleWidth, circleHeight);
        pane.add(circle, JLayeredPane.PALETTE_LAYER);

        CircleMouseAdapter mouseAdapter = new CircleMouseAdapter(circle);
        area.addMouseListener(mouseAdapter);
        area.addMouseMotionListener(mouseAdapter);

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
        area.setBackground(Color.WHITE);



        workspace = new TextWorkspace(area);
        Dimension size = area.getPreferredSize();
        area.setBounds(20, 20, size.width, size.height);
        pane.add(area, JLayeredPane.DEFAULT_LAYER);
    }

    public static void main(String[] args) {
        IntelliJTheme.install(BrushUiTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
        SwingUtilities.invokeLater(BrushUiTest::new);
    }

    private static class Circle extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }

    private class CircleMouseAdapter
                     extends MouseAdapter {

        private final Circle circle;

        public CircleMouseAdapter(Circle circle) {
            this.circle = circle;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = workspace.getX() + e.getX() - circle.getWidth() / 2;
            int y = workspace.getY() + e.getY() - circle.getHeight() / 2;
            circle.setLocation(x, y);
        }
    }



}
