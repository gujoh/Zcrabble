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
    // constructor takes a string in order to search for the matching text file
    // then calls the selectBoard function to fill the new board with cells
    public Board(String boardSelector){
        this.boardSelector = boardSelector;
    }
    //checks if the name matches the file
    //gets a new scanner and checks the first line for the boardsize
    //then iterates through the txt file and creates new cells for each matrix
    // each cell reads two numbers from the txt and creates a tile with the "empty" values of ' ' and 0
    public void selectBoard() throws FileNotFoundException {
        if(boardSelector.equals("defaultBoard")){
            File file = new File("src/main/resources/"+boardSelector);
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
    public int countPoint(Board tempBoard){
        int score = 0;
        Cell[][] tempBoardCells = tempBoard.matrix();
        for(int i = 0; i > tempBoardCells[0].length; i++){
            for(int j = 0; j > boardCells[0].length; j++){

            }
        }

        return score;
    }

    public void testBoard(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                System.out.println(boardCells[i][j].GetCellWordMultiplier() + boardCells[i][j].GetCellLetterMultiplier());
            }
        }
    }
    public Cell[][] matrix(){
        return boardCells;
    }
    private Board PlaceTile(Board board, int i, int j, Cell cell){
            board.matrix()[i][j] = cell;
        return board;
    }
}
