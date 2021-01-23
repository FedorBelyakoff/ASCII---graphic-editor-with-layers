package interfaces;

import classes.TextPixel;

import java.util.Set;

public interface Model {
    Set<TextPixel> getTextPixels(int layer);

    String getText(int layer);

    void setText(String text, int layer);
}
