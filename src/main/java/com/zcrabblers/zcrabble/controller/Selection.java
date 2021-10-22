package com.zcrabblers.zcrabble.controller;

import javafx.scene.image.Image;

public class Selection {
    private CellView selectedCell;
    private boolean fromRack;
    private boolean selected;
    private int startX, startY;

    /**
     * Change selected cell to its default image (See CellView).
     */
    public void changeToDefaultImage(){
        selectedCell.changeToDefaultImage();
    }

    /**
     * Sets the currently displayed image of the selected cell.
     * @param image The image to set.
     */
    public void setImage(Image image){
        selectedCell.setImage(image);
    }

    /**
     * Check if the selection started from a rack cell or not.
     * @return A boolean that is true if selection started on the rack.
     */
    public boolean getFromRack(){
        return fromRack;
    }

    /**
     * Set whether the current selection started from the rack or not.
     * @param fromRack A boolean that sets if selection came from rack.
     */
    public void setFromRack(boolean fromRack){
        this.fromRack = fromRack;
    }

    /**
     * Check if anything is currently selected.
     * @return A boolean that is true if anything is selected.
     */
    public boolean hasSelected(){
        return selected;//selectedCell != null;
    }

    /**
     * Return the currently displayed image of the selected cell.
     * @return The currently displayed image.
     */
    public Image getSelectedImage(){
        return selectedCell.getImage();
    }

    /**
     * Select a cell so that it can be interacted with.
     * @param selection The cell to select.
     */
    public void select(CellView selection){
        selected = true;
        selectedCell = selection;
        System.out.println("Selected something.");
    }

    /**
     * Unselect the currently selected cell.
     */
    public void unSelect(){
        //selectedCell = null; //TODO: avoid null
        selected = false;
        System.out.println("Unselected something.");
    }

    /**
     * Get the X index of the previous cell that started a selection.
     * @return The X index of the starting cell.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Method to set the X index of the cell where selection started.
     * @param startX X index to set.
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * Get the Y index of the previous cell that started a selection.
     * @return The Y index of the starting cell.
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Method to set the Y index of the cell where selection started.
     * @param startY Y index to set.
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * Set the selected cell's opacity.
     * @param v A value between 0 and 1, where 1 is fully opaque and 0 is fully transparent.
     */
    public void setSelectedOpacity(double v){
        selectedCell.setOpacity(v);
    }
}
