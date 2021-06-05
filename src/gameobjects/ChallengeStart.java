package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

public class ChallengeStart extends GameObject {

    private boolean challengeStarted;
    private boolean challengeComplete;

    public ChallengeStart(IRoom room) {
        super(new Rectangle(14, 7, 4, 4), room);
        this.challengeStarted = false;
        this.challengeComplete = false;
    }

    private void startChallenge() {
        challengeStarted = true;
        this.setVisible(false);
        for (GameObject go : this.getRoom().getGameObjects()) {
            if (go instanceof RoomExit) {
                go.setActive(false);
            }
        }
        for (int i = 0; i < 7; i++) {
            GameObject monster;
            if (Math.random() < 0.33) {
                monster = new FireMonster(this.getRoom());
            } else if (Math.random() < 0.5) {
                monster = new PoisonMonster(this.getRoom());
            } else {
                monster = new Monster(this.getRoom());
            }
            this.getRoom().addGameObject(monster);
        }
    }
    private void endChallenge() {
        this.challengeComplete = true;
        for (GameObject go : this.getRoom().getGameObjects()) {
            if (go instanceof RoomExit) {
                go.setActive(true);
            }
        }
        for (int i = 0; i < 4; i++) {
            GameObject reward = null;
            Point point = new Point(
                    this.getHitbox().getX() + (float) (Math.random() * this.getHitbox().getWidth()),
                    this.getHitbox().getY() + (float) (Math.random() * this.getHitbox().getHeight())
            );
            if (Math.random() < 0.33) {
                reward = new AttackPickup(this.getRoom(), point);
            } else if (Math.random() < 0.8) {
                reward = new HealthPickup(this.getRoom(), point);
            } else {
                reward = new InvincibilityPickup(this.getRoom(), point);
            }
            this.getRoom().addGameObject(reward);
        }
    }

    @Override
    public boolean update() {
        if (challengeStarted && !challengeComplete) {
            for (GameObject go : this.getRoom().getGameObjects()) {
                if (go instanceof IMonster) {
                    return false;
                }
            }
            this.endChallenge();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCollision(GameObject other) {
        if (other instanceof Player && !challengeStarted && !challengeComplete) {
            this.startChallenge();
        }
        return false;
    }

    @Override
    public void draw(DrawingAdapter adapter) {
        adapter.setFill(Color.GOLDENROD);
        adapter.fillRect(this.getHitbox());
    }
}
