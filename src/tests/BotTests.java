import com.zcrabblers.zcrabble.model.gameBoard.board.Board;
import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import com.zcrabblers.zcrabble.model.observers.ITurnObservable;
import com.zcrabblers.zcrabble.model.players.bot.Bot;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BotTests {

    @Test
    public void testPlay() {

        ITurnObservable observer = () -> false;
        TileBag bag = new TileBag();
        Rack rack = new Rack(bag);
        Bot bot = new Bot(0,rack,observer);
        Board board = new Board();
        Board tempBoard = new Board();
        tempBoard.copyBoardCells(board,false);

        for (int i = 0; i < 10; i++) {
            bot.beginTurn(tempBoard);
            assertTrue(tempBoard.checkBoard(tempBoard,board));
            assertEquals(tempBoard.getNewCells(board).size(), amountOfBlancRackTiles(bot));
            bot.fillRack(bag);
            bot.addScore(bot.getScore());
            printBoard(tempBoard);

            board.copyBoardCells(tempBoard,false);
            tempBoard.copyBoardCells(board,false);
        }



    }

    //Prints the board for debugging purposes
    private void printBoard(Board board) {
        char[][] boardPrint = new char[15][15];
        for (int i = 0; i < board.getBoardCells().length; i++) {
            for (int j = 0; j < board.getBoardCells()[0].length; j++) {
                boardPrint[i][j] = board.getBoardCells()[i][j].getTileLetter();
            }
        }
        for (char[] row : boardPrint) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(" ");
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
    // - Test that the bot uses the correct number of tiles from rack
    // - Test that all the tiles in a play came are the same ones that are missing in rack
    // - test that the bot can take a new turn with the same rack if a play is rejected. It can't and it won't
    // - No tests for optimal play for now
    // - more tests probably. the most important thing is that the board played and the rack is always correct.
}
