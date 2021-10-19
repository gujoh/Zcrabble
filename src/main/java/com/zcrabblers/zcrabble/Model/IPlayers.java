package com.zcrabblers.zcrabble.Model;

public interface IPlayers {
    int getScore();
    Rack getRack();
    Tile getRackTile(int x);
    void placeRackTile(int x, Tile tile);
    void beginTurn(Board tempBoard);
    void addScore(int score);
    void fillRack(TileBag bag);
    void removeRackTile(int x);
}
