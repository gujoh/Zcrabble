package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Board {

    private Cell[][] boardCells = new Cell[15][15];
    private String boardSelector;

    public Board(String boardSelector){
        this.boardSelector = boardSelector;
    }
    //testing board
    public Board(Board board){
        Cell[][] cells = board.boardCells;
        Cell[][] newCells = new Cell[cells.length][cells.length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                newCells[i][j] = new Cell(cells[i][j]);
            }

        }
    }
    private void selectBoard() throws FileNotFoundException {
        if(boardSelector.equals("defaultBoard")){
            File file = new File("src\\main\\resources\\"+boardSelector);
            Scanner scanner = new Scanner(file);
            scanner.hasNextLine();
            int boardSize = Integer.parseInt(scanner.next());
            while(scanner.hasNextLine()){
                char readLetter = scanner.next().toCharArray()[0];
                int readScore = Integer.parseInt(scanner.next());
                int readNumberOf = Integer.parseInt(scanner.next());
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
