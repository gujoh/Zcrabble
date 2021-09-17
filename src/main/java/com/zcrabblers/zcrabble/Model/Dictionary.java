package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {

    private static Dictionary instance;

    private Dictionary(){
        instance = this;
    }

    public static Dictionary getInstance(){
        return instance;
    }

    public boolean checkWord(String word) throws FileNotFoundException {
        File file = new File("Collins Scrabble Words 2019");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (word.toUpperCase().equals(line)) {
                return true;
            }
        }
        return false;
    }

    /*

    Method to create a faster dictionary.will be implemented later.

    private ArrayList<String> dictArray() throws FileNotFoundException {
        ArrayList<String> dict = new ArrayList<>();
        File collins = new File("Collins Scrabble Words 2019");
        Scanner sc = new Scanner(collins);
        while (sc.hasNextLine()){
        dict.add(sc.nextLine());
        }
        return dict;
    }

     */
}

