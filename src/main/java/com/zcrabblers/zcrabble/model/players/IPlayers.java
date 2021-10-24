package com.zcrabblers.zcrabble.model.players;

import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import com.zcrabblers.zcrabble.model.gameBoard.Board;

/**
 * Common abstraction for different type of players.
 * @author Niklas Axelsson
 * used by: Game
 * uses: Rack, Tile, Board, Tilebag
 */
public interface IPlayers {

    /**
     * @return The score of this player.
     */
    int getScore();

    /**
     * @return The rack of this player.
     */
    Rack getRack();

    /**
     * Gets a tile on the player's rack.
     * @param x Int index of rack.
     * @return The tile at x index on the rack.
     */
    Tile getRackTile(int x);

    /**
     * Places a tile to x index on this player's rack.
     * @param x index of rack
     * @param tile Tile to be placed
     */
    void placeRackTile(int x, Tile tile);

    /**
     * Begins the turn for this player.
     * @param tempBoard the Board to make a play on.
     */
    void beginTurn(Board tempBoard);

    /**
     * Adds points to this player's score.
     * @param score The points to be added to score.
     */
    void addScore(int score);

    /**
     * Fills this player's rack with tiles from the TileBag.
     * @param bag The Bag to get tiles from.
     */
    void fillRack(TileBag bag);

    /**
     * Removes a tile from this player's rack.
     * @param x Index of tile to be removed.
     */
    void removeRackTile(int x);
}
