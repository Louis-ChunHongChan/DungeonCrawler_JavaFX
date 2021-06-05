package sample;

import gameobjects.Player;

public interface IMonster {

    int getHealth();

    void punch(Player player);
    void kick(Player player);
    void updateHealth(int delta);
    void giveAttackBonus(int frames);
    void die();

}
