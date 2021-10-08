package com.zcrabblers.zcrabble.Model;

public interface IPlayers {
    int getScore();
    Rack getRack();
    Tile getRackTile(int x);
    void placeRackTile(int x, Tile tile);
    void beginTurn(TileBag bag);
    void addScore(int score);

}
