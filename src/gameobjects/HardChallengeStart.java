package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import sample.DrawingAdapter;
import sample.IMonster;
import sample.IRoom;

public class HardChallengeStart extends GameObject {

    private boolean challengeStarted;
    private boolean challengeComplete;
    private int round;

    public HardChallengeStart(IRoom room) {
        super(new Rectangle(14, 7, 4, 4), room);
        this.challengeStarted = false;
        this.challengeComplete = false;
    }

    private void startChallenge() {
        round++;
        int i = 0;
        if (round == 1) {
            i = 8;
        } else {
            i = 10;
        }
        challengeStarted = true;
        this.setVisible(false);
        for (GameObject go : this.getRoom().getGameObjects()) {
            if (go instanceof RoomExit) {
                go.setActive(false);
            }
        }
        if (round == 1) {
            for (int j = 0; j < i; j++) {
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
        } else if (round == 2) {
            for (int j = 0; j < i; j++) {
                GameObject monster;
                if (Math.random() < 0.33) {
                    monster = new Monster(this.getRoom());
                } else if (Math.random() < 0.5) {
                    monster = new FireMonster(this.getRoom());
                } else {
                    monster = new PoisonMonster(this.getRoom());
                }
                this.getRoom().addGameObject(monster);
            }
        }
    }
    private void endChallenge() {
        this.challengeComplete = true;
        for (GameObject go : this.getRoom().getGameObjects()) {
            if (go instanceof RoomExit) {
                go.setActive(true);
            }
        }
        for (int i = 0; i < 6; i++) {
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
            if (round == 2) {
                this.endChallenge();
                return true;
            } else {
                this.startChallenge();
            }
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
        adapter.setFill(Color.BLACK);
        adapter.fillRect(this.getHitbox());
    }

    public boolean getChallengeStarted() {
        return challengeStarted;
    }
}
