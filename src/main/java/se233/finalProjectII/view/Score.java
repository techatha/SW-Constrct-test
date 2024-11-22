package se233.finalProjectII.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score extends Pane {
    Label point;
    public Score( int y){
        point= new Label("0");
        setTranslateY(y);
        relocate();
        point.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        point.setTextFill(Color.web("#FFF"));
        getChildren().addAll(point);
    }
    public void relocate(){
        int x = Space.WIDTH - (int) this.getWidth() - 20;
        setTranslateX(x);
    }

    public void setPoint(int score){
        this.point.setText("Score: " + score);
        relocate();
    }
}
