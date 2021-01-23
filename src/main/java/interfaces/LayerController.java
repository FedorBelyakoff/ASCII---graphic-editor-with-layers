package interfaces;

public interface LayerController {
    void raiseLayer(int layer);

    void lowerLayer(int layer);

    void addLayer(String name, int width, int height);

    void removeLayer(int layer);
}
