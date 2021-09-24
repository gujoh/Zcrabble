package com.zcrabblers.zcrabble.Model;

public class Cell {
    private int cellWordMultiplier;
    private int cellLetterMultiplier;
    private Tile placedTile;


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

    private Tile getTile(){return placedTile;}
    private int GetCombinedCellScore() {
        int i = 0;
        if(placedTile != null){
            i = placedTile.getTileScore();
        }
        return i * cellWordMultiplier;
    }

}
