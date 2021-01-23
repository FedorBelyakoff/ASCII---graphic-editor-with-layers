package classes;

import com.formdev.flatlaf.IntelliJTheme;
import controllers.ScrollPaneTest;
import interfaces.Workspace;
import ui.Selection;
import ui.TextWorkspace;
import ui.UiConstants;

import javax.swing.*;
import java.awt.*;

public class ComponentPositionTest extends JFrame {

    private static final Rectangle CONTAINER_BOUNDS = new Rectangle(100, 100, 300, 300);
    public static final Dimension SELECTION_STROKE_DIMENSION = new Dimension(10, 3);
    public static final int SELECTION_STROKE_INSET = 10;
    public static final Rectangle SELECTION_BOUNDS = new Rectangle(100, 100, 100, 100);
    private JTextArea area;

    public ComponentPositionTest() throws HeadlessException {
        setWindowSettings();

        Workspace workspace = createWorkspace();
        drawTestStrokes(workspace);
    }

    private void drawTestStrokes(Workspace workspace) {
        Insets insets = workspace.getInsets();
        int charHeight = workspace.getCharHeight();
        int rows = workspace.getRows();
        Graphics g = getGraphics();

        int y = area.getY() + insets.top;
        for (int i = 0; i < rows; i++) {
            g.setColor(Color.BLACK);
            g.drawLine(0, y, 1000, y);
            y += charHeight;
        }


    }

    private Workspace createWorkspace() {
        area = new JTextArea();
        area.setLocation(100, 100);

        TextWorkspace workspace = new TextWorkspace(area);
        workspace.setDimensions(50, 20);
        workspace.fillEmpty('x');

        area.setSize(area.getPreferredSize());
        add(area);

        return workspace;
    }

    private void createSelection(JPanel container) {
        Selection selection = new Selection(SELECTION_STROKE_DIMENSION,
                         SELECTION_STROKE_INSET,
                         UiConstants.INPUT_AREA_SELECTION_COLOR);
        selection.setBounds(SELECTION_BOUNDS);

        container.add(selection);
    }

    private JPanel createContainerPanel() {
        JPanel container = new JPanel(null);
        container.setBounds(CONTAINER_BOUNDS);
        container.setBackground(Color.GRAY);
        add(container);

        return container;
    }

    private void setWindowSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        IntelliJTheme.install(ComponentPositionTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
        SwingUtilities.invokeLater(ComponentPositionTest::new);
    }
}
