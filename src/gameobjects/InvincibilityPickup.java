package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IRoom;

public class InvincibilityPickup extends GameObject {

    public static final int INVINCIBILITY_TIME = 600;
    private static final Color[] INVINCIBILITY_RAINBOW = new Color[] {
        Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PURPLE, Color.PINK
    };

    public InvincibilityPickup(IRoom room, Point position) {
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
            player.getGame().getInventory().addInvincibility(this);
            return true;
        }
        return false;
    }

    public void powerUp(Player player) {
        player.giveInvincibility(INVINCIBILITY_TIME);
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(
                INVINCIBILITY_RAINBOW[(int) (Math.random() * INVINCIBILITY_RAINBOW.length)]
        );
        adapter.fillRect(this.getHitbox());
    }
}
