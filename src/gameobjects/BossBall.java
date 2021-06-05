package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IRoom;

public class BossBall extends Projectile {

    public static final float SPEED = 0.7f;

    private FinalMonster finalMonster;
    private float angle;

    public BossBall(IRoom room, float angle, float initialX, float initialY,
                    FinalMonster finalMonster) {
        super(
                new Rectangle(initialX, initialY, 0.5f, 0.5f),
                room,
                angle,
                SPEED
        );
        this.finalMonster = finalMonster;
        this.angle = angle;
    }

    @Override
    public boolean update() {
        this.move(SPEED, angle);
        if (this.getHitbox().getX() < 0
                || this.getHitbox().getX() > 32
                || this.getHitbox().getY() < 0
                || this.getHitbox().getY() > 16) {
            this.resetPosition();
        }
        return false;
    }

    @Override
    public boolean onCollision(GameObject object) {
        if (object instanceof Player) {
            Player player = (Player) object;
            player.changeHealth(-20);
            this.resetPosition();
        } else if (object instanceof Wall) {
            this.resetPosition();
        }
        return false;
    }

    private void resetPosition() {
        this.getHitbox().moveTo(finalMonster.getHitbox().getCenter());
        Point pCenter = finalMonster.getRoom().getPlayer().getHitbox().getCenter();
        Point mCenter = finalMonster.getHitbox().getCenter();
        this.angle = (float) (Math.atan2(pCenter.getY() - mCenter.getY(),
                mCenter.getX() - pCenter.getX()) + Math.PI);
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.DARKGOLDENROD);
        adapter.fillRect(this.getHitbox());
    }

    @Override
    public void onRoomLoad() {
        this.resetPosition();
    }
}
