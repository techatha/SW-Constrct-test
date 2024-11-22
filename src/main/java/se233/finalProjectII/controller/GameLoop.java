package se233.finalProjectII.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import se233.finalProjectII.model.*;
import se233.finalProjectII.view.Lives;
import se233.finalProjectII.view.Score;
import se233.finalProjectII.view.Space;

import java.util.ArrayList;
import java.util.List;

public class GameLoop implements Runnable{
    private final Space space;
    private int frameRate;
    private float interval;
    private boolean running;
    private boolean  bossFight;
    public GameLoop(Space space) {
        this.space = space;
        frameRate = 10;
        interval = 1000.0f / frameRate;
        running = true;
        bossFight = false;
    }
    @Override
    public void run() {
        while (running) {
            float time = System.currentTimeMillis();
            update(space.getPlayerShip(), space.getEnemies(), space.getBullets());
            updateStatus(space.getScore(), space.getPlayerShip(), space.getLives());
            checkGameOver();
            paint();
            time = System.currentTimeMillis() - time;
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Weeeeee");
                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void update(Spaceship spaceship, List<Enemy> enemies, List<Bullet> bullets) {
        List<Bullet> bulletToAdd = new ArrayList<>();
        if(space.getKeys().isPressed(spaceship.getLeftKey())){
            spaceship.setScaleX(-1);
            spaceship.moveLeft();
        }
        if(space.getKeys().isPressed(spaceship.getRightKey())){
            spaceship.setScaleX(1);
            spaceship.moveRight();
        }
        if (spaceship.canShoot()){
            if (space.getKeys().isPressed(KeyCode.SPACE)) {
                createAndAddBullet(spaceship.getX() + Spaceship.WIDTH / 2 - Bullet.SIZE / 2, spaceship.getY(), true, bulletToAdd);
                spaceship.isShot();
            }
        }
        if (space.getKeys().isPressed(KeyCode.M)){
            bulletToAdd.add(new Bullet(spaceship.getX() + Spaceship.WIDTH / 2 - Bullet.MISSILE_SIZE / 2, spaceship.getY(), true, true));
        }
        if(space.getKeys().isPressed(spaceship.getLeftKey()) && space.getKeys().isPressed(spaceship.getRightKey())){
            spaceship.stop();
        }
        if(!space.getKeys().isPressed(spaceship.getLeftKey()) && !space.getKeys().isPressed(spaceship.getRightKey())){
            spaceship.stop();
        }
        spaceship.move();
        // Create a copy of the bullets list
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();
        List<Enemy> enemiesToAdd = new ArrayList<>();
        // Iterate over the copy and remove out-of-bounds bullets
        for (Bullet bullet : bullets) {
            bullet.move();
            if (bullet.isOutOfBounds()) {
                bullet.killThis();
            }
            for(Bullet otherBullet: bullets){
                if(bullet.getBoundsInParent().intersects(otherBullet.getBoundsInParent()) && !bullet.equals(otherBullet)){
                    if(bullet.isPlayerBullet() != otherBullet.isPlayerBullet()) {
                        if(!bullet.isMissile()) {
                            bullet.killThis();
                        }
                        if(!otherBullet.isMissile()) {
                            otherBullet.killThis();
                        }
                        break;
                    }
                }
            }
            for (Enemy enemy: enemies){
                if(!enemy.isDead()) {
                    if (bullet.getBoundsInParent().intersects(enemy.getBoundsInParent()) && bullet.isPlayerBullet()) {
                        if (!bullet.isMissile()) {
                            bullet.killThis();
                        }
                        enemy.hit();
                        if (enemy.getLives() <= 0) {
                            enemy.killThis();
                            int score = spaceship.getScore();
                            if (enemy.isBoss()) {
                                score += 1000;
                                //System.out.println("+1000 -> " + score);
                            } else {
                                score += 10;
                                //System.out.println("+10 -> " + score);
                            }
                            spaceship.setScore(score);
                        }
                    }
                }
            }
            if(bullet.getBoundsInParent().intersects(spaceship.getBoundsInParent()) && !bullet.isPlayerBullet()){
                bullet.killThis();
                System.out.println("YOU'VE BEEN HIT BY ENEMY POOP!");
                spaceship.hit();
                break;
            }
        }
        for (Enemy enemy : enemies) {
            if(!enemy.isDead()) {
                enemy.move();
                int size;
                if (enemy.isBoss()) {
                    size = Boss.SIZE;
                } else {
                    size = Enemy.SIZE;
                }
                if (enemy.isShoot()) {
                    int x = enemy.getX() + size / 2 - Bullet.SIZE / 2;
                    int y = enemy.getY() + size;
                    createAndAddBullet(x, y, false, bulletToAdd);
                    if(enemy.isBoss()) {
                        bulletToAdd.add(new Bullet(x, y, -1, false));
                        bulletToAdd.add(new Bullet(x, y, 1, false));
                    }
                }
                if (enemy.isBoss()) {
                    if (Boss.isSpawnEnemy()) {
                        enemiesToAdd.add(new Enemy((int) (Math.random() * 100) * (Space.WIDTH - Enemy.SIZE - 60) / 100, size, 0, 5));
                    }
                }
            } else if (enemy.getDelay() <= 0){
                enemiesToRemove.add(enemy);
            } else {
                enemy.countDownDelay();
            }
        }
        for(Bullet bullet: bullets){
            if(bullet.isDead()){
                bulletsToRemove.add(bullet);
            }
        }
        try {
            bullets.addAll(bulletToAdd);
            bullets.removeAll(bulletsToRemove);
            enemies.addAll(enemiesToAdd);
            enemies.removeAll(enemiesToRemove);
            Platform.runLater(() -> {
                space.getChildren().removeAll(bulletsToRemove);
                space.getChildren().removeAll(enemiesToRemove);
                space.getChildren().addAll(bulletToAdd);
                space.getChildren().addAll(enemiesToAdd);
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void checkGameOver(){
        if(space.getEnemies().isEmpty()){
            if(!bossFight){
                bossFight = true;
                List<Enemy> enemies = Initializer.initialize_boss();
                space.getEnemies().addAll(enemies);
                Platform.runLater(() -> space.getChildren().addAll(enemies));
            }else {
                running = false;
                displayGameCompletion("YOU WIN!!!", Color.BLUE);
            }
        }
        for(Enemy enemy: space.getEnemies()){
            if(enemy.getY() + Enemy.SIZE > Space.BorderLine){
                running = false;
                displayGameCompletion("Game Over!", Color.RED);
                break;
            }
        }
        if(space.getPlayerShip().getLives() <= 0){
            running = false;
            displayGameCompletion("Game Over!", Color.RED);
        }
    }
    private void displayGameCompletion(String message, Color textColor){
        Text gameCompletionText = new Text(message);
        gameCompletionText.setFont(Font.font("Arial", FontWeight.BOLD, 120));
//        Font font = Font.loadFont(getClass().getResourceAsStream("assets/PressStart2P-Regular.ttf"), 120);
//        gameCompletionText.setFont(font);
        gameCompletionText.setFill(textColor);
        gameCompletionText.setStroke(textColor.NAVAJOWHITE);
        gameCompletionText.setTranslateX((Space.WIDTH - gameCompletionText.getBoundsInParent().getWidth())/2);
        gameCompletionText.setTranslateY(Space.HEIGHT / 2);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), gameCompletionText);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), gameCompletionText);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);

        SequentialTransition sequentialTransition = new SequentialTransition(scaleTransition, fadeTransition);
        sequentialTransition.play();

        Platform.runLater(() -> space.getChildren().add(gameCompletionText));
    }
    public void paint(){
        space.getPlayerShip().repaint();
        for(Enemy enemy: space.getEnemies()){
            enemy.getSprite().tick();
            enemy.repaint();
        }
        for(Bullet bullet: space.getBullets()){
            bullet.getSprite().tick();
            bullet.repaint();
        }
    }
    public void updateStatus(Score score, Spaceship spaceship, Lives lives){
        Platform.runLater(() -> {
            score.setPoint(spaceship.getScore());
            lives.setLives(spaceship.getLives());
        });

    }
    private void createAndAddBullet(int x, int y, boolean playerBullet, List<Bullet> bullets) {
        Bullet newBullet = new Bullet(x, y, playerBullet);
        bullets.add(newBullet);
    }
}
