package sample;

public class ScalingManager {

    public static final int SCREEN_TILE_WIDTH = 32;
    public static final int SCREEN_TILE_HEIGHT = 18;

    private SceneManager manager;

    private double xOffset;
    private double yOffset;
    private double tileDim;

    public ScalingManager(SceneManager manager) {
        this.manager = manager;
        this.updateScaling();
    }

    public void updateScaling() {
        double width = manager.getSceneWidth();
        double height = manager.getSceneHeight();
        tileDim = Math.min(width / SCREEN_TILE_WIDTH,  height / SCREEN_TILE_HEIGHT);
        xOffset = (width - SCREEN_TILE_WIDTH * tileDim) / 2;
        yOffset = (height - SCREEN_TILE_HEIGHT * tileDim) / 2;
    }

    public float scaleTileLength(float f) {
        return (float) (tileDim * f);
    }
    public float scaleRealLength(float f) {
        return (float) (f / tileDim);
    }
    public float scaleTileXCoord(float x) {
        return (float) (xOffset + tileDim * x);
    }
    public float scaleTileYCoord(float y) {
        return (float) (yOffset + tileDim * y);
    }
    public float scaleRealXCoord(float x) {
        return (float) ((x - xOffset) / tileDim);
    }
    public float scaleRealYCoord(float y) {
        return (float) ((y - yOffset) / tileDim);
    }
}
