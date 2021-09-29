package com.zcrabblers.zcrabble.Model;

public class LetterTuple {
    private Letter letter;
    private int x;
    private int y;

    public LetterTuple(Letter letter, int x, int y){
        this.letter = letter;
        this.x = x;
        this.y = y;
    }

    public Letter getLetter() {
        return letter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
