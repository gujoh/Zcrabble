package com.zcrabblers.zcrabble.Model;
import com.zcrabblers.zcrabble.Utils.RandomSeed;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class TileBag {
    private Deque<Tile> bag = new ArrayDeque<>();
    String bagSelector;

    public TileBag(String bagSelector){
        this.bagSelector = bagSelector;
    }
    /* selectBag creates a temporary List and then reads the document in resources which string is identical to "bagselector"
    it then reads the file per line stopping between each space per line.
    the first set of characters is the letter of the tile the second is the score value the third is how many of then to create
    it then randomizes the List and adds it to the deque bag*/
    public void selectBag() throws FileNotFoundException{
        List<Tile> temp = new ArrayList<>();
        if(bagSelector.equals("defaultBag")){
            File file = new File("src/main/resources/"+bagSelector);
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
        }
        Collections.shuffle(temp, new Random(RandomSeed.INSTANCE.getSeed()));
        bag.addAll(temp);

    }
    public int remainingTiles(){
        return bag.size();
    }
    public Tile takeTile(){
        return bag.pop();
    }

    public boolean isEmpty(){
        return bag.isEmpty();
    }
    public Deque<Tile> getBag(){
        return bag;
    }
}

