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
        TileBag tilebag = new TileBag("defaultBag");
        tilebag.selectBag();
        Rack rack = new Rack(tilebag);
        Tile tile1 = rack.getTile(1);
        Tile tile2 = rack.getTile(2);
        rack.switchTiles(1,2);
        assertEquals(tile1, rack.getTile(2));
        assertEquals(tile2, rack.getTile(1));

    }
    @Test
    public void TestRack() throws FileNotFoundException {
        TileBag tilebag = new TileBag("defaultBag");
        tilebag.selectBag();
        Rack rack = new Rack(tilebag);
        Tile tile1 = rack.getTile(1);
        Tile tile2 = rack.getTile(2);
        rack.remove(1);
        assertTrue(rack.isEmpty(1));
        rack.set(1, new Tile('A',2));

    }
}
