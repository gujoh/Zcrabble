package com.zcrabblers.zcrabble.model;

import java.util.Objects;

/**
 * GameManager is responsible for starting new games.
 * It implements the singleton design pattern.
 */
public class GameManager {
    private Game currentGame;
    private static GameManager instance;

    private GameManager(){
        instance = this;
    }

    /**
     * Getter for an instance of GameManager using the Singleton design pattern.
     * @return an instance of GameManager
     */
    public static GameManager getInstance(){
        return Objects.requireNonNullElseGet(instance, GameManager::new);
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

}
