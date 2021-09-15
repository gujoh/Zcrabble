package Model;

public class Tile {
    private String letter;
    private int tileScore;
    public Tile(String letter, int tileScore) {
        this.letter = letter;
        this.tileScore = tileScore;
    }
    public int GetTileScore(){
        return tileScore;
    }
    public String getLetter(){
        return letter;
    }
}
