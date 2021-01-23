package classes;

import interfaces.Workspace;
import ui.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static enumerations.ToolSettingsPanels.*;
import static enumerations.WindowComponents.*;
import static javax.swing.JLayeredPane.DEFAULT_LAYER;
import static javax.swing.JLayeredPane.PALETTE_LAYER;
import static ui.UiResources.bundle;

public class UiFactory {
    public static final int STATUS_BAR_BOTTOM_INDENT = 65;
    public static final int RIGHT_INDENT = 17;
    public static final Integer TOOLS_WINDOW_LAYER = PALETTE_LAYER + 100;

    private final UiLocator locator;
    private JFrame frame;
    private JPanel workspacePanel;
    private JTextArea area;
    private StatusBar statusBar;
    private JPanel topPanel;
    private JScrollPane scrollPane;

    public UiFactory(UiLocator locator) {

        this.locator = locator;
    }

    public void createAllElements() {
        createWindow();

        createStatusBar();

        createTextArea();

        createWorkspace();

        createWorkspacePanel();

        createTopPanel();

        createScrollPane();

        createToolSettingsPanels();

        createToolsWindow();

        createLayersWindow();


    }

    private void createWorkspace() {

        Workspace workspace = new TextWorkspace(area);
        locator.add(workspace, WORKSPACE);

        workspace.setFontSize(15);
        workspace.setDimensions(50, 20);
    }

    private void createWindow() {
        createFrame();
        setWindowSettings();
    }

    private void createFrame() {
        frame = new JFrame();
        locator.add(frame, FRAME);
    }

    private void setWindowSettings() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(bundle.getString("WindowTitle"));
        frame.setContentPane(new JDesktopPane());
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }

    private void createStatusBar() {
        statusBar = new StatusBar();
//        statusBar.setBackground(Color.ORANGE);
        locator.add(statusBar, STATUS_BAR);

//        statusBar.setSize(getStatusBarSize());
        updateStatusBarLocation();

        frame.getContentPane().add(statusBar, DEFAULT_LAYER);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateStatusBarLocation();
            }
        });
    }


    private void createTextArea() {
        area = new JTextArea(20, 50) {
            @Override
            public void setBounds(int x, int y, int width, int height) {
                super.setBounds(x, y, width, height);
            }
        };
        area.setEditable(false);
        area.setBackground(Color.white);
        locator.add(area, AREA);
    }

    private void createWorkspacePanel() {
        workspacePanel = new WorkspacePanel(area);
        locator.add(workspacePanel, WORKSPACE_PANEl);
    }

    private void createTopPanel() {
        topPanel = new JPanel();
        topPanel.setBounds(frame.getBounds());
        topPanel.setLayout(null);
        frame.getContentPane().add(topPanel, DEFAULT_LAYER);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                topPanel.setBounds(getWorkspacePanelBounds());
            }
        });
    }

    private void createScrollPane() {
        scrollPane = new JScrollPane(workspacePanel);
        scrollPane.setBounds(frame.getBounds());

        topPanel.add(scrollPane);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scrollPane.setBounds(getWorkspacePanelBounds());
            }
        });
    }

    private void createToolsWindow() {
        ToolsWindow toolsWindow = new ToolsWindow();
        locator.add(toolsWindow, TOOLS_WINDOW);

        toolsWindow.setSettingsPanel((SettingsPanel) locator.get(BRUSH_PANEl));
        toolsWindow.setLocation(UiConstants.INITIAL_TOOLS_WINDOW_LOCATION);
        frame.getContentPane().add(toolsWindow, TOOLS_WINDOW_LAYER);
    }

    private void createToolSettingsPanels() {
        SizeCharPanel brushPanel = new SizeCharPanel(15);
        locator.add(brushPanel, BRUSH_PANEl);

        CharacterPanel linePanel = new CharacterPanel(15);
        locator.add(linePanel, LINE_PANEL);

        CharacterPanel fillPanel = new CharacterPanel(15);
        locator.add(fillPanel, FILL_PANEL);

        DimensionPanel selectionPanel = new DimensionPanel();
        locator.add(selectionPanel, SELCTION_PANEL);
    }

    private void createLayersWindow() {
        LayersWindow layersWindow = new LayersWindow();
        locator.add(layersWindow, LAYERS_WINDOW);

        layersWindow.setLocation(UiConstants.INITIAL_LAYERS_WINDOW_LOCATION);
        frame.getContentPane().add(layersWindow, PALETTE_LAYER);


    }

    private Rectangle getWorkspacePanelBounds() {
        int width = frame.getWidth() - RIGHT_INDENT;
        int height = frame.getHeight() - STATUS_BAR_BOTTOM_INDENT
                         - statusBar.getSize().height;
        return new Rectangle(0, 0, width, height);
    }

    private void updateStatusBarLocation() {
        statusBar.setSize(getStatusBarSize());
        int y = frame.getHeight() - statusBar.getSize().height - STATUS_BAR_BOTTOM_INDENT;
        statusBar.setLocation(0, y);
    }

    private Dimension getStatusBarSize() {
        int height = (int) statusBar.getPreferredSize().getHeight();
        return new Dimension(frame.getWidth(), height);
    }


}
