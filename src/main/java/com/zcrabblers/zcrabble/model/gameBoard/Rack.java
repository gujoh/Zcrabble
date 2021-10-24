package com.zcrabblers.zcrabble.model.gameBoard;

import com.zcrabblers.zcrabble.utils.RandomSeed;
import java.util.Random;

/**
 * A container for a player's playable tiles.
 * @author Niklas Axelsson, Martin Björklund, Ole Fjeldså, Gustaf Jonasson.
 * used by: IPlayer, Bot, Player
 * uses: Tile, Tilebag, RandomSeed
 */
public class Rack {
    private Tile[] playerTiles = new Tile[7];

    public Rack(TileBag bag){
        for(int i = 0; i < 7; i++){
            playerTiles[i] = bag.takeTile();
        }
    }

    public Rack() {
        for (int i = 0; i < 7; i++) {
            playerTiles[i] = new Tile(' ', 0);
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



    public void getRackCopy(Rack rack) {
        Tile[] tempTile = new Tile[rack.getTiles().length];
        for (int i = 0; i <rack.getTiles().length ; i++) {
            Tile rackTile = rack.getTile(i);
            tempTile[i] = new Tile(rackTile.getLetter(), rackTile.getTileScore());
        }
        playerTiles = tempTile;
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
        Random rand = RandomSeed.INSTANCE.getRandom();
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
