package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;


public class Main extends Application {
    private int counter = 0;
    private int WIDTH = 800;
    private int HEIGHT = 800;
    private boolean activeBattle = false;
    private boolean gameOver = false;
    private Goblin currentGoblin;
    private Controller controller = new Controller();
    private String[] landImages = {"assets/trees1.png", "assets/trees2.png", "assets/trees3.png",
            "assets/trees4.png", "assets/pond.png"};
    private GraphicsContext gc;
    private Timeline timeline;
    private ArrayList<Land> landItems;
    private ArrayList<Goblin> armyOfGoblins;
    private ArrayList<GoblinDrop> goblinDropList;
    private ArrayList<TreasureChest> treasureList;
    private Player player;

    @Override
    public void start(Stage primaryStage) {


        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER && gameOver) {
                setUP();
            }
            if(e.getCode() == KeyCode.E) {
                Platform.exit();
            }
            if(!gameOver) {
                player.playerMove(e.getCode());
                timeline.play();
            }
        });

        setUP();
        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.show();

        timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setUP() {
        gameOver = false;
        landItems = new ArrayList<>();
        armyOfGoblins = new ArrayList<>();
        goblinDropList = new ArrayList<>();
        treasureList = new ArrayList<>();
        landItems.add(new Land(new Image(landImages[0]), 465, 50, 125, 150));
        landItems.add(new Land(new Image(landImages[1]), 580, 75, 125 , 150));
        landItems.add(new Land(new Image(landImages[2]), 390, 150, 150, 200));
        landItems.add(new Land(new Image(landImages[3]), 550, 175, 150, 200));
        landItems.add(new Land(new Image(landImages[4]), 100, 400, 226, 400));
        player = new Player(400, 600);
    }

    // Main game loop
    private void run(GraphicsContext gc) {
        if(!activeBattle) {
            // Background
            controller.displayBackground(gc, WIDTH, HEIGHT, counter);
            // Land Items
            controller.drawLandItems(gc, landItems);
            // Add Additional Goblins
            controller.addAdditionalGoblins(armyOfGoblins);
            // Draw Player
            gc.drawImage(player.getPlayerImage(), player.getPosX(), player.getPosY());
            // Draw Goblins
            armyOfGoblins.forEach(e -> gc.drawImage(e.getGoblinImg(), e.getPosX(), e.getPosY()));
            // Goblin Drops
            controller.goblinDrops(goblinDropList, armyOfGoblins);
            // Draw goblin drops
            goblinDropList.forEach(e -> gc.drawImage(e.getImg(), e.getLocX(), e.getLocY()));
            // Draw treasure chest
            treasureList.forEach(e -> gc.drawImage(e.getImg(), e.getPosX(), e.getPosY()));
            // Goblins Move
            armyOfGoblins.forEach(e -> e.goblinMove(player.getPosX(), player.getPosY(), player.getPlayerWidth(), player.getPlayerHeight()));
            player.decrementPlayerEnhancements();
            // Goblin Drop Pickups and treasure pickups
            player.dropPickUps(goblinDropList, treasureList);
            // Remove drop from list if picked up
            goblinDropList.removeIf(e -> !e.isActive());
            treasureList.removeIf(e -> !e.isActive());
            // Display stats in lower left
            controller.displayStatsInLowerLeft(gc, player);
            // Check For Collision Between Player And Goblin
            armyOfGoblins.forEach(g -> {
                if(g.getPosX() == player.getPosX() && g.getPosY() == player.getPosY() && !activeBattle) {
                    currentGoblin = g;
                    activeBattle = true;
                }
            });
            player.generateHealth();
        }
        // Battle With Goblin
            if(activeBattle) {
                // Display Battle Stats
                controller.displayBattle(gc, player, currentGoblin);
                // 1=Player, 2=Goblin
                int whoIsAttacking = (int) (Math.random() * 2 + 1);

                // 1 in 3 chance of attack missing
                int attackerMisses = (int) (Math.random() * 3 + 1);
                if(attackerMisses == 2) { // 2 represents number to activate attack miss
                    if(whoIsAttacking == 1 && !player.isInvincible()) {
                        player.setHealth(player.getHealth() - player.getWeapon());
                        player.setNumberOfMisses(player.getNumberOfMisses() + 1);
                    } else {
                        currentGoblin.setNumberOfMisses(currentGoblin.getNumberOfMisses() + 1);
                    }
                }
                // If AttackerMisses is != 2 attackers hits
                else {
                    // Player attacking
                    if(whoIsAttacking == 1) {
                        currentGoblin.setHealth(currentGoblin.getHealth() - (player.getStrength() + player.getWeapon()));
                        player.setNumberOfSwings(player.getNumberOfSwings() + 1);
                    }
                    // Goblin attacking
                    if(whoIsAttacking == 2 && !player.isInvincible()) {
                        player.setHealth(player.getHealth() - currentGoblin.getStrength());
                        currentGoblin.setNumberOfSwings(currentGoblin.getNumberOfSwings() + 1);
                    }
                    // Check for player/goblin health < 0
                    if(player.getHealth() <= 0) {
                        controller.displayBattle(gc, player, currentGoblin);
                        controller.displayWinLoss(gc, 0);
                        gameOver = true;
                        timeline.pause();
                        controller.displayGameOver(gc, player);
                        activeBattle = false;
                    }
                    else if(currentGoblin.getHealth() <= 0) {
                        controller.displayBattle(gc, player, currentGoblin);
                        currentGoblin.setAlive(false);
                        armyOfGoblins.removeIf(x -> !x.isAlive());
                        player.setNumberOfGoblinKills(player.getNumberOfGoblinKills() + 1);
                        player.setNumberOfSwings(0);
                        player.setNumberOfMisses(0);
                        controller.displayWinLoss(gc, 1);
                        int treasureLocX = (int) (Math.random() * 600 + 100);
                        int treasureLocY = (int) (Math.random() * 600 + 100);
                        int treasureItem = (int) (Math.random() * 3 + 4);
                        treasureList.add(new TreasureChest(treasureLocX, treasureLocY, treasureItem, true));
                        // Pause game to review battle numbers
                        timeline.pause();
                        activeBattle = false;
                    }
                }
            }
            counter++;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
