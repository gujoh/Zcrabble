package com.zcrabblers.zcrabble.Model;

import com.zcrabblers.zcrabble.Utils.RandomSeed;
import java.util.Random;

public class Rack {
    private Tile[] playerTiles = new Tile[7];

    public Rack(TileBag bag){
        for(int i = 0; i < 7; i++){
            playerTiles[i] = bag.takeTile();
        }
    }

    /**
     * Switches positions of tiles at index x1 and x2.
     * @param x1 Index of the first tile.
     * @param x2 Index of the second tile.
     */
    public void switchTiles(int x1, int x2){
        Tile fst = playerTiles[x1];
        playerTiles[x1] = playerTiles[x2];
        playerTiles[x2] = fst;
    }

    // probably bad, prob not needed
    public int getFirstFreeIndex(){
        for (int i = 0; i < playerTiles.length; i++) {
            if(isEmpty(i))
                return i;
        }
        return -1;
    }

    /**
     * Sets position at index to the specified tile.
     * @param index Where in the rack to set the tile.
     * @param tile The tile to set.
     */
    public void set(int index, Tile tile){
        playerTiles[index] = tile;
    }

    /**
     * Remove the tile at position index.
     * @param index The position to remove at.
     */
    public void remove(int index){
        playerTiles[index] = new Tile(' ', 0);
    }

    /**
     * Return the tile at position index.
     * @param index Index.
     * @return The tile.
     */
    public Tile getTile(int index) {
        return playerTiles[index];
    }

    /**
     * Check if position x is empty.
     * @param x The position to look at.
     * @return Returns true if empty, false otherwise;
     */
    public boolean isEmpty(int x){
        return playerTiles[x].getLetter() == ' ';
    }

    public boolean rackIsEmpty(){
        for(int i = 0; i < playerTiles.length; i++){
            if(!isEmpty(i)){
                return false;
            }
        }
        return true;
    }

    public void fillRack(TileBag bag){
        for(int i = 0; i < playerTiles.length; i++){
            if(playerTiles[i].getLetter() == ' ' && !bag.isEmpty()){
                playerTiles[i] = bag.takeTile();
            }
        }
    }

    /**
     * Shuffles the current rack.
     */
    public void shuffleRack(){
        Random rand = new Random(RandomSeed.INSTANCE.getSeed());
        for(int i = 0; i < playerTiles.length - 2; i++){
            int randIndex = rand.nextInt(playerTiles.length);
            Tile temp = playerTiles[randIndex];
            playerTiles[randIndex] = playerTiles[i];
            playerTiles[i] = temp;
        }
    }

    /**
     * Getter for playerTiles i.e a player's rack.
     * @return an array of tiles.
     */
    public Tile[] getTiles(){
        return playerTiles;
    }
}
