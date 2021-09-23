package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {

    private static Dictionary instance;

    private Dictionary(){
        instance = this;
    }

    public static Dictionary getInstance(){
        return Objects.requireNonNullElseGet(instance, Dictionary::new);
    }


    /*---   Reads the dictionary and returns it as an ArrayList   ---*/
    private static ArrayList<String> dictArray () throws FileNotFoundException {
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

    /*---   Checks if a word is in the dictionary   ---*/
    private static boolean checkWord (String word) throws FileNotFoundException {
        return dictArray().contains(word);
    }
}

