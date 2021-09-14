package Model;

public class Bot implements IPlayers {

    private int score;

    public Bot(int score){
        this.score = score;
    }

    public void endTurn(){

    }

    public int getScore(){
        return score;
    }
}
