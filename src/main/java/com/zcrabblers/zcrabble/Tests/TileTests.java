package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Game.Tile;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTests {

    @Test
    public void testTile(){
        Tile tile = new Tile('A', 2);
        assertEquals(2,tile.getTileScore());
        assertEquals('A',tile.getLetter());

    }
}
