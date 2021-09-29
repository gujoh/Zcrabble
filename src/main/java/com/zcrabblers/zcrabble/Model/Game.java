package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Thread implements IGame {

    private List<IPlayers> players;
    private IPlayers current;
    private Board board;
    private TileBag tileBag;
    private final LetterObserver observer = new LetterObserver();

    public Game(){
        this.board = new Board("defaultBoard");
        this.tileBag = new TileBag("defaultBag");
    }

    public void newGame() {
        players = new ArrayList<>();
        players.add(new Player(0,new Rack()));
        players.add(new Player(0, new Rack()));
        current = players.get(0);
        try{
            board.selectBoard();
            tileBag.selectBag();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void endTurn(){
        experimentalEndTurn();
    }

    @Override
    public void run(){
        experimentalGameLoop();
    }

    @Override
    public void start(){
        newGame();
        super.start();
    }

    public synchronized void experimentalEndTurn(){
        notifyAll();
    }

    public synchronized void experimentalGameLoop() {
        try {
            while(!isGameOver()){
                current.beginTurn(tileBag);
                System.out.println("WAITING");
                wait();
                System.out.println("PASSED WAIT");
                // Add score from tempboard to current if it was correct

                // Låta Game vara en Thread och starta en ny Thread varje gång
                // vi startar ett nytt game, den tråden blir då "modell tråden"
                // och javafx sköter grafik tråden. Game tråden väntar till vi
                // trycker på en knapp på grafik tråden som notifyar game tråden
                // då körs hela loopen med all input spelaren gett innan hon tryckte
                // på end turn

                current = getNextPlayer();
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
        if(index >= players.size() - 1)
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
