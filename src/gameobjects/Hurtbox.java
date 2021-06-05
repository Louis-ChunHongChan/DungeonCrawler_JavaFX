package gameobjects;

import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

public class Hurtbox extends GameObject {

    private boolean hasUpdated;
    private boolean hitPlayer;
    private int damage;

    public Hurtbox(Rectangle hitbox, IRoom room, boolean hitPlayer, int damage) {
        super(hitbox, room);
        this.hitPlayer = hitPlayer;
        this.hasUpdated = false;
        this.damage = damage;
    }

    public boolean update() {
        if (hasUpdated) {
            return true;
        }
        hasUpdated = true;
        return false;
    }
    public boolean onCollision(GameObject object) {
        if (object instanceof Player && hitPlayer) {
            Player player = (Player) object;
            player.changeHealth(-this.damage);
        } else if (object instanceof IMonster && !hitPlayer) {
            IMonster monster = (IMonster) object;
            monster.updateHealth(-this.damage);
        }
        return false;
    }
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.RED);
        adapter.fillRect(this.getHitbox());
    }

}
