package utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageLayoutManger implements LayoutManager2 {
    private final Map<Integer, LayoutRow> rows = new HashMap<>();

    public PageLayoutManger() {
        rows.put(0, new LayoutRow());
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof Integer) {
            rows.get(constraints).add(comp);
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return null;
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {
        rows.values().forEach(cl -> cl.remove(comp));
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {

        }
    }

    private class LayoutRow {
        private final List<Component>  components;
        Alignment alignment;

        int ascent;
        int descent;

        private LayoutRow() {
            components = new ArrayList<>();
            alignment = Alignment.LEFT;
        }

        void add(Component comp) {
            components.add(comp);
        }

        void remove(Component comp) {
            components.remove(comp);
        }
    }


    public enum Alignment {
        LEFT, CENTER, RIGHT
    }
}
