package com.zcrabblers.zcrabble.model.players;

import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import com.zcrabblers.zcrabble.model.gameBoard.Board;

public interface IPlayers {

    /**
     * @return the score
     */
    int getScore();

    /**
     * @return the rack
     */
    Rack getRack();

    /**
     * @param x Int index of rack
     * @return the tile at x index on the rack
     */
    Tile getRackTile(int x);

    /**
     * Places a tile to x index on the rack
     * @param x index of rack
     * @param tile Tile to be placed
     */
    void placeRackTile(int x, Tile tile);

    /**
     * Begins the turn for the current player.
     * @param tempBoard the Board to make a play on.
     */
    void beginTurn(Board tempBoard);

    /**
     * Adds points of the latest play to score
     * @param score the points to be added to score
     */
    void addScore(int score);

    /**
     * Fills the rack with tiles from the TileBag
     * @param bag the Bag to get tiles from
     */
    void fillRack(TileBag bag);

    /**
     * Removes a tile from the rack
     *
     * @param x index of tile to be removed
     */
    void removeRackTile(int x);
}
