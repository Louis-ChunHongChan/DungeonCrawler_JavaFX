package gameobjects;


import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

import java.util.Random;

public class FinalMonster extends GameObject implements IMonster {

    public static final int COOLDOWN = 100;
    public static final float SPEED = 0.1f;

    private int health;
    private int attackCooldown;
    private int attackBonusTime;
    private BossBall bossBall;

    public FinalMonster(IRoom room) {
        super(
                new Rectangle(0, 0, 3f, 3f),
                room
        );
        this.getHitbox().moveTo(createSpawnPoint());
        this.health = 350;
        Point center = this.getHitbox().getCenter();
        bossBall = new BossBall(room, 0f, center.getX(), center.getY(), this);
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
    public int getHealth() {
        return 0;
    }

    @Override
    public void punch(Player player) {
        player.changeHealth(-15);
    }

    @Override
    public void kick(Player player) {
        player.changeHealth(-10);
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
    public void giveAttackBonus(int frames)  {
        this.attackBonusTime += frames;
    }

    @Override
    public void die() {
        health = 0;
        this.getRoom().removeGameObject(this);
        this.getRoom().getPlayer().changeMoney(100);
        this.getRoom().getPlayer().incrementMonstersKilled();

        this.getRoom().getPlayer().getGame().win();
    }

    @Override
    public void onInstantiate() {
        super.onInstantiate();
        this.getRoom().addGameObject(bossBall);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getRoom().removeGameObject(bossBall);
    }

    @Override
    public boolean update() {
        if (health <= 0) {
            this.die();
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
            attackCooldown = COOLDOWN;
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
    public boolean onCollision(GameObject other) {
        return false;
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.GOLDENROD);
        adapter.fillRect(this.getHitbox());
    }
}
