package com.zcrabblers.zcrabble.Model.Players;

import com.zcrabblers.zcrabble.Model.Game.Board;
import com.zcrabblers.zcrabble.Model.Game.Rack;
import com.zcrabblers.zcrabble.Model.Game.Tile;
import com.zcrabblers.zcrabble.Model.Game.TileBag;

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
