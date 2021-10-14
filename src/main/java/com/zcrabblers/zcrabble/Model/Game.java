package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game implements IGame, ITurnObservable {

    private List<IPlayers> players;
    private IPlayers current;
    private Board board;
    private Board tempBoard;
    private TileBag tileBag;
    private int nrPLayers;
    private int nrBots;
    private ArrayList<CellTuple> boardList = new ArrayList<>();
    private final LetterObserver observer = new LetterObserver();

    public Game(int nrPlayers, int nrBots){
        this.board = new Board("defaultBoard");
        this.tempBoard = new Board("defaultBoard");
        this.tileBag = new TileBag("defaultBag");
        this.nrPLayers = nrPlayers;
        this.nrBots = nrBots;
    }

    public void newGame() {
        try{
            board.selectBoard();
            tempBoard.selectBoard();
            tileBag.selectBag();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
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

    @Override
    public boolean endTurn(){ // DISCUSS THIS.
        if (tempBoard.checkBoard(tempBoard, board)){
            current.addScore(tempBoard.countPoints(board));
            current.fillRack(tileBag);
            current = getNextPlayer();
            observer.notifySubscribers((ArrayList<CellTuple>) tempBoard.getNewCells(board));
            System.out.println(tempBoard.getNewCells(board).size());
            board.copyBoardCells(tempBoard);
            boardList.clear();
            current.beginTurn(tempBoard);
            System.out.println(players.indexOf(current));
            return true;
        }
        else {
            System.out.println("WRONG WORD");
            return false;
        }

    }

    public synchronized void experimentalEndTurn(){
        notifyAll();
    }

    public synchronized void experimentalGameLoop() {
        try {
            while(!isGameOver()){
                current.beginTurn(tempBoard);
                System.out.println("WAITING");
                wait();
                System.out.println("PASSED WAIT");
                // Add score from tempboard to current if it was correct

                // Låta Game vara en Thread och starta en ny Thread varje gång
                // vi startar ett nytt game, den tråden blir då "modell tråden"
                // och javafx sköter grafik tråden. Game tråden väntar till vi
                // trycker på en knapp på grafik tråden som notifyar game tråden
                // då körs hela loopen med all input spelaren gett innan hon tryckte
                // på end turn

                current = getNextPlayer();
            }
            IPlayers winner = getWinner();
            // End game if there's a winner and display results
        } catch(InterruptedException e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    private boolean isGameOver(){
        return tileBag.isEmpty();
    }

    private IPlayers getNextPlayer(){
        int index = players.indexOf(current);
        if(index >= players.size() - 1)
            index = 0;
        else
            index++;
        return players.get(index);
    }

    private IPlayers getWinner(){
        IPlayers winner = players.get(0);
        for (IPlayers player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }

    @Override
    public Board getBoard(){
        return board;
    }

    @Override
    public Board getTempBoard(){
        return tempBoard;
    }

    @Override
    public int getBoardSize(){
        return board.getSize();
    }

    @Override
    public void addSubscriber(ILetterObservable sub) {
        observer.addSubscriber(sub);
    }

    @Override
    public void removeSubscriber(ILetterObservable sub) {
        observer.removeSubscriber(sub);
    }

    @Override
    public void removeAllSubscribers() {
        observer.removeAllSubscribers();
    }

    @Override
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
    @Override
    public void switchBoardCells(int x1, int y1, int x2, int y2) {
        board.switchTiles(x1, y1, x2, y2);
    }

    /**
     * Switch the tiles of two cells on the tempboard.
     * @param x1 X position of the first cell.
     * @param y1 Y position of the first cell.
     * @param x2 X position of the second cell.
     * @param y2 Y position of the second cell.
     */
    @Override
    public void switchTempCells(int x1, int y1, int x2, int y2) {
        tempBoard.switchTiles(x1, y1, x2, y2);
    }

    /**
     * Switch the tiles of two cells in the current rack.
     * @param x1 First index.
     * @param x2 Second index.
     */
    @Override
    public void switchRackCells(int x1, int x2) {
        current.getRack().switchTiles(x1, x2);
    }

    /**
     * Switches the tiles of two cells, one in the current rack and one on the temporary board.
     * @param rackX Index of the rack cell.
     * @param boardX X position of the board cell.
     * @param boardY Y position of the board cell.
     */
    @Override
    public void switchRackBoardCells(int rackX, int boardY, int boardX) {
        Tile tile = tempBoard.getTile(boardY, boardX);
        System.out.println("From: " + tile.getLetter());
        tempBoard.placeTile(boardY, boardX, current.getRackTile(rackX));
        current.placeRackTile(rackX, tile);
    }

    @Override
    public void shuffleCurrentRack(){
        current.getRack().shuffleRack();
    }

    @Override
    public Rack getRack(){
        return current.getRack();
    }

    @Override
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

    @Override
    public char getRackLetter(int index){
        return current.getRackTile(index).getLetter();
    }
}
