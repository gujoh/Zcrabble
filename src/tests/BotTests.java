import com.zcrabblers.zcrabble.model.gameBoard.board.Board;
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
        Bot bot = new Bot(37,rack,observer);
        Board board = new Board();

        board.placeTile(7, 4, new Tile('S', 2));
        board.placeTile(7, 5, new Tile('C', 2));
        board.placeTile(7, 6, new Tile('R', 2));
        board.placeTile(7, 7, new Tile('A', 2));
        board.placeTile(7, 8, new Tile('B', 2));
        board.placeTile(7, 9, new Tile('B', 2));
        board.placeTile(7, 10, new Tile('L', 2));
        board.placeTile(7, 11, new Tile('E', 2));
        board.placeTile(7, 12, new Tile('R', 2));
        board.placeTile(7, 13, new Tile('S', 2));

        Board tempBoard = new Board();
        tempBoard.copyBoardCells(board,false);

        bot.beginTurn(tempBoard);

        assertTrue(tempBoard.checkBoard(tempBoard,board));
        assertEquals(tempBoard.getNewCells(board).size(), amountOfBlancRackTiles(rack));


    }

    private int amountOfBlancRackTiles (Rack rack){
        int tileCount = 0;
        for (Tile t : rack.getTiles()){
            if (t.getLetter() == ' '){
                tileCount++;
            }
        }
        return tileCount;
    }
    //TODO tests:
    // - Test that the bot uses the correct number of tiles from rack
    // - Test that all the tiles in a play came are the same ones that are missing in rack
    // - test that the bot can take a new turn with the same rack if a play is rejected. It can't and it won't
    // - No tests for optimal play for now
    // - more tests probably. the most important thing is that the board played and the rack is always correct.
}
