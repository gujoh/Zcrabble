package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Cell;
import com.zcrabblers.zcrabble.Model.Tile;
import org.junit.Test;
import static org.junit.Assert.*;

public class CellTests {

    @Test
    public void TestConstructor(){
        Cell cell = new Cell(4 , 5, new Tile(' ', 0));
        assertEquals(4, cell.GetCellWordMultiplier());
        assertEquals(5, cell.GetCellLetterMultiplier());
    }
    @Test
    public void TestFunctions(){
        Cell cell = new Cell(6 , 8, new Tile('A', 0));
        assertEquals(0, cell.getPlacedTile().getTileScore());
        assertEquals('A', cell.getPlacedTile().getLetter());
        cell.setTile(new Tile('B',1));
        assertEquals(1, cell.getPlacedTile().getTileScore());
        assertEquals('B', cell.getPlacedTile().getLetter());
        cell.removeTile();
        assertEquals(0, cell.getPlacedTile().getTileScore());
        assertEquals(' ', cell.getPlacedTile().getLetter());
        assertTrue(cell.isEmpty());
    }
}
