package com.zcrabblers.zcrabble.Model;

public class Player implements IPlayers{

    private int score;
    private Rack rack;

    public Player(int score, Rack rack){
        this.score = score;
        this.rack = rack;
    }

    public int getScore(){
        return score;
    }
    public Rack getRack(){
        return rack;
    }

    private boolean endTurn = false;
/* for now useless
    delete later :)
    public void takeTurn(){
        while(!endTurn){
        }
        endTurn = false;
    }
    public void endTurn(){}
*/
}
