package ui;

import java.awt.*;

class MyLayoutManager implements LayoutManager {
    private final Insets insets;
    private int hgap;

    private int vgap;

    public MyLayoutManager(int hgap, int vgap, Insets insets) {
        this.hgap = hgap;
        this.vgap = vgap;
        this.insets = insets;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        final Dimension dim = new Dimension();
        final int nComponents = parent.getComponentCount();
//        int maxAccent = 0;
//        int maxDecent = 0;
        boolean firstVisibleComponent = true;

        for (int i = 0; i < nComponents; i++) {
            final Component c = parent.getComponent(i);

            if (c.isVisible()) {
                final Dimension compDim = c.getMaximumSize();
                dim.height = Math.max(dim.height, compDim.height);

                if (firstVisibleComponent) {
                    firstVisibleComponent = false;
                } else {
                    dim.width += hgap;
                }
                dim.width += compDim.width;
            }
        }
// TODO: 20.08.2020 проверить

//        final Insets insets = parent.getInsets();
//        dim.width += insets.left + insets.right + hgap * 2;
//        dim.height += insets.top + insets.bottom + vgap * 2;
//        final Insets insets = parent.getInsets();
        dim.width += insets.left + insets.right + hgap * 2;
        dim.height += insets.top + insets.bottom + vgap * 2;
//        dim.height += insets.top + insets.bottom;


//        dim.height +=
        return dim;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int x = 0;
            int y = 0;
            int cx;
            int cy;
            int columnWidth = 0;
            int rowHeight = 0;

//            Insets insets = parent.getInsets();

            int parentWidth = parent.getWidth();
            final int nComp = parent.getComponentCount();
            boolean firstVisibleComponent = true;

            for (int i = 0; i < nComp; i++) {
                final Component c = parent.getComponent(i);
                if (c.isVisible()) {
                    final Dimension maxSize = c.getMaximumSize();
                    rowHeight = Math.max(rowHeight, maxSize.height);
                    columnWidth = Math.max(columnWidth, maxSize.width);
                }
            }

            for (int i = 0; i < nComp; i++) {
                final Component c = parent.getComponent(i);
                c.setSize(c.getMaximumSize());

                if (c.isVisible()) {
                    if (firstVisibleComponent) {
                        firstVisibleComponent = false;
                        x = insets.left;
                        y = insets.top;
                        c.setLocation(x, y);
                        x += columnWidth + vgap;
                    } else {
                        if (x + c.getWidth() < parentWidth - insets.right) {
                        } else {
                            y += rowHeight + hgap;
                            x = insets.left;
                        }
                        cx = x + columnWidth / 2 - c.getWidth() / 2;
                        cy = y + rowHeight / 2 - c.getHeight() / 2;
                        c.setLocation(cx, cy);
                        x += columnWidth + vgap;
                    }
                }
            }
        }
    }
}
