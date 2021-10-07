package com.zcrabblers.zcrabble.Model;


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

    public void addScore(int score){
        this.score += score;
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


        }
        writeAHorizontalWord(Board board){

        StringBuilder rackAndFriends = new StringBuilder;
        rackAndFriends.append(rack)
        ArrayList<String> writableWords = new Arraylist;

        for (row:Rows)
            for(col:Columns)
                if(containsLetter(board,row,col) &&  checkInterval(board,row,col,rack.Size)) //if a cell has a letterTile and space around it
                   rackAndFriends.append(tileLetter)
                   writableWords = canWrite(rackAndFriends);
                   place down the first word that can be written


        }











     checkInterval(Board board,Int row,Int col,Int interval){
           for(col==col,col <=interval, col



     }






     */




    /*---   Takes in a string of letters and returns all dictionary words that can be written with them   ---*/
    private static ArrayList<String> canWrite (String letters){
        ArrayList <String> writableWords = new ArrayList<>();
        Map<Character,Integer> charCountMap = getCharCountMap(letters);
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
            if (canMakeWord){
                writableWords.add(s);
            }
        }
        return writableWords;
    }

    /*---   Takes in a string of letters and returns HashMap with its Characters as keys and number of times it is used as value   ---*/
    private static Map<Character,Integer> getCharCountMap(String letters){
        Map<Character, Integer> charCountMap = new HashMap<>();
        for (int i = 0; i <letters.length() ; i++) {

            char currentChar = letters.charAt(i);
            int count = charCountMap.getOrDefault(currentChar, 0);
            charCountMap.put(currentChar,count+1);
        }
        return charCountMap;
    }

}
