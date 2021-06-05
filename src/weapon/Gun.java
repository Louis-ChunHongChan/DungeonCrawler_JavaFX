package weapon;

import gameobjects.Bullet;
import gameobjects.Player;
import geometry.Point;
import sample.InputHandler;

public class Gun extends Weapon {

    public static final int WEAPON_TYPE = 0;

    public Gun(Player player) {
        super(WEAPON_TYPE, player);
    }

    @Override
    public void attack(InputHandler input) {
        float mx = input.getMouseX();
        float my = input.getMouseY();
        Point center = this.getPlayer().getHitbox().getCenter();
        Bullet b = new Bullet(
                this.getPlayer().getRoom(),
                (float) (Math.atan2(my - center.getY(), center.getX() - mx) + Math.PI),
                center
        );
        this.getPlayer().getRoom().addGameObject(b);
    }

    public String toString() {
        return "GUN";
    }
}
