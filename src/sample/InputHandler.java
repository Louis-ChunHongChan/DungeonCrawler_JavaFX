package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class InputHandler {

    private Scene scene;
    private ScalingManager scalingManager;
    private final Set<KeyCode> keysDown;
    private Set<KeyCode> lastKeysDown;
    private boolean mousePrimaryDown;
    private boolean lastMousePrimaryDown;
    private double mouseX;
    private double mouseY;

    public InputHandler(Scene boundScene, ScalingManager scalingManager) {
        this.scene = boundScene;
        this.scalingManager = scalingManager;
        this.keysDown = new HashSet<>();
        this.lastKeysDown = new HashSet<>();

        scene.setOnKeyPressed(event -> keysDown.add(event.getCode()));
        scene.setOnKeyReleased(event -> keysDown.remove(event.getCode()));
        scene.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });
        scene.setOnMousePressed(event -> mousePrimaryDown = event.isPrimaryButtonDown());
        scene.setOnMouseReleased(event -> mousePrimaryDown = event.isPrimaryButtonDown());
    }

    public void flushInputs() {
        lastKeysDown.clear();
        synchronized (keysDown) {
            lastKeysDown.addAll(keysDown);
        }

        lastMousePrimaryDown = mousePrimaryDown;
    }

    public boolean isKeyDown(KeyCode key) {
        return keysDown.contains(key);
    }
    public boolean keyPressed(KeyCode key) {
        return keysDown.contains(key) && !lastKeysDown.contains(key);
    }
    public boolean keyReleased(KeyCode key) {
        return !keysDown.contains(key) && lastKeysDown.contains(key);
    }

    public boolean isMousePrimaryDown() {
        return mousePrimaryDown;
    }
    public boolean mousePrimaryPressed() {
        return mousePrimaryDown && !lastMousePrimaryDown;
    }
    public boolean mousePrimaryReleased() {
        return !mousePrimaryDown && lastMousePrimaryDown;
    }

    public float getMouseX() {
        return scalingManager.scaleRealXCoord((float) mouseX);
    }
    public float getMouseY() {
        return scalingManager.scaleRealYCoord((float) mouseY);
    }
}
