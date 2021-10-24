package com.zcrabblers.zcrabble.model.gameBoard;

public enum EmptyTile {
    INSTANCE;
    Tile empty = new Tile(' ', 0);
    public Tile getEmpty(){
        return empty;
    }
    public boolean isEmpty(Tile tile){
        return tile == empty;
    }
}
