import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

/**
 * Tests for methods in the Rack class.
 * @author Niklas Axelsson
 */
public class RackTests {

    @Test
    public void TestSwitchRack(){
        TileBag tilebag = new TileBag();
        Rack rack = new Rack(tilebag);
        Tile tile1 = rack.getTile(1);
        Tile tile2 = rack.getTile(2);
        rack.switchTiles(1,2);
        assertEquals(tile1, rack.getTile(2));
        assertEquals(tile2, rack.getTile(1));

    }
    @Test
    public void TestRack(){
        TileBag tilebag = new TileBag();
        Rack rack = new Rack(tilebag);
        Tile tile1 = rack.getTile(1);
        Tile tile2 = rack.getTile(2);
        rack.remove(1);
        assertTrue(rack.isEmpty(1));
        rack.set(1, new Tile('A',2));
    }

    @Test
    public void TestEmptyRack() {
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        for (int i = 0; i < 7; i++)
            rack.remove(i);
        assertTrue(rack.rackIsEmpty());
    }

    @Test
    public void TestEmptyRack2(){
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        for (int i = 0; i < 6; i++)
            rack.remove(i);
        assertFalse(rack.rackIsEmpty());
    }

    @Test
    public void shuffleTest(){
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        Tile[] preShuffle = new Tile[7];
        Tile[] postShuffle = rack.getTiles();
        for (int i = 0; i < postShuffle.length; i++) {
            preShuffle[i] = new Tile(postShuffle[i].getLetter(), postShuffle[i].getTileScore());
        }

        rack.shuffleRack();

        boolean ok = false;
        for (int i = 0; i < preShuffle.length; i++) {
            if(preShuffle[i].getLetter() != postShuffle[i].getLetter())
                ok = true;
        }
        assertTrue(ok);
    }

    @Test
    public void fillRackTest(){
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        for(int i = 0; i < 7; i++)
            rack.remove(i);
        rack.fillRack(tileBag);
        boolean ok = true;
        for (int i = 0; i < 7; i++) {
            if(rack.isEmpty(i)){
                ok = false;
                break;
            }
        }
        assertTrue(ok);
    }

    @Test
    public void getFirstFreeIndexFailTest(){
        TileBag tileBag = new TileBag();
        Rack rack = new Rack(tileBag);
        assertEquals(-1, rack.getFirstFreeIndex());
    }

    @Test
    public void getRackCopyTest(){
        Rack rack = new Rack();
        rack.set(0, new Tile('A', 1));
        rack.set(1, new Tile('B', 1));
        rack.set(2, new Tile('C', 1));
        //rack.set(3, new Tile(' ', 1));
        //rack.set(4, new Tile(' ', 1));
        //rack.set(5, new Tile(' ', 1));
        //rack.set(6, new Tile(' ', 1));
        rack.shuffleRack();
        Rack rack2 = new Rack();
        rack2.getRackCopy(rack);
        for (int i = 0; i < 7; i++) {
            assertEquals(rack.getTile(i).getLetter(), rack2.getTile(i).getLetter());
        }
    }

}
