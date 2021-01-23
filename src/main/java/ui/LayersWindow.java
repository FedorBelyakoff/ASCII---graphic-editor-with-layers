package ui;

import interfaces.LayerController;
import interfaces.LayersListener;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Map;

import static javax.swing.SwingConstants.LEFT;
import static ui.UiResources.*;

public class LayersWindow extends JInternalFrame implements LayersListener {

    private static final int BUTTONS_SIZE = 20;
    private static final Dimension WINDOW_SIZE = new Dimension(210, 270);

    private LayerController controller;

    private GridBagConstraints gbc;
    private JPanel generalPanel;
    private JList<String> layersList;
    private Map<Integer, String> layers;
    private JPanel buttonPane;

    public LayersWindow() {
        super(bundle.getString("LayersWindow.title"), true);
        createGui();
    }

    public void setController(LayerController controller) {
        this.controller = controller;
    }

    @Override
    public void processLayers(Map<Integer, String> layers) {
        this.layers = layers;
        final String[] arr = (String[]) layers.values().toArray();
        layersList.setListData(arr);
    }

    private void createGui() {
        setWindowSettings();
        generalPanel();
        label();
        createList();
        buttonPanel();
    }

    private void setWindowSettings() {
        setSize(WINDOW_SIZE);
        setMinimumSize(WINDOW_SIZE);
        setLayout(new GridBagLayout());
        setVisible(true);
        setFont(new Font(Font.DIALOG, Font.BOLD, 20));
    }

    private void generalPanel() {
        //general panel
        generalPanel = new JPanel(new GridBagLayout());
        generalPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        add(generalPanel, gbc);
    }

    private void label() {
        final JLabel label = new JLabel("Слои", LEFT) {
            @Override
            public void setFont(Font font) {
                super.setFont(new Font(font.getFontName(),
                                 font.getStyle(),
                                 font.getSize() + 4));
            }
        };
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 15, 0, 15);
        add(label, gbc);
    }

    private void createList() {
        //list
        layersList = new JList<>(new String[]{"item1", "item2", "item3"});
        layersList.setFont(new Font(Font.SERIF, Font.ITALIC, 15));
        final MatteBorder listBorder = BorderFactory.createMatteBorder(0, 0,
                         1, 0, Color.LIGHT_GRAY);
        layersList.setBorder(listBorder);
        layersList.setDragEnabled(true);
        layersList.setDropMode(DropMode.ON);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        generalPanel.add(layersList, gbc);
    }

    private void buttonPanel() {
        createButtonPanel();

        createArrowDown();

        createArrowUp();

        createPlusButton();

        createMinusButton();

        createSpacer();
    }

    private void createPlusButton() {
        SquareButton plus = new SquareButton(BUTTONS_SIZE,
                         new IconReader("/newButtons/plus_.png"));
        plus.setBackground(getBackground());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 3, 2, 0);
        buttonPane.add(plus, gbc);
    }

    private void createMinusButton() {
        SquareButton minus = new SquareButton(BUTTONS_SIZE,
                         new IconReader("/newButtons/minus.png"));
        minus.setBackground(getBackground());
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 3, 2, 0);
        buttonPane.add(minus, gbc);
    }

    private void createSpacer() {
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(2, 7, 2, 0);
        buttonPane.add(new JPanel(), gbc);
    }

    private void createArrowUp() {
        final SquareButton arrowUp = new SquareButton(BUTTONS_SIZE,
                         new IconReader("/newButtons/arrow_Up.png"));
        arrowUp.setBackground(getBackground());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPane.add(arrowUp, gbc);
    }

    private void createArrowDown() {
        final SquareButton arrowDown = new SquareButton(BUTTONS_SIZE,
                         new IconReader("/newButtons/arrow_Down.png"));
        arrowDown.setBackground(getBackground());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 3, 2, 0);
        buttonPane.add(arrowDown, gbc);
    }

    private void createButtonPanel() {
        buttonPane = new JPanel(new GridBagLayout());
        buttonPane.setBackground(getBackground());

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        generalPanel.add(buttonPane, gbc);
    }
}
