package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Board {

    private static final Dictionary dict = Dictionary.getInstance();
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
        int wordCount = 0;
        List<CellTuple> newCells = getNewCells(tempBoard);
        List<ArrayList<CellTuple>> wordList = new ArrayList<>();
        if(newCells.size() > 1){
            if(newCells.get(0).getI() == newCells.get(1).getI()){
                ignoreI = true;
            }
        }
        for(int x = 0; x < newCells.size(); x++){
            int i = newCells.get(x).getI();
            int j = newCells.get(x).getJ();

            if(x == 0 || ignoreI){
                wordList.add(new ArrayList<>());
                int iIterator = 0;
                boolean stop = false;
                boolean up = true;

                while(i + iIterator < boardCells.length && !stop){
                    if(boardCells[i+iIterator][j].getPlacedTile().getLetter() != ' ') {
                        wordList.get(wordCount).add(new CellTuple(i+iIterator,j,boardCells[i+iIterator][j]));
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
        String line = new String();
        for(int i = 0; i < wordList.size(); i++){
            for(int j = 0; j < wordList.get(i).size(); j++){
                line += wordList.get(i).get(j).getCell().getPlacedTile().getLetter();
            }
            System.out.println(line);
            line = "";
        }
        return helpCalculateScore(wordList, newCells);
    }

    private int helpCalculateScore(List<ArrayList<CellTuple>> wordList, List<CellTuple> newCells){
        int score = 0;
        int letterScore = 0;
        int totalWordMultiplier = 1;
        for (ArrayList<CellTuple> cells : wordList) {
            for (int x = 0; x < cells.size(); x++) {
                if(cells.size() > 1) {
                    letterScore += cells.get(x).getCell().getPlacedTile().getTileScore();
                    for(int y = 0; y < newCells.size(); y++){
                        if(newCells.get(y).getI() == cells.get(x).getI()
                        && newCells.get(y).getJ() == cells.get(x).getJ()  ){
                            score += cells.get(x).getCell().getPlacedTile().getTileScore() *(
                            cells.get(x).getCell().GetCellLetterMultiplier()) - 2;
                            totalWordMultiplier = totalWordMultiplier * cells.get(x).getCell().GetCellWordMultiplier();
                            System.out.println(letterScore);
                        }
                    }
                }
                score += letterScore * totalWordMultiplier;
                totalWordMultiplier = 1;
                letterScore = 0;
            }

        }
        return score;
    }

    private boolean checkMatch(){
        boolean yes = false;

        return yes;
    }

    private List<CellTuple> getNewCells(Board tempBoard){
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

    public boolean isCellEmpty(int x, int y){
        return boardCells[x][y].isEmpty();
    }

    /*--- BoardChecks in progress below ---*/
    //TODO checkCoherence

    /**
     * Checks that all words on the board is valid and connected to each other
     * @param board
     * @return true/false validBoard
     */
    public boolean checkBoard(Board board) {
        return (checkRow(board)&&checkCol(board)&& checkCoherence(board));
    }

    /*--- Method for checking that all words in columns are valid. ---*/
    private static boolean checkCol(Board board) {
        boolean colAreIndeedValid = true;
        StringBuilder word = new StringBuilder();

        for (int col = 0; col < board.matrix().length; col++) {
            if (word.length() > 0) {
                colAreIndeedValid = dict.checkWord(word.toString());
                word.delete(0, word.length());
            }
            for (int row = 0; row < board.matrix().length; row++) {

                //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                if (containsLetter(board, row, col) && containsLetter(board, row==0?row:row-1, col ) || containsLetter(board, row==14?row:row+1, col)) {
                    word.append(board.matrix()[col][row].getPlacedTile().getLetter());
                }
                //If there already is a String in word and there is no letter on the current cell,
                // the String is finished, and will be checked, then deleted from word
                if (word.length() > 1 && !containsLetter(board, col, row)) {
                    colAreIndeedValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                if (!colAreIndeedValid) {
                    return false;
                }
            }
        }return colAreIndeedValid;
    }

    /*--- Method for checking that all words in rows are valid. ---*/
    private static boolean checkRow(Board board){
        boolean rowAreIndeedValid = true;
        StringBuilder word = new StringBuilder();

        for (int row = 0; row < board.matrix().length; row++) {
            if (word.length() > 0) {
                rowAreIndeedValid = dict.checkWord(word.toString());
                word.delete(0, word.length());
            }
            for (int col = 0; col < board.matrix().length; col++) {

                //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                if (containsLetter(board, row, col) && containsLetter(board, row, col==0?col:col-1) || containsLetter(board, row, col==14?col:col+1)) {
                    word.append(board.matrix()[row][col].getPlacedTile().getLetter());
                }
                //If there already is a String in word and there is no letter on the current cell,
                // the String is finished, and will be checked, then deleted from word
                if (word.length() > 1 && !containsLetter(board, row, col)) {
                    rowAreIndeedValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                if (!rowAreIndeedValid) {
                    return false;
                }
            }
        }return rowAreIndeedValid;
    }

    //TODO checkCoherence
    /*--- Checks if all letters on the board are in contact with each other. ---*/
    private static boolean checkCoherence(Board board){
        /*
        check that the center is not empty.
        check that every tile is connected to the center
         */
        if (!containsLetter(board,7,7))
            return false;
        for (int i = 0; i <12 ; i++) {

        }










        return true;
    }

    /*--- Checks if a cell contains a letter tile ---*/
    private static boolean containsLetter (Board board,int i, int j){
        return board.matrix()[i][j].getPlacedTile().getLetter() != ' ';
    }

}

