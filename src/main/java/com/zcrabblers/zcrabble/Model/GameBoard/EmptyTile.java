package com.zcrabblers.zcrabble.Model.GameBoard;

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
