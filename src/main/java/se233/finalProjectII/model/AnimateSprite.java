package se233.finalProjectII.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimateSprite extends ImageView {
    int count, intiCount, columns, rows, offsetX, offsetY, width, height, curIndex, curColumnIndex = 0, curRowIndex = 0;
    public AnimateSprite(Image img, int count, int columns, int rows, int offsetX, int offsetY, int width, int height){
        this.setImage(img);
        this.count = count;
        this.intiCount = count;
        this.columns = columns;
        this.rows = rows;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }
    public void tick(){
        if(count ==0) {
            curColumnIndex = curIndex % columns;
            curRowIndex = curIndex / columns;
            curIndex = (curIndex + 1) % (columns * rows);
            interpolate();
            count = intiCount;
        } else {
            count --;
        }
    }
    protected void interpolate(){
        final int x = curColumnIndex * width + offsetX;
        final int y = curRowIndex * height + offsetY;
        this.setViewport(new Rectangle2D(x, y, width, height));
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
    public void setAnimation(int rows, int columns, int offsetX, int offsetY){
        this.rows = rows;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.curIndex = 0;
        tick();
    }
}
