import gameobjects.*;
import geometry.Point;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.Game;
import sample.Main;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

public class GameTest extends ApplicationTest {

    private Main main;

    @Override
    public void start(Stage primaryStage) throws Exception {
        main = new Main();
        main.start(primaryStage);
    }

    //Verifies that exit to next room is not active when monsters are present

    @Test
    public void exitContinue() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Medium");
        clickOn("Knife");
        clickOn("Next");
        for (int i = 0; i < 5; i++) {
            press(KeyCode.W).release(KeyCode.W);
        }
        sleep();
        for (int i = 0; i < 15; i++) {
            press(KeyCode.W).release(KeyCode.W);
        }
        sleep();
        for (int i = 0; i < 15; i++) {
            press(KeyCode.A).release(KeyCode.A);
        }
    }

    //Verifies that exit to previous room works properly when monsters are present

    @Test
    public void exitRetreat() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Medium");
        clickOn("Knife");
        clickOn("Next");
        for (int i = 0; i < 5; i++) {
            press(KeyCode.W).release(KeyCode.W);
        }
        sleep();
        for (int i = 0; i < 5; i++) {
            press(KeyCode.S).release(KeyCode.S);
        }
    }

    @Test
    public void fireMonsterExists() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Medium");
        clickOn("Knife");
        clickOn("Next");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.W);
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof FireMonster) {
                return;
            }
        }
        fail();
    }

    @Test
    public void fireBallExists() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.W);
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Fireball) {
                return;
            }
        }
        fail();
    }

    @Test
    public void poisonMonsterExists() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Knife");
        clickOn("Next");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.W);
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof PoisonMonster) {
                return;
            }
        }
        fail();
    }

    @Test
    public void poisonBallExists() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Hard");
        clickOn("Gun");
        clickOn("Next");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.W);
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Poisonball) {
                return;
            }
        }
        fail();
    }

    @Test
    public void monsterExists() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Knife");
        clickOn("Next");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.W);
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Monster) {
                return;
            }
        }
        fail();
    }

    @Test
    public void monsterDie() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Knife");
        clickOn("Next");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.W);
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Monster) {
                ((Monster) go).die();
            }
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Monster) {
                fail();
            }
        }
        return;
    }

    @Test
    public void gunBulletExist() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        press(MouseButton.PRIMARY);
        sleep();

        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Bullet) {
                return;
            }
        }
        fail();
    }

    @Test
    public void knifeExist() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Knife");
        clickOn("Next");
        press(MouseButton.PRIMARY);
        sleep();

        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof ThrownKnife) {
                return;
            }
        }
        fail();
    }

    //Verifies that movement in all directions via user input is working properly

    @Test
    public void movementAll() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        press(KeyCode.D);
        press(KeyCode.A);
        press(KeyCode.W);
        press(KeyCode.S);
    }

    //Verifies that movement to the right via user input is working properly

    @Test
    public void movementRight() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        for (int i = 0; i < 5; i++) {
            press(KeyCode.D);
        }
    }

    //Verifies that movement to the left via user input is working properly

    @Test
    public void movementLeft() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        for (int i = 0; i < 5; i++) {
            press(KeyCode.A);
        }
    }

    //Verifies that movement up via user input is working properly

    @Test
    public void movementUp() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        for (int i = 0; i < 5; i++) {
            press(KeyCode.W);
        }
    }

    //Verifies that movement down via user input is working properly

    @Test
    public void movementDown() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        for (int i = 0; i < 5; i++) {
            press(KeyCode.S);
        }
    }

    //Test if axe attack input command is working
    @Test
    public void axeAttackTest() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        for (int i = 0; i < 5; i++) {
            clickOn(MouseButton.PRIMARY);
        }
    }

    @Test
    public void axeExist() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        press(MouseButton.PRIMARY);
        sleep();

        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Axe) {
                return;
            }
        }
        fail();
    }

    @Test
    public void initialInventory() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        press(KeyCode.I);
        verifyThat("INVENTORY", NodeMatchers.isNotNull());
        verifyThat("AXE", NodeMatchers.isNotNull());
    }

    @Test
    public void knifePickGenerateTest() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        KnifePick kp = new KnifePick(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(kp);
        sleep();
        assertTrue(getGame().getCurrentRoom().getGameObjects().contains(kp));
    }

    @Test
    public void gunPickGenerateTest() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        GunPick gp = new GunPick(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(gp);
        sleep();
        assertTrue(getGame().getCurrentRoom().getGameObjects().contains(gp));
    }

    @Test
    public void addInventory() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        sleep();
        KnifePick kp = new KnifePick(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(kp);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(kp.getHitbox().getLocation());
        sleep();
        press(KeyCode.I);
        verifyThat("INVENTORY", NodeMatchers.isNotNull());
        verifyThat("KNIFE", NodeMatchers.isNotNull());

    }

    @Test
    public void inventoryPauseMovement() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        Point start = new Point(getGame().getPlayer().getHitbox().getLocation());
        tap(KeyCode.I);
        tap(KeyCode.W);
        tap(KeyCode.A);
        tap(KeyCode.S);
        tap(KeyCode.D);
        assertEquals(start, getGame().getPlayer().getHitbox().getLocation());
    }

    @Test
    public void wallCollision() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        sleep();
        getGame().getPlayer().getHitbox().moveTo(2.0f, 2.0f);
        Point p = new Point(getGame().getPlayer().getHitbox().getLocation());
        tap(KeyCode.W);
        tap(KeyCode.A);
        assertEquals(p, getGame().getPlayer().getHitbox().getLocation());
    }

    @Test
    public void changeWeapon() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        sleep();
        AxePick ap = new AxePick(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(ap);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(ap.getHitbox().getLocation());
        sleep();
        tap(KeyCode.I);
        clickOn("AXE");
        assertTrue(getGame().getCurrentRoom().getPlayer().getWeapon().getWeaponType() == 2);

    }

    @Test
    public void addAndUseInvincibility() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        sleep();
        InvincibilityPickup ip =
                new InvincibilityPickup(getGame().getCurrentRoom(), new Point(15, 15));
        getGame().getCurrentRoom().addGameObject(ip);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(ip.getHitbox().getLocation());
        sleep();
        tap(KeyCode.I);
        sleep();
        clickOn("INVINCIBILITY POWERUP");
        tap(KeyCode.I);

        int origHealth = getGame().getCurrentRoom().getPlayer().getHealth();
        for (int i = 0; i < 5; i++) {
            tap(KeyCode.S);
        }
        for (int i = 0; i < 45; i++) {
            sleep();
        }
        assertTrue(origHealth == getGame().getPlayer().getHealth());

    }

    @Test
    public void attackPickGenerateTest() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        AttackPickup ap = new AttackPickup(getGame().getCurrentRoom(), new Point(15, 15));
        getGame().getCurrentRoom().addGameObject(ap);
        sleep();
        assertTrue(getGame().getCurrentRoom().getGameObjects().contains(ap));
    }

    @Test
    public void healthPickup() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        sleep();
        HealthPickup hp = new HealthPickup(getGame().getCurrentRoom(), new Point(15, 15));
        getGame().getCurrentRoom().addGameObject(hp);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(hp.getHitbox().getLocation());
        sleep();
        getGame().getPlayer().setHealth(130);
        sleep();
        press(KeyCode.I);
        clickOn("HEALTH POTION");
        assertTrue(getGame().getCurrentRoom().getPlayer().getHealth() == 150);

    }

    @Test
    public void hardChallengeRoomGenerate() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        HardChallengeStart hcs = new HardChallengeStart(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(hcs);
        sleep();
        assertTrue(getGame().getCurrentRoom().getGameObjects().contains(hcs));
    }

    @Test
    public void hardChallengeStart() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        sleep();
        HardChallengeStart hcs = new HardChallengeStart(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(hcs);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(hcs.getHitbox().getLocation());
        sleep();
        assertTrue(hcs.getChallengeStarted());
    }

    @Test
    public void hardChallengeRoomLocked() {
        boolean exit = true;
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        HardChallengeStart hcs = new HardChallengeStart(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(hcs);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(hcs.getHitbox().getLocation());
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof RoomExit) {
                exit = go.isActive();
            }
        }
        assertFalse(exit);
    }

    @Test
    public void callengeRoomUnlock() {
        boolean exit = false;
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        ChallengeStart cs = new ChallengeStart(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(cs);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(cs.getHitbox().getLocation());
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Monster) {
                ((Monster) go).die();
            }
            if (go instanceof PoisonMonster) {
                ((PoisonMonster) go).die();
            }
            if (go instanceof FireMonster) {
                ((FireMonster) go).die();
            }
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof RoomExit) {
                exit = go.isActive();
            }
        }
        assertTrue(exit);
    }

    @Test
    public void challengeRoomRewardExist() {
        int rewardAmount = 0;
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        ChallengeStart cs = new ChallengeStart(getGame().getCurrentRoom());
        getGame().getCurrentRoom().addGameObject(cs);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(cs.getHitbox().getLocation());
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Monster) {
                ((Monster) go).die();
            }
            if (go instanceof PoisonMonster) {
                ((PoisonMonster) go).die();
            }
            if (go instanceof FireMonster) {
                ((FireMonster) go).die();
            }
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof HealthPickup
                    || go instanceof InvincibilityPickup || go instanceof AttackPickup) {
                rewardAmount++;
            }
        }
        assertTrue(rewardAmount != 0);
    }

    @Test
    public void restart() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        getGame().getPlayer().die();
        clickOn("Restart");
        try {
            Thread.sleep(1100);
        } catch (InterruptedException ignored) { }
        verifyThat("Welcome to the Dungeon Crawler game!", NodeMatchers.isNotNull());
        verifyThat("Start Game", NodeMatchers.isNotNull());
    }

    @Test
    public void gameStats() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        getGame().getPlayer().die();
        verifyThat("Game Statistics", NodeMatchers.isNotNull());
        verifyThat("Monsters Killed: 0", NodeMatchers.isNotNull());
        verifyThat("Potions & PowerUps Used: 0", NodeMatchers.isNotNull());
        verifyThat("Damage Dealt: 0", NodeMatchers.isNotNull());
    }

    @Test
    public void checkMonstersDead() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Axe");
        clickOn("Next");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.W);
        }
        sleep();
        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Monster) {
                ((Monster) go).die();
            }
            if (go instanceof PoisonMonster) {
                ((PoisonMonster) go).die();
            }
            if (go instanceof FireMonster) {
                ((FireMonster) go).die();
            }
        }
        sleep();
        getGame().getPlayer().die();
        verifyThat("Game Statistics", NodeMatchers.isNotNull());
        verifyThat("Monsters Killed: 3", NodeMatchers.isNotNull());
    }

    @Test
    public void checkPowerUpsUsed() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        sleep();
        HealthPickup hp = new HealthPickup(getGame().getCurrentRoom(), new Point(15, 15));
        AttackPickup ap = new AttackPickup(getGame().getCurrentRoom(), new Point(15, 15));
        InvincibilityPickup ip =
                new InvincibilityPickup(getGame().getCurrentRoom(), new Point(15, 15));
        getGame().getCurrentRoom().addGameObject(hp);
        getGame().getCurrentRoom().addGameObject(ap);
        getGame().getCurrentRoom().addGameObject(ip);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(hp.getHitbox().getLocation());
        sleep();
        press(KeyCode.I);
        clickOn("HEALTH POTION");
        clickOn("ATTACK POTION");
        clickOn("INVINCIBILITY POWERUP");
        tap(KeyCode.I);
        getGame().getPlayer().die();
        verifyThat("Game Statistics", NodeMatchers.isNotNull());
        verifyThat("Potions & PowerUps Used: 3", NodeMatchers.isNotNull());
    }

    @Test
    public void checkDamageDealt() {
        clickOn("Start Game");
        write("MyName");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        sleep();
        InvincibilityPickup ip =
                new InvincibilityPickup(getGame().getCurrentRoom(), new Point(15, 15));
        getGame().getCurrentRoom().addGameObject(ip);
        sleep();
        getGame().getPlayer().getHitbox().moveTo(ip.getHitbox().getLocation());
        sleep();
        tap(KeyCode.I);
        sleep();
        clickOn("INVINCIBILITY POWERUP");
        tap(KeyCode.I);
        for (int i = 0; i < 5; i++) {
            tap(KeyCode.S);
        }

        for (GameObject go : new HashSet<>(getGame().getCurrentRoom().getGameObjects())) {
            if (go instanceof Monster) {
                getGame().getPlayer().getHitbox().moveTo(((Monster) go).getHitbox().getLocation());
                sleep();
                press(MouseButton.PRIMARY);
                sleep();
            }
        }
        getGame().getPlayer().die();
        verifyThat("Game Statistics", NodeMatchers.isNotNull());
        verifyThat("Damage Dealt: 10", NodeMatchers.isNotNull());
    }

    private Game getGame() {
        return main.getSceneManager().getGame();
    }
    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) { }
    }
    private void tap(KeyCode key) {
        press(key).release(key);
    }
}
