package com.zcrabblers.zcrabble.model.gameBoard;

/**
 * keeps track of the individual cells, word and letter multiplier as well as its placed tile
 * @author Gustaf Jonasson, Ole  Fjeldså, Martin Björklund, Niklas Axelsson.
 */
public class Cell {
    private final int cellWordMultiplier;
    private final int cellLetterMultiplier;
    private Tile placedTile;

    /**
     * boards are made up of a grid of cells
     * @param cellWordMultiplier the multiplier for a word that a tile that is placed on this cell is a part of
     * @param cellLetterMultiplier a multiplier for the specific letter placed on this tile
     * @param placedTile The tile of the given cell
     * @see Tile
     */
    public Cell(int cellWordMultiplier,int cellLetterMultiplier, Tile placedTile) {
        this.cellWordMultiplier = cellWordMultiplier;
        this.cellLetterMultiplier = cellLetterMultiplier;
        this.placedTile = placedTile;
    }

    //Returns the cellWordMultiplier.
    int getCellWordMultiplier() {
        return cellWordMultiplier;
    }

    //Returns the cellLetterMultiplier.
    int getCellLetterMultiplier() {
        return cellLetterMultiplier;
    }

    //Returns the placedTile.
    Tile getPlacedTile(){return placedTile;}

    //Sets the value of placedTile to tile.
    void setTile(Tile tile){placedTile = tile;}

    //Changes the value of placedTile to be an empty tile.
    void removeTile(){
        placedTile = new Tile(' ', 0);
    }

    //Checks if placedTile is empty, returns true if it is.
    boolean isEmpty(){
        return placedTile.getLetter() == ' ';
    }

    //Gets the letter on placedTile.
    char getTileLetter() {return placedTile.getLetter();}

}
