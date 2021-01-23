package ui;

import interfaces.LayerController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LayerControllerImpl implements LayerController {
    private final Map<Integer, String> layers;

    public LayerControllerImpl() {
        layers = new HashMap<>();
    }

    @Override
    public void raiseLayer(int layer) {
        if (layer == 0 || !layers.containsKey(layer)) {
            throw new IndexOutOfBoundsException();
        }

        swap(layer, layer + 1);
    }

    @Override
    public void lowerLayer(int layer) {
        final Integer max = Collections.max(layers.keySet(),
                         (o1, o2) -> o1 > o2 ? 1 : 0);


        if (!layers.containsKey(layer) || layer > max) {
            throw new IndexOutOfBoundsException();
        }
        swap(layer, layer - 1);
    }

    @Override
    public void addLayer(String name, int width, int height) {
        final Integer max = Collections.max(layers.keySet(),
                         (o1, o2) -> o1 > o2 ? 1 : 0);
        layers.put(max + 1, name);
    }

    @Override
    public void removeLayer(int layer) {
        if (!layers.containsKey(layer)) {
            throw new IndexOutOfBoundsException();
        }
        for (Integer key : layers.keySet()) {
            if (key >= layer) {
                swap(layer, layer + 1);
            }
        }
        layers.remove(layer);
    }

    private void swap(int a, int b) {
        final String oldName = layers.replace(a,
                         layers.get(b));
        layers.put(b, oldName);
    }
}
