package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dungeon Crawler");
        sceneManager = new SceneManager(primaryStage);
        sceneManager.init();
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
