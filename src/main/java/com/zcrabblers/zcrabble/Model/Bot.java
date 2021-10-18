package com.zcrabblers.zcrabble.Model;


import com.zcrabblers.zcrabble.Controller.BoardController;

import java.lang.reflect.Array;
import java.util.*;

public class Bot implements IPlayers {

    private int score;
    private Rack rack;
    private static final Dictionary dict = Dictionary.getInstance();
    private final TurnObserver observer = new TurnObserver();

    public Bot(int score, Rack rack, ITurnObservable sub){
        this.score = score;
        this.rack = rack;
        observer.addSubscriber(sub);
    }

    public void addScore(int score){
        this.score += score;
    }

    @Override
    public void fillRack(TileBag bag){
        rack.fillRack(bag);
    }

    @Override
    public void addRackTile(Tile tile) {
        rack.add(tile);
    }

    @Override
    public void removeRackTile(int x) {
        rack.remove(x);
    }

    @Override
    public void placeRackTile(int x, Tile tile){
        rack.set(x, tile);
    }

    public int getScore(){
        return score;
    }

    @Override
    public Rack getRack(){
        return null;
    }

    @Override
    public Tile getRackTile(int x) {
        return rack.getTile(x);
    }

    @Override
    public void beginTurn(Board board) {
        board.copyBoardCells(scrabbleWord(board,getRackString()));
        printBoard(board);
        observer.notifySubscribers();
    }

        //Prints the board for debugging p
    private void printBoard(Board board) {
        char[][] boardPrint = new char[15][15];
        for (int i = 0; i <board.getBoardCells().length ; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {
                boardPrint[i][j] = board.getBoardCells()[i][j].getTileLetter();
            }
        }
        for (char[] row : boardPrint)
            System.out.println(Arrays.toString(row));
    }

    /*
        {
          0  1  2  3  4  5  6  7  8  9  A  B  C  D  E

    0   {  ,  ,  ,  , X,  ,  ,  ,  ,  ,  ,  ,  ,  ,  },
    1   {  ,  ,  ,  , X,  ,  ,  ,  ,  ,  ,  ,  ,  ,  },
    2   {  ,  ,  ,  , X,  ,  ,  ,  ,  , X,  ,  ,  ,  },
    3   {  ,  , X, X, X, X, X, X, X,  , X,  ,  ,  ,  },
    4   {  ,  ,  ,  , X,  ,  ,  ,  ,  , X,  ,  ,  ,  },
    5   {  ,  ,  ,  , X,  ,  ,  ,  ,  , X,  ,  ,  ,  },
    6   {  ,  ,  ,  , X,  , X,  ,  ,  , X,  ,  ,  ,  },
    7   {  ,  ,  , X, X, X, X, X, X, X, X,  ,  ,  ,  },
    8   {  ,  ,  ,  ,  ,  , X,  ,  ,  , X,  ,  ,  ,  },
    9   {  ,  ,  ,  ,  ,  , X, X, X, X, X, X, X,  ,  },
    A   {  ,  ,  ,  ,  ,  , X,  ,  ,  , X,  ,  ,  ,  },
    B   {  , X, X, X, X, X, X,  ,  ,  ,  ,  ,  ,  ,  },
    C   {  ,  ,  ,  ,  ,  , X,  ,  ,  ,  ,  ,  ,  ,  },
    D   {  ,  ,  ,  ,  , X, X, X, X, X, X, X,  ,  ,  },
    E   {  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  },

  Bot is under construction, lots of weird stuff going on here

     */

    private  Board scrabbleWord(Board board, String rackString){

        Board currentBoard = new Board("defaultBoard");
        currentBoard.copyBoardCells(board);
        Board bestBoard = new Board("defaultBoard");
        bestBoard.copyBoardCells(board);
        String bestWord = "";

        int spaceBehind;
        int spaceAhead;
        ArrayList<String> writable;
        StringBuilder letters = new StringBuilder();
        StringBuilder tempRack = new StringBuilder(rackString);
        char [] wordSpace;  //Should this be Cell[], we will se I guess.

        for (int row = 0; row <board.getBoardCells().length ; row++) {
            for (int col = 0; col <board.getBoardCells()[0].length ; col++) {

            if (!board.getBoardCells()[row][col].isEmpty()) {

                searchForLetters(board, letters, tempRack, row, col);

                spaceBehind = checkSpaceBehind(board, row, col);        //SpaceBehind is negative. this breaks everything.
                spaceAhead = checkSpaceAhead(board, row, col, letters);
                wordSpace = createWordSpace(spaceBehind, spaceAhead, letters);
                writable = actuallyWritable(wordSpace, tempRack.toString(), spaceBehind, spaceAhead, letters.toString(), bestWord);
                sort(writable);

                for (String s : writable) {
                    System.out.println(s);
                    int j = 0;
                    for (int i = col - s.indexOf(letters.toString()); i < col; i++) {
                        writeToBoard(getRackIndex(s.charAt(j)), row, i, currentBoard);
                        j++;
                    }
                    int k = s.indexOf(String.valueOf(letters)) + letters.length();
                    for (int i = col + letters.length(); i < col + s.length() - s.indexOf(String.valueOf(letters)); i++) {
                        writeToBoard(getRackIndex(s.charAt(k)), row, i, currentBoard);
                        k++;
                    }
                    if (!currentBoard.checkBoard(currentBoard, board)) {
                        currentBoard.copyBoardCells(board);
                    } else {
                        bestBoard.copyBoardCells(currentBoard);
                        currentBoard.copyBoardCells(board);
                        bestWord = s;
                        break;
                    }
                }
                col += letters.length();
            }






            }
            letters.delete(0,letters.length());
            tempRack = new StringBuilder(rackString);
            wordSpace = new char[]{};
            System.out.println(bestWord);

        }
    return bestBoard;//bestBoard;
    }

    private  void writeToBoard(int rackX, int boardRow, int boardCol, Board board){
        Tile tile = board.getTile(boardCol, boardRow);
        board.placeTile(boardRow, boardCol, rack.getTile(rackX));
        rack.set(rackX, tile);
    }

    private int getRackIndex(char c){
        int index = 0;
        for (Tile t : rack.getTiles()) {
            if (t.getLetter() == c){
                break;
            }
            index++;
        }
        return index;
    }
        //TODO this is bubble sort because im lazy, make a quick sort.
    private static void sort (ArrayList<String> writable) {



        for (int i = 0; i < writable.size()-1; i++) {
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
    private static ArrayList<String> actuallyWritable(char[] wordSpace, String rackAndFriends, int spaceBehind, int spaceAhead, String letters, String bestWord) {

        ArrayList<String> writable = canWrite(rackAndFriends);
        ArrayList<String> actuallyWritable = new ArrayList<>();
        for (String s : writable){
            // eliminates all words that does not:
            if (s.length() > bestWord.length() &&           //have more characters than bestWord
                    s.contains(letters) &&                  //contain the letter(s) in order
                    s.length() <= wordSpace.length &&       //fit inside the size of the array
                    s.indexOf(letters) <= spaceBehind &&    //have enough space before and after the letter(s)
                    (s.length() - (s.indexOf(letters)+letters.length())) <= spaceAhead){
                actuallyWritable.add(s);
            }
        }
        return actuallyWritable;
    }

    private static char[] createWordSpace(int spaceBehind, int spaceAhead, StringBuilder letters) {
        char [] wordSpace = new char[spaceBehind+letters.length()+spaceAhead];
        for (int i = spaceBehind; i <spaceBehind+letters.length() ; i++) {
            wordSpace[i] = letters.charAt(i-spaceBehind);
            System.out.println(wordSpace);
        }
        return wordSpace;
    }

    private static int checkSpaceAhead(Board board, int row, int col, StringBuilder letters) {
        int space = 0;
        System.out.println(col);
        for (int k = col+letters.length(); k < board.getBoardCells()[0].length ; k++) {
            if (!board.getBoardCells()[row][k].isEmpty()){
                space -= 1;
                break;
            }else space += 1;
        }
        return space;
    }

    private static int checkSpaceBehind(Board board, int row, int startCol) {
        int space = 0;
        for (int k = startCol-1; k >= 0; k--) {
            if (!board.getBoardCells()[row][k].isEmpty()){
                space -= 1;
                break;
            }else space += 1;
        }
        return space;
    }

    private static void searchForLetters(Board board, StringBuilder letters, StringBuilder tempRack, int i, int j) {

        if (!board.getBoardCells()[i][j].isEmpty()) {

            tempRack.append(board.getBoardCells()[i][j].getTileLetter());
            letters.append(board.getBoardCells()[i][j].getTileLetter());
            System.out.println(tempRack);
            System.out.println(letters);
            while (!board.getBoardCells()[i][j==board.getBoardCells()[0].length - 1?j:j+1].isEmpty()) {
                if (j == board.getBoardCells()[0].length - 1) {break;}
                j++;
                tempRack.append(board.getBoardCells()[i][j].getTileLetter());
                System.out.println(tempRack);
                letters.append(board.getBoardCells()[i][j].getTileLetter());
                System.out.println(letters);
            }
        }
    }

    private String getRackString(){
        StringBuilder rackString = new StringBuilder();
        for(Tile t : rack.getTiles()){
            rackString.append(t.getLetter());
        }
        return rackString.toString();
    }



    //TODO: the operation of tilting a board should probably live in board, it is also a weird thing to do

    /**
     * Returns the cell matrix flipped pi/2 radians
     * first row is last column, first column is first row
     * @param board the cell pattern on the current board
     * @return the cell pattern on the board flipped pi/1 radians
     */
    private Cell[][] tiltPiHalf (Cell[][] board){
        Cell[][] tempBoard = new Cell[board[0].length][board.length];
        for (int i = 0; i <board.length ; i++) {
            for (int j = 0; j <board[0].length ; j++) {

                tempBoard[board[0].length-j-1][i] = board[i][j];

            }
        }
        return tempBoard;
    }

    //TODO: the operation of tilting a board should probably live in board

    /**
     * Returns the board
     * First row is first column, first column is last row
     * @param board the current board.
     * @return the cell pattern on the board flipped -pi/1 radians
     */

    private Board tilt3PiHalf (Board board){
        Board tempBoard = new Board("defaultBoard");
        for (int i = 0; i <board.getBoardCells().length ; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {

                tempBoard.getBoardCells()[j][board.getBoardCells().length-i-1] = board.getBoardCells()[i][j];

            }
        }
        return tempBoard;
    }

    /*--- Don't touch things below this line ---*

    /*---   Takes in a string of letters and returns all dictionary words that can be written with them   ---*/
    private static ArrayList<String> canWrite (String letters){
            ArrayList<String> writableWords = new ArrayList<>();
            Map<Character, Integer> charCountMap = getCharCountMap(letters);
            for (String s : dict.getDictArray()) {
                Map<Character, Integer> currentDictWordMap = getCharCountMap(s);
                boolean canMakeWord = true;
                for (Character character : currentDictWordMap.keySet()) {
                    int currentDictWordMapCharCount = currentDictWordMap.get(character);
                    int lettersCharCount = charCountMap.getOrDefault(character, 0);
                    if (currentDictWordMapCharCount > lettersCharCount) {
                        canMakeWord = false;
                        break;
                    }
                }
                if (canMakeWord) {
                    writableWords.add(s);
                }
            }
            return writableWords;
        }

    /*---   Takes in a string of letters and returns HashMap with its Characters as keys and number of times it is used as value   ---*/
    private static Map<Character, Integer> getCharCountMap (String letters){
            Map<Character, Integer> charCountMap = new HashMap<>();
            for (int i = 0; i < letters.length(); i++) {

                char currentChar = letters.charAt(i);
                int count = charCountMap.getOrDefault(currentChar, 0);
                charCountMap.put(currentChar, count + 1);
            }
            return charCountMap;
        }
}
