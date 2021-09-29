package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game implements IGame {

    private List<IPlayers> players;
    private IPlayers current;
    private Board board;
    private TileBag tileBag;
    private final LetterObserver observer = new LetterObserver();

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

        // TODO: Add score from tempboard to current if it was correct
        // TODO: End game if there's a winner and display results

        current = getNextPlayer();
        current.beginTurn(tileBag);
    }
    public synchronized void experimentalEndTurn(){
        notifyAll();
    }

    public synchronized void experimentalGameLoop() {
        try {
            while(!isGameOver()){
                wait();
                // Add score from tempboard to current if it was correct

                current = getNextPlayer();
                current.beginTurn(tileBag);
            }
            IPlayers winner = getWinner();
            // End game if there's a winner and display results
        } catch(InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        }

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

    @Override
    public void addSubscriber(ILetterObservable sub) {
        observer.addSubscriber(sub);
    }

    @Override
    public void removeSubscriber(ILetterObservable sub) {
        observer.removeSubscriber(sub);
    }

    @Override
    public void removeAllSubscribers() {
        observer.removeAllSubscribers();
    }

    public boolean isCellEmpty(int x, int y){
        return board.isCellEmpty(x, y);
    }

}
