package sample;

import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.RoomExit;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import weapon.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Game {

    public static final int STARTING_MONEY_EASY = 100;
    public static final int STARTING_MONEY_MEDIUM = 50;
    public static final int STARTING_MONEY_HARD = 0;
    public static final long FRAMERATE_NANO = 16666667;

    private Player player;
    private Maze maze;
    private IRoom currentRoom;
    private GameplayCamera camera;
    private UI ui;
    private Timeline framerateHandler;
    private GameState state;
    private SceneManager sceneManager;
    private Inventory inventory;

    private long lastUpdate;

    private Scene scene;
    private StackPane root;

    public Game(Difficulty difficulty, Weapon weapon, SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.player = new Player(sceneManager, this);
        if (difficulty == Difficulty.EASY) {
            player.setMoney(STARTING_MONEY_EASY);
        } else if (difficulty == Difficulty.MEDIUM) {
            player.setMoney(STARTING_MONEY_MEDIUM);
        } else if (difficulty == Difficulty.HARD) {
            player.setMoney(STARTING_MONEY_HARD);
        } else {
            throw new IllegalArgumentException("Difficulty cannot be null");
        }
        player.setWeapon(weapon);
        weapon.setPlayer(player);
        IRoom start = Room.createStartRoom(this);
        IRoom end = Room.createEndRoom(this);
        inventory = new Inventory(weapon, player);
        List<IRoom> roomList = new ArrayList<>(10);
        roomList.add(start);
        roomList.add(end);

        int challenge1 = (int) (Math.random() * 7) + 1;
        int challenge2;
        do {
            challenge2 = (int) (Math.random() * 7) + 1;
        } while (challenge1 == challenge2);
        roomList.add(Room.drawRoom(this, challenge1, true));
        roomList.add(Room.drawRoom(this, challenge2, true));
        for (int i = 1; i <= 8; i++) {
            if (i != challenge1 && i != challenge2) {
                roomList.add(Room.drawRoom(this, i, false));
            }
        }
        this.maze = new Maze(roomList, start, end);

        framerateHandler = new Timeline(new KeyFrame(Duration.seconds(1.0 / 60.0), event -> {
            if (sceneManager.getInputHandler().keyPressed(KeyCode.I)) {
                if (this.isInventoryOpen()) {
                    this.closeInventory();
                    sceneManager.getInputHandler().flushInputs();
                } else {
                    this.openInventory();
                }
            }

            if (state == GameState.PAUSED) {
                sceneManager.getInputHandler().flushInputs();
                lastUpdate = System.nanoTime();
                return;
            } else if (state == GameState.TRANSITIONING) {
                return;
            }
            sceneManager.getScalingManager().updateScaling();
            long now = System.nanoTime();
            long timeElapsed = now - lastUpdate;
            long frameUpdates = timeElapsed / FRAMERATE_NANO;
            for (int i = 0; i < frameUpdates; i++) {
                currentRoom.update();
                lastUpdate = System.nanoTime();
            }
            if (frameUpdates > 0) {
                ui.update();
                camera.update();
            }
        }));
        framerateHandler.setCycleCount(Animation.INDEFINITE);
    }

    public void enterRoomTransition(RoomExit transition) {
        state = GameState.TRANSITIONING;
        RoomExit end = maze.getNextRoom(transition);

        Set<GameObject> gameObjects = transition.getRoom().getGameObjects();
        for (GameObject go : gameObjects) {
            if (go.persistsThroughLoad()) {
                transition.getRoom().removeGameObject(go);
                end.getRoom().addGameObject(go);
                go.setRoom(end.getRoom());
            }
        }
        transition.getRoom().onRoomDestroy();
        this.currentRoom = end.getRoom();

        currentRoom.enterRoom(player, end);
        state = GameState.PLAYING;
    }
    public Parent initScene(SceneManager manager) {
        root = new StackPane();

        GameplayCamera camera = new GameplayCamera(manager, this);
        UI ui = new UI(player, manager.getScalingManager());

        root.getChildren().addAll(
                camera.getCamera(),
                ui.getRoot(),
                inventory.getInventoryPane()
        );

        this.camera = camera;
        this.ui = ui;
        inventory.getInventoryPane().setVisible(false);

        return root;
    }
    public void start() {
        state = GameState.TRANSITIONING;
        this.currentRoom = maze.getStart();
        currentRoom.addGameObject(player);
        currentRoom.enterRoom(player, currentRoom.getExits().get(0));
        state = GameState.PLAYING;

        lastUpdate = System.nanoTime();
        framerateHandler.play();
    }
    public void win() {
        sceneManager.showWin(player);
    }

    public void openInventory() {
        state = GameState.PAUSED;
        inventory.getInventoryPane().setVisible(true);
    }
    public void closeInventory() {
        inventory.getInventoryPane().setVisible(false);
        state = GameState.PLAYING;
    }
    public boolean isInventoryOpen() {
        return inventory.getInventoryPane().isVisible();
    }

    public Maze getMaze() {
        return maze;
    }

    public Player getPlayer() {
        return player;
    }
    public IRoom getCurrentRoom() {
        return currentRoom;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public static enum Difficulty {
        EASY, MEDIUM, HARD
    }
    public static enum GameState {
        PLAYING, TRANSITIONING, PAUSED
    }

}
