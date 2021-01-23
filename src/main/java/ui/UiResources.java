package ui;

import java.util.Locale;
import java.util.ResourceBundle;

public class UiResources {
    public static ResourceBundle bundle = ResourceBundle.getBundle(
                     "Bundle", Locale.getDefault(), new Utf8Control());

    private UiResources() {
    }

}
