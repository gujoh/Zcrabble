package com.zcrabblers.zcrabble.Controller;

import javafx.scene.image.Image;

public class Selection {
    private CellView selectedCell;
    private boolean fromRack;

    private int startX, startY;
    public Selection(){
        //TODO: set selected cell to some kind of non CellView
    }

    public void changeToDefaultImage(){
        selectedCell.changeToDefaultImage();
    }

    public void setImage(Image image){
        selectedCell.setImage(image);
    }

    public boolean getFromRack(){
        return fromRack;
    }

    public void setFromRack(boolean fromRack){
        this.fromRack = fromRack;
    }

    public boolean hasSelected(){
        return selectedCell != null;
    }

    public Image getSelectedImage(){
        return selectedCell.getImage();
    }

    public int getXCoord(){
        return selectedCell.xCoord();
    }

    public int getYCoord(){
        return selectedCell.yCoord();
    }

    public void select(CellView selection){
        selectedCell = selection;
    }

    public void unSelect(){
        selectedCell = null; //TODO: avoid null
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
