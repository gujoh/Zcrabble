package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Player;
import com.zcrabblers.zcrabble.Model.Rack;
import com.zcrabblers.zcrabble.Model.Tile;
import com.zcrabblers.zcrabble.Model.TileBag;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTests {

    @Test
    public void TestSwitchRack() throws FileNotFoundException {
        TileBag tilebag = new TileBag("defaultBag");
        tilebag.selectBag();
        Rack rack = new Rack(tilebag);
        Player player = new Player(0, rack);
        player.addScore(20);
        assertEquals(20,player.getScore());
        assertEquals(rack.getTile(1), player.getRackTile(1));
        Tile tile1 = new Tile('A', 2);
        player.placeRackTile(1,tile1);
        assertEquals(tile1,player.getRackTile(1));

    }
}