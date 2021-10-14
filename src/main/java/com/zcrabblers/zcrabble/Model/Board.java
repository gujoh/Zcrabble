package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO make a board deep copy

/**
 *
 */
public class Board {
    private final Dictionary dict = Dictionary.getInstance();
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

    /**
     * called after creating a new board taking the inputted string and using it in this method
     * to fill its board with cells
     * see src/main/resources/ for the files
     * @throws FileNotFoundException
     * @return returns a board with cells filled with information from the corresponding txt file
     */
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

    public void copyBoardCells(Board board){
        Cell[][] tempCell = new Cell[board.getBoardCells().length][board.getBoardCells()[0].length];
        for (int i = 0; i < board.getBoardCells().length; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {
                Cell newCell = board.getBoardCells()[i][j];
                if(newCell.isEmpty()){
                    tempCell[i][j] = new Cell(newCell.GetCellWordMultiplier(),newCell.GetCellLetterMultiplier(), newCell.getPlacedTile());
                }
                else tempCell[i][j] = new Cell(1,1, newCell.getPlacedTile());
            }
        }
        boardCells = tempCell;
    }

    /**
     * counts the point on a board any play gives, called on the newest board and given a list of new cells
     * @param newCells a List of the new cells added to the board, see getNewCells to find these
     *                 newCells is a cellTuple which carries the position of the cell on the board but also
     *                 it's tile, and the cell value
     * @return returns the score any given play will yield
     * @see CellTuple
     */
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
            wordList.add(new ArrayList<>());
            helpBuildWords(ignoreI,wordList,wordCount,i,j);
            //when we've added a word increment the counter
            wordCount++;
            //for the first node we want to check both horizontal and vertical
            if(x == 0){
                wordList.add(new ArrayList<>());
                helpBuildWords(!ignoreI,wordList,wordCount,i,j);
                wordCount++;
            }
        }
        return helpCalculateScore(wordList, newCells);
    }

    /**
     * helper function to count points that is used to find newly created words in any given play
     * it doesn't return anything since a reference is passed and that is mutated
     * @param ignoreI used to know if all the tiles were placed vertically or horizontally
     *                in order not to count the same word multiple times
     * @param wordList the passed reference that the words will be added to
     * @param wordCount a counter to keep track of how many words have been added
     * @param i the vertical counter
     * @param j the horizontal counter
     * @see CellTuple
     */
    private void helpBuildWords (boolean ignoreI ,List<ArrayList<CellTuple>> wordList, int wordCount, int i, int j ){
        //we add the Iterators to the i and j coordinate of the current cell, so we iterate from the position we are at
        int iIterator = 0;
        int jIterator = 0;
        //when we have found the full word stop becomes true
        boolean stop = false;
        //when we have found all the cells belonging to the word "above" the cell we started with, we then go downward
        boolean up = true;
        // it's worth noting when iterating over i what we are actually doing is checking everything in the same row of j
        // so iterating over i really means checking j and the reverse is true as well
        while(true){
            if(i + iIterator < boardCells.length && j + jIterator < boardCells.length &&
                    i + iIterator >= 0 && j +jIterator >= 0) {
                //we only want to add the cell if the letter of that cell is not empty
                if (boardCells[i + iIterator][j + jIterator].getPlacedTile().getLetter() != ' ') {
                    //when adding cells we make sure to save the coordinates in a "CellTuple"
                    wordList.get(wordCount).add(new CellTuple(i + iIterator, j + jIterator, boardCells[i + iIterator][j + jIterator]));
                }
                //when we've checked both ways and then find and empty cell we stop the loop since we've found our word
                if (boardCells[i + iIterator][j + jIterator].getPlacedTile().getLetter() == ' ' && !up) {
                    break;
                }
                //first time we find an empty cell we changed direction
                if (boardCells[i + iIterator][j + jIterator].getPlacedTile().getLetter() == ' ' && up) {
                    if (ignoreI) iIterator = -1;
                    if (!ignoreI) jIterator = -1;
                    up = false;
                }
                // if we are going "up" or in other words we've not changed directions yet, we go +
                // if we have changed direction we go further in that direction and go -
                //ignoreI exists to make sure we iterate over the right Iterator
                else {
                    if (up) {
                        if (ignoreI) iIterator++;
                        if (!ignoreI) jIterator++;
                    } else {
                        if (ignoreI) iIterator--;
                        if (!ignoreI) jIterator--;
                    }
                }
            }
            else if(up){
                if (ignoreI) iIterator = -1;
                if (!ignoreI) jIterator = -1;
                up = false;
            }
            else break;
        }
    }

    /**
     * a helper function to countPoints that returns the point value of a set of words
     * @param wordList The list of words which points are to be counted
     * @param newCells a List of the new cells added to the board, see getNewCells to find these
     *      *          newCells is a cellTuple which carries the position of the cell on the board but also
     *      *          it's tile, and the cell value
     * @return returns the final point value
     * @see CellTuple
     */
    private int helpCalculateScore(List<ArrayList<CellTuple>> wordList, List<CellTuple> newCells){
        // score keeps track of the total score of the entire sets of words
        int score = 0;
        // letter score is the score per word
        int letterScore = 0;
        // totalWordMultiplier as the name suggests is the all the multipliers multiplied together
        int totalWordMultiplier = 1;
        // goes through each word in word list
        for (ArrayList<CellTuple> cells : wordList) {
            //each word goes through each letter or Cell
            for (int x = 0; x < cells.size(); x++) {
                //a word that has size one is not technically a scrabble word and is worth 0 points
                if(cells.size() > 1) {
                    //in order we don't add any cell twice addedCell checks if we've added a newly placed tile
                    boolean addedCell = false;
                    //bonus squares are only counted if it is a new cell
                    // we go through each new cell and see if any match the cell we are currently looking at
                    for (CellTuple newCell : newCells) {
                        //if the positions match
                        if (newCell.getI() == cells.get(x).getI()
                                && newCell.getJ() == cells.get(x).getJ()) {
                            //add the new cell multiplied by the letter score
                            letterScore += cells.get(x).getCell().getPlacedTile().getTileScore() *
                                    cells.get(x).getCell().GetCellLetterMultiplier();
                            // and add the wordmultiplier
                            totalWordMultiplier = totalWordMultiplier * cells.get(x).getCell().GetCellWordMultiplier();
                            addedCell = true;
                        }
                    }
                    // if a cell wasn't added
                    if(!addedCell) letterScore += cells.get(x).getCell().getPlacedTile().getTileScore();
                }
            }
            score += letterScore * totalWordMultiplier;
            letterScore = 0;
            totalWordMultiplier = 1;
        }
        return score;
    }

    /**
     * returns a list of the new cells on a board comparing the old and new board
     * call this method on the new board and send the old board as a parameter
     * @param tempBoard the older board with fewer cells being compared to the current object
     * @return returns all the cells which the old board did not have
     *         compared to the new board in the form of cellTuples
     * @see CellTuple
     */
    // returns a list of new cells comparing the differance between two boards
    public List<CellTuple> getNewCells(Board tempBoard){
        //CellTuples have i,j coordinate and a Cell
        List<CellTuple> newCells = new ArrayList<>();
        Cell[][] tempBoardCells = tempBoard.getBoardCells();
        // double for loop checking each individual cell if they have the same tile
        for(int i = 0; i < tempBoardCells[0].length; i++){
            for(int j = 0; j < boardCells[0].length; j++){
                if(tempBoardCells[i][j].getPlacedTile().getLetter() != boardCells[i][j].getPlacedTile().getLetter()){
                    // add if they are different
                    newCells.add(new CellTuple(i,j,boardCells[i][j]));
                }
            }
        }
        return newCells;
    }

    /**
     * @return returns the cell matrix of the board
     */
    public Cell[][] getBoardCells(){
        return boardCells;
    }

    /**
     * switches two tiles on a board given their positions
     * @param i1 i coordinate of the first tile
     * @param j1 j coordinate of the first tile
     * @param i2 i coordinate of the second tile
     * @param j2 j coordinate of the second tile
     */
    public void switchTiles(int i1, int j1, int i2, int j2){
        Tile tile = boardCells[i1][j1].getPlacedTile();
        boardCells[i1][j1].setTile(boardCells[i2][j2].getPlacedTile());
        boardCells[i2][j2].setTile(tile);
    }

    /**
     * @param i i corresponds to the position in the first list
     * @param j j corresponds to the position in the second list
     * @return returns the tile of the given cell
     */
    public Tile getTile(int i, int j){
        return boardCells[i][j].getPlacedTile();
    }
    /**
     * @param i i corresponds to the position in the first list
     * @param j j corresponds to the position in the second list
     * @return places a tile in the given position
     */
    public void placeTile( int i, int j, Tile tile){
            boardCells[i][j].setTile(tile);
    }
    /**
     * removes the tile of the given position
     * @param i i corresponds to the position in the first list
     * @param j j corresponds to the position in the second list
     */
    public void removeTile(int i, int j){
        boardCells[i][j].removeTile();
    }
    /**
     * @param i i corresponds to the position in the first list
     * @param j j corresponds to the position in the second list
     * @return returns a true boolean if the cell is empty
     */
    public boolean isCellEmpty(int i, int j){
        return boardCells[i][j].isEmpty();
    }

    /**
     * Returns the length of one side of the board, both sides should always be the same length.
     * @return Number of rows in the board.
     */
    public int getSize(){
        return boardCells.length;
    }

    /*--- BoardChecks in progress below ---*/
    //TODO checkCoherence
    //TODO cleanup after the move from BoardCheck, make sure all checks are done on tempBoard.
    //TODO containsLetter should be replaced with isCellEmpty in all methods, no point in keeping both.

    /**
     * Checks that all words on the board is valid and connected to each other
     *
     * @param board
     * @param tempBoard
     * @return true/false validBoard
     */
    public boolean checkBoard(Board tempBoard,Board board) {
        return (checkCoherence(tempBoard,board)&&checkCol(tempBoard)&& checkRow(tempBoard));
    }

    /*--- Method for checking that all words in columns are valid. ---*/
    private boolean checkCol(Board board) {
        boolean colAreIndeedValid = true;
        StringBuilder word = new StringBuilder();

        for (int col = 0; col < board.getBoardCells().length; col++) {
            if (word.length() > 1) {
                colAreIndeedValid = dict.checkWord(word.toString());
            }
            word.delete(0,word.length());
            for (int row = 0; row < board.getBoardCells().length; row++) {

                //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                if (containsLetter(board, row, col) && (containsLetter(board, row==0?row:row-1, col ) || containsLetter(board, row==14?row:row+1, col))) {
                    word.append(board.getBoardCells()[row][col].getPlacedTile().getLetter());
                }
                //If there already is a String in word and there is no letter on the current cell,
                // the String is finished, and will be checked, then deleted from word
                if (word.length() > 1 && !containsLetter(board, row, col)) {
                    colAreIndeedValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                if (!colAreIndeedValid) {
                    return false;
                }
            }
        }
        System.out.println("col");
        return colAreIndeedValid;
    }

    /*--- Method for checking that all words in rows are valid. ---*/
    private boolean checkRow(Board board){
        boolean rowAreIndeedValid = true;
        StringBuilder word = new StringBuilder();

        for (int row = 0; row < board.getBoardCells().length; row++) {
            if (word.length() > 1) {
                rowAreIndeedValid = dict.checkWord(word.toString());
            }
            word.delete(0,word.length());
            for (int col = 0; col < board.getBoardCells().length; col++) {

                //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                if (containsLetter(board, row, col) && (containsLetter(board, row, col==0?col:col-1) || containsLetter(board, row, col==14?col:col+1))) {
                    word.append(board.getBoardCells()[row][col].getPlacedTile().getLetter());
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
        }
        System.out.println("row");
        return rowAreIndeedValid;
    }

    //TODO
    /*--- Checks if all letters on the board are in contact with each other and everything is in contact with the middle. ---*/
    private  boolean checkCoherence(Board tempBoard, Board board){


        int numberOfLetters=0;
        for (int row = 0; row < tempBoard.getBoardCells().length; row++) {
            for (int col = 0; col < tempBoard.getBoardCells().length; col++) {
                if (containsLetter(tempBoard, row, col)){
                    numberOfLetters++;
                    if (numberOfLetters>1){break;}
                }
            }
        }
        //checks if all new cells is either on one row or one column
        List<CellTuple> newCells = tempBoard.getNewCells(board);

        for (int i = 0; i <newCells.size() ; i++) {
            if (!(newCells.get(i).getI()==newCells.get(i== newCells.size()-1?i:i+1).getI())){
                for (int j = 0; j <newCells.size() ; j++) {
                    if (!(newCells.get(j).getJ()==newCells.get(j==newCells.size()-1?j:j+1).getJ())){
                       return false;
                    }
                }
            }
        }
       /*



        checkConnection
            for every new tile check the cells directly over/under and left/right of it, there should be at least one connection to an old cell or the middle
            control row+-1 and col +-1 make sure not to get out of bounds error, for + 1: row==14?row:row+1.
        for (int i = 0; i <newCells.size() ; i++) {
            newCells.get(i).getI();
        }


        read newCells, see witch direction the new word is going, new cells must go in one direction
            If there is only one new cell the word is just an appendix, check that it is connected to something.
            If all i values are the same, the word is horizontal
            If all j values are the same, the word is vertical
            If both i and j values differ the new board is not valid.

        if there is a gap in the new tiles it must be filled with old tiles.
         */
        return (containsLetter(tempBoard, 7, 7)&&numberOfLetters!=1);
    }

    /*--- Checks if a cell contains a letter tile ---*/
    private static boolean containsLetter (Board board,int row, int col){
        return board.getBoardCells()[row][col].getPlacedTile().getLetter() != ' ';
    }

}

