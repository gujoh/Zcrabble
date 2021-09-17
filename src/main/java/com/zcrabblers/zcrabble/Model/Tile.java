package com.zcrabblers.zcrabble.Model;

import javafx.geometry.Point2D;

public class Tile {
    private char letter;
    private int tileScore;
    private Point2D position; //Might not be needed

    public Tile(char letter, int tileScore, Point2D point) {
        this.letter = letter;
        this.tileScore = tileScore;
        this.position = point;
    }
    public int getTileScore(){
        return tileScore;
    }
    public char getLetter(){
        return letter;
    }
}
