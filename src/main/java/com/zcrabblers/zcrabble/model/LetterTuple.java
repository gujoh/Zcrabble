package com.zcrabblers.zcrabble.model;

/**
 * LetterTuple represents a Letter with a position.
 * @author Gustaf Jonasson.
 */
public class LetterTuple {
    private final Letter letter;
    private final int x;
    private final int y;

    public LetterTuple(Letter letter, int x, int y){
        this.letter = letter;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the Letter.
     * @return the Letter.
     */
    public Letter getLetter() {
        return letter;
    }

    /**
     * Returns the x coordinate of the Letter.
     * @return the x coordinate of the letter.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the letter.
     * @return the y coordinate of the letter.
     */
    public int getY() {
        return y;
    }
}
