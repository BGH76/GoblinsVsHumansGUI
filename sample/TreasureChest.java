package sample;

import javafx.scene.image.Image;

public class TreasureChest {

    private int posX;
    private int posY;
    private int item;
    private boolean active;
    private Image img = new Image("assets/treasure.png");

    public TreasureChest(int posX, int posY, int item, boolean active) {
        this.posX = posX;
        this.posY = posY;
        this.item = item;
        this.active = active;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getItem() {
        return item;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Image getImg() {
        return img;
    }
}
