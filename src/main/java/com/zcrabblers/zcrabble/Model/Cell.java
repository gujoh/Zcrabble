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

    public void setTile(Tile tile){placedTile = tile;}

    public void removeTile(){
        placedTile = new Tile(' ', 0);
    }

    private int GetCombinedCellScore() {
        int i = 0;
        if(placedTile != null){
            i = placedTile.getTileScore();
        }
        return i * cellWordMultiplier;
    }

    public boolean isEmpty(){
        return placedTile.getLetter() == ' ';
    }

}
