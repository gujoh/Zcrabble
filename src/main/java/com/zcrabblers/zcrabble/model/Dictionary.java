package com.zcrabblers.zcrabble.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dictionary {

    private static Dictionary instance;
    private static Set<String> dictSet;

    private Dictionary()  {
        instance = this;
        dictSet = createDictSet();
    }

    /**
     * Returns current Dictionary, if there is none; creates a dictionary and returns it
     * @return current Dictionary
     */
    public static Dictionary getInstance(){

        return Objects.requireNonNullElseGet(instance, Dictionary::new);
    }

    /**
     * @return dictionary as an ArrayList.
     */

    public Set<String> getDictSet(){
        return dictSet;
    }

    /**
     * True/False if a String is part of the dictionary.
     * @param word is the String to be tested against the dictionary.
     * @return dictionary contains word
     */
    public boolean checkWord (String word)  {
        return dictSet.contains(word.toUpperCase());
    }


    /*---   Reads the dictionary and returns it as an ArrayList   ---*/
    private static Set<String> createDictSet ()  {
        Set<String> dict = new HashSet<>();
        File file = new File("src/main/resources/CollinsScrabbleWords2019");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true){
            assert sc != null;
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine();
            dict.add(line);
        }
        sc.close();
        return dict;
    }
}

