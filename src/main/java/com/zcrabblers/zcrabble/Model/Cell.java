package com.zcrabblers.zcrabble.Model;

public class Cell {
    private int cellWordMultiplier;
    private int cellLetterMultiplier;
    private Tile placedTile;


    public Cell(int cellWordMultiplier,int cellLettermultiplier, Tile placedTile) {
        this.cellWordMultiplier = this.cellWordMultiplier;
        this.cellLetterMultiplier = this.cellWordMultiplier;
        this.placedTile = placedTile;
    }
    // testing cell
    public Cell(Cell cell){
        this.cellWordMultiplier = cell.cellWordMultiplier;
        this.cellLetterMultiplier = this.cellWordMultiplier;
        this.placedTile = new Tile(cell.placedTile);
    }

    public int GetCellScore() {
        return cellWordMultiplier;
    }

    private int GetCombinedCellScore() {
        int i = 0;
        if(placedTile != null){
            i = placedTile.getTileScore();
        }
        return i * cellWordMultiplier;
    }
}
