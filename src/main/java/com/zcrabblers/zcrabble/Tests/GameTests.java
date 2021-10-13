package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTests {
    @Test
    public void testSwitchRackBoardTile(){
        IGame game = new Game(2, 0);
        game.newGame();
        Board temp = game.getTempBoard();
        Rack rack = game.getRack();

        temp.placeTile(5, 5, new Tile('K', 5));

        char oBoard = temp.getTile(5, 5).getLetter();
        char oRack = rack.getTile(4).getLetter();

        game.switchRackBoardCells(4, 5, 5);

        Tile rackT = rack.getTile(4);
        Tile tempT = temp.getTile(5, 5);
        assertEquals(oBoard, rackT.getLetter());
        assertEquals(oRack, tempT.getLetter());
    }
}
