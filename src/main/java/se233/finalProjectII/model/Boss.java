package se233.finalProjectII.model;

import javafx.scene.image.Image;
import se233.finalProjectII.Launcher;

import java.util.Objects;

public class Boss extends Enemy{
    public static final int SIZE = 128;
    public Boss(int x, int y){
        super(x, y, true);
        setTranslateX(x);
        setTranslateY(y);
        this.spriteSheet =  new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("assets/poop.png")));
        this.sprite = new AnimateSprite(spriteSheet, 4, 1, 1, 0, 0, (int) spriteSheet.getWidth(), (int) spriteSheet.getHeight());
        sprite.setFitWidth(SIZE);
        sprite.setFitHeight(SIZE);

        this.getChildren().addAll(sprite);
    }
    @Override
    public boolean isShoot(){
        return Math.random() < .05;
    }
    public static boolean isSpawnEnemy(){
        return Math.random() < .04;
    }
}
