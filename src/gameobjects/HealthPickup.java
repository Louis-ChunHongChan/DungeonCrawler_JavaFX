package gameobjects;
import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IRoom;

public class HealthPickup extends GameObject implements Potion {

    public HealthPickup(IRoom room, Point position) {
        super(new Rectangle(0, 0, 0.5f, 0.5f), room);
        this.getHitbox().moveTo(position);
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean onCollision(GameObject other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            player.getGame().getInventory().addPotion(this);
            return true;
        }
        return false;
    }

    public void powerUp(Player player) {
        player.setHealth(150);
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.DARKRED);
        adapter.fillRect(this.getHitbox());
    }

    public String toString() {
        return "HEALTH POTION";
    }
}
