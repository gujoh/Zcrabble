package com.zcrabblers.zcrabble.model.gameBoard;

/**
 * Represents an empty tile.
 * @author Niklas Axelsson.
 */
enum EmptyTile {
    INSTANCE;
    Tile empty = new Tile(' ', 0);
    public Tile getEmpty(){
        return empty;
    }
    public boolean isEmpty(Tile tile){
        return tile == empty;
    }
}
