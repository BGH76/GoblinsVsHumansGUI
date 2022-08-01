package sample;

import javafx.scene.image.Image;

public class GoblinDrop {

    private int locX;
    private int locY;
    private int dropItem; // 1 = Additional Health, 2 = Speed Increase, 3 = Power Up
    private boolean active;
    private Image img = new Image("assets/package.png");

    public GoblinDrop(int locX, int locY, int dropItem, boolean active) {
        this.locX = locX;
        this.locY = locY;
        this.dropItem = dropItem;
        this.active = active;
    }

    public int getLocX() {
        return locX;
    }

    public int getLocY() {
        return locY;
    }

    public int getDropItem() {
        return dropItem;
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
