package com.zcrabblers.zcrabble.Model;

public class Rack {
    private Tile[] playerTiles = new Tile[7];

    public Rack(TileBag bag){
        for(int i = 0; i < 7; i++){
            playerTiles[i] = bag.takeTile();
        }
    }

    public void add(Tile tile) {
        for (int i = 0; i < playerTiles.length; i++) {
            if(playerTiles[i] == null){
                playerTiles[i] = tile;
            }
        }
    }

    public void remove(int index){
        Tile tile = playerTiles[index];
        playerTiles[index] = null;
    }

    public void remove(char letter){
        for (int i = 0; i < playerTiles.length; i++) {
            if(letter == playerTiles[i].getLetter())
                playerTiles[i] = null;
        }
    }

    public Tile getTile(char letter){
        for (Tile playerTile : playerTiles) {
            if (letter == playerTile.getLetter())
                return playerTile;
        }
        return null; //TODO: this is bad
    }

    public Tile getTile(int index) {
        return playerTiles[index];
    }

    public boolean isEmpty(int x){
        return playerTiles[x].getLetter() == ' ';
    }
}
