package gameobjects;

import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

public class Wall extends GameObject {

    public Wall(IRoom room, Rectangle hitbox) {
        super(hitbox, room);
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean onCollision(GameObject other) {
        if (!(other instanceof IMonster || other instanceof Player)) {
            return false;
        }
        float myX = this.getHitbox().getX();
        float myY = this.getHitbox().getY();
        float myWidth = this.getHitbox().getWidth();
        float myHeight = this.getHitbox().getHeight();
        float otherX = other.getHitbox().getX();
        float otherY = other.getHitbox().getY();
        float otherWidth = other.getHitbox().getWidth();
        float otherHeight = other.getHitbox().getHeight();

        boolean left = myX > otherX;
        boolean right = otherX + otherWidth > myX + myWidth;
        boolean top = myY > otherY;
        boolean bottom = otherY + otherHeight > myY + myHeight;

        if (left && right) { //wider than wall
            if (top) {
                other.moveY(myY - otherY);
            } else {
                other.moveY(otherY - (myY + myHeight));
            }
        } else if (left) { //left
            float leftDist = otherX + otherWidth - myX;
            if (top) { //top left
                float topDist = otherY + otherHeight - myY;
                if (leftDist < topDist) {
                    other.moveX(-leftDist);
                } else {
                    other.moveY(-topDist);
                }
            } else { //bottom left
                float bottomDist = myY + myHeight - otherY;
                if (leftDist < bottomDist) {
                    other.moveX(-leftDist);
                } else {
                    other.moveY(bottomDist);
                }
            }
        } else { //right
            float rightDist = myX + myWidth - otherX;
            if (top) { //top right
                float topDist = otherY + otherHeight - myY;
                if (rightDist < topDist) {
                    other.moveX(rightDist);
                } else {
                    other.moveY(-topDist);
                }
            } else { //bottom left
                float bottomDist = myY + myHeight - otherY;
                if (rightDist < bottomDist) {
                    other.moveX(rightDist);
                } else {
                    other.moveY(bottomDist);
                }
            }
        }
        return false;
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.BLACK);
        adapter.fillRect(this.getHitbox());
    }
}
