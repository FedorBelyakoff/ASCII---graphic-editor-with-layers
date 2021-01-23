package ui;

import javax.swing.*;

public class SizePanel extends SettingsPanel {
    public SizePanel(int fontSize) {
        super(fontSize);
        createGui();
    }

    @Override
    void createGui() {
        createTitlePanel(0, bundle.getString("settingsTitle"));
        final JPanel panel = createSizePanel(1);
        createDescriptionLabel(bundle.getString("ToolSettingsWindow.size"),
                         panel, 0, 10);
        createBottomSpacer(2);
        createApplyPanel(3, bundle.getString("applyButton"));
    }
}
