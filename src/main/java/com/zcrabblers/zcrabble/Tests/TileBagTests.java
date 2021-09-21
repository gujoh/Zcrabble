package com.zcrabblers.zcrabble.Tests;
import com.zcrabblers.zcrabble.Model.Tile;
import com.zcrabblers.zcrabble.Model.TileBag;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TileBagTests {
    @Test
    public void testTileBag() throws FileNotFoundException {
        TileBag tilebag = new TileBag("default");
        assertTrue(tilebag.getBag().size() == 100);

    }

}
