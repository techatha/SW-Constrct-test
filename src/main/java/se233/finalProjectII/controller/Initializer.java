package se233.finalProjectII.controller;

import se233.finalProjectII.model.Boss;
import se233.finalProjectII.model.Enemy;
import se233.finalProjectII.view.Space;

import java.util.ArrayList;
import java.util.List;

public class Initializer {
    public static List<Enemy> initialize_enemies(){
        List<Enemy> enemyList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            enemyList.add(new Enemy(i * Enemy.SIZE, 0, 0, 5));
            enemyList.add(new Enemy(i * Enemy.SIZE, 80, 1, 5));
            enemyList.add(new Enemy(i * Enemy.SIZE, 160, 2, 5));
        }
        return enemyList;
    }
    public static  List<Enemy> initialize_boss(){
        List<Enemy> enemyList = new ArrayList<>();
        enemyList.add(new Boss(0, 0));

        return enemyList;
    }
}
