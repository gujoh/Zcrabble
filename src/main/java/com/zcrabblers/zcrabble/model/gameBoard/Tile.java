package com.zcrabblers.zcrabble.model.gameBoard;

/**
 * Tile represents the individual playable pieces with letters and a score associated with them.
 * @author Niklas Axelsson, Martin Björklund, Ole Fjeldså, Gustaf Jonasson.
 */
public class Tile {
    private char letter;
    private int tileScore;
    /**
     * Constructor that creates a new Tile. Use when a Tile does not require a position (x and y coordinate).
     * @param letter the letter that a Tile represents (A through Z)
     * @param tileScore The score that a tile represents.
     */
    public Tile(char letter, int tileScore) {
        this.letter = letter;
        this.tileScore = tileScore;
    }

    /**
     * Returns the score of a Tile.
     * @return the score of a Tile.
     */
    public int getTileScore(){
        return tileScore;
    }

    /**
     * Returns the letter that a Tile represents.
     * @return the letter that a Tile represents.
     */
    public char getLetter(){
        return letter;
    }

}
