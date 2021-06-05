package sample;

import gameobjects.InvincibilityPickup;
import gameobjects.Player;
import gameobjects.Potion;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import weapon.Axe;
import weapon.Gun;
import weapon.Knife;
import weapon.Weapon;

import java.util.HashSet;
import java.util.Set;

public class Inventory {

    private Player player;
    private Weapon starting;
    private Set<Weapon> weaponsList;
    private Set<Potion> potionsList;
    private Set<InvincibilityPickup> invincList;
    private VBox inventory;
    private HBox objects;
    private VBox weapons;
    private VBox potions;
    private VBox invincs;

    public Inventory(Weapon starting, Player player) {
        this.starting = starting;
        this.player = player;
        weaponsList = new HashSet<>();
        weaponsList.add(starting);
        potionsList = new HashSet<>();
        invincList = new HashSet<>();


        this.createPane();
    }

    public void createPane() {
        inventory = new VBox();
        inventory.setAlignment(Pos.CENTER);
        inventory.setSpacing(15);

        Text title = new Text("INVENTORY");
        title.setFont(Font.font("verdana", FontWeight.BOLD, 30));
        title.setTextAlignment(TextAlignment.CENTER);

        objects = new HBox();
        objects.setAlignment(Pos.CENTER);
        objects.setSpacing(45);

        weapons = new VBox();
        Text weapon = new Text("WEAPONS");
        weapon.setFont(Font.font("verdana", FontWeight.BOLD, 20));
        Button startWep = new Button(starting.toString());
        startWep.setOnAction(ae -> {
            if (starting.toString().equals("GUN")) {
                player.setWeapon(new Gun(player));
            }
            if (starting.toString().equals("AXE")) {
                player.setWeapon(new Axe(player));
            }
            if (starting.toString().equals("KNIFE")) {
                player.setWeapon(new Knife(player));
            }
        });
        weapons.getChildren().addAll(
                weapon,
                startWep
        );

        potions = new VBox();
        Text potion = new Text("POTIONS");
        potion.setFont(Font.font("verdana", FontWeight.BOLD, 20));
        potions.getChildren().addAll(
                potion
        );

        invincs = new VBox();
        Text invinc = new Text("INVINCIBILITY POWERUPS");
        invinc.setFont(Font.font("verdana", FontWeight.BOLD, 20));
        invincs.getChildren().addAll(
                invinc
        );

        objects.getChildren().addAll(
                weapons,
                potions,
                invincs
        );

        inventory.getChildren().addAll(
                title,
                objects
        );
    }

    public VBox getInventoryPane() {
        return inventory;
    }

    public void addWeapon(Weapon add) {
        for (Weapon i : weaponsList) {
            if (i.toString().equals(add.toString())) {
                return;
            }
        }
        Button addWep = new Button(add.toString());
        addWep.setOnAction(ae -> {
            if (add.toString().equals("GUN")) {
                player.setWeapon(new Gun(player));
            }
            if (add.toString().equals("AXE")) {
                player.setWeapon(new Axe(player));
            }
            if (add.toString().equals("KNIFE")) {
                player.setWeapon(new Knife(player));
            }
        });
        weapons.getChildren().add(addWep);
    }

    public void addInvincibility(InvincibilityPickup add) {
        if (invincList.size() >= 5) {
            return;
        }
        Button addInvinc = new Button("INVINCIBILITY POWERUP");
        invincs.getChildren().add(addInvinc);
        addInvinc.setOnAction(ae -> {
            add.powerUp(player);
            invincs.getChildren().remove(addInvinc);
            player.incrementPowerUps();
        });
    }

    public void addPotion(Potion add) {
        if (potionsList.size() >= 5) {
            return;
        }
        Button addPot = new Button(add.toString());
        potions.getChildren().add(addPot);
        addPot.setOnAction(ae -> {
            add.powerUp(player);
            potions.getChildren().remove(addPot);
            player.incrementPowerUps();
        });
    }

}
