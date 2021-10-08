package com.zcrabblers.zcrabble.Controller;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class Selection {
    private Button selectedCell;
    private boolean fromRack;
    private boolean selected;

    private int startX, startY;
    public Selection(){
        //TODO: set selected cell to some kind of non CellView
    }

    public void changeToDefaultImage(){
        selectedCell.setGraphic(null);
    }

    public void setImage(Node image){
        selectedCell.setGraphic(image);
    }

    public boolean getFromRack(){
        return fromRack;
    }

    public void setFromRack(boolean fromRack){
        this.fromRack = fromRack;
    }

    public boolean hasSelected(){
        return selected;//selectedCell != null;
    }

    public Node getSelectedImage(){
        return selectedCell.getGraphic();
    }

    //public int getXCoord(){
    //    return selectedCell.xCoord();
    //}
//
    //public int getYCoord(){
    //    return selectedCell.yCoord();
    //}

    public void select(Button selection){
        selected = true;
        selectedCell = selection;
        System.out.println("Selected something.");
    }

    public void unSelect(){
        //selectedCell = null; //TODO: avoid null
        selected = false;
        System.out.println("Unselected something.");
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
