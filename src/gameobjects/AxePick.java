package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.*;
import weapon.Axe;

import java.util.Random;

public class AxePick extends GameObject {

    public AxePick(IRoom room) {
        super(
                new Rectangle(0, 0, 0.65f, 0.65f),
                room
        );
        this.getHitbox().moveTo(createSpawnPoint());
    }

    public Point createSpawnPoint() {
        Random r = new Random();
        int minX = 3;
        int maxX = 26;
        int xVal = r.nextInt(maxX - minX) + minX;
        int minY = 3;
        int maxY = 14;
        int yVal = r.nextInt(maxY - minY) + minY;

        return new Point(xVal, yVal);
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean onCollision(GameObject other) {
        if (other instanceof Player) {
            Player player = (Player) other;
            player.getGame().getInventory().addWeapon(new Axe(player));
            return true;
        }
        return false;
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.CYAN);
        adapter.fillRect(this.getHitbox());
    }
}
