package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

public class ThrownKnife extends Projectile {

    public static final float SPEED = 0.6f;
    public static final int LIFETIME = 5;

    private int livedFor;

    public ThrownKnife(IRoom room, float angle, float initialX, float initialY) {
        super(
                new Rectangle(initialX, initialY, 0.35f, 0.35f),
                room,
                angle,
                SPEED
        );
        livedFor = 0;
    }
    public ThrownKnife(IRoom room, float angle, Point initialPos) {
        this(room, angle, initialPos.getX(), initialPos.getY());
    }

    @Override
    public boolean update() {
        if (++livedFor > LIFETIME) {
            return true;
        }
        super.update();
        return false;
    }

    @Override
    public boolean onCollision(GameObject other) {
        if (other instanceof IMonster) {
            IMonster monster = (IMonster) other;
            monster.updateHealth(-20);
            return true;
        } else if (other instanceof Wall) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.PINK);
        adapter.fillRect(this.getHitbox());
    }

}
