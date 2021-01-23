package ui;

import classes.TextPixel;
import classes.TextPosition;
import com.formdev.flatlaf.ui.FlatTextAreaUI;
import interfaces.Workspace;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WorkspaceHighlighter {
    private final JTextArea area;
    private final Workspace workspace;
    private MyTextView textView;


    public WorkspaceHighlighter(JTextArea area, Workspace workspace) {
        this.area = area;
        this.workspace = workspace;

        changeAreaModel();
    }

    private void changeAreaModel() {
        FlatTextAreaUI areaUI = getAreaUI();
        area.setLineWrap(true);
        area.setUI(areaUI);
    }

    private FlatTextAreaUI getAreaUI() {
        return new FlatTextAreaUI() {
            @Override
            public View create(Element elem) {
                View view = super.create(elem);

                if (view instanceof WrappedPlainView) {
                    return textView = new MyTextView(elem);
                }
                return view;
            }
        };
    }


    public void highlightPixels(Set<TextPixel> pixels) {
        ArrayList<Integer> positionsInStr = new ArrayList<>();
        for (TextPixel p : pixels) {
            if (workspace.checkReplaceInArea(p.getColumn(), p.getRow())) {
                positionsInStr.add(workspace.getPosition(p.getColumn(), p.getRow()));
            }
        }
        addHighlight(positionsInStr);
    }

    public void highlight(Set<TextPosition> positions) {
        ArrayList<Integer> positionsInStr = new ArrayList<>();
        for (TextPosition p : positions) {
            if (workspace.checkReplaceInArea(p.getColumn(), p.getRow())) {
                positionsInStr.add(workspace.getPosition(p.getColumn(), p.getRow()));
            }
        }
        addHighlight(positionsInStr);
    }

    private void addHighlight(ArrayList<Integer> positionsInStr) {
        if (textView != null) {
            textView.setHighlights(positionsInStr);
        }
        highlightBackground(positionsInStr);
    }

    public void removeHighlight() {
        area.getHighlighter().removeAllHighlights();
        textView.setHighlights(Collections.emptyList());
    }


    private void highlightBackground(ArrayList<Integer> positions) {
        Highlighter h = area.getHighlighter();

        for (Integer p : positions) {
            try {
                h.addHighlight(p, p + 1, DefaultHighlighter.DefaultPainter);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MyTextView extends WrappedPlainView {

        private List<Integer> highlights;

        public MyTextView(Element elem) {
            super(elem);
            highlights = Collections.emptyList();
        }

        public void setHighlights(List<Integer> highlights) {
            this.highlights = new ArrayList<>(highlights);
            this.highlights.sort(Integer::compare);
        }

        @Override
        protected void drawLine(int p0, int p1, Graphics g, int x, int y) {
            Element lineMap = getElement();
            Element line = lineMap.getElement(lineMap.getElementIndex(p0));
            Element elem;

            try {
                if (line.isLeaf()) {
                    drawText(line, p0, p1, g, x, y);
                } else {
                    // this line contains the composed text.
                    int idx = line.getElementIndex(p0);
                    int lastIdx = line.getElementIndex(p1);
                    for (; idx <= lastIdx; idx++) {
                        elem = line.getElement(idx);
                        int start = Math.max(elem.getStartOffset(), p0);
                        int end = Math.min(elem.getEndOffset(), p1);
                        x = drawText(elem, start, end, g, x, y);
                    }
                }
            } catch (BadLocationException e) {
            }
        }


        private int drawText(Element elem, int p0, int p1,
                             Graphics g, int x, int y) throws BadLocationException {
            p1 = Math.min(getDocument().getLength(), p1);

            int highlightStart = p0;
            int highlightEnd = p0;
            boolean beforeHighlight = true;

            for (Integer highlight : highlights) {// TODO: 03.10.2020 Проверить позиции рисования
                if (highlight < p0) {
                    continue;
                } else if (highlight >= p1 ) {
                    break;
                }

                if (beforeHighlight) {
                    x = drawUnselectedText(g, x, y, p0, highlight);
                    highlightStart = highlight;
                    highlightEnd = highlight + 1;
                    beforeHighlight = false;
                } else if (highlightEnd == highlight) {
                    highlightEnd = highlight + 1;
                } else {
                    x = drawSelectedText(g, x, y, highlightStart, highlightEnd);
                    x = drawUnselectedText(g, x, y, highlightEnd, highlight);
                    highlightStart = highlight;
                    highlightEnd = highlight + 1;
                }


            }
            x = drawSelectedText(g, x, y, highlightStart, highlightEnd);
            x = drawUnselectedText(g, x, y, highlightEnd, p1);


            return x;
        }
    }
}
