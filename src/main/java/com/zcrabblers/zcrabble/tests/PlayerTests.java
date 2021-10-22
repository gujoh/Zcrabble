package com.zcrabblers.zcrabble.tests;

import com.zcrabblers.zcrabble.model.players.Player;
import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTests {

    @Test
    public void TestSwitchRack(){
        TileBag tilebag = new TileBag();
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
