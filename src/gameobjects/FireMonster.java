package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

import java.util.Random;

public class FireMonster extends GameObject implements IMonster {

    private int health;
    private Fireball fireball;
    private int attackBonusTime;

    public FireMonster(IRoom room) {
        super(
                new Rectangle(0, 0, 2f, 2f),
                room
        );
        this.getHitbox().moveTo(createSpawnPoint());
        Point center = this.getHitbox().getCenter();
        fireball = new Fireball(room, 0f, center.getX(), center.getY(), this);
        this.health = 80;
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
        if (this.attackBonusTime > 0) {
            this.attackBonusTime--;
        }
        return false;
    }
    @Override
    public boolean onCollision(GameObject object) {
        return false;
    }
    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.RED);
        adapter.fillRect(this.getHitbox());
    }

    public Fireball getFireball() {
        return fireball;
    }

    @Override
    public void punch(Player player) {
        player.changeHealth(-3);
    }

    @Override
    public void kick(Player player) {
        player.changeHealth(-5);
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
    public void die() {
        health = 0;
        this.getRoom().removeGameObject(this);
        if (Math.random() < 0.5) {
            this.getRoom().addGameObject(
                    new HealthPickup(this.getRoom(), this.getHitbox().getCenter())
            );
        }
        this.getRoom().getPlayer().incrementMonstersKilled();
    }

    @Override
    public void giveAttackBonus(int frames) {
        this.attackBonusTime += frames;
    }

    @Override
    public void onInstantiate() {
        super.onInstantiate();
        this.getRoom().addGameObject(fireball);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getRoom().removeGameObject(fireball);
    }

    @Override
    public int getHealth() {
        return health;
    }
}
