package com.zcrabblers.zcrabble.Model;

public class Player implements IPlayers{

    private int score;

    public Player(int score){
        this.score = score;
    }

    private boolean endTurn = false;

    public void takeTurn(){
        while(!endTurn){

        }
        endTurn = false;
    }

    public void endTurn(){

    }

    public int getScore(){
        return score;
    }

}
