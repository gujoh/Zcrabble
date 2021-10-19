package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Rack;
import com.zcrabblers.zcrabble.Model.Tile;
import com.zcrabblers.zcrabble.Model.TileBag;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

public class RackTests {

    @Test
    public void TestSwitchRack() throws FileNotFoundException {
        TileBag tilebag = new TileBag();
        Rack rack = new Rack(tilebag);
        Tile tile1 = rack.getTile(1);
        Tile tile2 = rack.getTile(2);
        rack.switchTiles(1,2);
        assertEquals(tile1, rack.getTile(2));
        assertEquals(tile2, rack.getTile(1));

    }
    @Test
    public void TestRack() throws FileNotFoundException {
        TileBag tilebag = new TileBag();
        Rack rack = new Rack(tilebag);
        Tile tile1 = rack.getTile(1);
        Tile tile2 = rack.getTile(2);
        rack.remove(1);
        assertTrue(rack.isEmpty(1));
        rack.set(1, new Tile('A',2));
    }

    @Test
    public void TestEmptyRack() throws FileNotFoundException {
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        for (int i = 0; i < 7; i++)
            rack.remove(i);
        assertTrue(rack.rackIsEmpty());
    }

    @Test
    public void TestEmptyRack2() throws FileNotFoundException {
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        for (int i = 0; i < 6; i++)
            rack.remove(i);
        assertFalse(rack.rackIsEmpty());
    }

    @Test
    public void shuffleTest() throws FileNotFoundException {
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        Tile[] preShuffle = new Tile[7];
        Tile[] postShuffle = rack.getTiles();
        for (int i = 0; i < postShuffle.length; i++) {
            preShuffle[i] = new Tile(postShuffle[i].getLetter(), postShuffle[i].getTileScore());
        }

        rack.shuffleRack();

        boolean ok = false;
        for (int i = 0; i < preShuffle.length; i++) {
            if(preShuffle[i].getLetter() != postShuffle[i].getLetter())
                ok = true;
        }
        assertTrue(ok);
    }

    @Test
    public void fillRackTest() throws FileNotFoundException {
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        for(int i = 0; i < 7; i++)
            rack.remove(i);
        rack.fillRack(tileBag);
        boolean ok = true;
        for (int i = 0; i < 7; i++) {
            if(rack.isEmpty(i)){
                ok = false;
                break;
            }
        }
        assertTrue(ok);
    }

    @Test
    public void getFirstFreeIndexFailTest() throws FileNotFoundException {
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        assertEquals(-1, rack.getFirstFreeIndex());
    }

}
