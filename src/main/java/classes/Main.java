package classes;

import enumerations.ToolTypes;
import interfaces.EditorMenu;
import interfaces.MainController;
import interfaces.Workspace;
import interfaces.ToolFrame;
import ui.SizeCharPanel;
import ui.ToolsWindow;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame implements MainController {
    private EditorMenu menu;
    private ToolFrame toolFrame;
    private Workspace workspace;
    private final JDesktopPane desktopPane;
    private ToolsWindow toolsWindow;


    public Main() throws HeadlessException {
        super("ASCII - editor");

        createGui();

        final JTextArea area = new JTextArea(50, 50);
//        workspace = new TextWorkspace(area);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        setSize(1000, 1000);
        setVisible(true);
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    @Override
    public void setTool(ToolTypes tool) {
        switch (tool) {
            case BRUSH:
                createBrush();  break;
        }
    }

    private void createBrush() {
        final SizeCharPanel brushPanel =
                         new SizeCharPanel(16);
        toolsWindow.setSettingsPanel(brushPanel);




    }

    private void createGui() {
        //Настройка окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setVisible(true);

        //Создание панели инструментов
        toolsWindow = new ToolsWindow("Инструменты", this);
//        toolsFrame.setBounds(100, 100, 200, 200);
        desktopPane.add(toolsWindow, JDesktopPane.PALETTE_LAYER);
    }

    public static void main(String[] args) {

    }
}
