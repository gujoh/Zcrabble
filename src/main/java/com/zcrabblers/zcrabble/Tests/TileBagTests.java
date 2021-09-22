package com.zcrabblers.zcrabble.Tests;
import com.zcrabblers.zcrabble.Model.Tile;
import com.zcrabblers.zcrabble.Model.TileBag;
import org.junit.Test;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class TileBagTests {
    // checks some basic numbers if a bag with the correct string works
    // and makes sure the remaining tiles are correct
    @Test
    public void testTileBagNumbers() throws FileNotFoundException {
        TileBag tilebag = new TileBag("");
        assertTrue(tilebag.isEmpty() && tilebag.remainingTiles() == 0);
        tilebag = new TileBag("defaultBag");
        assertTrue(tilebag.remainingTiles() == 100);
        assertFalse(tilebag.isEmpty());
    }
    //checks if the read tiles have allowed letters and numbers
    @Test
    public void testTileBagLogic() throws FileNotFoundException {
        TileBag tilebag = new TileBag("defaultBag");
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_";
        while(!tilebag.isEmpty()){
            Tile tile = tilebag.getBag().pop();
            assertTrue(validChars.contains(String.valueOf(tile.getLetter())));
            assertTrue(tile.getTileScore() <= 10);
        }
    }

}
