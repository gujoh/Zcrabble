import com.zcrabblers.zcrabble.model.gameBoard.Board;
import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import com.zcrabblers.zcrabble.model.observers.ITurnObservable;
import com.zcrabblers.zcrabble.model.players.bot.Bot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BotTests {

    @Test
    public void testPlay() {

        ITurnObservable observer = () -> false;
        TileBag bag = new TileBag();
        Rack rack = new Rack(bag);
        Bot bot = new Bot(0,rack,observer);
        assertTrue(bot.getRackTile(3).getLetter() != ' ');
        Tile tile3 = bot.getRackTile(3);
        bot.removeRackTile(3);
        assertEquals(' ', bot.getRackTile(3).getLetter());
        bot.placeRackTile(3,tile3);
        assertEquals(tile3,bot.getRackTile(3));
        Board board = new Board();
        Board tempBoard = new Board();
        tempBoard.copyBoardCells(board,false);

        for (int i = 0; i < 6; i++) {
            bot.beginTurn(tempBoard);
            assertTrue(tempBoard.checkBoard(tempBoard,board));
            assertEquals(tempBoard.getNewCells(board).size(), amountOfBlancRackTiles(bot));
            bot.fillRack(bag);
            bot.addScore(bot.getScore());
            //printBoard(tempBoard);

            board.copyBoardCells(tempBoard,false);
            tempBoard.copyBoardCells(board,false);
        }
    }


    private int amountOfBlancRackTiles (Bot bot){
        int tileCount = 0;
        for (Tile t : bot.getRack().getTiles()){
            if (t.getLetter() == ' '){
                tileCount++;
            }
        }
        return tileCount;
    }
    //TODO tests:
    // - Test that all the tiles in a play came are the same ones that are missing in rack
    // - test that the bot can take a new turn with the same rack if a play is rejected. It can't and it won't
    // - No tests for optimal play for now
    // - more tests probably. the most important thing is that the board played and the rack is always correct.
}
