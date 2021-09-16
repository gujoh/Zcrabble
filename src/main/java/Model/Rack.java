package Model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private Tile[] playerTiles = new Tile[7];

    public boolean add(Tile tile) {
        for (int i = 0; i < playerTiles.length; i++) {
            if(playerTiles[i] == null){
                playerTiles[i] = tile;
                return true;
            }
        }
        return false;
    }

    public Tile remove(int index){
        Tile tile = playerTiles[index];
        playerTiles[index] = null;
        return tile;
    }

    public Tile getTile(int index) {
        return playerTiles[index];
    }
}
