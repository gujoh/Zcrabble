package com.zcrabblers.zcrabble.Model;

public class Bot implements IPlayers {

    private int score;
    Dictionary dict = Dictionary.getInstance();

    public Bot(int score){
        this.score = score;
    }

    public void takeTurn(){

    }

    public int getScore(){
        return score;
    }


}
