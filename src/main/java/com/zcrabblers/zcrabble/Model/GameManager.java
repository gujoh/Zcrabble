package com.zcrabblers.zcrabble.Model;

// Is the class with the main responsibility over the model and starting new games
public class GameManager {
    static IGame currentGame;
    public GameManager(){

    }

    public static IGame getCurrentGame(){
        if(currentGame == null)
            newGame();
        return currentGame;
    }

    public static void newGame(){
        //currentGame = new Game();
    }

}
