package sample;

import javafx.scene.image.Image;

public class Land {
    private Image image;
    private int posX;
    private int posY;
    private int itemHeight;
    private int itemWidth;

    public Land(Image image, int posX, int posY, int itemH, int itemW) {
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.itemHeight = itemH;
        this.itemWidth = itemW;
    }

    public Image getImage() {
        return image;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
