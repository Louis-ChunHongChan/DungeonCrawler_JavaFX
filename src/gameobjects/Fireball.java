package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IRoom;

public class Fireball extends Projectile {

    public static final float SPEED = 0.4f;

    private FireMonster fireMonster;
    private float angle;

    public Fireball(IRoom room, float angle, float initialX, float initialY,
                    FireMonster fireMonster) {
        super(
                new Rectangle(initialX, initialY, 0.5f, 0.5f),
                room,
                angle,
                SPEED
        );
        this.fireMonster = fireMonster;
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
            player.changeHealth(-10);
            this.resetPosition();
        } else if (object instanceof Wall) {
            this.resetPosition();
        }
        return false;
    }
    private void resetPosition() {
        this.getHitbox().moveTo(fireMonster.getHitbox().getCenter());
        Point pCenter = fireMonster.getRoom().getPlayer().getHitbox().getCenter();
        Point mCenter = fireMonster.getHitbox().getCenter();
        this.angle = (float) (Math.atan2(pCenter.getY() - mCenter.getY(),
                mCenter.getX() - pCenter.getX()) + Math.PI);
    }
    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.ORANGERED);
        adapter.fillRect(this.getHitbox());
    }

    @Override
    public void onRoomLoad() {
        this.resetPosition();
    }
}
