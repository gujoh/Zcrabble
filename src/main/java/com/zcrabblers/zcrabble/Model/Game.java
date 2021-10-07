package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game implements IGame {

    private List<IPlayers> players;
    private IPlayers current;
    private Board board;
    private Board tempBoard;
    private TileBag tileBag;
    private int nrPLayers;
    private int nrBots;
    private ArrayList<LetterTuple> boardList = new ArrayList<>();
    private ArrayList<LetterTuple> rackList = new ArrayList<>();
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
            players.add(new Bot(0, new Rack(tileBag)));
        }
        current = players.get(0);
    }

    @Override
    public void endTurn(){
        board.checkBoard(tempBoard);
        current.addScore(board.countPoints(board.getNewCells(tempBoard)));
        getNextPlayer();
        current.beginTurn(tileBag);
        observer.notifySubscribers(boardList,rackList);
        boardList.clear();
        rackList.clear();
    }

    public synchronized void experimentalEndTurn(){
        notifyAll();
    }

    public synchronized void experimentalGameLoop() {
        try {
            while(!isGameOver()){
                current.beginTurn(tileBag);
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
    public boolean isBoardCellEmpty(int x, int y){
        return board.isCellEmpty(x, y);
    }

    public boolean isTempCellEmpty(int x, int y){
        return tempBoard.isCellEmpty(x, y);
    }

    @Override
    public void switchBoardCells(int x1, int y1, int x2, int y2) {
        board.switchTiles(x1, y1, x2, y2);
    }

    @Override
    public void switchTempCells(int x1, int y1, int x2, int y2) {
        tempBoard.switchTiles(x1, y1, x2, y2);
    }

    @Override
    public void switchRackCells(int x1, int x2) {
        current.getRack().switchTiles(x1, x2);
    }

    @Override
    public void switchRackBoardCells(int rackX, int boardX, int boardY) {
        Tile tile = tempBoard.getTile(boardX, boardY);
        tempBoard.placeTile(boardX, boardY, current.getRackTile(rackX));
        current.placeRackTile(rackX, tile);
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
}
