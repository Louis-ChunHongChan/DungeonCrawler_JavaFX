package gameobjects;

import geometry.Rectangle;
import sample.IRoom;

public abstract class Projectile extends GameObject {

    private float angle;
    private float speed;

    public Projectile(Rectangle hitbox, IRoom room, float angle, float speed) {
        super(hitbox, room);
        this.angle = angle;
        this.speed = speed;
    }

    @Override
    public boolean update() {
        this.move(speed, angle);

        return false;
    }

    public float getAngle() {
        return angle;
    }
    public void setAngle(float angle) {
        this.angle = angle;
    }
    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
