package interfaces;

import classes.TextPosition;
import controllers.InputAreaMouseController;
import controllers.TextInputArea;
import controllers.TextUiController;
import ui.SelectionAlgorithm;

import javax.swing.*;
import java.awt.*;

public interface TextInputAreaFactory {

    void createComponents(TextPosition location);

    TextInputArea getInputArea();

    InputAreaMouseController getAreaMouseController();

    void removeComponents();

    boolean isInputAreaExist();
}
