package classes;

import ui.Selection;
import ui.UiConstants;

import javax.swing.*;
import java.awt.*;

public class ComponentPositionTest extends JFrame {

    private static final Rectangle CONTAINER_BOUNDS = new Rectangle(100, 100, 300, 300);
    public static final Dimension SELECTION_STROKE_DIMENSION = new Dimension(10, 3);
    public static final int SELECTION_STROKE_INSET = 10;
    public static final Rectangle SELECTION_BOUNDS = new Rectangle(100, 100, 100, 100);

    public ComponentPositionTest() throws HeadlessException {
        setWindowSettings();

        JPanel container = createContainerPanel();
        createSelection(container);
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
        SwingUtilities.invokeLater(ComponentPositionTest::new);
    }
}
