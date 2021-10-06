package com.zcrabblers.zcrabble.Controller;

import javafx.scene.image.Image;

public class Selection {
    private CellView selectedCell;

    public Selection(){
        //TODO: set selected cell to some kind of non CellView
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
}
