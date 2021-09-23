package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game implements IGame {

    private List<IPlayers> players;
    private IPlayers current;
    private Board board;
    private TileBag tileBag;

    public Game(){
        this.board = new Board("defaultBoard");
        this.tileBag = new TileBag("defaultBag");
    }

    public void newGame() throws FileNotFoundException {
        players = new ArrayList<>();
        players.add(new Player(0,new Rack()));
        players.add(new Player(0, new Rack()));
        current = players.get(0);
        board.selectBoard();
        tileBag.selectBag();
        takeTurn();
    }
    private void takeTurn(){
        //TODO: insert check in while loop if end turn button has been pushed
        //while(){}
        //TODO: calculate points function
        if(isGameOver()){getWinner();}
        endTurn();

    }
    private void endTurn(){
        //TODO: make temp board with changes made by current
        // to check against dictionary for correctness
        Board tempBoard = new Board("defaultBoard");
        //current.takeTurn();

        current = getNextPlayer();

        //TODO: find out who won and display
    }

    private boolean isGameOver(){
        return tileBag.isEmpty();
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
        IPlayers winner = players.get(0);
        for (IPlayers player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }

    public Board getBoard(){
        return board;
    }

}
