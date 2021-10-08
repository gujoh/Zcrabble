package com.zcrabblers.zcrabble.Model;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bot implements IPlayers {

    private int score;
    private Rack rack;
    private static final Dictionary dict = Dictionary.getInstance();

    public Bot(int score, Rack rack){
        this.score = score;
        this.rack = rack;
    }

    public void takeTurn(){

    }

    @Override
    public void placeRackTile(int x, Tile tile){

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
        return null;
    }

    @Override
    public void beginTurn(TileBag bag) {

    }
    //TODO bot need to be able to find all previous letter tiles and build a word with them in
    //TODO remove all words that does not fit with the board around it. (this seems to be the tricky part)




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

     */


    private static Cell[][] scrabbleWord(Cell[][] board, String rackString){
        int rows = board.length;
        int cols = board[0].length;
        Cell[][] currentWord = new Cell[rows][cols];
        Cell[][] bestWord = new Cell[rows][cols];
        int spaceBehind;
        int spaceAhead;
        ArrayList<String> writable;
        StringBuilder letters = new StringBuilder();
        StringBuilder tempRack = new StringBuilder(rackString);
        char [] wordSpace;  //Should this be Cell[], we will se I guess.

        for (int row = 0; row <board.length ; row++) {
            for (int col = 0; col <board[0].length ; col++) {

                searchForLetters(board,letters,tempRack,row,col);

                spaceBehind = checkSpaceBehind(board, row, col-letters.length());
                spaceAhead = checkSpaceAhead(board, row, col);
                wordSpace = createWordSpace(col-letters.length(),spaceBehind,spaceAhead,letters);
                writable = actuallyWritable(wordSpace,tempRack.toString(),spaceBehind,spaceAhead,letters.toString());

            }
            
        }
    return bestWord;
    }

    private static ArrayList<String> actuallyWritable(char[] wordSpace, String toString, int spaceBehind, int spaceAhead, String toString1) {
        return null;
    }

    private static char[] createWordSpace(int i, int spaceBehind, int spaceAhead, StringBuilder letters) {
        return null;
    }

    private static int checkSpaceAhead(Cell[][] board, int row, int col) {
        return 0;
    }

    private static int checkSpaceBehind(Cell[][] board, int row, int startCol) {
        int space = 0;
        for (int k = startCol-1; k >= 0; k--) {
            if (!board[row][k].isEmpty()){
                space -= 1;
                break;
            }else space += 1;
        }
        return space;
    }


/*
                    spaceBehind = checkSpaceBehind(tempBoard,i,startOfWord);
                    spaceAhead = checkSpaceAhead(tempBoard,i,j);
                    wordSpace = createWordSpace(startOfWord,spaceBehind,spaceAhead,letters);
                    writable = actuallyWritable(wordSpace,tempRack.toString(),spaceBehind,spaceAhead,letters.toString());


    /*

                    writeToTempBoard(tempBoard, writable, i, j, letters.toString(), spaceBehind);
                    print2D(tempBoard);
                }
                letters.delete(0,letters.length());
                tempRack = new StringBuilder(rack);
                wordSpace = new char[0];
                tempBoard = boardCopy(board);
            }
        }
        return tempBoard;
    }


     */

    private static void searchForLetters(Cell[][] board, StringBuilder letters, StringBuilder tempRack, int i, int j) {

        if (!board[i][j].isEmpty()) {
            tempRack.append(board[i][j].getTileLetter());
            letters.append(board[i][j].getTileLetter());

            //TODO make this loop better, while j != cols-1 && !j+1.isEmpty
            while (!board[i][j == board[0].length - 1 ? j : j + 1].isEmpty()) {
                if (j == board[0].length - 1) {
                    break;
                }
                j++;
                tempRack.append(board[i][j].getTileLetter());
                letters.append(board[i][j].getTileLetter());
            }
        }
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
