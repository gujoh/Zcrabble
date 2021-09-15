package Model;

public class Cell {
    private int cellScore;
    private Tile placedTile;

    public Cell(int cellScore, Tile placedTile){
    this.cellScore = cellScore;
    this.placedTile = placedTile;
    }
    public int GetCellScore(){
        return cellScore;
    }

    private int GetCombinedCellScore() {
        int i;

        try{
            i = placedTile.GetTileScore();
        }
        catch(Exception e){
            i = 0;
        }

        return i*cellScore;
    }
}
