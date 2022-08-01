package sample;

import javafx.scene.image.Image;

public class Goblin {

    private int health;
    private int strength;
    private int numberOfSwings;
    private int numberOfMisses;
    private int posX;
    private int posY;
    private boolean alive;
    private int[] dropItems = {1, 2, 3}; //1 = Additional Health, 2 = Speed Increase, 3 = Power Up
    private Image goblinImg = new Image("assets/goblin.png");
    private int goblinWidth = 80;
    private int goblinHeight = 110;

    public Goblin(int health, int strength, int posX, int posY, boolean alive) {
        this.health = health;
        this.strength = strength;
        this.posX = posX;
        this.posY = posY;
        this.alive = alive;
    }

    // Goblins moves are automatic. Goblins will move based on the players position. Movements include up, down, left, right and diagonal.
    // Based on player x, y locations the goblins x, y will be set to move one step closer. The goblin is then re-drawn in the main and moves
    // one step closer.
    public void goblinMove(int hummanLocX, int humanLocY, int humanW, int humanH) {
        if((posX + goblinWidth) == (hummanLocX + humanW) && (posY + goblinHeight) == (humanLocY + humanH)) return;
        // Down
        if((posX + goblinWidth) == (hummanLocX + humanW) && (posY + goblinHeight) > (humanLocY + humanH)) {
            posY -= 10;
        }
        // UP
        else if((posX + goblinWidth) == (hummanLocX + humanW) && (posY + goblinHeight) < (humanLocY + humanH)) {
            posY += 10;
        }
        // Right
        else if((posX + goblinWidth) < (hummanLocX + humanW) && (posY + goblinHeight) == (humanLocY + humanH)) {
            posX += 10;
        }
        // Left
        else if((posX + goblinWidth) > (hummanLocX + humanW) && (posY + goblinHeight) == (humanLocY + humanH)) {
            posX -= 10;
        }
        // Right Down
        else if((posX + goblinWidth) < (hummanLocX + humanW) && (posY + goblinHeight) < (humanLocY + humanH)) {
            posX += 10; posY += 10;
        }
        // Right Up
        else if((posX + goblinWidth) < (hummanLocX + humanW) && (posY + goblinHeight) > (humanLocY + humanH)) {
            posX += 10; posY -= 10;
        }
        // Left Up
        else if((posX + goblinWidth) > (hummanLocX + humanW) && (posY + goblinHeight) < (humanLocY + humanH)) {
            posX -= 10; posY += 10;
        }
        // Left Down
        else if((posX + goblinWidth) > (hummanLocX + humanW) && (posY + goblinHeight) > (humanLocY + humanH)) {
            posX -= 10; posY -= 10;
        }
    }

    // With each screen refresh, each goblin has a 1 in 50 chance of dropping an item.
    // This object is droped at the goblins current x, y locations and remains until the player picks it up.
    public GoblinDrop goblinDrop() {
        int temp = (int) (Math.random() * 50 + 1);
        if(temp == 5) {
            int dropRandom = (int) (Math.random() * dropItems.length);
            int item = dropItems[dropRandom];
            GoblinDrop goblinDrop = new GoblinDrop(posX, posY, item, true);
            return goblinDrop;
        }
        return null;
    }


    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getNumberOfSwings() {
        return numberOfSwings;
    }

    public int getNumberOfMisses() {
        return numberOfMisses;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isAlive() {
        return alive;
    }

    public Image getGoblinImg() {
        return goblinImg;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setNumberOfSwings(int numberOfSwings) {
        this.numberOfSwings = numberOfSwings;
    }

    public void setNumberOfMisses(int numberOfMisses) {
        this.numberOfMisses = numberOfMisses;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
