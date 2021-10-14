package com.zcrabblers.zcrabble.Model;

/**
 * keeps track of the individual cells, word and letter multiplier as well as its placed tile
 */
public class Cell {
    private int cellWordMultiplier;
    private int cellLetterMultiplier;
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

    public int GetCellWordMultiplier() {
        return cellWordMultiplier;
    }

    public int GetCellLetterMultiplier() {
        return cellLetterMultiplier;
    }

    public Tile getPlacedTile(){return placedTile;}

    public void setTile(Tile tile){placedTile = tile;}

    public void removeTile(){
        placedTile = new Tile(' ', 0);
    }

    public boolean isEmpty(){
        return placedTile.getLetter() == ' ';
    }

    public char getTileLetter() {return placedTile.getLetter();}

}
