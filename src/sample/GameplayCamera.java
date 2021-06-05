package sample;

import javafx.scene.canvas.Canvas;

public class GameplayCamera {

    private Canvas canvas;
    private Game game;
    private SceneManager manager;
    private DrawingAdapter adapter;

    public GameplayCamera(SceneManager manager, Game game) {
        this.manager = manager;
        this.game = game;
        this.canvas = new Canvas(manager.getScreenWidth(), manager.getScreenHeight());
        this.adapter = new DrawingAdapter(manager.getScalingManager(), canvas);
    }

    public void update() {
        drawRoom();
    }
    public void drawRoom() {
        if (game.getCurrentRoom() == null) {
            return;
        }
        game.getCurrentRoom().drawRoom(adapter);
    }

    public Canvas getCamera() {
        return canvas;
    }

}
