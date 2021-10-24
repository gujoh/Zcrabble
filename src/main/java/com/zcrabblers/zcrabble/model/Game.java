package com.zcrabblers.zcrabble.model;

import com.zcrabblers.zcrabble.model.gameBoard.*;
import com.zcrabblers.zcrabble.model.gameBoard.Board;
import com.zcrabblers.zcrabble.model.observers.ILetterObservable;
import com.zcrabblers.zcrabble.model.observers.ITurnObservable;
import com.zcrabblers.zcrabble.model.observers.LetterObserver;
import com.zcrabblers.zcrabble.model.players.bot.Bot;
import com.zcrabblers.zcrabble.model.players.IPlayers;
import com.zcrabblers.zcrabble.model.players.Player;


import java.util.ArrayList;
import java.util.List;

/**
 * Game connects multiple classes in the model package in order to construct a Zcrabble game.
 * @author Niklas Axelsson
 * used by: GameManager, BoardViewController
 * uses: IPlayers, Board, Tile, TileBag, LetterObserver
 */
public class Game implements ITurnObservable {

    private List<IPlayers> players;
    private IPlayers current;
    private IPlayers previous;
    private final Board board;
    private final Board tempBoard;
    private final TileBag tileBag;
    private final int nrPLayers;
    private final int nrBots;
    private final LetterObserver observer = new LetterObserver();
    private int passCounter;

    public Game(int nrPlayers, int nrBots){
        this.board = new Board();
        this.tempBoard = new Board();
        this.tileBag = new TileBag();
        this.nrPLayers = nrPlayers;
        this.nrBots = nrBots;
    }

    /**
     * Starts a new game by instantiating Players and Bots. Then calls beginTurn on the current (first) player.
     */
    public void newGame() {
        players = new ArrayList<>();
        for(int i = 0; i < nrPLayers; i++){
            players.add(new Player(0, new Rack(tileBag)));
        }
        for(int i = 0; i < nrBots; i++){
            players.add(new Bot(0, new Rack(tileBag), this));
        }
        current = players.get(0);
        current.beginTurn(tempBoard);
    }

    //TODO if checkBoard is false, rack and board should be reset. it can be done by playing with a copy of the rack and resetting tempBoard to board and tempRack to rack. Method rackCopy is added to rack to facilitate this.
    //TODO stop the game if someone wins, bots keep playing :(
    @Override
    public boolean endTurn(){
        if (tempBoard.checkBoard(tempBoard, board)){
            int score = tempBoard.countPoints(board);
            if (score == 0){
                passCounter++;
            }
            else passCounter = 0;

            current.addScore(score);
            current.fillRack(tileBag);
            previous = current;
            current = getNextPlayer();
            observer.notifySubscribers(tempBoard.getNewCells(board), isGameOver());
            board.copyBoardCells(tempBoard,false);
            if(!isGameOver()){
                current.beginTurn(tempBoard);
            }
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Checks if the current game is over.
     * @return true or false depending on the current game is over.
     */
    public boolean isGameOver(){
        if(tileBag.isEmpty() && previous.getRack().rackIsEmpty()) {
            return true;
        }
        return passCounter == 7;
    }

    //Returns the next IPlayer.
    private IPlayers getNextPlayer(){
        int index = players.indexOf(current);
        if(index >= players.size() - 1)
            index = 0;
        else
            index++;
        return players.get(index);
    }

    /**
     * Returns the player with the highest score.
     * @return The index of the player + 1, to get the nth player.
     */
    public int getWinner(){
        IPlayers winner = players.get(0);
        for (IPlayers player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        return players.indexOf(winner) + 1;
    }

    /**
     * Returns the board of the current Game.
     * @return The current board.
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Returns the temporary board of the current Game, i.e. the one IPlayers modify when they play words.
     * @return The temporary board.
     */
    public Board getTempBoard(){
        return tempBoard;
    }

    /**
     * Returns the size of the board.
     * @return the size of the board.
     */
    public int getBoardSize(){
        return board.getSize();
    }

    /**
     * Adds a subscriber to LetterObserver.
     * @param sub the ILetterObservable that is to be added as a subscriber.
     */
    public void addSubscriber(ILetterObservable sub) {
        observer.addSubscriber(sub);
    }

    /**
     * Removes all subscribers from the LetterObserver.
     */
    public void removeAllSubscribers() {
        observer.removeAllSubscribers();
    }

    /**
     * Checks if a cell is empty.
     * @param y the y position in the board.
     * @param x the x position in the board.
     * @return true if the cell is empty, false otherwise.
     */
    public boolean isBoardCellEmpty(int y, int x){
        return board.isCellEmpty(y, x);
    }

    /**
     * Returns true if the cell at x, y in the temporary board contains an empty cell.
     * @param x X position of the cell.
     * @param y Y position of the cell.
     * @return True if an empty cell.
     */
    public boolean isTempCellEmpty(int y, int x){
        return tempBoard.isCellEmpty(y, x);
    }

    /**
     * Switch the tiles of two cells on the tempBoard.
     * @param x1 X position of the first cell.
     * @param y1 Y position of the first cell.
     * @param x2 X position of the second cell.
     * @param y2 Y position of the second cell.
     */
    public void switchTempCells(int y1, int x1, int y2, int x2) {
        tempBoard.switchTiles(y1, x1, y2, x2);
    }

    /**
     * Switch the tiles of two cells in the current rack.
     * @param x1 First index.
     * @param x2 Second index.
     */
    public void switchRackCells(int x1, int x2) {
        current.getRack().switchTiles(x1, x2);
    }

    /**
     * Switches the tiles of two cells, one in the current rack and one on the temporary board.
     * @param rackX Index of the rack cell.
     * @param boardX X position of the board cell.
     * @param boardY Y position of the board cell.
     */
    public void switchRackBoardCells(int rackX, int boardY, int boardX) {
        Tile tile = tempBoard.getTile(boardY, boardX);
        tempBoard.placeTile(boardY, boardX, current.getRackTile(rackX));
        current.placeRackTile(rackX, tile);
    }

    /**
     * Returns all tiles played on the temporary board to the current players rack.
     */
    public void returnTilesToRack(){
        for(CellTuple ct : tempBoard.getNewCells(board)){
            int rIndex = getRack().getFirstFreeIndex();//getRack().getFirstFreeIndex();
            switchRackBoardCells(rIndex, ct.getI(), ct.getJ());
        }
    }

    /**
     * Takes tiles from the current player's rack and puts them in the tile bag.
     * Used when the player wants to swap tiles.
     * @param i the index of a tile in the rack.
     */
    public void fromRackToBag(int i){
        Tile t = current.getRackTile(i);
        current.removeRackTile(i);
        tileBag.add(t); //TODO: will just add tile to the end, might want to shuffle the tileBag
    }

    /**
     * Shuffles the rack of the current player.
     */
    public void shuffleCurrentRack(){
        current.getRack().shuffleRack();
    }

    /**
     * Returns the rack of the current player.
     * @return the rack of the current player.
     */
    public Rack getRack(){
        return current.getRack();
    }

    /**
     * Returns the amount of tiles in the tile bag.
     * @return the amount of tiles in the tile bag.
     */
    public int getRemainingTiles(){
        return tileBag.remainingTiles();
    }

    /**
     * Checks if a position in the rack is empty.
     * @param x the position to check.
     * @return true if the position is empty, false otherwise.
     */
    public boolean isRackEmpty(int x){
        return current.getRack().isEmpty(x);
    }

    /**
     * Returns a list of IPLayers.
     * @return a list of IPlayers.
     */
    public List<IPlayers> getPlayers(){
        return players;
    }

    /**
     * Returns the score of an IPlayer.
     * @param index the index of the IPlayer.
     * @return the score of an IPlayer.
     */
    public int getPlayerScore(int index){
        return players.get(index).getScore();
    }

    /**
     * Returns the letter that is on a tile in the current player's rack.
     * @param index the position of the tile that is to be checked.
     * @return the letter that is on a tile in the current player's rack.
     */
    public char getRackLetter(int index){
        return current.getRackTile(index).getLetter();
    }

    /**
     * Returns the index the current player has in the players list.
     * @return the index the current player has in the player list.
     */
    public int getCurrentPlayerIndex(){
        return players.indexOf(current);
    }
}
