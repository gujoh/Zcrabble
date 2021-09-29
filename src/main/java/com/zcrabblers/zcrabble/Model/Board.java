package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {

    private Cell[][] boardCells;
    private final String boardSelector;
    // constructor takes a string in order to search for the matching text file
    // then calls the selectBoard function to fill the new board with cells
    public Board(String boardSelector){
        this.boardSelector = boardSelector;
    }
    //checks if the name matches the file
    //gets a new scanner and checks the first line for the board size
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
    /* countPoints is called on the board and given a list of the new cells will return the number of points
    the given play is worth */
    public int countPoints(List<CellTuple> newCells){
        //since any scrabble play can only be made fully vertically or fully horizontally
        // we only want to check any row that has multiple new tiles placed only once
        //ignoreI will be used to make sure of that
        boolean ignoreI = false;
        //increased after we add a word to the list
        int wordCount = 0;
        //a list with a list of cellTuples, a cell tuple is a cell with its coordinates
        List<ArrayList<CellTuple>> wordList = new ArrayList<>();
        //if only one tile was placed it doesn't matter we ignore any rows since they will be checked once either way
        if(newCells.size() > 1){
            //if two tiles share the same J coordinate means they are on the same row
            //then it means we should not check that row twice
            //hence "ignoreI" or ignore row I is false
            if(newCells.get(0).getI() == newCells.get(1).getI()){
                ignoreI = true;
            }
        }
        //goes through each of the new tiles "x" is the current tile
        for(int x = 0; x < newCells.size(); x++){
            // i and j are the coordinate of the current tile
            int i = newCells.get(x).getI();
            int j = newCells.get(x).getJ();
            //we always want to check each row at least once so if we are checking the first new cell we can check all directions
            //after which, if we are not checking the first cell we don't want to iterate over the row with multiple new cells
            if(x == 0 || ignoreI){
                wordList.add(new ArrayList<>());
                //we add the iIterator to the i coordinate of the current tile so we iterate from the position we are at
                int iIterator = 0;
                //when we have found the full word stop becomes true
                boolean stop = false;
                //when we have found all the cells belonging to the word "above" the cell we started with, we then go downward
                boolean up = true;
                // it's worth noting when iterating over i what we are actually doing is checking everything in the same row of j
                // so iterating over i really means checking j and the reverse is true aswell
                while(i + iIterator < boardCells.length && !stop){
                    //we only want to add the cell if the letter of that cell is not empty
                    if(boardCells[i+iIterator][j].getPlacedTile().getLetter() != ' ') {
                        //when adding cells we make sure to save the coordinates in a "CellTuple"
                        wordList.get(wordCount).add(new CellTuple(i+iIterator,j,boardCells[i+iIterator][j]));
                        // if we are going "up" or in other words we've not changed directions yet we go +
                        // if we have changed direction we go further in that direction and go -
                        if(up){iIterator++;}
                        else {iIterator--;}
                    }
                    if(boardCells[i+iIterator][j].getPlacedTile().getLetter() == ' ' && !up){
                        stop = true;
                    }
                    if(boardCells[i+iIterator][j].getPlacedTile().getLetter() == ' ' && up){
                        iIterator = -1;
                        up = false;
                    }
                }

                wordCount++;
            }
            if(x == 0 || !ignoreI){
                wordList.add(new ArrayList<>());
                int jIterator = 0;

                boolean stop = false;
                boolean up = true;

                while(j + jIterator < boardCells.length && !stop){
                    if(boardCells[i][j+jIterator].getPlacedTile().getLetter() != ' ') {
                        wordList.get(wordCount).add(new CellTuple(i,j+jIterator, boardCells[i][j+jIterator]));
                        if(up){jIterator++;}
                        else {jIterator--;}
                    }
                    if(boardCells[i][j+jIterator].getPlacedTile().getLetter() == ' ' && !up){
                        stop = true;
                    }
                    if(boardCells[i][j+jIterator].getPlacedTile().getLetter() == ' ' && up){
                        jIterator = -1;
                        up = false;
                    }
                }

                wordCount++;
            }
        }
        StringBuilder line = new StringBuilder();
        for (ArrayList<CellTuple> cellTuples : wordList) {
            for (CellTuple cellTuple : cellTuples) {
                line.append(cellTuple.getCell().getPlacedTile().getLetter());
            }
            System.out.println(line);
            line = new StringBuilder();
        }
        return helpCalculateScore(wordList, newCells);
    }
    private List<ArrayList<CellTuple>> helpBuildWords (List<CellTuple> newCells, boolean ignoreI,List<ArrayList<CellTuple>> wordList, int wordCount, int i, int j ){
        wordList.add(new ArrayList<>());
        //we add the iIterator to the i coordinate of the current tile so we iterate from the position we are at
        int iIterator = 0;
        int jIterator = 0;
        //when we have found the full word stop becomes true
        boolean stop = false;
        //when we have found all the cells belonging to the word "above" the cell we started with, we then go downward
        boolean up = true;
        // it's worth noting when iterating over i what we are actually doing is checking everything in the same row of j
        // so iterating over i really means checking j and the reverse is true aswell
        while(i + iIterator < boardCells.length && j + jIterator < boardCells.length && !stop){
            //we only want to add the cell if the letter of that cell is not empty
            if(boardCells[i+iIterator][j+jIterator].getPlacedTile().getLetter() != ' ') {
                //when adding cells we make sure to save the coordinates in a "CellTuple"
                wordList.get(wordCount).add(new CellTuple(i+iIterator,j+jIterator,boardCells[i+iIterator][j+jIterator]));
                // if we are going "up" or in other words we've not changed directions yet we go +
                // if we have changed direction we go further in that direction and go -
                if(up){
                    if (ignoreI) iIterator++;
                    if (!ignoreI) jIterator++;
                }
                else {
                    if (ignoreI) iIterator--;
                    if (!ignoreI) jIterator--;
                }
            }
            if(boardCells[i+iIterator][j+jIterator].getPlacedTile().getLetter() == ' ' && !up){
                stop = true;
            }
            if(boardCells[i+iIterator][j+jIterator].getPlacedTile().getLetter() == ' ' && up){
                if (ignoreI) iIterator = -1;
                if (!ignoreI) jIterator = -1;
                up = false;
            }
        }
        return wordList;
    }

    private int helpCalculateScore(List<ArrayList<CellTuple>> wordList, List<CellTuple> newCells){
        int score = 0;
        int letterScore = 0;
        int totalWordMultiplier = 1;
        for (ArrayList<CellTuple> cells : wordList) {
            for (int x = 0; x < cells.size(); x++) {
                if(cells.size() > 1) {
                    letterScore += cells.get(x).getCell().getPlacedTile().getTileScore();
                    for (CellTuple newCell : newCells) {
                        if (newCell.getI() == cells.get(x).getI()
                                && newCell.getJ() == cells.get(x).getJ()) {
                            letterScore += (cells.get(x).getCell().getPlacedTile().getTileScore() *
                                    cells.get(x).getCell().GetCellLetterMultiplier()) - 2;
                            totalWordMultiplier = totalWordMultiplier * cells.get(x).getCell().GetCellWordMultiplier();
                        }
                    }
                }
            }
            System.out.println(letterScore);
            score += letterScore * totalWordMultiplier;
            totalWordMultiplier = 1;
            letterScore = 0;
        }
        return score;
    }
    public List<CellTuple> getNewCells(Board tempBoard){
        List<CellTuple> newCells = new ArrayList<>();
        Cell[][] tempBoardCells = tempBoard.matrix();
        for(int i = 0; i < tempBoardCells[0].length; i++){
            for(int j = 0; j < boardCells[0].length; j++){
                if(tempBoardCells[i][j].getPlacedTile().getLetter() != boardCells[i][j].getPlacedTile().getLetter()){
                    newCells.add(new CellTuple(i,j,boardCells[i][j]));
                }
            }
        }
        return newCells;
    }
    public Cell[][] matrix(){
        return boardCells;
    }
    public void placeTile( int i, int j, Tile tile){
            boardCells[i][j].setTile(tile);
    }
    public boolean isCellEmpty(int x, int y){
        return boardCells[x][y].isEmpty();
    }
}
/*
    public void printBoard(Board pBoard){
        String line = "";
        for(int i = 0; i < pBoard.matrix().length + 1; i++){
            if(i < 11) {
                line += Math.abs(i-1)+ " ";
            }
            else line += i -11 + " ";
            for(int j = 0; j < pBoard.matrix().length; j++){
                if(i == 0){
                    if(j > 0 && j < 10) {
                        line += j-1;
                        line += " ";
                    }
                    if(j > 10){
                        line += j - 11;
                        line += " ";
                    }
                    if(j == 14){line += "4";}
                }
                else {
                    if (pBoard.matrix()[i-1][j].getPlacedTile().getLetter() == ' ') {
                        line += '_';
                        line += " ";
                    } else {
                        line += pBoard.matrix()[i-1][j].getPlacedTile().getLetter();
                        line += " ";
                    }
                }
            }
            System.out.println(line);
            line = "";
        }
    }
    public void printBoardPoints(Board pBoard){
        String line = "";
        for(int i = 0; i < pBoard.matrix().length; i++){
            for(int j = 0; j < pBoard.matrix().length; j++){
                String letterMult = String.valueOf(pBoard.matrix()[i][j].GetCellLetterMultiplier());
                line += letterMult + pBoard.matrix()[i][j].GetCellWordMultiplier();
                line += " ";
            }
            System.out.println(line);
            line = "";
        }
    }

 */

