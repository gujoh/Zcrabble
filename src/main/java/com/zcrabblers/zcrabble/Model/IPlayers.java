package com.zcrabblers.zcrabble.Model;

public interface IPlayers {
    int getScore();
    Rack getRack();
    void beginTurn(TileBag bag);

}
