package se233.finalProjectII.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.finalProjectII.Launcher;
import se233.finalProjectII.controller.Initializer;
import se233.finalProjectII.model.Bullet;
import se233.finalProjectII.model.Enemy;
import se233.finalProjectII.model.Keys;
import se233.finalProjectII.model.Spaceship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Space extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    public static final int BorderLine = HEIGHT - Spaceship.HEIGHT - 100;
    private Image spaceImg;
    private Keys keys;
    private Score score;
    private Lives lives;
    private Spaceship playerShip;
    private List<Enemy> enemies;
    private List<Bullet> bullets;

    public Space(){
        keys  = new Keys();
        spaceImg = new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("assets/upImage.png")));
        ImageView backgroundImg = new ImageView(spaceImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);
        playerShip = new Spaceship(30, (Space.HEIGHT - Spaceship.HEIGHT -100), KeyCode.A, KeyCode.D);
        enemies = Initializer.initialize_enemies();
        bullets = new ArrayList<>();
        score = new Score(Space.HEIGHT - 60);
        lives = new Lives(20, Space.HEIGHT - 60);


        getChildren().addAll(backgroundImg, playerShip, score, lives);
        for (Enemy enemy: enemies){
            getChildren().add(enemy);
        }
        for (Bullet bullet: bullets){
            getChildren().add(bullet);
        }
    }
    public Keys getKeys() {
        return keys;
    }

    public Spaceship getPlayerShip() {
        return playerShip;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Score getScore() {
        return score;
    }

    public Lives getLives() {
        return lives;
    }
}
