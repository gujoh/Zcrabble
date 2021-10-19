package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.List;

// Is the class with the main responsibility over the model and starting new games
public class GameManager {
    private Game currentGame;
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
    public void newGame(int nrPlayers, int nrBots){
        if(currentGame != null)
            currentGame.removeAllSubscribers();
        currentGame = new Game(nrPlayers, nrBots);
        currentGame.newGame();
    }

    /**
     * Getter for the current Game.
     * @return the Game that is being played.
     */
    public Game getCurrentGame(){
        return currentGame;
    }

    /**
     * Getter for the current game being played.
     * @return the board of the current game being played.
     */
    public Board getBoard(){
        return currentGame.getBoard();
    }

}
