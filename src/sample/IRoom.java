package sample;

import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.RoomExit;

import java.util.List;
import java.util.Set;

public interface IRoom {

    int getNumExits();
    List<RoomExit> getExits();

    void drawRoom(DrawingAdapter adapter);
    void enterRoom(Player player, RoomExit entrance);
    void update();
    void onRoomDestroy();

    void addGameObject(GameObject go);
    void removeGameObject(GameObject go);
    Set<GameObject> getGameObjects();
    Player getPlayer();

    int hashCode();
    boolean equals(Object other);

    int getRoomType();
}
