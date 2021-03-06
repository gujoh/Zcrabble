import com.zcrabblers.zcrabble.model.gameBoard.Board;
import com.zcrabblers.zcrabble.model.gameBoard.Cell;
import com.zcrabblers.zcrabble.model.gameBoard.CellTuple;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Tests methods in Board.
 * @author Martin Björklund.
 */
public class BoardTests {
    @Test
    public void testSize() {
        Board board = new Board();
        assertEquals(15, board.getBoardCells()[0].length);
    }

    @Test
    public void testCalculateScore() {
        Board board = new Board();
        for(int i = 0; i < board.getBoardCells().length; i++){
            for(int j = 0; j < board.getBoardCells().length; j++)
                if(!(7 > j && i == 8)) {
                    board.placeTile(i, j, new Tile('A', 2));
                }
        }
        Board boardTwo = new Board();
        for(int i = 0; i < boardTwo.getBoardCells().length; i++){
            for(int j = 0; j < boardTwo.getBoardCells().length; j++)
                if((7 > j && i == 8)) {
                    boardTwo.placeTile(i, j, new Tile('B', 2));
                }
                else{
                    boardTwo.placeTile(i, j, new Tile('A', 2));
                }
        }
        printBoardJustLetters(boardTwo);
    }

    @Test
    public void testBoard1()  {
        Board board = new Board();
        board.placeTile(1,5,new Tile('A',2));
        board.placeTile(1,6,new Tile('B',2));
        board.placeTile(1,7,new Tile('C',2));
        board.placeTile(1,8,new Tile('D',2));
        board.placeTile(1,9,new Tile('E',2));
        board.placeTile(2,9,new Tile('F',2));
        board.placeTile(3,9,new Tile('G',2));
        board.placeTile(4,8,new Tile('H',2));
        board.placeTile(5,8,new Tile('I',2));
        board.placeTile(2,7,new Tile('J',2));
        board.placeTile(3,7,new Tile('K',2));
        board.placeTile(2,5,new Tile('O',2));


        Board boardTwo = new Board();
        boardTwo.placeTile(1,5,new Tile('A',2));
        boardTwo.placeTile(1,6,new Tile('B',2));
        boardTwo.placeTile(1,7,new Tile('C',2));
        boardTwo.placeTile(1,8,new Tile('D',2));
        boardTwo.placeTile(1,9,new Tile('E',2));
        boardTwo.placeTile(2,9,new Tile('F',2));
        boardTwo.placeTile(3,9,new Tile('G',2));
        boardTwo.placeTile(4,8,new Tile('H',2));
        boardTwo.placeTile(5,8,new Tile('I',2));
        boardTwo.placeTile(2,7,new Tile('J',2));
        boardTwo.placeTile(3,7,new Tile('K',2));
        boardTwo.placeTile(2,5,new Tile('O',2));

        boardTwo.placeTile(3,5,new Tile('L',2));
        boardTwo.placeTile(3,6,new Tile('M',2));
        boardTwo.placeTile(3,8,new Tile('N',2));
        boardTwo.placeTile(3,9,new Tile('P',2));
        boardTwo.placeTile(3,10,new Tile('Q',2));
        boardTwo.placeTile(3,11,new Tile('R',2));
        int points = boardTwo.countPoints(board);
        assertEquals(46, points);

    }

    @Test
    public void testBoard2()  {
        Board board = new Board();
        board.placeTile(1,5,new Tile('A',2));
        board.placeTile(2,5,new Tile('B',2));
        board.placeTile(3,5,new Tile('C',2));
        board.placeTile(4,5,new Tile('D',2));
        board.placeTile(5,5,new Tile('E',2));
        board.placeTile(6,5,new Tile('F',2));
        board.placeTile(7,5,new Tile('G',2));
        board.placeTile(8,5,new Tile('H',2));
        board.placeTile(9,5,new Tile('I',2));
        board.placeTile(9,6,new Tile('J',2));
        board.placeTile(9,7,new Tile('K',2));
        board.placeTile(9,8,new Tile('O',2));
        board.placeTile(9,9,new Tile('O',2));
        board.placeTile(10,9,new Tile('P',2));
        board.placeTile(11,9,new Tile('S',2));
        board.placeTile(12,9,new Tile('A',2));
        board.placeTile(4,6,new Tile('R',2));
        board.placeTile(4,7,new Tile('G',2));
        board.placeTile(4,8,new Tile('N',2));
        board.placeTile(4,9,new Tile('Z',2));
        board.placeTile(6,6,new Tile('Y',2));
        board.placeTile(6,7,new Tile('Q',2));
        board.placeTile(6,8,new Tile('I',2));


        Board boardTwo = new Board();
        boardTwo.placeTile(1,5,new Tile('A',2));
        boardTwo.placeTile(2,5,new Tile('B',2));
        boardTwo.placeTile(3,5,new Tile('C',2));
        boardTwo.placeTile(4,5,new Tile('D',2));
        boardTwo.placeTile(5,5,new Tile('E',2));
        boardTwo.placeTile(6,5,new Tile('F',2));
        boardTwo.placeTile(7,5,new Tile('G',2));
        boardTwo.placeTile(8,5,new Tile('H',2));
        boardTwo.placeTile(9,5,new Tile('I',2));
        boardTwo.placeTile(9,6,new Tile('J',2));
        boardTwo.placeTile(9,7,new Tile('K',2));
        boardTwo.placeTile(9,8,new Tile('O',2));
        boardTwo.placeTile(9,9,new Tile('O',2));
        boardTwo.placeTile(10,9,new Tile('P',2));
        boardTwo.placeTile(11,9,new Tile('S',2));
        boardTwo.placeTile(12,9,new Tile('A',2));
        boardTwo.placeTile(4,6,new Tile('R',2));
        boardTwo.placeTile(4,7,new Tile('G',2));
        boardTwo.placeTile(4,8,new Tile('N',2));
        boardTwo.placeTile(4,9,new Tile('Z',2));
        boardTwo.placeTile(6,6,new Tile('Y',2));
        boardTwo.placeTile(6,7,new Tile('Q',2));
        boardTwo.placeTile(6,8,new Tile('I',2));

        boardTwo.placeTile(3,7,new Tile('L',2));
        boardTwo.placeTile(5,7,new Tile('M',2));
        boardTwo.placeTile(7,7,new Tile('N',2));
        boardTwo.placeTile(8,7,new Tile('P',2));
        boardTwo.placeTile(10,7,new Tile('R',2));
        int points = boardTwo.countPoints(board);
        assertEquals(36, points);

    }

    @Test
    public void testBoard3() {
        Board board = new Board();
        board.placeTile(1,5,new Tile('A',2));
        board.placeTile(2,5,new Tile('B',2));
        board.placeTile(3,5,new Tile('C',2));
        board.placeTile(4,5,new Tile('D',2));
        board.placeTile(5,5,new Tile('E',2));
        board.placeTile(6,5,new Tile('F',2));
        board.placeTile(7,5,new Tile('G',2));
        board.placeTile(8,5,new Tile('H',2));
        board.placeTile(9,5,new Tile('I',2));
        board.placeTile(9,6,new Tile('J',2));
        board.placeTile(9,7,new Tile('K',2));
        board.placeTile(9,8,new Tile('O',2));
        board.placeTile(9,9,new Tile('O',2));
        board.placeTile(10,9,new Tile('P',2));
        board.placeTile(11,9,new Tile('S',2));
        board.placeTile(12,9,new Tile('A',2));
        board.placeTile(4,6,new Tile('R',2));
        board.placeTile(4,7,new Tile('G',2));
        board.placeTile(4,8,new Tile('N',2));
        board.placeTile(4,9,new Tile('Z',2));
        board.placeTile(6,6,new Tile('Y',2));
        board.placeTile(6,7,new Tile('Q',2));
        board.placeTile(6,8,new Tile('I',2));
        board.placeTile(7,8,new Tile('I',2));
        board.placeTile(7,9,new Tile('K',2));
        board.placeTile(7,10,new Tile('Y',2));
        board.placeTile(7,11,new Tile('H',2));
        board.placeTile(8,6,new Tile('A',2));
        board.placeTile(8,4,new Tile('B',2));


        Board boardTwo = new Board();
        boardTwo.placeTile(1,5,new Tile('A',2));
        boardTwo.placeTile(2,5,new Tile('B',2));
        boardTwo.placeTile(3,5,new Tile('C',2));
        boardTwo.placeTile(4,5,new Tile('D',2));
        boardTwo.placeTile(5,5,new Tile('E',2));
        boardTwo.placeTile(6,5,new Tile('F',2));
        boardTwo.placeTile(7,5,new Tile('G',2));
        boardTwo.placeTile(8,5,new Tile('H',2));
        boardTwo.placeTile(9,5,new Tile('I',2));
        boardTwo.placeTile(9,6,new Tile('J',2));
        boardTwo.placeTile(9,7,new Tile('K',2));
        boardTwo.placeTile(9,8,new Tile('O',2));
        boardTwo.placeTile(9,9,new Tile('O',2));
        boardTwo.placeTile(10,9,new Tile('P',2));
        boardTwo.placeTile(11,9,new Tile('S',2));
        boardTwo.placeTile(12,9,new Tile('A',2));
        boardTwo.placeTile(4,6,new Tile('R',2));
        boardTwo.placeTile(4,7,new Tile('G',2));
        boardTwo.placeTile(4,8,new Tile('N',2));
        boardTwo.placeTile(4,9,new Tile('Z',2));
        boardTwo.placeTile(6,6,new Tile('Y',2));
        boardTwo.placeTile(6,7,new Tile('Q',2));
        boardTwo.placeTile(6,8,new Tile('I',2));
        boardTwo.placeTile(7,8,new Tile('I',2));
        boardTwo.placeTile(7,9,new Tile('K',2));
        boardTwo.placeTile(7,10,new Tile('Y',2));
        boardTwo.placeTile(7,11,new Tile('H',2));
        boardTwo.placeTile(8,6,new Tile('A',2));
        boardTwo.placeTile(8,4,new Tile('B',2));

        boardTwo.placeTile(3,7,new Tile('L',2));
        boardTwo.placeTile(5,7,new Tile('M',2));
        boardTwo.placeTile(7,7,new Tile('N',2));
        boardTwo.placeTile(8,7,new Tile('P',2));
        boardTwo.placeTile(10,7,new Tile('R',2));
        int points = boardTwo.countPoints(board);
        assertEquals(64, points);
    }

    @Test
    public void testBoard4() {
        Board board = new Board();
        board.placeTile(0,0,new Tile('A',2));
        board.placeTile(0,1,new Tile('B',2));
        board.placeTile(0,2,new Tile('C',2));
        board.placeTile(0,3,new Tile('D',2));
        board.placeTile(0,0,new Tile('E',2));
        board.placeTile(1,0,new Tile('G',2));
        board.placeTile(2,0,new Tile('F',2));
        board.placeTile(3,0,new Tile('G',2));

        Board boardTwo = new Board();
        boardTwo.placeTile(0,0,new Tile('A',2));
        boardTwo.placeTile(0,1,new Tile('B',2));
        boardTwo.placeTile(0,2,new Tile('C',2));
        boardTwo.placeTile(0,3,new Tile('D',2));
        boardTwo.placeTile(0,0,new Tile('E',2));
        boardTwo.placeTile(1,0,new Tile('G',2));
        boardTwo.placeTile(2,0,new Tile('F',2));
        boardTwo.placeTile(3,0,new Tile('G',2));

        boardTwo.placeTile(1,1,new Tile('Q',2));
        boardTwo.placeTile(2,1,new Tile('S',2));

        printBoard(boardTwo);
        int points = boardTwo.countPoints(board);
        assertEquals(24, points);
    }

    @Test
    public void testBoard5() {
        Board board = new Board();
        board.placeTile(14,14,new Tile('A',2));
        board.placeTile(14,13,new Tile('B',2));
        board.placeTile(14,12,new Tile('C',2));
        board.placeTile(14,11,new Tile('D',2));
        board.placeTile(14,14,new Tile('E',2));
        board.placeTile(13,14,new Tile('G',2));
        board.placeTile(12,14,new Tile('F',2));
        board.placeTile(11,14,new Tile('T',2));

        Board boardTwo = new Board();
        boardTwo.placeTile(14,14,new Tile('A',2));
        boardTwo.placeTile(14,13,new Tile('B',2));
        boardTwo.placeTile(14,12,new Tile('C',2));
        boardTwo.placeTile(14,11,new Tile('D',2));
        boardTwo.placeTile(14,14,new Tile('E',2));
        boardTwo.placeTile(13,14,new Tile('G',2));
        boardTwo.placeTile(12,14,new Tile('F',2));
        boardTwo.placeTile(11,14,new Tile('T',2));

        boardTwo.placeTile(12,13,new Tile('Q',2));
        boardTwo.placeTile(13,13,new Tile('S',2));

        printBoard(boardTwo);
        int points = boardTwo.countPoints(board);
        assertEquals(24, points);
    }

    @Test
    public void testBoard6() {
        Board board = new Board();
        board.placeTile(14,12,new Tile('C',2));
        board.placeTile(14,11,new Tile('D',2));
        board.placeTile(13,14,new Tile('T',2));
        board.placeTile(12,14,new Tile('F',2));
        board.placeTile(11,14,new Tile('G',2));

        Board boardTwo = new Board();
        boardTwo.placeTile(14,12,new Tile('C',2));
        boardTwo.placeTile(14,11,new Tile('D',2));
        boardTwo.placeTile(13,14,new Tile('T',2));
        boardTwo.placeTile(12,14,new Tile('F',2));
        boardTwo.placeTile(11,14,new Tile('G',2));

        boardTwo.placeTile(14,13,new Tile('B',2));
        boardTwo.placeTile(14,14,new Tile('S',2));

        printBoard(boardTwo);
        int points = boardTwo.countPoints(board);
        assertEquals(48, points);
    }
    @Test
    public void testBoard7() {
        Board board = new Board();
        board.placeTile(14,1,new Tile('C',2));
        board.placeTile(14,2,new Tile('D',2));
        board.placeTile(14,3,new Tile('T',2));
        board.placeTile(12,0,new Tile('F',2));
        board.placeTile(11,0,new Tile('G',2));

        Board boardTwo = new Board();
        boardTwo.placeTile(14,1,new Tile('C',2));
        boardTwo.placeTile(14,2,new Tile('D',2));
        boardTwo.placeTile(14,3,new Tile('T',2));
        boardTwo.placeTile(12,0,new Tile('F',2));
        boardTwo.placeTile(11,0,new Tile('G',2));

        boardTwo.placeTile(14,0,new Tile('B',2));
        boardTwo.placeTile(13,0,new Tile('S',2));

        printBoard(boardTwo);
        int points = boardTwo.countPoints(board);
        assertEquals(48, points);
    }

    @Test
    public void testRemoveTile() {
        Board board = new Board();
        board.placeTile(14,14,new Tile('S',2));
        assertEquals('S', board.getTileLetter(14,14));
        assertEquals(2, board.getTile(14, 14).getTileScore());
        board.removeTile(14,14);
        assertTrue(board.isCellEmpty(14,14));
    }

    @Test
    public void testSwitchTile() {
        Board board = new Board();
        board.placeTile(14,14,new Tile('S',2));
        board.placeTile(13,13,new Tile('A',7));
        board.switchTiles(14, 14, 13, 13);
        assertEquals('S', board.getTileLetter(13,13));//getTile(13, 13).getLetter());
        assertEquals(2, board.getTile(13, 13).getTileScore());
        assertEquals('A', board.getTileLetter(14,14));//getTile(14, 14).getLetter());
        assertEquals(7, board.getTile(14, 14).getTileScore());
    }
    @Test
    public void testMatrix()  {
        Board board = new Board();
        Cell cell = board.getBoardCells()[0][0];
        assertNotNull(cell);
    }

    @Test
    public void testGetNewCells() {
        Board board = new Board();

        board.placeTile(14,12,new Tile('C',2));
        board.placeTile(14,11,new Tile('D',2));
        board.placeTile(13,14,new Tile('T',2));
        board.placeTile(12,14,new Tile('F',2));
        board.placeTile(11,14,new Tile('G',2));

        Board boardTwo = new Board();
        boardTwo.placeTile(14,12,new Tile('C',2));
        boardTwo.placeTile(14,11,new Tile('D',2));
        boardTwo.placeTile(13,14,new Tile('T',2));
        boardTwo.placeTile(12,14,new Tile('F',2));
        boardTwo.placeTile(11,14,new Tile('G',2));

        boardTwo.placeTile(14,13,new Tile('B',2));
        boardTwo.placeTile(14,14,new Tile('S',5));

        List<CellTuple> celltuple = boardTwo.getNewCells(board);
        assertEquals(14, celltuple.get(0).getI());
        assertEquals(13, celltuple.get(0).getJ());
        assertEquals('B', celltuple.get(0).getTileLetter());
        assertEquals(2, celltuple.get(0).getTileScore());

        assertEquals(14, celltuple.get(1).getI());
        assertEquals(14, celltuple.get(1).getJ());
        assertEquals('S', celltuple.get(1).getTileLetter());
        assertEquals(5, celltuple.get(1).getTileScore());

    }



    public void printBoard(Board pBoard){
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < pBoard.getBoardCells().length + 1; i++){
            if(i < 11) {
                line.append(Math.abs(i - 1)).append(" ");
            }
            else line.append(i - 11).append(" ");
            for(int j = 0; j < pBoard.getBoardCells().length; j++){
                if(i == 0){
                    if(j > 0 && j < 10) {
                        line.append(j - 1);
                        line.append("   ");
                    }
                    if(j > 10){
                        line.append(j - 11);
                        line.append("    ");
                    }
                    if(j == 14){
                        line.append("4");}
                }
                else {
                    if (pBoard.getTileLetter(i-1,j) == ' '){//getTile(i-1,j).getLetter() == ' ') {
                        line.append(pBoard.getCellLetterMultiplier(i-1,j));//getBoardCells()[i - 1][j].GetCellLetterMultiplier());
                        line.append('_');
                        line.append(pBoard.getCellWordMultiplier(i - 1,j));//getBoardCells()[i - 1][j].GetCellWordMultiplier());
                        line.append(" ");
                    } else {
                        line.append(pBoard.getCellLetterMultiplier(i-1,j));//getBoardCells()[i - 1][j].GetCellLetterMultiplier());
                        line.append(pBoard.getTileLetter(i-1,j));//getTile(i-1,j).getLetter());
                        line.append(pBoard.getCellWordMultiplier(i - 1,j));//getBoardCells()[i - 1][j].GetCellWordMultiplier());
                        line.append(" ");
                    }
                }
            }
            System.out.println(line);
            line = new StringBuilder();
        }
    }
    public void printBoardJustLetters(Board pBoard){
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < pBoard.getBoardCells().length + 1; i++){
            if(i < 11) {
                line.append(Math.abs(i - 1)).append(" ");
            }
            else line.append(i - 11).append(" ");
            for(int j = 0; j < pBoard.getBoardCells().length; j++){
                if(i == 0){
                    if(j > 0 && j < 10) {
                        line.append(j - 1);
                        line.append(" ");
                    }
                    if(j > 10){
                        line.append(j - 11);
                        line.append(" ");
                    }
                    if(j == 14){
                        line.append("4");}
                }
                else {
                    if (pBoard.getTileLetter(i-1,j) == ' ') {
                        line.append('_');
                    } else {
                        line.append(pBoard.getTileLetter(i-1,j));
                    }
                    line.append(" ");
                }
            }
            System.out.println(line);
            line = new StringBuilder();
        }
    }

    public void printBoardPoints(Board pBoard){
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < pBoard.getBoardCells().length; i++){
            for(int j = 0; j < pBoard.getBoardCells().length; j++){
                String letterMult = String.valueOf(pBoard.getCellLetterMultiplier(i,j));//getBoardCells()[i][j].GetCellLetterMultiplier());
                line.append(letterMult).append(pBoard.getCellWordMultiplier(i,j));
                line.append(" ");
            }
            System.out.println(line);
            line = new StringBuilder();
        }
    }

    /*---- BoardCheckTests below ----*/

    @Test
    public void testRowCheck() {
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

        tempBoard.placeTile(7, 4, new Tile('S', 2));
        tempBoard.placeTile(7, 5, new Tile('C', 2));
        tempBoard.placeTile(7, 6, new Tile('R', 2));
        tempBoard.placeTile(7, 7, new Tile('A', 2));
        tempBoard.placeTile(7, 8, new Tile('B', 2));
        tempBoard.placeTile(7, 9, new Tile('B', 2));
        tempBoard.placeTile(7, 10, new Tile('L', 2));
        tempBoard.placeTile(7, 11, new Tile('E', 2));
        tempBoard.placeTile(7, 12, new Tile('R', 2));
        tempBoard.placeTile(7, 13, new Tile('S', 2));

        tempBoard.placeTile(2, 11, new Tile('F', 2));
        tempBoard.placeTile(3, 11, new Tile('O', 2));
        tempBoard.placeTile(4, 11, new Tile('R', 2));
        tempBoard.placeTile(5, 11, new Tile('E', 2));
        tempBoard.placeTile(6, 11, new Tile('V', 2));
        tempBoard.placeTile(7, 11, new Tile('V', 2));
        tempBoard.placeTile(8, 11, new Tile('E', 2));
        tempBoard.placeTile(9, 11, new Tile('R', 2));
        tempBoard.placeTile(10, 11, new Tile('R', 2));

        assertFalse(tempBoard.checkBoard(tempBoard, board));
    }


}