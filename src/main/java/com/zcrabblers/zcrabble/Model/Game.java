package com.zcrabblers.zcrabble.Model;

import java.util.ArrayList;
import java.util.List;

public class Game implements ITurnObservable {

    private List<IPlayers> players;
    private IPlayers current;
    private Board board;
    private Board tempBoard;
    private TileBag tileBag;
    private int nrPLayers;
    private int nrBots;
    private List<CellTuple> boardList = new ArrayList<>();
    private final LetterObserver observer = new LetterObserver();
    private int passCounter;

    public Game(int nrPlayers, int nrBots){
        this.board = new Board();
        this.tempBoard = new Board();
        this.tileBag = new TileBag();
        this.nrPLayers = nrPlayers;
        this.nrBots = nrBots;
    }

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

    //TODO, if checkBoard is false, rack and board should be reset. it can be done by playing with a copy of the rack and resetting tempBoard to board and tempRack to rack. Method rackCopy is added to rack to facilitate this.

    public boolean endTurn(){ // DISCUSS THIS.
        if (tempBoard.checkBoard(tempBoard, board)){
            int score = tempBoard.countPoints(board);
            if (score == 0){
                passCounter++;
            }
            else passCounter = 0;

            current.addScore(score);
            current.fillRack(tileBag);
            current = getNextPlayer();
            observer.notifySubscribers(tempBoard.getNewCells(board), isGameOver());
            //System.out.println(tempBoard.getNewCells(board).size());
            board.copyBoardCells(tempBoard);
            boardList.clear();
            current.beginTurn(tempBoard);
            //System.out.println(players.indexOf(current));
            return true;
        }
        else {
            //System.out.println("WRONG WORD");
            return false;
        }

    }

    /**
     * Checks if the current game is over.
     * @return true or false depending on the current game is over.
     */
    public boolean isGameOver(){
        if(tileBag.isEmpty() && current.getRack().rackIsEmpty()) {
            return true;
        }
        return passCounter == 7;
    }

    private IPlayers getNextPlayer(){
        int index = players.indexOf(current);
        if(index >= players.size() - 1)
            index = 0;
        else
            index++;
        return players.get(index);
    }

    /**
     * Return the player with the highest score.
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

    public Board getBoard(){
        return board;
    }

    public Board getTempBoard(){
        return tempBoard;
    }

    public int getBoardSize(){
        return board.getSize();
    }

    public void addSubscriber(ILetterObservable sub) {
        observer.addSubscriber(sub);
    }

    public void removeSubscriber(ILetterObservable sub) {
        observer.removeSubscriber(sub);
    }

    public void removeAllSubscribers() {
        observer.removeAllSubscribers();
    }

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
     * Switch the tiles of two cells on the actual board.
     * @param x1 X position of the first cell.
     * @param y1 Y position of the first cell.
     * @param x2 X position of the second cell.
     * @param y2 Y position of the second cell.
     */
    public void switchBoardCells(int x1, int y1, int x2, int y2) {
        board.switchTiles(x1, y1, x2, y2);
    }

    /**
     * Switch the tiles of two cells on the tempBoard.
     * @param x1 X position of the first cell.
     * @param y1 Y position of the first cell.
     * @param x2 X position of the second cell.
     * @param y2 Y position of the second cell.
     */
    public void switchTempCells(int x1, int y1, int x2, int y2) {
        tempBoard.switchTiles(x1, y1, x2, y2);
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
        //System.out.println("From: " + tile.getLetter());
        tempBoard.placeTile(boardY, boardX, current.getRackTile(rackX));
        current.placeRackTile(rackX, tile);
    }

    /**
     * Returns all tiles played on the temporary board to the current players rack.
     */
    public void returnTilesToRack(){
        for(CellTuple ct : tempBoard.getNewCells(board)){
            int rIndex = getRack().getFirstFreeIndex();
            switchRackBoardCells(rIndex, ct.getI(), ct.getJ());
        }
    }

    public void fromRackToBag(int i){
        Tile t = current.getRackTile(i);
        current.removeRackTile(i);
        tileBag.add(t); //TODO: will just add tile to the end, might want to shuffle the tileBag
    }

    public void shuffleCurrentRack(){
        current.getRack().shuffleRack();
    }

    public Rack getRack(){
        return current.getRack();
    }

    public int getRemainingTiles(){
        return tileBag.remainingTiles();
    }

    public boolean isRackEmpty(int x){
        return current.getRack().isEmpty(x);
    }

    public List<IPlayers> getPlayers(){
        return players;
    }

    public int getPlayerScore(int index){
        return players.get(index).getScore();
    }

    public char getRackLetter(int index){
        return current.getRackTile(index).getLetter();
    }
}
