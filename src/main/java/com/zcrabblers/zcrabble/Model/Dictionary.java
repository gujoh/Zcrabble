package com.zcrabblers.zcrabble.Model;

import java.io.File;
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

    private ArrayList<String> dictArray(){
        ArrayList<String> dict = new ArrayList<>();
        File collins = new File("Collins Scrabble Words 2019");
        Scanner sc = new Scanner(collins);
        while ()





        return dict;
    }
}

