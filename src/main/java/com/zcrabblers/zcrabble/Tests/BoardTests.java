package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Board;
import com.zcrabblers.zcrabble.Model.Cell;
import com.zcrabblers.zcrabble.Model.CellTuple;
import com.zcrabblers.zcrabble.Model.Tile;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BoardTests {
    @Test
    public void testSize() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        assertEquals(15, board.matrix()[0].length);
    }
    @Test
    public void testCalculateScore() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        for(int i = 0; i < board.matrix().length; i++){
            for(int j = 0; j < board.matrix().length; j++)
                if((7 > j && i == 8)) {
                }
                else{
                    board.placeTile(i, j, new Tile('A', 2));
                }
        }
        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
        for(int i = 0; i < boardTwo.matrix().length; i++){
            for(int j = 0; j < boardTwo.matrix().length; j++)
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
    public void TestBoard1() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
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


        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
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
        int points = boardTwo.countPoints(boardTwo.getNewCells(board));
        assertEquals(46, points);

    }
    @Test
    public void TestBoard2() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
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



        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
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
        int points = boardTwo.countPoints(boardTwo.getNewCells(board));
        assertEquals(36, points);

    }
    @Test
    public void TestBoard3() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
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



        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
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
        int points = boardTwo.countPoints(boardTwo.getNewCells(board));
        assertEquals(64, points);
    }
    @Test
    public void TestBoard4() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        board.placeTile(0,0,new Tile('A',2));
        board.placeTile(0,1,new Tile('B',2));
        board.placeTile(0,2,new Tile('C',2));
        board.placeTile(0,3,new Tile('D',2));
        board.placeTile(0,0,new Tile('E',2));
        board.placeTile(1,0,new Tile('G',2));
        board.placeTile(2,0,new Tile('F',2));
        board.placeTile(3,0,new Tile('G',2));

        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
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
        int points = boardTwo.countPoints(boardTwo.getNewCells(board));
        assertEquals(24, points);
    }
    @Test
    public void TestBoard5() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        board.placeTile(14,14,new Tile('A',2));
        board.placeTile(14,13,new Tile('B',2));
        board.placeTile(14,12,new Tile('C',2));
        board.placeTile(14,11,new Tile('D',2));
        board.placeTile(14,14,new Tile('E',2));
        board.placeTile(13,14,new Tile('G',2));
        board.placeTile(12,14,new Tile('F',2));
        board.placeTile(11,14,new Tile('T',2));

        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
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
        int points = boardTwo.countPoints(boardTwo.getNewCells(board));
        assertEquals(24, points);
    }
    @Test
    public void TestBoard6() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        board.placeTile(14,12,new Tile('C',2));
        board.placeTile(14,11,new Tile('D',2));
        board.placeTile(13,14,new Tile('T',2));
        board.placeTile(12,14,new Tile('F',2));
        board.placeTile(11,14,new Tile('G',2));

        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
        boardTwo.placeTile(14,12,new Tile('C',2));
        boardTwo.placeTile(14,11,new Tile('D',2));
        boardTwo.placeTile(13,14,new Tile('T',2));
        boardTwo.placeTile(12,14,new Tile('F',2));
        boardTwo.placeTile(11,14,new Tile('G',2));

        boardTwo.placeTile(14,13,new Tile('B',2));
        boardTwo.placeTile(14,14,new Tile('S',2));

        printBoard(boardTwo);
        int points = boardTwo.countPoints(boardTwo.getNewCells(board));
        assertEquals(48, points);
    }
    @Test
    public void TestBoard7() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        board.placeTile(14,1,new Tile('C',2));
        board.placeTile(14,2,new Tile('D',2));
        board.placeTile(14,3,new Tile('T',2));
        board.placeTile(12,0,new Tile('F',2));
        board.placeTile(11,0,new Tile('G',2));

        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
        boardTwo.placeTile(14,1,new Tile('C',2));
        boardTwo.placeTile(14,2,new Tile('D',2));
        boardTwo.placeTile(14,3,new Tile('T',2));
        boardTwo.placeTile(12,0,new Tile('F',2));
        boardTwo.placeTile(11,0,new Tile('G',2));

        boardTwo.placeTile(14,0,new Tile('B',2));
        boardTwo.placeTile(13,0,new Tile('S',2));

        printBoard(boardTwo);
        int points = boardTwo.countPoints(boardTwo.getNewCells(board));
        assertEquals(48, points);
    }
    @Test
    public void TestRemoveTile() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        board.placeTile(14,14,new Tile('S',2));
        assertEquals('S', board.getTile(14, 14).getLetter());
        assertEquals(2, board.getTile(14, 14).getTileScore());
        board.removeTile(14,14);
        assertTrue(board.isCellEmpty(14,14));
    }
    @Test
    public void testSwitchTile() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        board.placeTile(14,14,new Tile('S',2));
        board.placeTile(13,13,new Tile('A',7));
        board.switchTiles(14, 14, 13, 13);
        assertEquals('S', board.getTile(13, 13).getLetter());
        assertEquals(2, board.getTile(13, 13).getTileScore());
        assertEquals('A', board.getTile(14, 14).getLetter());
        assertEquals(7, board.getTile(14, 14).getTileScore());
    }
    @Test
    public void testMatrix() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        Cell cell = board.matrix()[0][0];
        assertNotNull(cell);
    }
    @Test
    public void testGetNewCells() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();

        board.placeTile(14,12,new Tile('C',2));
        board.placeTile(14,11,new Tile('D',2));
        board.placeTile(13,14,new Tile('T',2));
        board.placeTile(12,14,new Tile('F',2));
        board.placeTile(11,14,new Tile('G',2));

        Board boardTwo = new Board("defaultBoard");
        boardTwo.selectBoard();
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
        assertEquals('B', celltuple.get(0).getCell().getPlacedTile().getLetter());
        assertEquals(2, celltuple.get(0).getCell().getPlacedTile().getTileScore());

        assertEquals(14, celltuple.get(1).getI());
        assertEquals(14, celltuple.get(1).getJ());
        assertEquals('S', celltuple.get(1).getCell().getPlacedTile().getLetter());
        assertEquals(5, celltuple.get(1).getCell().getPlacedTile().getTileScore());

    }

    public void printBoard(Board pBoard){
        StringBuilder line = new StringBuilder();
        for(int i = 0; i < pBoard.matrix().length + 1; i++){
            if(i < 11) {
                line.append(Math.abs(i - 1)).append(" ");
            }
            else line.append(i - 11).append(" ");
            for(int j = 0; j < pBoard.matrix().length; j++){
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
                    if (pBoard.matrix()[i-1][j].getPlacedTile().getLetter() == ' ') {
                        line.append(pBoard.matrix()[i - 1][j].GetCellLetterMultiplier());
                        line.append('_');
                        line.append(pBoard.matrix()[i - 1][j].GetCellWordMultiplier());
                        line.append(" ");
                    } else {
                        line.append(pBoard.matrix()[i - 1][j].GetCellLetterMultiplier());
                        line.append(pBoard.matrix()[i - 1][j].getPlacedTile().getLetter());
                        line.append(pBoard.matrix()[i - 1][j].GetCellWordMultiplier());
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
        for(int i = 0; i < pBoard.matrix().length + 1; i++){
            if(i < 11) {
                line.append(Math.abs(i - 1)).append(" ");
            }
            else line.append(i - 11).append(" ");
            for(int j = 0; j < pBoard.matrix().length; j++){
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
                    if (pBoard.matrix()[i-1][j].getPlacedTile().getLetter() == ' ') {
                        line.append('_');
                    } else {
                        line.append(pBoard.matrix()[i - 1][j].getPlacedTile().getLetter());
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
        for(int i = 0; i < pBoard.matrix().length; i++){
            for(int j = 0; j < pBoard.matrix().length; j++){
                String letterMult = String.valueOf(pBoard.matrix()[i][j].GetCellLetterMultiplier());
                line.append(letterMult).append(pBoard.matrix()[i][j].GetCellWordMultiplier());
                line.append(" ");
            }
            System.out.println(line);
            line = new StringBuilder();
        }
    }
}
