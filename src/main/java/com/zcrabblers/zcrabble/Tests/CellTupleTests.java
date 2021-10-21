package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Game.Cell;
import com.zcrabblers.zcrabble.Model.CellTuple;
import com.zcrabblers.zcrabble.Model.Game.Tile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CellTupleTests {

    @Test
    public void TestConstructor(){
        CellTuple cellTuple =new CellTuple ( 7, 2,new Cell(4 , 5, new Tile(' ', 0)));
        assertEquals(7, cellTuple.getI());
        assertEquals(2, cellTuple.getJ());
    }
}
