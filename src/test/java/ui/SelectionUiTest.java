package ui;

import classes.TextSelectionBoundsResolver;
import com.formdev.flatlaf.IntelliJTheme;
import controllers.SelectionUiControllerImpl;
import controllers.TextBoundsRounder;


import javax.swing.*;
import java.awt.*;

public class SelectionUiTest extends JFrame {

    public SelectionUiTest() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setSize(500, 500);
        setVisible(true);

        JLayeredPane pane = getLayeredPane();


        Color color;



        JTextArea area = new JTextArea(20, 50);
//        area.setSelectionColor(new Color(color.getRed(),
//                         color.getGreen(),
//                         color.getBlue(),
//                         100));

        TextWorkspace workspace = new TextWorkspace(area);
        Dimension size = area.getPreferredSize();
        area.setBounds(20, 20, size.width, size.height);
        pane.add(area, JLayeredPane.DEFAULT_LAYER);



//        color = new Color(255, 127, 39);
        color = new Color(73, 70, 202);

        final Selection selection = new Selection(
                         new Dimension(20, 3), 9, color);
//        selection.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        TextBoundsRounder rounder = new TextBoundsRounder(new TextSelectionBoundsResolver(workspace, pane), workspace, selection,
                         pane);
        SelectionUiControllerImpl controller = new SelectionUiControllerImpl(
                         new SelectionAlgorithm(selection, 10),
                         rounder, selection, workspace, new WorkspaceHighlighter(area, workspace));
        selection.addMouseMotionListener(controller);
        selection.addMouseListener(controller);
        selection.setBounds(100,100,100,100);
        pane.add(selection, JLayeredPane.PALETTE_LAYER);
    }
    public static void main(String[] args) {
        IntelliJTheme.install(SelectionUiTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
//        IntelliJTheme.install(SelectionUiTest.class.getResourceAsStream("/Solarized Light.theme.json"));
        SwingUtilities.invokeLater(SelectionUiTest::new);
    }
}
