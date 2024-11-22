package se233.finalProjectII.model;

import javafx.scene.image.Image;
import se233.finalProjectII.Launcher;
import se233.finalProjectII.view.Space;

import java.util.Objects;

public class Enemy extends Sprite {
    public static final int SIZE = 64;
    private int direction, curStep, delay, delayCounter, maxMove;
    private final boolean isBoss;
    private boolean justBorn;
    public Enemy(int x, int y, int imgNo, int delay) {
        super(x, y, 1);
        this.spriteSheet = new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("assets/Mobile_-_Arkanoid_Vs_Space_Invaders_-_Invaders.png")));
        this.sprite = new AnimateSprite(spriteSheet, 5, 2, 1, 5 + (imgNo * 164), 0, 82, 80);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.delay =delay;
        isBoss = false;
        maxMove = (Space.WIDTH - (SIZE * 8)) / 20;
        delayCounter = 0;
        curStep = 0;
        justBorn = true;
        isDead = false;

        this.getChildren().addAll(sprite);
    }
    public Enemy(int x, int y, boolean isBoss) {
        super(x, y,  1);
        if(isBoss){
            super.setLives(99);
            maxMove = (Space.WIDTH - Boss.SIZE + x) / 20;
        }
        this.isBoss = isBoss;
        delay = 5;
        delayCounter = 0;
        curStep = 0;
        justBorn = true;
        isDead = false;
    }
    public void move(){
        if(delayCounter == delay){
            if (curStep == 0){
                direction = 1;
                if(!justBorn) {
                    y += 25;
                } else {
                    justBorn = false;
                }
            } else if (curStep >= maxMove || ((x + SIZE >= Space.WIDTH) && direction > 0 )){
                direction = -1;
                y += 25;
                curStep = maxMove;
            } else {
                x = x + direction * 20;
            }
            delayCounter = 0;
            curStep += direction;

        } else {
            delayCounter++;
        }

    }
    public void repaint(){
        setTranslateX(x);
        setTranslateY(y);
    }
    public boolean isShoot(){
        return Math.random() < .0025;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public void killThis() {
        isDead = true;
        sprite.setAnimation(1, 1, 5 + (3 * 164), 0);
    }

    public int getDelay() {
        return delay;
    }

    public void countDownDelay() {
        this.delay --;
    }
}
