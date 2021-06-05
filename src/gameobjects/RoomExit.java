package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IRoom;

public class RoomExit extends GameObject {

    private int roomExitNum;
    private Point spawnPoint;
    private Wall blocker;

    public RoomExit(IRoom room, int exitNum, Rectangle hitbox, Point spawnPoint) {
        super(hitbox, room);
        this.roomExitNum = exitNum;
        this.spawnPoint = new Point(spawnPoint);
        this.blocker = new Wall(this.getRoom(), this.getHitbox());
        this.blocker.setVisible(false);

        this.setActive(false);
    }

    public int getRoomExitNum() {
        return roomExitNum;
    }
    public Point getSpawnPoint() {
        return new Point(spawnPoint);
    }

    @Override
    public void onInstantiate() {
        this.getRoom().addGameObject(blocker);
    }
    @Override
    public void onDestroy() {
        this.getRoom().removeGameObject(blocker);
    }
    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        blocker.setActive(!active);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.getRoom().hashCode() ^ Integer.hashCode(roomExitNum));
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean onCollision(GameObject other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            player.getGame().enterRoomTransition(this);
        } else {
            blocker.onCollision(other);
        }
        return false;
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.GREEN);
        adapter.fillRect(this.getHitbox());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoomExit)) {
            return false;
        }
        RoomExit other = (RoomExit) o;
        return this.getRoom().equals(other.getRoom()) && roomExitNum == other.roomExitNum;
    }
}
