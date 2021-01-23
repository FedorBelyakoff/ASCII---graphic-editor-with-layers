package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.awt.GridBagConstraints.*;

public abstract class SettingsPanel extends JPanel {
    protected final ResourceBundle bundle = UiResources.bundle;
    protected int fontSize;
    private GridBagConstraints gbc;
    private JSlider thicknessSlider;
    private JSpinner thicknessSpinner;
    private JButton applyButton;
    private Insets insets;
    private Font font;
    private int maxLabelSize;

    SettingsPanel() {
        this(14);
    }

    SettingsPanel(int fontSize) {
        this(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                         new Insets(5, 10, 5, 15));
    }

    SettingsPanel(Font font, Insets insets) {
        super(new GridBagLayout());
        this.font = Objects.requireNonNull(font);
        this.insets = Objects.requireNonNull(insets);
        setEnabled(true);
        setVisible(true);
    }

    abstract void createGui();

    public int getPreferredHeight() {
        int height  = 0;
        for (Component c : getComponents()) {
            height += c.getMinimumSize().height;
        }
        return height;
    }

    // TODO: 09.08.2020 Cделать отступы через Insets панелей

    public JSlider getThicknessSlider() {
        return thicknessSlider;
    }

    public JSpinner getThicknessSpinner() {
        return thicknessSpinner;
    }

    public JButton getApplyButton() {
        return applyButton;
    }



    void createOkCancelPanel(int gridy, JButton ok, JButton cancel) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        gbc.weightx = 0.1;
        gbc.insets = (Insets) insets.clone();
        gbc.fill = BOTH;
        add(panel, gbc);

//        applyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = HORIZONTAL;
        gbc.ipadx = 20;
        gbc.insets = new Insets(0, insets.right, 0, 0);
        panel.add(ok, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = HORIZONTAL;
        gbc.ipadx = 20;
        gbc.insets = new Insets(0, insets.right, 0, insets.right);
        panel.add(cancel, gbc);

        final JPanel spacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = BOTH;
        panel.add(spacer, gbc);
    }

    public JPanel createPanel(int gridy, Component comp) {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.fill = BOTH;
        gbc.insets = (Insets) insets.clone();
        gbc.insets.left += 15;
        add(panel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.ipady = 0;
        gbc.fill = HORIZONTAL;
        panel.add(comp, gbc);
        return panel;
    }

    public JPanel createTextPanel(int gridy) {
        final JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.fill = BOTH;
        gbc.insets = (Insets) insets.clone();
        gbc.insets.left += 15;
        add(textPanel, gbc);



        final JTextField textField = new JTextField(1);
        textField.setActionCommand("SymbolField");
//        textField.addActionListener(this);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                final JTextField textField = (JTextField) e.getSource();
                textField.setText("");
                textField.selectAll();

            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.ipady = 0;
        gbc.fill = HORIZONTAL;
        textPanel.add(textField, gbc);
        return textPanel;
    }

    public JPanel createSizePanel(int gridy) {
        final JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 0.1;
        gbc.fill = BOTH;
        gbc.insets = (Insets) insets.clone();
        gbc.insets.left += 15;
        add(sizePanel, gbc);

        thicknessSpinner = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.anchor = EAST;
        gbc.fill = HORIZONTAL;
        gbc.ipadx = 25;
//        gbc.insets = new Insets(0, 0, 0, 0);
        sizePanel.add(thicknessSpinner, gbc);
        return sizePanel;

    }


    public void createDescriptionLabel(String text, JPanel panel,
                                       int groupId, int rightInset) {
        class LabelMarker extends JLabel {
            final int groupId;

            public LabelMarker(String text, int groupId) {
                super(text);
                this.groupId = groupId;
            }
        }


        final LabelMarker label = new LabelMarker(text, groupId);
        label.setFont(font);
        final int minWidth = label.getMinimumSize().width;
        maxLabelSize = Math.max(minWidth, maxLabelSize);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = HORIZONTAL;


        for (Component c : getComponents()) {
            if (c instanceof JPanel) {

                final JPanel pan = (JPanel) c;
                for (Component p : pan.getComponents()) {
                    if (p instanceof LabelMarker) {
                        final LabelMarker l = (LabelMarker) p;
                        if (l.groupId == groupId) {
                            final int minS = l.getMinimumSize().width;
                            gbc.ipadx = maxLabelSize - minS + rightInset;
                            pan.remove(l);
                            pan.add(l, gbc);
                        }
                    }
                }
            }

        }
        final int minS = label.getMinimumSize().width;
        gbc.ipadx = maxLabelSize - minS + rightInset;
        panel.add(label, gbc);
    }

    void createApplyPanel(int gridy, String text) {
        final JPanel applyPanel = new JPanel();
        applyPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        gbc.weightx = 0.1;
        gbc.fill = BOTH;
        add(applyPanel, gbc);

//        applyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        applyButton = new JButton();
        applyButton.setText(text);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = HORIZONTAL;
        gbc.ipadx = 20;
        gbc.insets = new Insets(5, 15, 15, 15);
        applyPanel.add(applyButton, gbc);

        final JPanel spacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = BOTH;
        applyPanel.add(spacer, gbc);
    }

    void createHSpacer(int gridx, int gridy, int ipady) {
        final JPanel spacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.fill = VERTICAL;
        gbc.ipady = ipady;
        add(spacer, gbc);
    }

    void createBottomSpacer(int gridy) {
        final JPanel spacer = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weighty = 0.1;
        gbc.fill = VERTICAL;
        add(spacer, gbc);
    }

    JPanel createTitlePanel(int gridy, String text) {
        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 0.1;
        gbc.fill = BOTH;
        gbc.ipady = 5;
        gbc.insets = (Insets) insets.clone();
        add(titlePanel, gbc);

        final JLabel label = new JLabel(text);
//        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = WEST;
        titlePanel.add(label, gbc);

        final HorizontalLine line = new HorizontalLine();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = BOTH;

        titlePanel.add(line, gbc);
        return titlePanel;
    }


}