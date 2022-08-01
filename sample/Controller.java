package sample;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;

public class Controller {

    public Controller() {

    }

    // Displays the  background using Canvas GraphicsContext
    public void displayBackground(GraphicsContext gc, int WIDTH, int HEIGHT, int counter) {
        gc.setFill(Color.rgb(140, 237, 166));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Counter: " + counter, 60, 20);
    }

    // Displays the battle stats during the battle.
    public void displayBattle(GraphicsContext gc, Player player, Goblin currentGoblin) {
        gc.clearRect(100, 50, 400, 400);
        gc.setFill(Color.rgb(140, 237, 166));
        gc.fillRect(100, 50, 400, 400);
        gc.setFont(Font.font(35));
        gc.setFill(Color.RED);
        gc.fillText("Time For Battle \n          Player    |   Goblin\nHealth:     " + player.getHealth() + " | " + currentGoblin.getHealth() + "\n" +
                "Strength: " + (player.getStrength() + player.getWeapon()) + " | " + currentGoblin.getStrength() + "\n" +
                "Hits:             " + player.getNumberOfSwings() + " | " + currentGoblin.getNumberOfSwings() + "\n" +
                "Misses:       " + player.getNumberOfMisses() + " | " + currentGoblin.getNumberOfMisses(), 100, 50);
    }

    // Displays the  game over and option to play again by pressing enter.
    public void displayGameOver(GraphicsContext gc, Player player) {
        gc.clearRect(0, 0, 800, 800);
        gc.setFill(Color.rgb(140, 237, 166));
        gc.fillRect(0, 0, 800, 800);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(35));
        gc.setFill(Color.BLUE);
        String temp = player.getNumberOfGoblinKills() == 1 ? "Goblin" : "Goblins";
        gc.fillText("Game Over\n You killed " + player.getNumberOfGoblinKills() + " " + temp + "\nPress Enter to play again, e to exit", 400, 350);
    }

    // Displays the stats in the lower left. Displays player health, strength, selected weapon and if the player is currently INVINCIBLE.
    public void displayStatsInLowerLeft(GraphicsContext gc, Player player) {
        gc.setFont(Font.font(15));
        gc.setFill(Color.RED);
        gc.fillText(player.getWeaponList(), 10, 740);
        gc.setFont(Font.font(20));
        gc.setFill(Color.RED);
        String inv = player.isInvincible() ? "INVINCIBLE" : "";
        gc.fillText("Current Weapon: " + player.getCurrentWeapon() + "\nHealth: " + player.getHealth() +
                "\nStrength: " + (player.getStrength() + player.getWeapon()) + "\n" +
                inv, 10, 655);
    }

    // Display Win Lost
    public void displayWinLoss(GraphicsContext gc, int num) {
        String temp = num == 1 ? "You Win!!!" : "You Lost!!!";
        gc.setFont(Font.font( 50));
        gc.setFill(num == 1 ? Color.GREEN : Color.RED);
        gc.fillText(temp, 200, 350);
    }

    // Draws the land items.
    public void drawLandItems(GraphicsContext gc, ArrayList<Land> landItems) {
        landItems.forEach(e -> gc.drawImage(e.getImage(), e.getPosX(), e.getPosY()));
    }

    // Adds additional goblins as needed. If army of goblins is 0 one will be created.
    // Additional goblins have a 1 in 40 change to spawn each screen refresh. Goblin health, strength, and location can vary
    // depending on random outcomes.
    public void addAdditionalGoblins(ArrayList<Goblin> armyOfGoblins) {
        if (armyOfGoblins.size() == 0) {
            armyOfGoblins.add(new Goblin(100, 20, 0, 0, true));
        } else {
            if ((int) (Math.random() * 40 + 1) == 1 && armyOfGoblins.size() < 3) {
                int[][] healthAndStrength = {{20, 2}, {50, 10}, {75, 15}, {100, 20}};
                int[][] los = {{0,0}, {700,0}, {0,700}, {700,700}};
                int num = (int) (Math.random() * (4));
                armyOfGoblins.add(new Goblin(healthAndStrength[num][0], healthAndStrength[num][1], los[num][0], los[num][1], true));
            }
        }
    }

    // If the number of goblin drops is less than 3 each goblin will be triggered to drop an item. This drop is handled
    // in the Goblin class and has a 1 in 50 chance of dropping something.
    public void goblinDrops(ArrayList<GoblinDrop> goblinDropList, ArrayList<Goblin> armyOfGoblins) {
        if(goblinDropList.size() < 3) {
            armyOfGoblins.forEach(e -> {
                GoblinDrop temp = e.goblinDrop();
                if(temp != null) goblinDropList.add(temp);
            });
        }
    }

}
