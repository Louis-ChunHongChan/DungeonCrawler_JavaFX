package sample;

import gameobjects.*;
import geometry.Point;
import geometry.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Room implements IRoom {

    public static final int TYPE_MONSTER_ROOM = 0;
    public static final int TYPE_START_ROOM = -1;
    public static final int TYPE_END_ROOM = -2;

    private static int roomNumberCounter = 0;

    private Game game;
    private Set<GameObject> gameObjects;
    private Map<GameObject, Boolean> gameObjectDeltaMap;
    private Player player;
    private int roomNumber;
    private int roomType;
    private boolean roomComplete;

    private Room(Game game) {
        this.game = game;
        this.roomNumber = roomNumberCounter++;
        this.gameObjects = new HashSet<>();
        this.gameObjectDeltaMap = new HashMap<>();
    }

    public void removeGameObject(GameObject object) {
        gameObjectDeltaMap.put(object, Boolean.FALSE);
    }
    public void removeGameObjects(GameObject... objects) {
        for (GameObject go : objects) {
            this.removeGameObject(go);
        }
    }
    public void addGameObject(GameObject object) {
        gameObjectDeltaMap.put(object, Boolean.TRUE);
    }
    public void addGameObjects(GameObject... objects) {
        for (GameObject go : objects) {
            this.addGameObject(go);
        }
    }
    private void updateGameObjectSet() {
        while (!gameObjectDeltaMap.isEmpty()) {
            GameObject go = gameObjectDeltaMap.keySet().iterator().next();
            if (gameObjectDeltaMap.get(go).equals(Boolean.TRUE)) {
                gameObjects.add(go);
                go.onInstantiate();
            } else {
                go.onDestroy();
                gameObjects.remove(go);
            }
            gameObjectDeltaMap.remove(go);
        }
    }
    @Override
    public Set<GameObject> getGameObjects() {
        return gameObjects;
    }
    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void enterRoom(Player player, RoomExit exit) {
        this.player = player;
        player.setRoom(this);
        player.setPosition(exit.getSpawnPoint());
        exit.setActive(true);
        for (GameObject go : gameObjects) {
            go.onRoomLoad();
        }
    }
    @Override
    public void update() {
        this.updateGameObjectSet();
        for (GameObject go : gameObjects) {
            if (!go.isActive() || gameObjectDeltaMap.containsKey(go)) {
                continue;
            }
            if (go.update()) {
                this.removeGameObject(go);
            }
        }
        this.updateGameObjectSet();
        
        if (!this.roomComplete) {
            this.roomComplete = true;
            for (GameObject go : gameObjects) {
                if (go instanceof IMonster) {
                    roomComplete = false;
                    break;
                }
            }
            if (this.roomComplete) {
                for (GameObject go : gameObjects) {
                    if (go instanceof RoomExit) {
                        go.setActive(true);
                    }
                }
            }
        }

        for (GameObject go1 : gameObjects) {
            if (gameObjectDeltaMap.containsKey(go1) || !go1.isActive()) {
                continue;
            }
            for (GameObject go2 : gameObjects) {
                if (go1 == go2 || gameObjectDeltaMap.containsKey(go2) || !go2.isActive()) {
                    continue;
                }
                if (go1.getHitbox().intersects(go2.getHitbox())) {
                    boolean destroy1 = go1.onCollision(go2);
                    if (destroy1) {
                        this.removeGameObject(go1);
                    }
                    if (go2.onCollision(go1)) {
                        this.removeGameObject(go2);
                    }
                    if (destroy1) {
                        break;
                    }
                }
            }
        }
        this.updateGameObjectSet();
    }
    @Override
    public void onRoomDestroy() {
        for (GameObject go : gameObjects) {
            go.onRoomUnload();
        }
    }
    @Override
    public void drawRoom(DrawingAdapter adapter) {
        adapter.clear();

        for (GameObject go : gameObjects) {
            if (!go.isVisible()) {
                continue;
            }
            go.draw(adapter);
        }
    }

    @Override
    public int getNumExits() {
        int res = 0;
        for (GameObject go : gameObjects) {
            if (go instanceof RoomExit) {
                res++;
            }
        }
        return res;
    }
    @Override
    public List<RoomExit> getExits() {
        List<RoomExit> res = new ArrayList<>();
        for (GameObject go : gameObjects) {
            if (go instanceof RoomExit) {
                res.add((RoomExit) go);
            }
        }
        return res;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(roomNumber);
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Room)) {
            return false;
        }
        return this.roomNumber == ((Room) other).roomNumber;
    }

    public static Room createStartRoom(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 13, 1)),
                new Wall(r, new Rectangle(18, 1, 13, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 5)),
                new Wall(r, new Rectangle(30, 2, 1, 5)),
                new Wall(r, new Rectangle(1, 11, 1, 6)),
                new Wall(r, new Rectangle(30, 11, 1, 6)),
                new Wall(r, new Rectangle(2, 16, 12, 1)),
                new Wall(r, new Rectangle(18, 16, 12, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(14, 0, 4, 1), new Point(16, 2)),
                new RoomExit(r, 1, new Rectangle(14, 17, 4, 1), new Point(16, 15)),
                new RoomExit(r, 2, new Rectangle(0, 7, 1, 4), new Point(2, 8)),
                new RoomExit(r, 3, new Rectangle(31, 7, 1, 4), new Point(29, 8))
        );
        r.updateGameObjectSet();
        r.roomType = TYPE_START_ROOM;
        return r;
    }

    public static Room drawRoom(Game game, int number, boolean makeChallenge) {
        Room r;
        if (number == 1) {
            r = draw1(game);
        } else if (number == 2) {
            r = draw2(game);
        } else if (number == 3) {
            r = draw3(game);
        } else if (number == 4) {
            r = draw4(game);
        } else if (number == 5) {
            r = draw5(game);
        } else if (number == 6) {
            r = draw6(game);
        } else if (number == 7) {
            r = draw7(game);
        } else if (number == 8) {
            r = draw8(game);
        } else {
            throw new IllegalArgumentException("Invalid room number");
        }
        if (makeChallenge) {
            r.addGameObject(new ChallengeStart(r));
        } else {
            addMonsters(r);
        }
        r.updateGameObjectSet();
        return r;
    }

    public static Room draw1(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 22, 1)),
                new Wall(r, new Rectangle(27, 1, 4, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 1)),
                new Wall(r, new Rectangle(30, 2, 1, 9)),
                new Wall(r, new Rectangle(1, 7, 1, 10)),
                new Wall(r, new Rectangle(30, 15, 1, 2)),
                new Wall(r, new Rectangle(2, 16, 4, 1)),
                new Wall(r, new Rectangle(10, 16, 20, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(6, 17, 4, 1), new Point(8, 15)),
                new RoomExit(r, 1, new Rectangle(23, 0, 4, 1), new Point(25, 2)),
                new RoomExit(r, 2, new Rectangle(0, 3, 1, 4), new Point(2, 5)),
                new RoomExit(r, 3, new Rectangle(31, 11, 1, 4), new Point(29, 13))
        );
        r.roomType = 1;
        addKnife(r);
        return r;
    }

    public static Room draw2(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 30, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 4)),
                new Wall(r, new Rectangle(30, 2, 1, 15)),
                new Wall(r, new Rectangle(2, 5, 8, 1)),
                new Wall(r, new Rectangle(1, 10, 9, 1)),
                new Wall(r, new Rectangle(1, 11, 1, 6)),
                new Wall(r, new Rectangle(2, 16, 28, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(0, 6, 1, 4), new Point(2, 8))
        );
        r.roomType = 2;
        addAxe(r);
        return r;
    }

    public static Room draw3(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 30, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 5)),
                new Wall(r, new Rectangle(30, 2, 1, 5)),
                new Wall(r, new Rectangle(1, 11, 1, 6)),
                new Wall(r, new Rectangle(30, 11, 1, 6)),
                new Wall(r, new Rectangle(2, 16, 28, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(0, 7, 1, 4), new Point(2, 9)),
                new RoomExit(r, 1, new Rectangle(31, 7, 1, 4), new Point(29, 9))
        );
        r.roomType = 3;
        return r;
    }

    public static Room draw4(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 5, 1)),
                new Wall(r, new Rectangle(10, 1, 21, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 15)),
                new Wall(r, new Rectangle(5, 2, 1, 5)),
                new Wall(r, new Rectangle(10, 2, 1, 5)),
                new Wall(r, new Rectangle(30, 2, 1, 7)),
                new Wall(r, new Rectangle(22, 8, 8, 1)),
                new Wall(r, new Rectangle(22, 13, 9, 1)),
                new Wall(r, new Rectangle(30, 14, 1, 3)),
                new Wall(r, new Rectangle(2, 16, 28, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(6, 0, 4, 1), new Point(8, 2)),
                new RoomExit(r, 1, new Rectangle(31, 9, 1, 4), new Point(29, 11))
        );
        r.roomType = 4;
        addGun(r);
        return r;
    }

    public static Room draw5(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 23, 1)),
                new Wall(r, new Rectangle(28, 1, 3, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 15)),
                new Wall(r, new Rectangle(30, 2, 1, 15)),
                new Wall(r, new Rectangle(2, 16, 2, 1)),
                new Wall(r, new Rectangle(8, 16, 16, 1)),
                new Wall(r, new Rectangle(28, 16, 2, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(24, 0, 4, 1), new Point(26, 2)),
                new RoomExit(r, 1, new Rectangle(4, 17, 4, 1), new Point(6, 15)),
                new RoomExit(r, 2, new Rectangle(24, 17, 4, 1), new Point(26, 15))
        );
        r.roomType = 5;
        r.addGameObject(new HardChallengeStart(r));
        return r;
    }

    public static Room draw6(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 3, 1)),
                new Wall(r, new Rectangle(8, 1, 16, 1)),
                new Wall(r, new Rectangle(28, 1, 3, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 15)),
                new Wall(r, new Rectangle(30, 2, 1, 15)),
                new Wall(r, new Rectangle(2, 16, 14, 1)),
                new Wall(r, new Rectangle(20, 16, 10, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(4, 0, 4, 1), new Point(6, 2)),
                new RoomExit(r, 1, new Rectangle(24, 0, 4, 1), new Point(26, 2)),
                new RoomExit(r, 2, new Rectangle(16, 17, 4, 1), new Point(18, 15))
        );
        r.roomType = 6;
        return r;
    }

    public static Room draw7(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 13, 1)),
                new Wall(r, new Rectangle(20, 1, 11, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 7)),
                new Wall(r, new Rectangle(30, 2, 1, 7)),
                new Wall(r, new Rectangle(26, 8, 4, 1)),
                new Wall(r, new Rectangle(1, 13, 1, 4)),
                new Wall(r, new Rectangle(26, 13, 5, 1)),
                new Wall(r, new Rectangle(30, 14, 1, 3)),
                new Wall(r, new Rectangle(2, 16, 28, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(31, 9, 1, 4), new Point(29, 11)),
                new RoomExit(r, 1, new Rectangle(0, 9, 1, 4), new Point(2, 11)),
                new RoomExit(r, 2, new Rectangle(14, 0, 6, 1), new Point(16, 2))
        );
        r.roomType = 7;
        return r;
    }

    public static Room draw8(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
               new Wall(r, new Rectangle(1, 1, 23, 1)),
               new Wall(r, new Rectangle(28, 1, 3, 1)),
               new Wall(r, new Rectangle(1, 2, 1, 15)),
               new Wall(r, new Rectangle(30, 2, 1, 2)),
               new Wall(r, new Rectangle(30, 8, 1, 9)),
               new Wall(r, new Rectangle(2, 16, 2, 1)),
               new Wall(r, new Rectangle(8, 16, 22, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(24, 0, 4, 1), new Point(26, 2)),
                new RoomExit(r, 1, new Rectangle(4, 17, 4, 1), new Point(6, 15)),
                new RoomExit(r, 2, new Rectangle(31, 4, 1, 4), new Point(29, 6))
        );
        r.roomType = 8;
        return r;
    }

    public static Room createEndRoom(Game game) {
        Room r = new Room(game);
        r.addGameObjects(
                new Wall(r, new Rectangle(1, 1, 13, 1)),
                new Wall(r, new Rectangle(18, 1, 13, 1)),
                new Wall(r, new Rectangle(1, 2, 1, 15)),
                new Wall(r, new Rectangle(30, 2, 1, 15)),
                new Wall(r, new Rectangle(2, 16, 28, 1))
        );
        r.addGameObjects(
                new RoomExit(r, 0, new Rectangle(14, 0, 4, 1), new Point(16, 2))
        );
        r.addGameObject(new FinalMonster(r));
        r.updateGameObjectSet();
        r.roomType = TYPE_END_ROOM;
        return r;
    }

    private static void addMonsters(Room r) {
        r.addGameObject(new FireMonster(r));
        r.addGameObject(new PoisonMonster(r));
        r.addGameObject(new Monster(r));
    }

    private static void addAxe(Room r) {
        r.addGameObject(new AxePick(r));
    }

    private static void addKnife(Room r) {
        r.addGameObject(new KnifePick(r));
    }

    private static void addGun(Room r) {
        r.addGameObject(new GunPick(r));
    }

    public int getRoomType() {
        return roomType;
    }

}
