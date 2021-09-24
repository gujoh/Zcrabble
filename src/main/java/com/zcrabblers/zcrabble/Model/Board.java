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
    public int countPoints(Board tempBoard){
        boolean ignoreI = false;
        List<CellTuple> newCells = getNewCells(tempBoard);
        List<ArrayList<Cell>> wordList = new ArrayList<>();
        if(newCells.size() > 1){
            if(newCells.get(0).getI() == newCells.get(1).getI()){
                ignoreI = true;
            }
        }
        for(int x = 0; x < newCells.size(); x++){
            if(x >= 1 && !ignoreI){
                int wordCount = 0;
                int j = newCells.get(x).getJ();
                for (Cell[] boardCell : boardCells) {
                    if (boardCell[j].getTile().getLetter() != ' ') {
                        wordList.get(wordCount).add(boardCell[j]);
                        wordCount++;
                    }
                }
            }
            if(x >= 1 && ignoreI){
                int wordCount = 0;
                int i = newCells.get(x).getI();
                for(int j = 0; j < boardCells.length; j++){
                    if(boardCells[i][j].getTile().getLetter() != ' '){
                        wordList.get(wordCount).add(boardCells[i][j]);
                        wordCount++;
                    }
                }
            }
        }

        return helpCalculateScore(wordList);
    }
    private int helpCalculateScore(List<ArrayList<Cell>> wordList){
        int score;
        int letterScore = 0;
        int totalWordMultiplier = 1;
        Cell cell = null;
        for (ArrayList<Cell> cells : wordList) {
            for (int i = 0; i < cells.size(); i++)
                cell = cells.get(i);
            letterScore += cell.GetCellLetterMultiplier() * cell.getTile().getTileScore();
            totalWordMultiplier *= cell.GetCellWordMultiplier();
        }
        score = letterScore * totalWordMultiplier;
        return score;
    }
    private List<CellTuple> getNewCells(Board tempBoard){
        List<CellTuple> newCells = new ArrayList<>();
        Cell[][] tempBoardCells = tempBoard.matrix();
        for(int i = 0; i > tempBoardCells[0].length; i++){
            for(int j = 0; j > boardCells[0].length; j++){
                if(!tempBoardCells[i][j].equals(boardCells[i][j])){
                    newCells.add(new CellTuple(i,j,boardCells[i][j]));
                }
            }
        }
        return newCells;
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
    private class CellTuple{
        private int i;
        private int j;
        private Cell cell;

        private CellTuple(int i, int j, Cell cell){
            this.i = i;
            this.j = j;
            this.cell = cell;
        }
        private int getI(){return i;}
        private int getJ(){return j;}
        private Cell getCell(){return cell;}

    }
        /* perhaps unneeded generalization
    private List<Cell> findword(int x, boolean ignoreI, List<CellTuple> newCells){
        List<Cell> word = new ArrayList<>();
        if(x >= 1 && ignoreI == true){
            int i = newCells.get(x).getI();
            for(int j = 0; j < boardCells.length; j++){
                if(boardCells[i][j].getTile().getLetter() != ' '){
                    word.add(boardCells[i][j]);
                }
            }
        }
        return word;
    }
    */
}

