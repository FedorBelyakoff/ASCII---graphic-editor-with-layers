package classes;

import interfaces.EditorMenu;
import interfaces.EditorMenuListener;

import java.util.Locale;
import java.util.ResourceBundle;

public class Test3<T extends Enum<T>> implements EditorMenu {

    private static ResourceBundle bundle;

    public static void main(String[] args) {
//        Locale.setDefault(Locale.ENGLISH);
        bundle = ResourceBundle.getBundle("Bundle", Locale.getDefault());
//        final ResourceBundle bundle = ResourceBundle.getBundle("Bundle", Locale.ENGLISH);
        System.out.println();
        display();
        Locale.setDefault(Locale.ENGLISH);
        display();
    }

    private static void display() {
        bundle.keySet().forEach(s ->
                         System.out.println(s + "=" + bundle.getString(s))
        );
    }

    @Override
    public void register(EditorMenuListener listener) {

    }

    @Override
    public void unregister(EditorMenuListener listener) {

    }

    @Override
    public  Enum<T> getSelectedItem() {
        return null;
    }
}
