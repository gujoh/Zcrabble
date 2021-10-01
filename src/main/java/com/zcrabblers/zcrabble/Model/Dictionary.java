package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {

    private static Dictionary instance;
    private static ArrayList<String> dictArray;

    private Dictionary()  {
        instance = this;
        dictArray = createDictArray();
    }

    /**
     * Returns current Dictionary, if there is none; creates a dictionary and returns it
     * @return current Dictionary
     */
    static Dictionary getInstance(){

        return Objects.requireNonNullElseGet(instance, Dictionary::new);
    }

    /**
     * @return dictionary as an ArrayList.
     */

    ArrayList<String> getDictArray(){
        return dictArray;
    }

    /**
     * True/False if a String is part of the dictionary.
     * @param word is the String to be tested against the dictionary.
     * @return dictionary contains word
     */
    boolean checkWord (String word)  {
        return dictArray.contains(word);
    }


    /*---   Reads the dictionary and returns it as an ArrayList   ---*/
    private static ArrayList<String> createDictArray ()  {
        ArrayList<String> dict = new ArrayList<>();
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

