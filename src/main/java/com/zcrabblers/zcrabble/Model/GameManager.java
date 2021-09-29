package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;

// Is the class with the main responsibility over the model and starting new games
public class GameManager {
    private IGame currentGame;
    private static GameManager instance;

    public static GameManager getInstance(){
        if(instance == null)
            instance = new GameManager();
        return instance;
    }

    // subscribe to the current game
    public void addSubscriber(ILetterObservable sub){
        if(currentGame != null)
            currentGame.addSubscriber(sub);
    }

    // unsubscribe to the current game
    public void removeSubscriber(ILetterObservable sub){
        if(currentGame != null)
            currentGame.removeSubscriber(sub);
    }

    public IGame getCurrentGame(){
        return currentGame;
    }

    public void newGame(){
        if(currentGame != null)
            currentGame.removeAllSubscribers();
        currentGame = new Game();
        currentGame.start();
    }

    public int getBoardSize(){
        return currentGame.getBoard().matrix().length;
    }

    public Cell[][] getBoard(){
        return currentGame.getBoard().matrix();
    }

}
