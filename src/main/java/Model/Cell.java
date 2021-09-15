package Model;

public class Cell {
    private int cellScore;
    private Tile placedTile;

    public Cell(int cellScore, Tile placedTile){
    this.cellScore = cellScore;
    this.placedTile = placedTile;
    }
    private int GetCellScore(){
        return placedTile.GetTileScore() * cellScore;
    }
}
