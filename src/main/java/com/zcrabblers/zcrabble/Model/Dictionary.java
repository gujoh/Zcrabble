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

    public static Dictionary getInstance(){
        return Objects.requireNonNullElseGet(instance, Dictionary::new);
    }
    public ArrayList<String> getDictArray(){
        return dictArray;
    }
    /*---   Checks if a word is in the dictionary   ---*/
    public  boolean checkWord (String word)  {
        return dictArray.contains(word);
    }


    /*---   Reads the dictionary and returns it as an ArrayList   ---*/
    private static ArrayList<String> createDictArray ()  {
        ArrayList<String> dict = new ArrayList<>();
        File file = new File("src\\main\\resources\\CollinsScrabbleWords2019");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            dict.add(line);
        }
        sc.close();
        return dict;
    }
}

