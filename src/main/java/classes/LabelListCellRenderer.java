package classes;

import javax.swing.*;
import java.awt.*;

class LabelListCellRenderer extends JLabel implements ListCellRenderer<JLabel> {
    public LabelListCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList<? extends JLabel> list,
                                                  JLabel value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

//        setText("Text");

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                         && !dropLocation.isInsert()
                         && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.BLACK;

            // check if this cell is selected
        } else if (isSelected) {
            background = list.getSelectionBackground();
            foreground = list.getSelectionForeground();

            // unselected, and not the DnD drop location
        } else {
            background = list.getBackground();
            foreground = list.getForeground();
        };

        value.setBackground(background);
        value.setForeground(foreground);

        value.setOpaque(true);

        return value;
    }
}