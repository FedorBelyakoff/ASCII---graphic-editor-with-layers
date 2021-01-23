package ui;

import classes.TextPosition;
import interfaces.AreaDimensionController;

import javax.swing.*;
import java.awt.*;

import static classes.LogicConstraints.*;
import static ui.UiResources.*;

public class StatusBar extends JPanel {

    public static final int WIDTH = 5000;
    private JPanel positionPanel;
    private GridBagConstraints gbc;
    private JLabel positionLabel;
    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private AreaDimensionController controller;

    public StatusBar() {
        super(new GridBagLayout());
        createGui();
        setMousePosition(new TextPosition(0, 0));
    }

    public int getSpinnerWidth() {
        return (int) widthSpinner.getValue();
    }

    public int getSpinnerHeight() {
        return (int) heightSpinner.getValue();
    }

    public void setMousePosition(TextPosition position) {
        String positionText = String.format("Column: %1$3d Row: %2$3d",
                         position.getColumn(), position.getRow());
        positionLabel.setText(positionText);
    }

    public void setController(AreaDimensionController controller) {
        this.controller = controller;
    }


    private void createGui() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(
                         1, 0, 0, 0, Color.GRAY));

        createPositionLabel();
        createSeparator();
        createWidthLabel();
        createWidthSpinner();
        createHeightLabel();
        createHeightSpinner();
        createRightSpacer();
        setDimensions();
    }

    private void createPositionLabel() {

//        final ImageIcon icon = new IconReader("/newButtons/brush.png")
//                         .readDefault(25);
        positionLabel = new JLabel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(positionLabel, gbc);
    }

    private void createSeparator() {
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.weighty = 0.1;

        JComponent separator = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.gray);
                int middle = getWidth() / 2;
                g.drawLine(middle, 0, middle, getHeight());
            }
        };
        separator.setPreferredSize(new Dimension(5, 10));
        add(separator, gbc);
    }

    private void createWidthLabel() {
        JLabel widthLabel = new JLabel(bundle.getString("width"));

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 5, 0, 4);
        add(widthLabel, gbc);
    }

    private void createWidthSpinner() {
        final SpinnerNumberModel model = new SpinnerNumberModel(MIN_LAYER_WIDTH,
                         MIN_LAYER_WIDTH, MAX_LAYER_HEIGHT, 1);
        widthSpinner = new JSpinner(model);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        add(widthSpinner, gbc);
    }

    private void createHeightLabel() {
        JLabel heightLabel = new JLabel(bundle.getString("height"));

        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.insets = new Insets(0, 5, 0, 4);
        add(heightLabel, gbc);
    }

    private void createHeightSpinner() {
        final SpinnerNumberModel model = new SpinnerNumberModel(MIN_LAYER_HEIGHT,
                         MIN_LAYER_HEIGHT, MAX_LAYER_HEIGHT, 1);
        heightSpinner = new JSpinner(model);
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        add(heightSpinner, gbc);
    }

    private void createRightSpacer() {
        JPanel spacer = new JPanel();
//        spacer.setBackground();
        spacer.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.weightx = 0.1;
        add(spacer, gbc);
    }

    private void setDimensions() {
        int maxHeight = 0;
        for (Component c : getComponents()) {
            maxHeight = Math.max(c.getPreferredSize().height, maxHeight);
        }
        setSize(WIDTH, maxHeight);
    }


    private class VerticalLine extends JComponent {

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.gray);
            int middle = getWidth() / 2;
            g.drawLine(middle, 0, middle, getHeight());
        }
    }
}
