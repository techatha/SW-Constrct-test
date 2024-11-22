package se233.finalProjectII.model;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Sprite extends Pane {
    protected Image spriteSheet;
    protected AnimateSprite sprite;
    protected int x;
    protected int y;
    protected int lives, maxLives;
    protected boolean isDead;

    public Sprite(int x, int y, int lives){
        this.x = x;
        this.y = y;
        this.lives = lives;
        maxLives = lives;
    }

    public AnimateSprite getSprite() {
        return sprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
        maxLives = lives;
    }

    public void hit(){
        this.lives--;
        //System.out.println( lives +"/"+ maxLives);
    }

    public boolean isDead() {
        return isDead;
    }
    public void killThis(){
        this.isDead = true;
    }
}
