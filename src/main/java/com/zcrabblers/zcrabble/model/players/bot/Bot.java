package com.zcrabblers.zcrabble.model.players.bot;

import com.zcrabblers.zcrabble.model.gameBoard.Board;
import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import com.zcrabblers.zcrabble.model.observers.ITurnObservable;
import com.zcrabblers.zcrabble.model.observers.TurnObserver;
import com.zcrabblers.zcrabble.model.players.IPlayers;

import java.util.*;


//TODO Make the bot evaluate words by score, not by length. "A well-contested Scrabble game should end with around 600 to 700 total points" Now it is 600! HUZZA!
//TODO Functional decomposition on everything, but particularly scrabbleWord
//TODO comment and test everything, tidy up.
//TODO make bot faster!
//TODO when someone wins the game should stop, if bots play each other they just keep going.
//TODO different difficulty levels
//TODO what happens if the bot makes a mistake?


/**
 * Bot takes a Board and makes a play on it
 */
public class Bot implements IPlayers {

    private int score;
    private final Rack rack;
    private final TurnObserver observer = new TurnObserver();

    public Bot(int score, Rack rack, ITurnObservable sub) {
        this.score = score;
        this.rack = rack;
        observer.addSubscriber(sub);
    }

    /**
     * Adds points of the latest play to score
     *
     * @param score the points to be added to score
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Fills the rack with tiles from the TileBag
     *
     * @param bag the Bag to get tiles from
     */
    @Override
    public void fillRack(TileBag bag) {
        rack.fillRack(bag);
    }

    /**
     * Removes a tile from the rack
     *
     * @param x index of tile to be removed
     */
    @Override
    public void removeRackTile(int x) {
        rack.remove(x);
    }

    /**
     * Places a tile to x index on the rack
     *
     * @param x    Int index of rack
     * @param tile Tile to be placed
     */
    @Override
    public void placeRackTile(int x, Tile tile) {
        rack.set(x, tile);
    }

    /**
     * @return the bots score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the bots rack
     */
    @Override
    public Rack getRack() {
        return rack;
    }

    /**
     * @param x Int index of rack
     * @return the tile at x index on the rack
     */
    @Override
    public Tile getRackTile(int x) {
        return rack.getTile(x);
    }

    /**
     * Mutates a Board to include the bots best play
     * Notifies subscribers when it is done
     *
     * @param board the Board to be mutated
     */
    @Override
    public void beginTurn(Board board) {

        if (board.getBoardCells()[7][7].isEmpty()) {
            board.copyBoardCells(takeFirstTurn(board), true);
        } else {
            takeTurn(board);
        }
        observer.notifySubscribers();
    }

    /**
     * Creates board copies with the best horizontal and vertical plays on a given board, compares them and mutates given board to the highest scoring one
     *
     * @param board the Board to be mutated
     */
    private void takeTurn(Board board) {

        //Temporary boards and racks for comparison

        Rack tempRack = new Rack();
        tempRack.getRackCopy(rack);
        Rack horizontalRack = new Rack();
        horizontalRack.getRackCopy(rack);
        Rack verticalRack = new Rack();
        verticalRack.getRackCopy(rack);

        Board horizontalBoard = new Board();
        horizontalBoard.copyBoardCells(board, true);
        Board verticalBoard = new Board();
        verticalBoard.copyBoardCells(board, true);


        makeHorizontalBoard(horizontalBoard, horizontalRack);
        makeVerticalBoard(verticalBoard, verticalRack);
        chooseBoard(horizontalBoard, horizontalRack, verticalBoard, verticalRack, board);
    }

    //selects the best board to play
    private void chooseBoard(Board horizontalBoard, Rack horizontalRack, Board verticalBoard, Rack verticalRack, Board board) {
        if (horizontalBoard.countPoints(board) > verticalBoard.countPoints(board)) {
            board.copyBoardCells(horizontalBoard, true);
            rack.getRackCopy(horizontalRack);

        } else {
            board.copyBoardCells(verticalBoard, true);
            rack.getRackCopy(verticalRack);
        }
    }

    //writes a horizontal word to board
    private void makeHorizontalBoard(Board horizontalBoard, Rack horizontalRack) {
        horizontalBoard.copyBoardCells(scrabbleWord(horizontalBoard, horizontalRack), true);
    }

    //tilts then mirrors board, writes a horizontal word to it, then mirrors and tilts it back
    private void makeVerticalBoard(Board verticalBoard, Rack verticalRack) {
        verticalBoard.tiltPiHalf(verticalBoard);
        verticalBoard.mirrorAroundCol7(verticalBoard);
        verticalBoard.copyBoardCells(scrabbleWord(verticalBoard, verticalRack), true);
        verticalBoard.mirrorAroundCol7(verticalBoard);
        verticalBoard.tilt3PiHalf(verticalBoard);
    }

    //Prints the board for debugging purposes
    private void printBoard(Board board) {
        char[][] boardPrint = new char[15][15];
        for (int i = 0; i < board.getBoardCells().length; i++) {
            for (int j = 0; j < board.getBoardCells()[0].length; j++) {
                boardPrint[i][j] = board.getBoardCells()[i][j].getTileLetter();
            }
        }
        for (char[] row : boardPrint) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(" ");
    }

    //prints the rack for debugging purposes
    private void printRack(Rack rack) {
        System.out.println(getRackString(rack));
    }

    //TODO the longest word is not necessarily the highest scoring word, if there is time this should be used as a metric instead.
    //Takes a Board and a rack and returns a board with the longest horizontal word it can write
    private Board scrabbleWord(Board board, Rack rack1) {

        //Temporary boards and racks for comparison

        Board currentBoard = new Board();
        currentBoard.copyBoardCells(board, true);
        Board bestBoard = new Board();
        bestBoard.copyBoardCells(board, true);
        String bestWord = "";
        Rack tempRack = new Rack();
        tempRack.getRackCopy(rack1);
        Rack bestRack = new Rack();
        bestRack.getRackCopy(rack1);

        //variables

        int spaceBehind;
        int spaceAhead;
        ArrayList<String> writable;
        StringBuilder letters = new StringBuilder();
        StringBuilder tempRackString = new StringBuilder(getRackString(rack1));
        char[] wordSpace;

        //method starts here

        for (int row = 0; row < board.getBoardCells().length; row++) {
            for (int col = 0; col < board.getBoardCells()[0].length; col++) {

                if (!board.getBoardCells()[row][col].isEmpty()) {

                    searchForLetters(board, letters, tempRackString, row, col);

                    spaceBehind = checkSpaceBehind(board, row, col);        //SpaceBehind is negative. this breaks everything.
                    spaceAhead = checkSpaceAhead(board, row, col, letters);
                    wordSpace = createWordSpace(spaceBehind, spaceAhead, letters);
                    writable = getCheckedWritable(wordSpace, tempRackString.toString(), spaceBehind, spaceAhead, letters.toString(), bestWord);
                    sort(writable);

                    for (String s : writable) {
                        System.out.println(s);
                        tempRack.getRackCopy(rack1);
                        int j = 0;
                        for (int i = col - s.indexOf(letters.toString()); i < col; i++) {
                            writeToBoard(getRackIndex(tempRack, s.charAt(j)), row, i, currentBoard, tempRack);
                            j++;
                        }
                        int k = s.indexOf(String.valueOf(letters)) + letters.length();

                        for (int i = col + letters.length(); i < col + s.length() - s.indexOf(String.valueOf(letters)); i++) {
                            writeToBoard(getRackIndex(tempRack, s.charAt(k)), row, i, currentBoard, tempRack);
                            k++;
                        }
                        if (!currentBoard.botBoardCheck(currentBoard)) {
                            currentBoard.copyBoardCells(board, true);
                            tempRack.getRackCopy(rack1);
                        } else {
                            bestBoard.copyBoardCells(currentBoard, true);
                            currentBoard.copyBoardCells(board, true);
                            bestWord = s;
                            bestRack.getRackCopy(tempRack);
                            tempRack.getRackCopy(rack1);
                            break;
                        }
                    }
                    col += letters.length();
                    letters.delete(0, letters.length());
                    tempRackString = new StringBuilder(getRackString(rack1));
                }
            }

        }
        rack1.getRackCopy(bestRack);
        return bestBoard;//bestBoard;
    }

    //TODO bot can no longer copy letter tiles from board. Witch is good! However it should never try to either. the problem seems come from scrabbleWord. Also, the bot should never be able to steal random tiles in the first place.
    //Switches place between a tile in a given position on a rack and a tile in a given position on a board.
    private void writeToBoard(int rackX, int boardRow, int boardCol, Board board, Rack currentRack) {
        Tile tile = board.getTile(boardCol, boardRow);
        board.placeTile(boardRow, boardCol, currentRack.getTile(rackX));
        if (tile.getLetter() == ' ') {
            currentRack.set(rackX, tile);
        } else {
            currentRack.set(rackX, new Tile(' ', 0));
        }

    }

    //TODO Sleep, then fix this
    //returns the first index of the rack containing a given char
    private int getRackIndex(Rack tempRack, char c) {
        int index = 0;
        for (Tile t : tempRack.getTiles()) {
            if (t.getLetter() == c) {
                return index;
            }
            index++;
        }
        return 6;   //TODO this works but is hella ugly
    }

    //Returns an ArrayList of strings in order from longest to shortest.
    private static void sort(ArrayList<String> writable) { //TODO this is bubble sort because im lazy, make a quick sort.

        for (int i = 0; i < writable.size() - 1; i++) {
            for (int j = 0; j < writable.size() - i - 1; j++) {
                if (writable.get(j).length() < writable.get(j + 1).length()) {
                    // swap arr[j+1] and arr[j]
                    String temp = writable.get(j);
                    writable.set(j, writable.get(j + 1));
                    writable.set(j + 1, temp);
                }
            }
        }
    }

    //TODO this method should be able to test for all positions of "letters" in the String,
    // right now it only checks if the word fits with the first instance of "letters" in String
    //Takes in a list of Strings and runs a series of checks on it to shorten the list of words that needs to be further processed.
    //The checks in order are:
    //
    //eliminates all words that does not:
    //have more characters than bestWord
    //s can not be the String the bot is adding to
    //s must contain the letter(s) in order
    //fit inside the size of the array
    //have enough space before and after the letter(s)
    private static ArrayList<String> getCheckedWritable(char[] wordSpace, String rackString, int spaceBehind, int spaceAhead, String letters, String bestWord) {

        ArrayList<String> writable = BotDict.canWrite(rackString);
        ArrayList<String> actuallyWritable = new ArrayList<>();
        for (String s : writable) {
            // eliminates all words that does not:
            if (s.length() > bestWord.length() &&           //have more characters than bestWord
                    !(s.equals(letters)) &&                 //s can not be the String the bot is adding to
                    s.contains(letters) &&                  //s must contain the letter(s) in order
                    s.length() <= wordSpace.length &&       //fit inside the size of the array
                    s.indexOf(letters) <= spaceBehind &&    //have enough space before and after the letter(s)
                    (s.length() - (s.indexOf(letters) + letters.length())) <= spaceAhead) {
                actuallyWritable.add(s);
            }
        }
        return actuallyWritable;
    }

    //Takes the available space around a cell on the board and creates a space that the new word needs to fit into.
    private static char[] createWordSpace(int spaceBehind, int spaceAhead, StringBuilder letters) {
        char[] wordSpace = new char[spaceBehind + letters.length() + spaceAhead];
        for (int i = spaceBehind; i < spaceBehind + letters.length(); i++) {
            wordSpace[i] = letters.charAt(i - spaceBehind);
        }
        return wordSpace;
    }

    //Checks the length of available space to the right of a cell
    private static int checkSpaceAhead(Board board, int row, int col, StringBuilder letters) {
        int space = 0;
        for (int k = col + letters.length(); k < board.getBoardCells()[0].length; k++) {
            if (!board.getBoardCells()[row][k].isEmpty()) {
                space -= 1;
                break;
            } else space += 1;
        }
        return space;
    }

    //Checks the length of available space to the left of a cell
    private static int checkSpaceBehind(Board board, int row, int startCol) {
        int space = 0;
        for (int k = startCol - 1; k >= 0; k--) {
            if (!board.getBoardCells()[row][k].isEmpty()) {
                space -= 1;
                break;
            } else space += 1;
        }
        return space;
    }

    //finds all consecutive letterTiles from left to right in a section of a row of the board
    private static void searchForLetters(Board board, StringBuilder letters, StringBuilder tempRack, int row, int col) {
        while (!board.getBoardCells()[row][col].isEmpty()) {
            tempRack.append(board.getBoardCells()[row][col].getTileLetter());
            letters.append(board.getBoardCells()[row][col].getTileLetter());
            if (col == board.getBoardCells()[0].length - 1) {
                break;
            }
            col++;
        }
    }

    //Returns a given rack as a String
    private String getRackString(Rack rack) {
        StringBuilder rackString = new StringBuilder();
        for (Tile t : rack.getTiles()) {
            rackString.append(t.getLetter());
        }
        return rackString.toString();
    }

    //TODO first word is longest word not highest scoring word, should be the other way around.
    //Takes an empty board, returns a board with a play in the middle
    private Board takeFirstTurn(Board board) {
        //Temporary boards and racks for comparison
        Board bestBoard = new Board();
        bestBoard.copyBoardCells(board, true);
        String bestWord = "";

        ArrayList<String> writable = BotDict.canWrite(getRackString(rack));
        for (String s : writable) {
            if (s.length() > bestWord.length()) {
                bestWord = s;
            }
        }
        for (int i = 7; i < 7 + bestWord.length(); i++) {
            writeToBoard(getRackIndex(rack, bestWord.charAt(i - 7)), 7, i, bestBoard, rack);
        }
        return bestBoard;
    }
}
