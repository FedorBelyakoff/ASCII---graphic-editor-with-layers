package interfaces;

public interface EditorMenu {
    void register(EditorMenuListener listener);

    void unregister(EditorMenuListener listener);

    <T extends Enum<T>> Enum<T> getSelectedItem();

}
