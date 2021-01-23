package ui;


import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Test3 extends JFrame {
    public Test3() throws HeadlessException {
        setSize(1000, 1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);

        final Color color = new Color(255, 127, 39);

        JLayeredPane pane = getLayeredPane();

        // selection
        final Selection selection = new Selection(
                         new Dimension(20, 3), 9, color);
        selection.setBounds(0, 0, 100, 100);
        selection.setVisible(true);
        final SelectionAlgorithm algorithm = new SelectionAlgorithm(selection, 10);
        algorithm.setMoveEnabled(false);
        selection.addMouseListener(algorithm);
        selection.addMouseMotionListener(algorithm);
        pane.add(selection, JLayeredPane.PALETTE_LAYER);

        // text area
        JTextArea area = new JTextArea(50, 50);
        fillArea(area, 'T');
        highlight(area, 0, 10);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        area.setLineWrap(true);
        area.setSelectionColor(color);
//        area.setBackground(new Color(0, 0, 0, 0));
        area.setBounds(100, 100, 300, 300);
        pane.add(area, JLayeredPane.DEFAULT_LAYER);

        // component
        MovingComponent comp = new MovingComponent();
        ComponentMover mover = new ComponentMover(comp);
        comp.addMouseListener(mover);
        comp.addMouseMotionListener(mover);
        comp.setBounds(50, 50, 15, 15);
        pane.add(comp, JLayeredPane.PALETTE_LAYER);




    }

    private void highlight(JTextArea area, int p0, int p1) {
        Highlighter h = area.getHighlighter();
        try {
            h.addHighlight(0, 10, DefaultHighlighter.DefaultPainter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void fillArea(JTextArea area, char symbol) {
        int areaCapacity = area.getRows() * area.getColumns();
        char[] chars = new char[areaCapacity];
        Arrays.fill(chars, symbol);
        String textForArea = String.valueOf(chars);
        area.setText(textForArea);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test3::new);
    }

    private static class MovingComponent extends JComponent {

        public static final int CIRCLE_WIDTH = 3;
        private final Color circleColor = new Color(255, 127, 39);

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setStroke(new BasicStroke(CIRCLE_WIDTH));
            g2d.setColor(circleColor);
            g2d.drawOval(0, 0, getWidth(), getHeight());
        }
    }

    private static class ComponentMover extends MouseAdapter {
        private final JComponent component;
        private int xOnComponent;
        private int yOnComponent;
        private boolean dragged;


        private ComponentMover(JComponent component) {
            this.component = component;
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if (!dragged) {
                xOnComponent = x;
                yOnComponent = y;
                dragged = true;
            }

            int dx = x - xOnComponent;
            int dy = y - yOnComponent;

            int newXLocation = component.getX() + dx;
            int newYLocation = component.getY() + dy;
            component.setLocation(newXLocation, newYLocation);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            dragged = false;
        }
    }
}
