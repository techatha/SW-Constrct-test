package se233.finalProjectII.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import se233.finalProjectII.Launcher;

import java.util.Objects;

public class Lives extends Pane {
    ImageView heart;
    Label lives;
    public Lives(int x, int y){
        Image img = new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("assets/poop.png")));
        heart= new ImageView();
        heart.setImage(img);
        heart.setFitWidth(64);
        heart.setFitHeight(64);
        lives= new Label(" x3");
        setTranslateX(x);
        setTranslateY(y);
        lives.setTranslateX(64);
        heart.setTranslateY(-16);
        lives.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        lives.setTextFill(Color.web("#FFF"));

        getChildren().addAll(heart, lives);
    }
    public void setLives(int lives){
        this.lives.setText(" x" + lives);
    }
}
