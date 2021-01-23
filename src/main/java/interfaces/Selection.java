package interfaces;

import classes.TextPixel;

import java.util.List;

public interface Selection {
    void setBounds(int x, int y, int width, int height);

    List<TextPixel> getSelected();
}
