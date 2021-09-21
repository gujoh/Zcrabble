package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Board {

    private Cell[][] boardCells;
    private String boardSelector;

    public Board(String boardSelector) throws FileNotFoundException {
        this.boardSelector = boardSelector;
        selectBoard();
    }
    private void selectBoard() throws FileNotFoundException {
        if(boardSelector.equals("defaultBoard")){
            File file = new File("src\\main\\resources\\"+boardSelector);
            Scanner scanner = new Scanner(file);
            int boardSize = scanner.nextInt();
            boardCells = new Cell[boardSize][boardSize];
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    int word = scanner.nextInt();
                    int letter = scanner.nextInt();
                    boardCells[i][j] = new Cell(word,letter, new Tile(' ',0));
                }
            }
        }
    }
    public void testBoard(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                System.out.println(boardCells[i][j].GetCellWordMultiplier() + boardCells[i][j].GetCellLetterMultiplier());
            }
        }
    }
    public Cell[][] Matrix(){
        return boardCells;
    }
    private Board PlaceTile(Board board, int i, int j, Cell cell){
            board.Matrix()[i][j] = cell;
        return board;
    }
}
