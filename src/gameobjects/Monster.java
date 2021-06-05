package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

import java.util.Random;

public class Monster extends GameObject implements IMonster {

    public static final int ATTACK_COOLDOWN = 120;
    public static final float SPEED = 0.1f;

    private int health;
    private int attackCooldown;
    private int attackBonusTime;

    public Monster(IRoom room) {
        super(
                new Rectangle(0, 0, 2f, 2f),
                room
        );
        this.getHitbox().moveTo(createSpawnPoint());
        this.health = 65;
        this.attackCooldown = 0;
        this.attackBonusTime = 0;
    }

    public Point createSpawnPoint() {
        Random r = new Random();
        int minX = 3;
        int maxX = 26;
        int xVal = r.nextInt(maxX - minX) + minX;
        int minY = 3;
        int maxY = 14;
        int yVal = r.nextInt(maxY - minY) + minY;

        return new Point(xVal, yVal);
    }

    @Override
    public boolean update() {
        if (health <= 0) {
            return true;
        }
        if (attackCooldown > 0) {
            attackCooldown--;
        }
        if (this.attackBonusTime > 0) {
            this.attackBonusTime--;
        }
        if (attackCooldown == 0
                && this.getRoom().getPlayer().getHitbox().minDistanceTo(this.getHitbox()) < 0.5f) {
            if (Math.random() < 0.5) {
                this.punch(this.getRoom().getPlayer());
            } else {
                this.kick(this.getRoom().getPlayer());
            }
            attackCooldown = ATTACK_COOLDOWN;
        } else {
            Point center = this.getHitbox().getCenter();
            Point pCenter = this.getRoom().getPlayer().getHitbox().getCenter();
            float angle = (float) (Math.atan2(pCenter.getY() - center.getY(),
                    center.getX() - pCenter.getX()) + Math.PI);
            this.move(SPEED, angle);
        }
        return false;
    }
    @Override
    public boolean onCollision(GameObject object) {
        return false;
    }
    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.PURPLE);
        adapter.fillRect(this.getHitbox());
    }

    @Override
    public void punch(Player player) {
        player.changeHealth(-10);
    }

    @Override
    public void kick(Player player) {
        player.changeHealth(-15);
    }

    @Override
    public void updateHealth(int delta) {
        if (this.attackBonusTime > 0) {
            health += 5 * delta;
            return;
        }
        health += delta;
        if (health <= 0) {
            die();
        }
        this.getRoom().getPlayer().incrementDamage(delta * -1);
    }

    @Override
    public void giveAttackBonus(int frames) {
        this.attackBonusTime += frames;
    }

    @Override
    public void die() {
        health = 0;
        this.getRoom().removeGameObject(this);
        if (Math.random() < 0.33) {
            this.getRoom().addGameObject(
                    new InvincibilityPickup(this.getRoom(), this.getHitbox().getCenter())
            );
        }
        this.getRoom().getPlayer().incrementMonstersKilled();
    }

    @Override
    public int getHealth() {
        return health;
    }
}
