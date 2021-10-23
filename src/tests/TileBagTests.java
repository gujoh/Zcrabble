import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import org.junit.Test;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class TileBagTests {
    // checks some basic numbers if a bag with the correct string works
    // and makes sure the remaining tiles are correct
    @Test
    public void testTileBagNumbers() throws FileNotFoundException {
        TileBag tilebag = new TileBag();
        assertEquals(100, tilebag.remainingTiles());
        assertFalse(tilebag.isEmpty());
    }
    //checks if the read tiles have allowed letters and numbers
    @Test
    public void testTileBagLogic() throws FileNotFoundException {
        TileBag tilebag = new TileBag();
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_";
        while(!tilebag.isEmpty()){
            char letter = tilebag.getBag().peekFirst().getLetter();
            int score = tilebag.takeTile().getTileScore();
            assertTrue(validChars.contains(String.valueOf(letter)));
            assertTrue(score <= 10);
        }
    }

}
