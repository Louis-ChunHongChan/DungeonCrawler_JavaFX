package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;
import sample.ScalingManager;

public class Bullet extends Projectile {

    public static final float SPEED = 0.3f;

    public Bullet(IRoom room, float angle, float initialX, float initialY) {
        super(
                new Rectangle(initialX, initialY, 0.1f, 0.1f),
                room,
                angle,
                SPEED
        );
    }
    public Bullet(IRoom room, float angle, Point initialPos) {
        this(room, angle, initialPos.getX(), initialPos.getY());
    }

    @Override
    public boolean update() {
        super.update();
        if (getHitbox().getX() < 0
                || getHitbox().getX() > ScalingManager.SCREEN_TILE_WIDTH
                || getHitbox().getY() < 0
                || getHitbox().getY() > ScalingManager.SCREEN_TILE_HEIGHT) {
            return true;
        }
        return false;
    }
    @Override
    public void onRoomUnload() {
        this.getRoom().removeGameObject(this);
    }

    @Override
    public boolean onCollision(GameObject other) {
        if (other instanceof IMonster) {
            IMonster monster = (IMonster) other;
            monster.updateHealth(-10);
            return true;
        } else if (other instanceof Wall) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.GRAY);
        adapter.fillRect(this.getHitbox());
    }
}
