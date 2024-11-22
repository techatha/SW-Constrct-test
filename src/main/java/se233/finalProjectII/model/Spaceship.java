package se233.finalProjectII.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.finalProjectII.Launcher;
import se233.finalProjectII.view.Space;

import java.util.Objects;

public class Spaceship extends Sprite {
    public static final int WIDTH = 128;
    public static final int HEIGHT = 64;
    private final KeyCode leftKey;
    private final KeyCode rightKey;
    private static final Logger LOGGER = LogManager.getLogger(Spaceship.class.getName());
    //Status
    boolean isMoveLeft;
    boolean isMoveRight;
    private int score, shootingCooldown;
    public Spaceship(int x, int y, KeyCode leftKey, KeyCode rightKey){
        super(x, y, 3);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.spriteSheet = new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("assets/magicSchoolBus.png")));
        this.sprite = new AnimateSprite(spriteSheet, 4, 1, 1, 0, 0, (int) spriteSheet.getWidth(), (int) spriteSheet.getHeight());
        this.sprite.setFitWidth(WIDTH);
        this.sprite.setFitHeight(HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        shootingCooldown = 0;

        this.getChildren().addAll(sprite);
    }
    public void moveLeft(){
        isMoveLeft = true;
        isMoveRight = false;
        LOGGER.info("Player moved left");
    }
    public void moveRight(){
        isMoveLeft = false;
        isMoveRight = true;
        LOGGER.info("Player moved right");
    }
    public void checkReachGameWall(){
        if(x <= 0){
            x = 0;
        } else if (x + getWidth() >= Space.WIDTH) {
            x = (int) (Space.WIDTH - getWidth());
        }
    }
    public void stop(){
        isMoveLeft = false;
        isMoveRight = false;
        LOGGER.info("Player stopped moving");
    }
    public void move(){
        if(isMoveLeft){
            x = x - 10;
            LOGGER.info("Player moved left. New X coordinate: " + x);
        }
        if(isMoveRight){
            x = x + 10;
            LOGGER.info("Player moved right. New X coordinate: " + x);
        }
        setTranslateX(x);
    }
    public void repaint(){
        setTranslateX(x);
        setTranslateY(y);
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }
    public KeyCode getRightKey() {
        return rightKey;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public boolean canShoot(){
        if(shootingCooldown > 0){
            shootingCooldown --;
        }
        return shootingCooldown <= 0;
    }
    public void isShot(){
        shootingCooldown = 5;
    }
}
