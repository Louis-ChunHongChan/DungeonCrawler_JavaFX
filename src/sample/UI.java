package sample;

import gameobjects.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UI {

    private Player player;
    private ScalingManager scalingManager;

    private AnchorPane root;
    private VBox hudRoot;
    private Label goldCounter;
    private ProgressBar health;

    public UI(Player player, ScalingManager scalingManager) {
        this.player = player;
        this.scalingManager = scalingManager;

        root = new AnchorPane();
        hudRoot = new VBox();

        AnchorPane.setTopAnchor(hudRoot, 5.0);
        AnchorPane.setLeftAnchor(hudRoot, 5.0);
        root.getChildren().add(hudRoot);
        this.initHudRoot();
        this.initHealthBar();
        this.initGoldCounter();
    }

    public AnchorPane getRoot() {
        return root;
    }

    public void update() {
        health.setProgress(player.getHealth() / (double) player.getMaxHealth());
        goldCounter.setText(player.getMoney() + " G");
    }

    private void initHudRoot() {
        hudRoot.setBackground(new Background(
                new BackgroundFill(Color.rgb(222, 196, 149), new CornerRadii(10), Insets.EMPTY)
        ));
    }
    private void initHealthBar() {
        health = new ProgressBar();
        health.setProgress(1.0);
        health.setPadding(new Insets(10));
        hudRoot.getChildren().add(health);
    }
    private void initGoldCounter() {
        goldCounter = new Label();
        goldCounter.setFont(Font.font("Arial", 20.0));
        goldCounter.setPadding(new Insets(10));
        goldCounter.setText(player.getMoney() + " G");
        hudRoot.getChildren().add(goldCounter);
    }

}
