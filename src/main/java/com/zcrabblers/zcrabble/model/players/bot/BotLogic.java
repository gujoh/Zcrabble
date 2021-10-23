package com.zcrabblers.zcrabble.model.players.bot;

import com.zcrabblers.zcrabble.model.gameBoard.board.Board;
import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;

import java.util.ArrayList;
import java.util.Map;


//TODO dictMap should be created at the beginning of the game and live in BotDict

public class BotLogic {

    //Returns a given rack as a String
    static String getRackString(Rack rack) {
        StringBuilder rackString = new StringBuilder();
        for (Tile t : rack.getTiles()) {
            rackString.append(t.getLetter());
        }
        return rackString.toString();
    }

    //TODO first word is longest word not highest scoring word, should be the other way around.
    //Takes an empty board, returns a board with a play in the middle
    static Board takeFirstTurn(Board board, Rack rack) {
        //Temporary boards and racks for comparison
        Board bestBoard = new Board();
        bestBoard.copyBoardCells(board, true);
        String bestWord = "";
        Map<String, Map<Character,Integer>> dictMap = BotDict.dictMap();

        ArrayList<String> writable = BotDict.canWrite(getRackString(rack), dictMap);
        for (String s : writable) {
            if (s.length() > bestWord.length()) {
                bestWord = s;
            }
        }
        for (int i = 7; i < 7 + bestWord.length(); i++) {
            placeTileOnBoard(getRackIndex(rack, bestWord.charAt(i - 7)), 7, i, bestBoard, rack);
        }
        return bestBoard;
    }

    //TODO the longest word is not necessarily the highest scoring word, if there is time this should be used as a metric instead.
    //Takes a Board and a rack and returns a board with the longest horizontal word it can write
    static Board scrabbleWord(Board board, Rack rack1) {

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

        int spaceBehind;
        int spaceAhead;
        ArrayList<String> writable;
        StringBuilder letters = new StringBuilder();
        StringBuilder tempRackString = new StringBuilder(getRackString(rack1));
        Map<String, Map<Character,Integer>> dictMap = BotDict.dictMap();

        //method starts here

        for (int row = 0; row < board.getBoardCells().length; row++) {
            for (int col = 0; col < board.getBoardCells()[0].length; col++) {

                if (!board.getBoardCells()[row][col].isEmpty()) {

                    searchForLetters(board, letters, tempRackString, row, col);

                    spaceBehind = checkSpaceBehind(board, row, col);  
                    spaceAhead = checkSpaceAhead(board, row, col, letters);
                    writable = getCheckedWritable(tempRackString.toString(), spaceBehind, spaceAhead, letters.toString(), bestWord, dictMap);
                    sort(writable);

                    for (String s : writable) {
                        tempRack.getRackCopy(rack1);
                        int j = 0;

                        writeWord(currentBoard, tempRack, letters, row, col, s, j);

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

    private static void writeWord(Board currentBoard, Rack tempRack, StringBuilder letters, int row, int col, String s, int j) {
        for (int i = col - s.indexOf(letters.toString()); i < col; i++) {
            placeTileOnBoard(getRackIndex(tempRack, s.charAt(j)), row, i, currentBoard, tempRack);
            j++;
        }
        int k = s.indexOf(String.valueOf(letters)) + letters.length();

        for (int i = col + letters.length(); i < col + s.length() - s.indexOf(String.valueOf(letters)); i++) {
            placeTileOnBoard(getRackIndex(tempRack, s.charAt(k)), row, i, currentBoard, tempRack);
            k++;
        }
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


    //TODO bot can no longer copy letter tiles from board. Witch is good! However it should never try to either. the problem seems come from scrabbleWord. Also, the bot should never be able to steal random tiles in the first place.
    //Switches place between a tile in a given position on a rack and a tile in a given position on a board.
    private static void placeTileOnBoard(int rackX, int boardRow, int boardCol, Board board, Rack currentRack) {
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
    private static int getRackIndex(Rack tempRack, char c) {
        int index = 0;
        for (Tile t : tempRack.getTiles()) {
            if (t.getLetter() == c) {
                return index;
            }
            index++;
        }
        return 6;   //TODO this works but is hella ugly
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
    private static ArrayList<String> getCheckedWritable(String rackString, int spaceBehind,
                                                        int spaceAhead, String letters, String bestWord, Map<String, Map<Character, Integer>> dictMap) {

        ArrayList<String> writable = BotDict.canWrite(rackString,dictMap);
        ArrayList<String> actuallyWritable = new ArrayList<>();
        for (String s : writable) {
            // eliminates all words that does not:
            if (s.length() > bestWord.length() &&           //have more characters than bestWord
                    !(s.equals(letters)) &&                 //s can not be the String the bot is adding to
                    s.contains(letters) &&                  //s must contain the letter(s) in order
                    s.length() <= spaceBehind+spaceAhead+letters.length() &&       //fit inside the size of the array
                    s.indexOf(letters) <= spaceBehind &&    //have enough space before and after the letter(s)
                    (s.length() - (s.indexOf(letters) + letters.length())) <= spaceAhead) {
                actuallyWritable.add(s);
            }
        }
        return actuallyWritable;
    }

    //Takes the available space around a cell on the board and creates a space that the new word needs to fit into.
    //Was probably made mostly to make it easier to think.
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

}