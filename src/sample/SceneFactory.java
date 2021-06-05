package sample;

import gameobjects.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import weapon.Axe;
import weapon.Gun;
import weapon.Knife;
import weapon.Weapon;

public abstract class SceneFactory {

    public static Parent createConfig(SceneManager manager) {
        VBox vbox = new VBox();

        Text instructions = new Text("Enter your name");
        TextField playerName = new TextField();
        playerName.setPromptText("Enter your name");

        VBox difficulty = new VBox();
        Text diffInstruct = new Text("Choose a difficulty level");
        RadioButton easy = new RadioButton("Easy");
        RadioButton medium = new RadioButton("Medium");
        RadioButton hard = new RadioButton("Hard");
        difficulty.getChildren().addAll(diffInstruct, easy, medium, hard);
        ToggleGroup diff = new ToggleGroup();
        easy.setToggleGroup(diff);
        medium.setToggleGroup(diff);
        hard.setToggleGroup(diff);
        difficulty.setPadding(new Insets(0, 20, 0, 0));
        difficulty.setAlignment(Pos.CENTER);

        VBox weapon = new VBox();
        Text wepInstruct = new Text("Choose a weapon");
        RadioButton knife = new RadioButton("Knife");
        RadioButton gun = new RadioButton("Gun");
        RadioButton axe = new RadioButton("Axe");
        weapon.getChildren().addAll(wepInstruct, knife, gun, axe);
        ToggleGroup wep = new ToggleGroup();
        knife.setToggleGroup(wep);
        gun.setToggleGroup(wep);
        axe.setToggleGroup(wep);
        weapon.setPadding(new Insets(0, 20, 0, 0));
        weapon.setAlignment(Pos.CENTER);

        Button next = new Button("Next");
        next.setOnAction(ae -> {
            boolean canMoveOn = true;
            if (playerName.getText().equals("") || playerName.getText().trim().equals("")
                    || playerName.getText() == null) {
                instructions.setText("Name cannot be empty or only whitespace characters");
                canMoveOn = false;
            } else {
                instructions.setText("Enter your name");
            }
            if (diff.getSelectedToggle() == null) {
                diffInstruct.setText("Must choose difficulty before moving on");
                canMoveOn = false;
            } else {
                diffInstruct.setText("Choose a difficulty level");
            }

            if (wep.getSelectedToggle() == null) {
                wepInstruct.setText("Must choose weapon before moving on");
                canMoveOn = false;
            } else {
                wepInstruct.setText("Choose a weapon");
            }
            if (canMoveOn) {
                Game.Difficulty startDifficulty = Game.Difficulty.EASY;
                if (diff.getSelectedToggle() == medium) {
                    startDifficulty = Game.Difficulty.MEDIUM;
                } else if (diff.getSelectedToggle() == hard) {
                    startDifficulty = Game.Difficulty.HARD;
                }
                Weapon startingWeapon = new Knife(null);
                if (wep.getSelectedToggle() == gun) {
                    startingWeapon = new Gun(null);
                } else if (wep.getSelectedToggle() == axe) {
                    startingWeapon = new Axe(null);
                }
                manager.showGame(startDifficulty, startingWeapon);
            }
        });

        vbox.getChildren().addAll(instructions, playerName, difficulty, weapon, next);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    public static Parent createWelcome(SceneManager manager) {
        VBox welcome = new VBox();
        Text text = new Text("Welcome to the Dungeon Crawler game!");
        text.setFont(Font.font("Times New Roman", 45));

        Button start = new Button("Start Game");
        start.setOnAction(ae -> manager.showConfig());

        Button exit = new Button("Exit Game");
        exit.setOnAction(ae -> {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });

        welcome.getChildren().addAll(text, start, exit);
        welcome.setAlignment(Pos.CENTER);

        return welcome;
    }

    public static Parent createWin(SceneManager manager, Player player) {
        VBox win = new VBox();
        Text text = new Text("Congratulations! You escaped the Dungeon!");
        text.setFont(Font.font("Times New Roman", 45));

        Button restart = new Button("Restart");
        restart.setOnAction(ae -> {
            Stage stage = (Stage) restart.getScene().getWindow();
            new SceneManager(stage).init();
            stage.setFullScreen(true);
        });

        Button exit = new Button("Exit");
        exit.setOnAction(ae -> {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });

        VBox stats = new VBox();
        Text stat = new Text("Game Statistics");
        stat.setFont(Font.font("Times New Roman", 35));
        Text monstersKilled = new Text("Monsters Killed: " + player.getMonstersKilled());
        Text powerUps = new Text("Potions & PowerUps Used: " + player.getPowerUps());
        Text damage = new Text("Damage Dealt: " + player.getDamageDealt());
        monstersKilled.setFont(Font.font("Times New Roman", 20));
        powerUps.setFont(Font.font("Times New Roman", 20));
        damage.setFont(Font.font("Times New Roman", 20));
        stats.getChildren().addAll(stat, monstersKilled, powerUps, damage);
        stats.setAlignment(Pos.CENTER);

        win.getChildren().addAll(text, restart, exit, stats);
        win.setAlignment(Pos.CENTER);

        return win;
    }

    public static Parent createLoss(SceneManager manager, Player player) {
        VBox loss = new VBox();
        Text text = new Text("Oh no! You died! :(");
        text.setFont(Font.font("Times New Roman", 45));

        Button restart = new Button("Restart");
        restart.setOnAction(ae -> {
            Stage stage = (Stage) restart.getScene().getWindow();
            new SceneManager(stage).init();
            stage.setFullScreen(true);
        });

        Button exit = new Button("Exit");
        exit.setOnAction(ae -> {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });

        VBox stats = new VBox();
        Text stat = new Text("Game Statistics");
        stat.setFont(Font.font("Times New Roman", 35));
        Text monstersKilled = new Text("Monsters Killed: " + player.getMonstersKilled());
        Text powerUps = new Text("Potions & PowerUps Used: " + player.getPowerUps());
        Text damage = new Text("Damage Dealt: " + player.getDamageDealt());
        monstersKilled.setFont(Font.font("Times New Roman", 20));
        powerUps.setFont(Font.font("Times New Roman", 20));
        damage.setFont(Font.font("Times New Roman", 20));
        stats.getChildren().addAll(stat, monstersKilled, powerUps, damage);
        stats.setAlignment(Pos.CENTER);

        loss.getChildren().addAll(text, restart, exit, stats);
        loss.setAlignment(Pos.CENTER);

        return loss;
    }

}
