package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Board;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;

public class BoardTests {
    @Test
    public void testSize() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        assertTrue(board.matrix()[0].length == 15);
    }
}
