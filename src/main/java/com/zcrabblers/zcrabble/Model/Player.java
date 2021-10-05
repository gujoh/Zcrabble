package com.zcrabblers.zcrabble.Model;

public class Player implements IPlayers{

    private int score;
    private Rack rack;

    public Player(int score, Rack rack){
        this.score = score;
        this.rack = rack;
    }

    public int getScore(){
        return score;
    }
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
    public void beginTurn(TileBag bag) {

    }

    private boolean endTurn = false;
}
