package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.List;

// Is the class with the main responsibility over the model and starting new games
public class GameManager {
    private IGame currentGame;
    private static GameManager instance;

    /**
     * Getter for an instance of GameManager using the Singleton design pattern.
     * @return an instance of GameManager
     */
    public static GameManager getInstance(){
        if(instance == null)
            instance = new GameManager();
        return instance;
    }

    /**
     * Adds a subscriber to the current game.
     * @param sub the subscriber.
     */
    public void addSubscriber(ILetterObservable sub){
        if(currentGame != null)
            currentGame.addSubscriber(sub);
    }

    /**
     * Removes a subscriber from the current game.
     * @param sub the subscriber.
     */
    public void removeSubscriber(ILetterObservable sub){
        if(currentGame != null)
            currentGame.removeSubscriber(sub);
    }

    /**
     * Creates a new Game and removes all subscribers from the observer.
     */
    public void newGame(){
        if(currentGame != null)
            currentGame.removeAllSubscribers();
        currentGame = new Game();
        currentGame.start();
    }

    /**
     * Getter for the current Game.
     * @return the Game that is being played.
     */
    public IGame getCurrentGame(){
        return currentGame;
    }

    /**
     * Getter for the size of the current board.
     * @return the size of the current board.
     */
    public int getBoardSize(){
        return getBoardCells().length;
    }

    /**
     * Getter for the current game being played.
     * @return the board of the current game being played.
     */
    public Board getBoard(){
        return currentGame.getBoard();
    }

    public Cell[][] getBoardCells(){
            return getBoard().matrix();
    }

    public Rack getRack(){
        return currentGame.getRack();
    }

    public List<IPlayers> getPlayers(){
        return currentGame.getPlayers();
    }

    public int getPlayerScore(int index){
        return currentGame.getPlayerScore(index);
    }

}
