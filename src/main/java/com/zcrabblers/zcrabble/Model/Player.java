package com.zcrabblers.zcrabble.Model;

/**
 * represents the player holds a score and a rack
 */
public class Player implements IPlayers{

    private int score;
    private Rack rack;

    /**
     * call at the start of the game to create the players
     * @param score keeps track of the players score
     * @param rack the set of tiles the player can play
     */
    public Player(int score, Rack rack){
        this.score = score;
        this.rack = rack;
    }

    /**
     * adds score to the players total score
     * @param score the amount of score to add
     */
    @Override
    public void addScore(int score){
        this.score += score;
    }

    /**
     * @return returns the amount of score the player currently has
     */
    public int getScore(){
        return score;
    }

    /**
     * @return gives an instance of the players rack
     */
    public Rack getRack(){
        return rack;
    }

    /**
     * gives a rack tile at the specified position
     * @param x the specific tile position
     * @return returns the specific tile
     */
    @Override
    public Tile getRackTile(int x) {
        return rack.getTile(x);
    }

    /**
     * sets a given tile in the specified position in the player rack
     * @param x the position
     * @param tile the tile which is to be added
     */
    @Override
    public void placeRackTile(int x, Tile tile) {
        rack.set(x, tile);
    }

    @Override
    public void beginTurn(Board board) {

    }

    /**
     * draws new tiles to the player rack
     * @param bag the TileBag from which to draw tiles
     * @see TileBag
     */
    @Override
    public void fillRack(TileBag bag){
        rack.fillRack(bag);
    }

    private boolean endTurn = false;
}
