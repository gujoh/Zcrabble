package com.zcrabblers.zcrabble.Controller;

import javafx.scene.image.Image;

import java.util.List;
import java.util.ArrayList;

public class MultiSelection {
    private final List<CellView> selected = new ArrayList<>();
    private Image selectionImage;

    //public MultiSelection(Image selectionImage){
    //    this.selectionImage = selectionImage;
    //}

    // get indices instead ?
    // aka have a method to calc indices here or in BoardController ?
    public List<CellView> getSelected(){
        return selected;
    }

    public boolean isSelected(CellView cellView){
        return selected.contains(cellView);
    }

    public void select(CellView cellView){
        //cellView.setImage(selectionImage);
        System.out.println("Selected");
        selected.add(cellView);
    }

    public void unSelect(CellView cellView){
        //cellView.changeToDefaultImage();
        System.out.println("Unselected");
        selected.remove(cellView);
    }

    public void unSelectAll(){
        selected.clear();
    }
}
