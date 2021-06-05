package gameobjects;

import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

import java.util.HashSet;
import java.util.Set;

public class Axe extends GameObject {

    public static final int LIFETIME = 8;

    private int livedFor;
    private Set<GameObject> hurtGameObjects;

    public Axe(IRoom room, float initialX, float initialY) {
        super(new Rectangle(initialX, initialY, 1.5f, 1.5f), room);
        hurtGameObjects = new HashSet<>();
    }

    @Override
    public boolean update() {
        if (++livedFor > LIFETIME) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCollision(GameObject other) {
        if (other instanceof IMonster && !hurtGameObjects.contains(other)) {
            IMonster monster = (IMonster) other;
            monster.updateHealth(-20);
            hurtGameObjects.add(other);
        }
        return false;
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.CYAN);
        adapter.fillRect(this.getHitbox());
    }
}
