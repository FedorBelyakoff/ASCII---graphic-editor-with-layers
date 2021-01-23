package controllers;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.ui.FlatTextAreaUI;
import ui.WorkspaceHighlighter;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.WrappedPlainView;
import java.awt.*;
import java.util.*;

public class ViewTest extends JFrame {

    private JTextArea area;

    public ViewTest() throws HeadlessException {
        createGui();
    }

    private void createGui() {
        setWindowSettings();

        createArea();


    }

    private void createArea() {
        FlatTextAreaUI areaUI = getAreaUI();


        area = new JTextArea(20, 50);
        area.setLineWrap(true);
        area.setUI(areaUI);
        area.setText(fillEmpty('x'));

        add(area);
    }

    private void setWindowSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setSize(500, 500);
        setVisible(true);
    }

    private String fillEmpty(char ch) {
        char[] chars = new char[(area.getRows())
                         * (area.getColumns())];
        Arrays.fill(chars, ch);
        String str = String.valueOf(chars);
//        displayedCharacters.insert(0, str);
        return str;
    }

    private FlatTextAreaUI getAreaUI() {
        return new FlatTextAreaUI() {
            @Override
            public View create(Element elem) {
                View view = super.create(elem);

                if (view instanceof WrappedPlainView) {
                    return getMyView(elem);
                }
                return view;
            }
        };
    }

    private WorkspaceHighlighter.MyTextView getMyView(Element elem) {
        WorkspaceHighlighter.MyTextView view = new WorkspaceHighlighter.MyTextView(elem);
        ArrayList<Integer> highlights = new ArrayList<>();
        Collections.addAll(highlights,
                         10, 11, 15, 16, 17,
                         18, 19, 20, 21, 22, 1000, 1223);
        view.setHighlights(highlights);
        return view;
    }

    public static void main(String[] args) {
        IntelliJTheme.install(BrushUiTest.class.getResourceAsStream("/arc-theme-orange.theme.json"));
        SwingUtilities.invokeLater(ViewTest::new);
    }

}
