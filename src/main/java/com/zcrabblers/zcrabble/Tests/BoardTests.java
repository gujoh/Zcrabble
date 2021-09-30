package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.Board;
import com.zcrabblers.zcrabble.Model.Cell;
import com.zcrabblers.zcrabble.Model.CellTuple;
import com.zcrabblers.zcrabble.Model.Tile;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BoardTests {
    @Test
    public void testSize() throws FileNotFoundException {
        Board board = new Board("defaultBoard");
        board.selectBoard();
        assertTrue(board.matrix()[0].length == 15);
    }
    @Test
    public void testCalculateScore() throws FileNotFoundException {
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
        printBoard(boardTwo);
        printBoardPoints(boardTwo);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(boardTwo.countPoints(boardTwo.getNewCells(board)));

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
                        line.append(" ");
                    } else {
                        line.append(pBoard.matrix()[i - 1][j].getPlacedTile().getLetter());
                        line.append(" ");
                    }
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
    /*
    StringBuilder line = new StringBuilder();
        for (
    ArrayList<CellTuple> cellTuples : wordList) {
        for (CellTuple cellTuple : cellTuples) {
            line.append(cellTuple.getCell().getPlacedTile().getLetter());
        }
        System.out.println(line);
        line = new StringBuilder();
    }
     */
}
