package weapon;

import gameobjects.Player;
import sample.InputHandler;

public abstract class Weapon {

    private int weaponType;
    private Player player;

    public Weapon(int weaponType, Player player) {
        this.weaponType = weaponType;
        this.player = player;
    }

    public int getWeaponType() {
        return weaponType;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract void attack(InputHandler input);

}
