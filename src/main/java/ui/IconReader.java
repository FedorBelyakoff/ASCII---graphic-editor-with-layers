package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class IconReader {

    private final String defaultUrl;
    private final String rolloverUrl;
    private final String selectedUrl;

    public IconReader(String imageUrl) {
        this(imageUrl, imageUrl, imageUrl);
    }

    public IconReader(String defaultUrl,
                      String rolloverUrl,
                      String selectedUrl) {
        assert defaultUrl != null : "Default url should be not null!";
        assert rolloverUrl != null : "Rollover url should be not null!";
        assert selectedUrl != null : "Selected url should be not null!";

        this.defaultUrl = defaultUrl;
        this.rolloverUrl = rolloverUrl;
        this.selectedUrl = selectedUrl;
    }

    public ImageIcon readDefault(int size) {
        return readIcon(getClass().getResource(defaultUrl), size, size);
    }

    public ImageIcon readRollover(int size) {
        return readIcon(getClass().getResource(rolloverUrl), size, size);
    }

     public ImageIcon readSelected(int size) {
        return readIcon(getClass().getResource(selectedUrl), size, size);
    }



    private ImageIcon readIcon(URL imageUrl, int width, int height) {
        BufferedImage readImage = null;
        try {
            readImage = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert readImage != null;

        final Image scaledImage = readImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }
}
