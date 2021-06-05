package sample;

import geometry.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class DrawingAdapter {

    private ScalingManager manager;
    private GraphicsContext gc;

    public DrawingAdapter(ScalingManager manager, Canvas canvas) {
        this.manager = manager;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void updateScaling() {
        manager.updateScaling();
    }

    public void setFont(Font f) {
        gc.setFont(f);
    }
    public void fillText(String text, float x, float y) {
        gc.fillText(text, manager.scaleTileXCoord(x), manager.scaleTileYCoord(y));
    }
    public void setFill(Paint p) {
        gc.setFill(p);
    }
    public void fillRect(float x, float y, float width, float height) {
        gc.fillRect(
                manager.scaleTileXCoord(x),
                manager.scaleTileYCoord(y),
                manager.scaleTileLength(width),
                manager.scaleTileLength(height)
        );
    }
    public void fillRect(Rectangle r) {
        this.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    public void strokeRect(float x, float y, float width, float height) {
        gc.strokeRect(
                manager.scaleTileXCoord(x),
                manager.scaleTileYCoord(y),
                manager.scaleTileLength(width),
                manager.scaleTileLength(height)
        );
    }
    public void strokeRect(Rectangle r) {
        this.strokeRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    public void clearRect(float x, float y, float width, float height) {
        gc.clearRect(
                manager.scaleTileXCoord(x),
                manager.scaleTileYCoord(y),
                manager.scaleTileLength(width),
                manager.scaleTileLength(height)
        );
    }
    public void fillOval(float x, float y, float width, float height) {
        gc.fillOval(
                manager.scaleTileXCoord(x),
                manager.scaleTileYCoord(y),
                manager.scaleTileLength(width),
                manager.scaleTileLength(height)
        );
    }
    public void strokeOval(float x, float y, float width, float height) {
        gc.strokeOval(
                manager.scaleTileXCoord(x),
                manager.scaleTileYCoord(y),
                manager.scaleTileLength(width),
                manager.scaleTileLength(height)
        );
    }
    public void clearRect(Rectangle r) {
        this.clearRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    public void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

}
