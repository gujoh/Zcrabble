package com.zcrabblers.zcrabble.Model;

import javafx.geometry.Point2D;

public class Tile {
    private char letter;
    private int tileScore;
    private Point2D position; //Might not be needed
    private boolean placedThisTurn;

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
     * Constructor that creates a new Tile. Only use when a Tile requires a position (x and y coordinate).
     * @param tile the Tile that requires a position.
     * @param x the x coordinate of the new Tile.
     * @param y the y coordinate of the new Tile.
     */
    public Tile(Tile tile, int x, int y, boolean placedThisTurn){
        this.letter = tile.letter;
        this.tileScore = tile.tileScore;
        this.position = new Point2D(x, y);
        this.placedThisTurn = placedThisTurn;
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

    /**
     * Returns the x coordinate of a Tile.
     * @return the x coordinate of a Tile.
     */
    public int getX(){
        return (int)position.getX();
    }

    /**
     * Returns the x coordinate of a Tile.
     * @return the x coordinate of a Tile.
     */
    public int getY(){
        return (int)position.getY();
    }
}
