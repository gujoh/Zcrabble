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
    private Deque<Tile> SelectBag(){
        if(bagSelector.equals("default")){
            bag.add(new Tile('a',1));
            bag.add(new Tile('b',2));
            bag.add(new Tile('c',3));
            bag.add(new Tile('c',4));
            bag.add(new Tile('e',5));
            bag.add(new Tile('f',6));
            bag.add(new Tile('g',7));
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
}
