package interfaces;

import controllers.LineMouseController;

import java.awt.*;

public interface UILineController {
    void setMouseController(LineMouseController mouseController);

    void drawLine();

    void removeLine();

    void setCursor(Cursor cursor);

    void setChar(char c);
}
