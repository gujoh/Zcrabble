package com.zcrabblers.zcrabble.Model.Players;

import com.zcrabblers.zcrabble.Model.Board;
import com.zcrabblers.zcrabble.Model.Rack;
import com.zcrabblers.zcrabble.Model.Tile;
import com.zcrabblers.zcrabble.Model.TileBag;

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
