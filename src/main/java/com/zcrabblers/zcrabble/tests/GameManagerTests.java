package com.zcrabblers.zcrabble.tests;

import com.zcrabblers.zcrabble.model.GameManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameManagerTests {

    @Test
    public void singeltonTest(){
        GameManager g = GameManager.getInstance();
        assertEquals(g, GameManager.getInstance());
    }

    @Test
    public void newGameTest(){
        GameManager g = GameManager.getInstance();
        g.newGame(2, 2);
        g.newGame(1, 1);
        assertNotEquals(null, g.getCurrentGame());
    }
}
