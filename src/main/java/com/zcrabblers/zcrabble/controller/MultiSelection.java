package com.zcrabblers.zcrabble.controller;

import javafx.scene.image.Image;

import java.util.List;
import java.util.ArrayList;

public class MultiSelection {
    private final List<CellView> selected = new ArrayList<>();

    // get indices instead ?
    // aka have a method to calc indices here or in BoardController ?
    public List<CellView> getSelected(){
        return selected;
    }

    /**
     * Checks to see if provided cell is in the selection.
     * @param cellView Cell to compare.
     * @return True if the current selection contains the cell.
     */
    public boolean isSelected(CellView cellView){
        return selected.contains(cellView);
    }

    /**
     * Selects a cell and adds it to the selection.
     * @param cellView Cell to select.
     */
    public void select(CellView cellView){
        //System.out.println("Selected");
        selected.add(cellView);
        cellView.setOpacity(0.25);
    }

    /**
     * Unselects a cell from the selection.
     * @param cellView Cell to unselect
     */
    public void unSelect(CellView cellView){
        //System.out.println("Unselected");
        selected.remove(cellView);
        cellView.setOpacity(1);
    }

    /**
     * Unselects any previously selected cell.
     */
    public void unSelectAll(){
        selected.forEach(x -> x.setOpacity(1));
        selected.clear();
    }
}
