package com.zcrabblers.zcrabble.model.gameBoard;

import com.zcrabblers.zcrabble.utils.RandomSeed;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represent the bag of tiles each player draw new tiles from.
 * @author Niklas Axelsson, Martin Björklund, Ole Fjeldså, Gustaf Jonasson.
 * used by:
 * uses: Tile, IPlayer, Game
 */
public class TileBag {
    private final Deque<Tile> bag = new ArrayDeque<>();

    public TileBag(){
        try {
            fillBag();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Fills the bag based on the information in the defaultBag.txt file.
    private void fillBag() throws FileNotFoundException{
        List<Tile> temp = new ArrayList<>();
            File file = new File("src/main/resources/defaultBag");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                char readLetter = scanner.next().toCharArray()[0];
                int readScore = Integer.parseInt(scanner.next());
                int readNumberOf = Integer.parseInt(scanner.next());
                Tile tile = new Tile(readLetter,readScore);
                while(readNumberOf > 0){
                    temp.add(tile);
                    readNumberOf--;
                }
            }
            scanner.close();
        Collections.shuffle(temp, RandomSeed.INSTANCE.getRandom());
        bag.addAll(temp);
    }

    /**
     * @return number of Tiles left in the bag
     */
    public int remainingTiles(){
        return bag.size();
    }

    /**
     * pops the top Tile out of the bag and returns it.
     * @return the top tile in the bag
     */
    public Tile takeTile(){
        return bag.pop();
    }

    /**
     * Adds a tile to the bag
     * @param tile the Tile to be added to the bag.
     */
    public void add(Tile tile){
        bag.add(tile);
    }

    /**
     * Checks if there is any tiles left in the bag
     * @return true/false the bag is empty
     */
    public boolean isEmpty(){
        return bag.isEmpty();
    }

    /**
     * @return returns the tileBag
     */
    public Deque<Tile> getBag(){
        return bag;
    }
}

