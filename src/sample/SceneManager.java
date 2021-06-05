package sample;

import gameobjects.Player;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import weapon.Weapon;

public class SceneManager {

    private Stage primaryStage;
    private Scene root;
    private Game game;
    private ScalingManager scalingManager;
    private InputHandler inputHandler;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        HBox pane = new HBox();
        pane.setMinSize(this.getScreenWidth(), this.getScreenHeight());
        root = new Scene(pane, 1000, 700);
        primaryStage.setScene(root);

        this.scalingManager = new ScalingManager(this);
        this.inputHandler = new InputHandler(root, scalingManager);
    }

    public double getScreenWidth() {
        return primaryStage.getWidth();
    }

    public double getScreenHeight() {
        return primaryStage.getHeight();
    }

    public double getSceneWidth() {
        return root.getWidth();
    }

    public double getSceneHeight() {
        return root.getHeight();
    }

    public void init() {
        this.showWelcome();
    }

    public void showWelcome() {
        root.setRoot(SceneFactory.createWelcome(this));
    }

    public void showWin(Player player) {
        root.setRoot(SceneFactory.createWin(this, player));
    }

    public void showLoss(Player player) {
        root.setRoot(SceneFactory.createLoss(this, player));
    }

    public void showConfig() {
        root.setRoot(SceneFactory.createConfig(this));
    }

    public void showGame(Game.Difficulty difficulty, Weapon startingWeapon) {
        game = new Game(difficulty, startingWeapon, this);
        root.setRoot(game.initScene(this));
        game.start();
    }

    public ScalingManager getScalingManager() {
        return scalingManager;
    }
    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public Game getGame() {
        return game;
    }

}
