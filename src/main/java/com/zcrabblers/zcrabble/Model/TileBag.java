package com.zcrabblers.zcrabble.Model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TileBag {
    private Deque<Tile> bag = new ArrayDeque<>();
    String bagSelector;

    public TileBag(String bagSelector){
        this.bagSelector = bagSelector;
    }
    public Deque<Tile> selectBag() throws FileNotFoundException{
        if(bagSelector.equals("default")){
                File file = new File("src\\main\\resources\\"+bagSelector);
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()){
                    char readLetter = scanner.next().toCharArray()[0];
                    int readScore = Integer.parseInt(scanner.next());
                    int readNumberOf = Integer.parseInt(scanner.next());
                    Tile tile = new Tile(readLetter,readScore);
                    while(readNumberOf > 0){
                        bag.add(tile);
                        readNumberOf--;
                        System.out.println(readLetter + String.valueOf(readScore));
                    }

            }
        }
        return bag;
    }
    public int RemainingTiles(){
        return bag.size();
    }
    public Tile TakeTile(){
        return bag.pop();
    }

    public boolean IsEmpty(){
        return bag.isEmpty();
    }

    public static void main(String[] args) throws FileNotFoundException {
        TileBag tileBag = new TileBag("default");
        tileBag.selectBag();
    }
}
