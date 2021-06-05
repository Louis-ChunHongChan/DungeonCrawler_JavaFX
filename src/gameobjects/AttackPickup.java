package gameobjects;
import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

import java.util.Set;

public class AttackPickup extends GameObject implements Potion {

    public static final int ATTACK_BONUS_TIME = 600;
    public AttackPickup(IRoom room, Point position) {
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
        Set<GameObject> currGameObjects = getRoom().getGameObjects();
        for (GameObject m : currGameObjects) {
            if (m instanceof IMonster) {
                ((IMonster) m).giveAttackBonus(ATTACK_BONUS_TIME);
            }
        }
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.LIGHTBLUE);
        adapter.fillRect(this.getHitbox());
    }

    public String toString() {
        return "ATTACK POTION";
    }
}
