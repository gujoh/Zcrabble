package Model;

public class Player implements IPlayers{

    private int score;

    public Player(int score){
        this.score = score;
    }

    public void endTurn(){

    }

    public int getScore(){
        return score;
    }

}
