package com.zcrabblers.zcrabble.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {

    private static Dictionary instance;

    private Dictionary(){
        instance = this;
    }

    public static Dictionary getInstance(){
        return instance;
    }

    private ArrayList<String> dictArray() throws FileNotFoundException {
        ArrayList<String> dict = new ArrayList<>();
        File collins = new File("Collins Scrabble Words 2019");
        Scanner sc = new Scanner(collins);
        while (sc.hasNextLine()){
        int =

        }





        return dict;
    }
}

