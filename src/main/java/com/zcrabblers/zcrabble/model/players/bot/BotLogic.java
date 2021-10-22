package com.zcrabblers.zcrabble.model.players.bot;

import com.zcrabblers.zcrabble.model.Dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BotLogic {

    private static final com.zcrabblers.zcrabble.model.Dictionary dict = Dictionary.getInstance();

    //TODO make a Map of Strings and CharCountMaps so that canWrite does not have to run getCharCountMap 270000*number of tiles on the board times.
    /*---   Takes in a string of letters and returns all dictionary words that can be written with them   ---*/
    static ArrayList<String> canWrite(String letters){
        ArrayList<String> writableWords = new ArrayList<>();
        Map<Character, Integer> charCountMap = getCharCountMap(letters);
        for (String s : dict.getDictSet()) {
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
