package sample;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;


public class Player extends Main {

    private Image playerImage = new Image("assets/player.png");
    private int health = 100;
    private int strength = 20;
    private int numberOfSwings = 0;
    private int numberOfMisses = 0;
    private boolean invincible = false;
    private int invincibleCounter = 0;
    private int posX;
    private int posY;
    private int playerWidth = 80;
    private int playerHeight = 110;
    private int numberOfGoblinKills = 0;
    private int weapon = 0;
    private String currentWeapon = "Fist";
    private int speed = 10;
    private int speedCounter = 0;
    private int powerUpCounter = 0;


    public Player(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    // Handles player movement and allows player to move through the screen. Exit one side and appear on the other.
    // Also handles which weapon the player has active.
    public void  playerMove(KeyCode keyCode) {
        switch (keyCode) {
            case UP:
                posY -= speed;
                if(posY < -100) posY = 770;
                break;
            case DOWN:
                posY += speed;
                if(posY > 770) posY = -100;
                break;
            case LEFT:
                posX -= speed;
                if(posX < -50) posX = 780;
                break;
            case RIGHT:
                posX += speed;
                if(posX > 780) posX = -50;
                break;
            case NUMPAD1:
            case DIGIT1:
                weapon = 0;
                currentWeapon = "Fist";
                break;
            case NUMPAD2:
            case DIGIT2:
                weapon = 7;
                currentWeapon = "Sword";
                break;
            case NUMPAD3:
            case DIGIT3:
                weapon = 9;
                currentWeapon = "Axe";
                break;
            default:
                break;
        }
    }

    // Decrements player enhancements as needed. This process is controlled with every screen refresh.
    public void decrementPlayerEnhancements() {
        if(speedCounter > 0) speedCounter--; else speed = 10;
        if(powerUpCounter > 0) powerUpCounter--; else strength = 20;
        if(invincibleCounter > 0) invincibleCounter--; else invincible = false;
    }

    // Checks both goblin drops and treasure chest drops and applies enhancements as needed.
    // Enhancements come with a counter, the enhancement is set and the  counter is set. With each
    // screen refresh the counter is decremented. When the counter reaches zero the the enhancements will be removed. see decrementPlayerEnhancements()
    // This method only sets the enhancements.
    public void dropPickUps(ArrayList<GoblinDrop> itemList, ArrayList<TreasureChest> treasureList) {
        itemList.forEach(e -> {
            if(distance(e.getLocX(), e.getLocY(), posX, posY) < 50) {
                e.setActive(false);
                switch (e.getDropItem()){
                    case 1:
                        health += 50;
                        if(health > 100) health = 100;
                        break;
                    case 2:
                        speed = 20;
                        speedCounter = 25;
                        break;
                    case 3:
                        if(powerUpCounter == 0) strength += 30;
                        powerUpCounter = 50;
                        break;
                }
            }
        });

        treasureList.forEach(e -> {
            if(distance(e.getPosX(), e.getPosY(), posX, posY) < 50) {
                e.setActive(false);
                // 4=Full Health, 5=Power Up, 6=Invincible
                switch(e.getItem()) {
                    case 4:
                        health = 100;
                        break;
                    case 5:
                        if(powerUpCounter == 0) strength += 50;
                        powerUpCounter = 50;
                        break;
                    case 6:
                        invincible = true;
                        invincibleCounter = 50;
                }
            }
        });
    }

    // Used to calculate distance between two objects.
    public int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    // Returns a string explaining the weapons options.
    public String getWeaponList() {
        return "\t\t\t*** Weapons List ***\n" +
                "1) Fist -  Increases strength by 0 and reduces health by 0 for missed swings\n" +
                "2) Sword - Increases strength by 7 but reduces health by 7 for all missed swings\n" +
                "3) Axe - Increases strength by 9 but reduces health by 9 for all missed swings\n";
    }

    public void generateHealth() {
        if(health < 100) health++;
    }


    public Image getPlayerImage() {
        return playerImage;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public int getPlayerHeight() {
        return playerHeight;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public int getNumberOfSwings() {
        return numberOfSwings;
    }

    public void setNumberOfSwings(int numberOfSwings) {
        this.numberOfSwings = numberOfSwings;
    }

    public int getNumberOfMisses() {
        return numberOfMisses;
    }

    public void setNumberOfMisses(int numberOfMisses) {
        this.numberOfMisses = numberOfMisses;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public int getNumberOfGoblinKills() {
        return numberOfGoblinKills;
    }

    public void setNumberOfGoblinKills(int numberOfGoblinKills) {
        this.numberOfGoblinKills = numberOfGoblinKills;
    }

    public int getWeapon() {
        return weapon;
    }

    public String getCurrentWeapon() {
        return currentWeapon;
    }
}
