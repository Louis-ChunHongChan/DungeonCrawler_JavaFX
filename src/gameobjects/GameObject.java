package gameobjects;

import geometry.Rectangle;
import sample.DrawingAdapter;
import sample.IRoom;

public abstract class GameObject {

    private static int idCounter = 0;

    private boolean active;
    private boolean visible;
    private boolean persistThroughLoad;
    private Rectangle hitbox;
    private IRoom room;
    private int uid;

    public GameObject(Rectangle hitbox, IRoom room) {
        this.hitbox = new Rectangle(hitbox);
        this.room = room;
        this.active = true;
        this.visible = true;
        this.persistThroughLoad = false;
        this.uid = idCounter++;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean isActive() {
        return active;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setPersistThroughLoad(boolean persistThroughLoad) {
        this.persistThroughLoad = persistThroughLoad;
    }
    public boolean persistsThroughLoad() {
        return persistThroughLoad;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public IRoom getRoom() {
        return room;
    }
    public void setRoom(IRoom room) {
        this.room = room;
    }
    public void moveX(float delta) {
        hitbox.moveX(delta);
    }
    public void moveY(float delta) {
        hitbox.moveY(delta);
    }
    public void move(float distance, float angle) {
        this.moveX(distance * (float) Math.cos(angle));
        this.moveY(-distance * (float) Math.sin(angle));
    }
    public void onInstantiate() { }
    public void onDestroy() { }
    public void onRoomLoad() { }
    public void onRoomUnload() { }

    public abstract boolean update();
    public abstract boolean onCollision(GameObject other);

    public abstract void draw(DrawingAdapter adapter);

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GameObject)) {
            return false;
        }
        return this.uid == ((GameObject) other).uid;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(uid);
    }

}
