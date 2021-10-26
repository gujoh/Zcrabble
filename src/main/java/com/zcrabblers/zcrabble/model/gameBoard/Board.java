package com.zcrabblers.zcrabble.model.gameBoard;

import com.zcrabblers.zcrabble.model.Dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO split this class into:
// - BoardMutations, for all methods that makes a mutated copy of the board
// - BoardChecks, for all validations of a board
// - Scoring, for all methods that help score a given play
// - Board, for the obvious board stuff
/**
 *The board on which the game is played, it's made up of a matrix of cells
 * @author Gustaf Jonasson, Ole  Fjeldså, Martin Björklund.
 * @see Cell
 */
public class Board {
    private final Dictionary dict = Dictionary.getInstance();
    private Cell[][] boardCells;

    /**
     * The board constructor is called with a string it will search resources for a matching txt file to load
     * the board layout from
     */
    public Board(){
        try {
            fillBoard();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called in the constructor.
     * Fills the board with cells based on  a txt file called defaultBoard.
     * @throws FileNotFoundException throws an error if the reading of the txt is incorrect
     */
    private void fillBoard() throws FileNotFoundException {
            File file = new File("src/main/resources/defaultBoard");
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

    public boolean botBoardCheck(Board board){
        return checkCol(board);
    }
    /**
     * Checks that all words on the board is valid and connected to each other
     * @param board
     * @param tempBoard
     * @return true/false validBoard
     */
    public boolean checkBoard(Board tempBoard,Board board) {
        return (checkCoherence(board)&&checkCol(tempBoard)&& checkRow(tempBoard));
    }

    /**
     * @return returns the cell matrix of the board
     */
    public Cell[][] getBoardCells(){
        return boardCells;
    }

    /**
     * @param row row part of index
     * @param col column part of index
     * @return returns the word multiplier on a given index on the board
     */
    public int getCellWordMultiplier(int row, int col){
        return boardCells[row][col].getCellWordMultiplier();
    }

    /**
     * @param row row part of index
     * @param col column part of index
     * @return returns the letter multiplier on a given index on the board.
     */
    public int getCellLetterMultiplier(int row,int col){return boardCells[row][col].getCellLetterMultiplier();}

    /**
     * @param row row part of index
     * @param col column part of index
     * @return returns false if there is a letter tile on the cell
     */
    public boolean cellIsEmpty(int row, int col){return getTile(row,col).getLetter() == ' ';}

    /**
     * @param row row part of index
     * @param col column part of index
     * @return returns the char on the tile at the given index(row, col)
     */
    public char getTileLetter(int row, int col){return getTile(row,col).getLetter();}
    /**
     * switches two tiles on a board given their positions
     * @param i1 i-coordinate of the first tile
     * @param j1 j-coordinate of the first tile
     * @param i2 i-coordinate of the second tile
     * @param j2 j-coordinate of the second tile
     */
    public void switchTiles(int i1, int j1, int i2, int j2){
        Tile tile = boardCells[i1][j1].getPlacedTile();
        boardCells[i1][j1].setTile(boardCells[i2][j2].getPlacedTile());
        boardCells[i2][j2].setTile(tile);
    }

    /**
     * @param i corresponds to the position in the first list
     * @param j corresponds to the position in the second list
     * @return returns the tile of the given cell
     */
    public Tile getTile(int i, int j){
        return boardCells[i][j].getPlacedTile();
    }

    /**
     * @param i i corresponds to the position in the first list
     * @param j j corresponds to the position in the second list
     * places a tile in the given position
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

    /**
     * Copies the cells on a board
     * @param board the Board to be copied
     */
    public void copyBoardCells(Board board,boolean copyAllMultipliers){
        Cell[][] tempCell = new Cell[board.getBoardCells().length][board.getBoardCells()[0].length];
        for (int i = 0; i < board.getBoardCells().length; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {
                Cell newCell = board.getBoardCells()[i][j];
                if (copyAllMultipliers){
                    tempCell[i][j] = new Cell(board.getCellWordMultiplier(i,j),newCell.getCellLetterMultiplier(), newCell.getPlacedTile());
                }else{
                    if (newCell.isEmpty()) {
                        tempCell[i][j] = new Cell(board.getCellWordMultiplier(i,j), newCell.getCellLetterMultiplier(), newCell.getPlacedTile());
                    } else tempCell[i][j] = new Cell(1, 1, newCell.getPlacedTile());
                }
            }
        }
        boardCells = tempCell;
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


    /*--- ScorePlay below ---*/

    /**
     * counts the point on a board any play gives, called on the newest board and given a list of new cells
     * @param board a board that gets compared to this board in order to get the cells that were placed this round.
     * @return returns the score any given play will yield
     * @see CellTuple
     */
    public int countPoints(Board board){
        List<CellTuple> newCells = this.getNewCells(board);
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
                                    cells.get(x).getCell().getCellLetterMultiplier();
                            // and add the wordmultiplier
                            totalWordMultiplier = totalWordMultiplier * cells.get(x).getCell().getCellWordMultiplier();
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

    /*--- BoardChecks  below ---*/

    //TODO containsLetter should be replaced with isCellEmpty in all methods, no point in keeping both.

    /*--- Method for checking that all words in columns are valid. ---*/
    private boolean checkCol(Board board) {

        boolean colIsValid = true;
        StringBuilder word = new StringBuilder();

        for (int col = 0; col < board.getBoardCells().length; col++) {
            if (word.length() > 1) {
                colIsValid = dict.checkWord(word.toString());
            }
            word.delete(0,word.length());
            for (int row = 0; row < board.getBoardCells().length; row++) {

                //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                if (containsLetter(board, row, col) &&
                        (row==0?containsLetter(board, row+1, col ):containsLetter(board, row-1, col ) ||
                                containsLetter(board, row==14?row:row+1, col))) {
                    word.append(board.getBoardCells()[row][col].getPlacedTile().getLetter());
                }
                //If there already is a String in word and there is no letter on the current cell,
                // the String is finished, and will be checked, then deleted from word
                if (word.length() > 1 && !containsLetter(board, row, col)) {
                    colIsValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                if (!colIsValid) {
                    return false;
                }
            }
        }

        return colIsValid;
    }

    /*--- Method for checking that all words in rows are valid. ---*/
    private boolean checkRow(Board board){
        boolean rowIsValid = true;
        StringBuilder word = new StringBuilder();

        for (int row = 0; row < board.getBoardCells().length; row++) {
            if (word.length() > 1) {
                rowIsValid = dict.checkWord(word.toString());
            }
            word.delete(0,word.length());
            for (int col = 0; col < board.getBoardCells().length; col++) {

                //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                if (containsLetter(board, row, col) && (col==0?containsLetter(board, row,col+1):containsLetter(board, row,col-1) || containsLetter(board, row, col==14?col:col+1))) {
                    word.append(board.getBoardCells()[row][col].getPlacedTile().getLetter());
                }
                //If there already is a String in word and there is no letter on the current cell,
                // the String is finished, and will be checked, then deleted from word
                if (word.length() > 1 && !containsLetter(board, row, col)) {
                    rowIsValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                if (!rowIsValid) {
                    return false;
                }
            }
        }
        return rowIsValid;
    }

    //Checks if all letters on the board are in contact with each other and everything is in contact with the middle.
    private  boolean checkCoherence(Board board){
        List<CellTuple> newCells = getNewCells(board);

        //If there are no new cells then the turn counts as a pass, and there is nothing to check.
        if(newCells.size() == 0){
            return true;
        }

        int neighbourCount;
        int oldCellCount = 0;
        boolean rowOrColValid = checkRowAndColCoherence(newCells);

        //Checks if all new cells have a neighbour.
        for(CellTuple cell : newCells){
            neighbourCount = 0;
            for(int i = -1; i < 2; i++){
                for(int j = -1; j < 2; j++){
                    int row = cell.getI() + i;
                    int col = cell.getJ() + j;
                    if(row <= 14 && row >= 0 && col <= 14 && col >= 0 && !(row == cell.getI() && col == cell.getJ())){ //If we are not checking "ourselves" and we are within the board.
                        if((row == cell.getI() || col == cell.getJ())  && !isCellEmpty(row,col)){ //We do not check diagonal cells.
                            neighbourCount++;
                            if(getTile(row,col).getLetter() == board.getTile(row,col).getLetter()){
                                oldCellCount++;
                            }
                        }
                    }
                }
            }
            if (neighbourCount == 0){
                return false;
            }
        }

        //If all new cells have neighbours, there is at least one old cell connected to the new cells, rows and
        //columns are valid, and there is a letter in the middle cell, then the play is valid.
        if(oldCellCount > 0 && rowOrColValid && containsLetter(this,7,7)){
            return true;
        }
        else{ //If it is the first round of play, oldCellCount will be 0 and there will not be a tile on the middle cell.
            return oldCellCount == 0 && rowOrColValid && board.isCellEmpty(7, 7) && containsLetter(this, 7, 7);
        }
    }

    //Checks if all new cells are either on one row or one column and that the cells are coherent along that row or column.
    private boolean checkRowAndColCoherence(List<CellTuple> newCells){
        if(newCells.size() <= 1) {
            return true;
        }

        //Checking that all y coordinates (I) are the same if the first two y coordinates are.
        if(newCells.get(0).getI() == newCells.get(1).getI()){
            for(int i = 1; i < newCells.size()-1; i++){
                CellTuple cell = newCells.get(i);
                CellTuple previousCell = newCells.get(i-1);
                if(!(cell.getI() == previousCell.getI())){
                    return false;
                }
            }

            //Checking that the cells are coherent along the x coordinates.
            for(int i = newCells.get(0).getJ(); i < newCells.get(newCells.size()-1).getJ(); i++){
                Cell cell = boardCells[newCells.get(0).getI()][i];
                if(cell.isEmpty()){
                    return false;
                }
            }
            return true;
        }

        //Checking that all x coordinates (J) are the same if the first two x coordinates are.
        if(newCells.get(0).getJ() == newCells.get(1).getJ()){
            for(int i = 1; i < newCells.size()-1; i++){
                CellTuple cell = newCells.get(i);
                CellTuple previousCell = newCells.get(i-1);
                if(!(cell.getJ() == previousCell.getJ())){
                    return false;
                }
            }

            //Checking that the cells are coherent along the y coordinates.
            for(int i = newCells.get(0).getI(); i < newCells.get(newCells.size()-1).getI();i++){
                Cell cell = boardCells[i][newCells.get(0).getJ()];
                if(cell.isEmpty()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /*--- Checks if a cell contains a letter tile ---*/
    private static boolean containsLetter (Board board,int row, int col){
        return board.getBoardCells()[row][col].getPlacedTile().getLetter() != ' ';
    }


}

