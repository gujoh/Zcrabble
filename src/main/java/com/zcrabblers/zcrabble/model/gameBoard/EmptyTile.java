package com.zcrabblers.zcrabble.model.gameBoard;

/**
 * A singleton for an empty tile.
 * @author Niklas Axelsson
 * used by:
 * uses: Tile
 */
enum EmptyTile {
    INSTANCE;
    Tile empty = new Tile(' ', 0);

    /**
     * Gets the empty tile.
     * @return A reference to the empty tile.
     */
    public Tile getEmpty(){
        return empty;
    }

    /**
     * Check to see if a tile is the empty tile.
     * @param tile Tile to compare.
     * @return True if tile is the empty tile, false otherwise.
     */
    public boolean isEmpty(Tile tile){
        return tile == empty;
    }
}
