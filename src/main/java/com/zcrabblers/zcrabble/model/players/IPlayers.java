package com.zcrabblers.zcrabble.model.players;

import com.zcrabblers.zcrabble.model.gameBoard.board.Board;
import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;

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
