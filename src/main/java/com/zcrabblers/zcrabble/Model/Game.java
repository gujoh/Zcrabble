package com.zcrabblers.zcrabble.Model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<IPlayers> players;
    private IPlayers current;
    private Board board;

    public Game(){

    }

    public void newGame(){
        players = new ArrayList<>();
        players.add(new Player(0));
        players.add(new Player(0));
        current = players.get(0);
        board = new Board(new Cell[15][15]);

    }

    private void endTurn(){
        //TODO: make temp board with changes made by current
        // to check against dictionary for correctness
        Board tempBoard = new Board(board);
        current.takeTurn();

        current = getNextPlayer();

        //TODO: find out who won and display
    }

    private boolean isGameNotOver(){
        // TODO: Check if game is over
        System.out.println("isGameOver() not implemented! Always returns true.");
        return true;
    }

    private IPlayers getNextPlayer(){
        int index = players.indexOf(current);
        if(index >= players.size())
            index = 0;
        else
            index++;
        return players.get(index);
    }

    private IPlayers getWinner(){
        //TODO: decide who won
        return null;
    }

}
