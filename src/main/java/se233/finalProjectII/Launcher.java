package se233.finalProjectII;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se233.finalProjectII.controller.GameLoop;
import se233.finalProjectII.view.Space;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Space space = new Space();
        GameLoop gameLoop = new GameLoop(space);
        Scene scene = new Scene(space, Space.WIDTH, Space.HEIGHT);
        scene.setOnKeyPressed(keyEvent -> space.getKeys().add(keyEvent.getCode()));

        scene.setOnKeyReleased(keyEvent -> space.getKeys().remove(keyEvent.getCode()));
        stage.setTitle("Space Invaders se233 Final_Project II");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        (new Thread(gameLoop)).start();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
