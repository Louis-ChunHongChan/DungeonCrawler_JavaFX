package weapon;

import gameobjects.Player;
import sample.InputHandler;

public class Axe extends Weapon {

    public static final int WEAPON_TYPE = 2;

    public Axe(Player player) {
        super(WEAPON_TYPE, player);
    }

    @Override
    public void attack(InputHandler input) {
        gameobjects.Axe axe = new gameobjects.Axe(
                this.getPlayer().getRoom(),
                this.getPlayer().getHitbox().getX() - 1.5f / 3,
                this.getPlayer().getHitbox().getY() - 1.5f / 3
        );
        this.getPlayer().getRoom().addGameObject(axe);
    }

    public String toString() {
        return "AXE";
    }
}
