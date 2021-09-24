package com.zcrabblers.zcrabble.Model;

// Is the class with the main responsibility over the model and starting new games
public class GameManager {
    private IGame currentGame;
    private GameManager instance;

    public GameManager(){

    }

    public GameManager getInstance(){
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

    public static void newGame(){
        //currentGame = new Game();
    }

}
