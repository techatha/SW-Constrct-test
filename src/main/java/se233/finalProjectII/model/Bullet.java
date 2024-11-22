package se233.finalProjectII.model;

import javafx.scene.image.Image;
import se233.finalProjectII.Launcher;
import se233.finalProjectII.view.Space;

import java.util.Objects;

public class Bullet extends Sprite{
    public static final int SIZE = 16;
    public static final  int MISSILE_SIZE = 64;
    private final boolean playerBullet;
    private final boolean isMissile;
    private int xAcceleration;
    public Bullet(int x, int y){
        super(x, y, 0);
        this.setTranslateX(x);
        this.setTranslateY(y);
        playerBullet = true;
        isMissile = true;
    }
    public Bullet(int x, int y, boolean playerBullet) {
        super(x, y, 0);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.spriteSheet =  new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream(bulletImg(playerBullet))));
        this.sprite = new AnimateSprite(spriteSheet, 4, 1, 1, 0, 0, (int) spriteSheet.getWidth(), (int) spriteSheet.getHeight());
        this.playerBullet = playerBullet;
        isMissile = false;
        sprite.setFitWidth(SIZE);
        sprite.setFitHeight(SIZE);

        this.getChildren().addAll(sprite);
    }
    public Bullet(int x, int y, int xAcceleration, boolean playerBullet) {
        super(x, y, 0);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.spriteSheet =  new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream(bulletImg(playerBullet))));
        this.sprite = new AnimateSprite(spriteSheet, 4, 1, 1, 0, 0, (int) spriteSheet.getWidth(), (int) spriteSheet.getHeight());
        this.playerBullet = playerBullet;
        this.xAcceleration = xAcceleration;
        isMissile = false;
        sprite.setFitWidth(SIZE);
        sprite.setFitHeight(SIZE);

        this.getChildren().addAll(sprite);
    }
    public Bullet(int x, int y, boolean playerBullet, boolean isMissile ){
        super(x, y, 0);
        this.playerBullet = playerBullet;
        this.isMissile = isMissile;
        this.spriteSheet =  new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("assets/custom_sprite_sheet.png")));
        this.sprite = new AnimateSprite(spriteSheet, 0, 3, 1, 0, 0, 751, 789);
        sprite.setFitWidth(64);
        sprite.setFitHeight(64);

        this.getChildren().addAll(sprite);
    }

    public void move(){
        if(playerBullet){
            if(isMissile){
                y -= 15;
            } else {
                y -= 5;
            }
        } else {
            y += 5;
        }
        x += xAcceleration;
        setTranslateX(x);
        setTranslateY(y);
    }
    public void repaint(){
        setTranslateX(x);
        setTranslateY(y);
    }
    public boolean isOutOfBounds(){
        return this.y < 0 || this.y > Space.HEIGHT;
    }

    public boolean isPlayerBullet() {
        return playerBullet;
    }

    public boolean isMissile() {
        return isMissile;
    }

    @Override
    public AnimateSprite getSprite() {
        return super.getSprite();
    }
    private String bulletImg(boolean playerBullet){
        if(playerBullet){
            return "assets/waterDrop.png";
        } else {
            return "assets/poop.png";
        }
    }
}
