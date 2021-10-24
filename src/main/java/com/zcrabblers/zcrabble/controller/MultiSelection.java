package com.zcrabblers.zcrabble.controller;

import java.util.List;
import java.util.ArrayList;

/**
 * MultiSelection is responsible for keeping track of several selected CellImageViews.
 * @author Niklas Axelsson
 * used by: BoardViewController
 * uses: CellImageView
 */
public class MultiSelection {
    private final List<CellImageView> selected = new ArrayList<>();
    private final int unselectedOpacity = 1;
    // get indices instead ?
    // aka have a method to calc indices here or in BoardController ?
    public List<CellImageView> getSelected(){
        return selected;
    }

    /**
     * Checks to see if provided cell is in the selection.
     * @param cellView Cell to compare.
     * @return True if the current selection contains the cell.
     */
    public boolean isSelected(CellImageView cellView){
        return selected.contains(cellView);
    }

    /**
     * Selects a cell and adds it to the selection.
     * @param cellView Cell to select.
     */
    public void select(CellImageView cellView){
        selected.add(cellView);
        double selectedOpacity = 0.25;
        cellView.setOpacity(selectedOpacity);
    }

    /**
     * Unselects a cell from the selection.
     * @param cellView Cell to unselect
     */
    public void unSelect(CellImageView cellView){
        selected.remove(cellView);
        cellView.setOpacity(unselectedOpacity);
    }

    /**
     * Unselects any previously selected cell.
     */
    public void unSelectAll(){
        selected.forEach(x -> x.setOpacity(unselectedOpacity));
        selected.clear();
    }
}
