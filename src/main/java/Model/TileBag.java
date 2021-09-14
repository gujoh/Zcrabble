package Model;

import java.util.ArrayDeque;
import java.util.Deque;

public class TileBag {
    private Deque<Tile> bag = new ArrayDeque<Tile>();

    public TileBag(){

    }

    public Tile takeTile(){
        return bag.pop();
    }

    public boolean isEmpty(){
        return bag.isEmpty();
    }
}
