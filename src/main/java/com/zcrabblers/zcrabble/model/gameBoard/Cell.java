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

    int getCellWordMultiplier() {
        return cellWordMultiplier;
    }

    int getCellLetterMultiplier() {
        return cellLetterMultiplier;
    }

    Tile getPlacedTile(){return placedTile;}

    void setTile(Tile tile){placedTile = tile;}

    void removeTile(){
        placedTile = new Tile(' ', 0);
    }

    boolean isEmpty(){
        return placedTile.getLetter() == ' ';
    }

    char getTileLetter() {return placedTile.getLetter();}

    int getTileScore() {return placedTile.getTileScore();}
}
