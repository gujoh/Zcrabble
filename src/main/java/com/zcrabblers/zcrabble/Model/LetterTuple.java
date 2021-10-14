package com.zcrabblers.zcrabble.Model;

/**
 * a grouping of Letter and it's position'
 * @see Letter
 */
public class LetterTuple {
    private Letter letter;
    private int x;
    private int y;

    /**
     *
     * @param letter the given letter
     * @param x x pos
     * @param y y pos
     */
    public LetterTuple(Letter letter, int x, int y){
        this.letter = letter;
        this.x = x;
        this.y = y;
    }

    /**
     * @return the letter
     */
    public Letter getLetter() {
        return letter;
    }

    /**
     * @return x pos
     */
    public int getX() {
        return x;
    }

    /**
     * @return y pos
     */
    public int getY() {
        return y;
    }
}
