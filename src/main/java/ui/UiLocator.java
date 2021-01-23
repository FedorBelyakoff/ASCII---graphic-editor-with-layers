package ui;

import java.util.*;


public class UiLocator {
    private static final UiLocator locator = new UiLocator();
    public final Map<? super Enum<?>, Object> resources;
    private final List<Object> list;

    public static UiLocator getInstance() {
        return locator;
    }

    private UiLocator() {
        resources = new HashMap<>();
        list = new ArrayList<>();
    }

    public <E, K extends Enum<K>> void add(E elem, K key) {
        resources.put(key, elem);
        list.add(elem);
    }

    public <K extends Enum<K>> Object get(K key ) {
        Object value = resources.get(key);

        if (value == null) {
            throw new NoSuchElementException(key.toString()
                             + " not found!");
        }
        return value;
    }


}
