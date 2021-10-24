package com.zcrabblers.zcrabble.model.players;

import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import com.zcrabblers.zcrabble.model.gameBoard.Board;

/**
 * Models a human player.
 * @author Gustaf Jonasson, Niklas Axelsson, Ole Fjelså, Martin Björklund.
 * used by:
 * uses: IPlayer, Rack, Board, Tile, Tilebag
 */
public class Player implements IPlayers {

    private int score;
    private final Rack rack;

    public Player(int score, Rack rack){
        this.score = score;
        this.rack = rack;
    }

    @Override
    public void addScore(int score){
        this.score += score;
    }

    /**
     * Returns the current score of this player.
     * @return The score of this player.
     */
    public int getScore(){
        return score;
    }

    /**
     * Gets the rack of this player.
     * @return player's rack.
     */
    public Rack getRack(){
        return rack;
    }

    @Override
    public Tile getRackTile(int x) {
        return rack.getTile(x);
    }

    @Override
    public void placeRackTile(int x, Tile tile) {
        rack.set(x, tile);
    }

    @Override
    public void removeRackTile(int x) {
        rack.remove(x);
    }

    @Override
    public void beginTurn(Board board) {}

    @Override
    public void fillRack(TileBag bag){
        rack.fillRack(bag);
    }

}
