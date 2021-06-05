package gameobjects;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lib.ClampedInt;
import sample.DrawingAdapter;
import sample.Game;
import sample.InputHandler;
import sample.SceneManager;
import weapon.Weapon;

public class Player extends GameObject {

    public static final float SPEED = 0.25f;
    private static final Color[] INVINCIBILITY_RAINBOW = new Color[] {
        Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PURPLE, Color.PINK
    };

    private int money;
    private ClampedInt health;
    private int invincibilityTime;
    private SceneManager manager;
    private InputHandler input;
    private Game game;
    private Weapon weapon;
    private int monstersKilled;
    private int powerUps;
    private int damageDealt;

    public Player(SceneManager manager, Game game) {
        super(new Rectangle(0, 0, 0.5f, 0.5f), null);
        this.setPersistThroughLoad(true);

        this.manager = manager;
        this.game = game;
        this.input = manager.getInputHandler();
        this.health = new ClampedInt(0, 150, 150);
        this.invincibilityTime = 0;
        this.monstersKilled = 0;
    }

    @Override
    public boolean update() {
        if (input.isKeyDown(KeyCode.A)) {
            this.changeX(-SPEED);
        }
        if (input.isKeyDown(KeyCode.D)) {
            this.changeX(SPEED);
        }
        if (input.isKeyDown(KeyCode.W)) {
            this.changeY(-SPEED);
        }
        if (input.isKeyDown(KeyCode.S)) {
            this.changeY(SPEED);
        }

        if (input.mousePrimaryPressed()) {
            weapon.attack(input);
        }

        input.flushInputs();

        if (this.invincibilityTime > 0) {
            this.invincibilityTime--;
        }
        return false;
    }
    @Override
    public boolean onCollision(GameObject other) {
        return false;
    }
    @Override
    public void draw(DrawingAdapter adapter) {
        if (this.invincibilityTime > 0) {
            adapter.setFill(
                    INVINCIBILITY_RAINBOW[(int) (Math.random() * INVINCIBILITY_RAINBOW.length)]
            );
        } else {
            adapter.setFill(Color.BLUE);
        }
        adapter.fillRect(this.getHitbox());
    }

    public int getMoney() {
        return money;
    }
    public void changeMoney(int delta) {
        money += delta;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public int getHealth() {
        return health.getValue();
    }
    public int getMaxHealth() {
        return health.getMax();
    }
    public void changeHealth(int delta) {
        if (invincibilityTime > 0) {
            return;
        }
        health.changeValue(delta);
        if (health.getValue() <= 0) {
            die();
        }
    }
    public void setHealth(int health) {
        this.health.setValue(health);
    }
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public Game getGame() {
        return game;
    }

    public void giveInvincibility(int frames) {
        this.invincibilityTime += frames;
    }

    public void die() {
        health.setValue(0);
        this.getRoom().removeGameObject(this);
        manager.showLoss(this);
    }

    public void setPosition(Point position) {
        this.getHitbox().moveTo(position);
    }
    public void changeX(float delta) {
        this.getHitbox().moveX(delta);
    }
    public void changeY(float delta) {
        this.getHitbox().moveY(delta);
    }
    public Point getPosition() {
        return new Point(this.getHitbox().getLocation());
    }

    public int getMonstersKilled() {
        return this.monstersKilled;
    }
    public void incrementMonstersKilled() {
        this.monstersKilled++;
    }

    public int getPowerUps() {
        return this.powerUps;
    }
    public void incrementPowerUps() {
        this.powerUps++;
    }

    public int getDamageDealt() {
        return this.damageDealt;
    }
    public void incrementDamage(int damage) {
        this.damageDealt += damage;
    }
}
