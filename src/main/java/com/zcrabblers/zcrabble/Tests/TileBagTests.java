package com.zcrabblers.zcrabble.Tests;
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
        tilebag.selectBag();
        assertTrue(tilebag.isEmpty() && tilebag.remainingTiles() == 0);
        tilebag = new TileBag("defaultBag");
        tilebag.selectBag();
        assertTrue(tilebag.remainingTiles() == 100);
        assertFalse(tilebag.isEmpty());
    }
    //checks if the read tiles have allowed letters and numbers
    @Test
    public void testTileBagLogic() throws FileNotFoundException {
        TileBag tilebag = new TileBag("defaultBag");
        tilebag.selectBag();
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_";
        while(!tilebag.isEmpty()){
            char letter = tilebag.getBag().peekFirst().getLetter();
            int score = tilebag.getBag().pop().getTileScore();
            assertTrue(validChars.contains(String.valueOf(letter)));
            assertTrue(score <= 10);
        }
    }

}
