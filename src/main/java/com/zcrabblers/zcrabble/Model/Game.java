package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game implements IGame {

    private List<IPlayers> players;
    private IPlayers current;
    private Board board;

    public Game() throws FileNotFoundException{
        this.board = new Board("defaultBoard");
    }

    public void newGame(){
        players = new ArrayList<>();
        players.add(new Player(0));
        players.add(new Player(0));
        current = players.get(0);
      //board = new Board(new Cell[15][15]);

    }

    private void endTurn() throws FileNotFoundException {
        //TODO: make temp board with changes made by current
        // to check against dictionary for correctness
        Board tempBoard = new Board("defaultBoard");
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

    public Board getBoard(){
        return board;
    }

}
