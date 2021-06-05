package weapon;

import gameobjects.GameObject;
import gameobjects.Hurtbox;
import gameobjects.Player;
import gameobjects.ThrownKnife;
import geometry.Point;
import geometry.Rectangle;
import sample.IMonster;
import sample.InputHandler;

public class Knife extends Weapon {
    
    public static final int WEAPON_TYPE = 1;

    public static final double QUARTER_PI = Math.PI / 4;
    
    public Knife(Player player) {
        super(WEAPON_TYPE, player);
    }

    @Override
    public void attack(InputHandler input) {
        float minDistance = Float.MAX_VALUE;
        for (GameObject go : this.getPlayer().getRoom().getGameObjects()) {
            if (go instanceof IMonster) {
                minDistance = Math.min(minDistance,
                        this.getPlayer().getHitbox().minDistanceTo(go.getHitbox()));
            }
        }
        if (minDistance <= 0.5f) {
            meleeAttack(input);
        } else {
            rangeAttack(input);
        }
    }
    private void rangeAttack(InputHandler input) {
        float mx = input.getMouseX();
        float my = input.getMouseY();
        Point center = this.getPlayer().getHitbox().getCenter();
        ThrownKnife knife = new ThrownKnife(
                this.getPlayer().getRoom(),
                (float) (Math.atan2(my - center.getY(), center.getX() - mx) + Math.PI),
                center
        );
        this.getPlayer().getRoom().addGameObject(knife);
    }
    private void meleeAttack(InputHandler input) {
        float mx = input.getMouseX();
        float my = input.getMouseY();
        Point center = this.getPlayer().getHitbox().getCenter();
        double degree = Math.atan2(my - center.getY(), center.getX() - mx) + Math.PI;
        Rectangle hitbox = null;
        if (degree >= QUARTER_PI && degree <= 3 * QUARTER_PI) {
            hitbox = new Rectangle(
                    center.getX() - 0.12f,
                    this.getPlayer().getHitbox().getY() - 0.5f,
                    0.24f,
                    0.5f
            );
        } else if (degree >= 3 * QUARTER_PI && degree <= 5 * QUARTER_PI) {
            hitbox = new Rectangle(
                    this.getPlayer().getHitbox().getX() - 0.5f,
                    center.getY() - 0.12f,
                    0.5f,
                    0.24f
            );
        } else if (degree >= 5 * QUARTER_PI && degree <= 7 * QUARTER_PI) {
            hitbox = new Rectangle(
                    center.getX() - 0.12f,
                    this.getPlayer().getHitbox().getY() + this.getPlayer().getHitbox().getHeight(),
                    0.24f,
                    0.5f
            );
        } else {
            hitbox = new Rectangle(
                    this.getPlayer().getHitbox().getX() + this.getPlayer().getHitbox().getWidth(),
                    center.getY() - 0.12f,
                    0.5f,
                    0.24f
            );
        }
        Hurtbox knife = new Hurtbox(hitbox, this.getPlayer().getRoom(), false, 10);
        this.getPlayer().getRoom().addGameObject(knife);
    }

    public String toString() {
        return "KNIFE";
    }
}
