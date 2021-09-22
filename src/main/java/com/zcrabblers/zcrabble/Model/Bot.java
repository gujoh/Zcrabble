package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bot implements IPlayers {

    private int score;
    Dictionary dict = Dictionary.getInstance();

    public Bot(int score){
        this.score = score;
    }

    public void takeTurn(){

    }

    public int getScore(){
        return score;
    }






    /*---   Takes in a string of letters and returns all dictionary words that can be written with them   ---*/
    private static ArrayList<String> canWrite (String letters) throws FileNotFoundException {
        ArrayList <String> writableWords = new ArrayList<>();
        Map<Character,Integer> charCountMap = getCharCountMap(letters);
        for (String s : dictArray()) {
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


    /*---   Reads the dictionary and returns it as an ArrayList   ---*/

    private static ArrayList <String> dictArray () throws FileNotFoundException {
        ArrayList<String> dict = new ArrayList<>();
        File file = new File("src\\main\\resources\\CollinsScrabbleWords2019");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            dict.add(line);
        }
        sc.close();
        return dict;
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
