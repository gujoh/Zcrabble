package com.zcrabblers.zcrabble.model.players.bot;

import com.zcrabblers.zcrabble.model.gameBoard.Rack;
import com.zcrabblers.zcrabble.model.gameBoard.Tile;
import com.zcrabblers.zcrabble.model.gameBoard.TileBag;
import com.zcrabblers.zcrabble.model.gameBoard.Board;
import com.zcrabblers.zcrabble.model.observers.ITurnObservable;
import com.zcrabblers.zcrabble.model.observers.TurnObserver;
import com.zcrabblers.zcrabble.model.players.IPlayers;


//TODO Make the bot evaluate words by score, not by length. "A well-contested Scrabble game should end with around 600 to 700 total points" Now it is 600! HUZZA!
//TODO Functional decomposition on everything, but particularly scrabbleWord
//TODO comment and test everything, tidy up.
//TODO make bot faster!
//TODO when someone wins the game should stop, if bots play each other they just keep going.
//TODO different difficulty levels
//TODO what happens if the bot makes a mistake? It never does, don't worry about it.


/**
 * Bot takes a Board and makes a play on it
 */
public class Bot implements IPlayers {

    private int score;
    private final Rack rack;
    private final TurnObserver observer = new TurnObserver();

    public Bot(int score, Rack rack, ITurnObservable sub) {
        this.score = score;
        this.rack = rack;
        observer.addSubscriber(sub);
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public void fillRack(TileBag bag) {
        rack.fillRack(bag);
    }

    @Override
    public void removeRackTile(int x) {
        rack.remove(x);
    }

    @Override
    public void placeRackTile(int x, Tile tile) {
        rack.set(x, tile);
    }

    public int getScore() {
        return score;
    }

    @Override
    public Rack getRack() {
        return rack;
    }

    @Override
    public Tile getRackTile(int x) {
        return rack.getTile(x);
    }

    /**
     * Mutates a Board to include the bots best play
     * Notifies subscribers when it is done
     * @param board the Board to be mutated
     */
    @Override
    public void beginTurn(Board board) {

        if (board.cellIsEmpty(7,7)) {
            board.copyBoardCells(BotLogic.takeFirstTurn(board, rack), true);
        } else {
            takeTurn(board);
        }
        observer.notifySubscribers();
    }

    /**
     * Creates board copies with the best horizontal and vertical plays on a given board, compares them and mutates given board to the highest scoring one
     *
     * @param board the Board to be mutated
     */
    private void takeTurn(Board board) {

        //Temporary boards and racks for comparison

        Rack tempRack = new Rack();
        tempRack.getRackCopy(rack);
        Rack horizontalRack = new Rack();
        horizontalRack.getRackCopy(rack);
        Rack verticalRack = new Rack();
        verticalRack.getRackCopy(rack);

        Board horizontalBoard = new Board();
        horizontalBoard.copyBoardCells(board, true);
        Board verticalBoard = new Board();
        verticalBoard.copyBoardCells(board, true);


        makeHorizontalBoard(horizontalBoard, horizontalRack);
        makeVerticalBoard(verticalBoard, verticalRack);
        chooseBoard(horizontalBoard, horizontalRack, verticalBoard, verticalRack, board);
    }

    //selects the best board to play
    private void chooseBoard(Board horizontalBoard, Rack horizontalRack, Board verticalBoard, Rack verticalRack, Board board) {
        if (horizontalBoard.countPoints(board) > verticalBoard.countPoints(board)) {
            board.copyBoardCells(horizontalBoard, true);
            rack.getRackCopy(horizontalRack);

        } else {
            board.copyBoardCells(verticalBoard, true);
            rack.getRackCopy(verticalRack);
        }
    }

    //writes a horizontal word to board
    private void makeHorizontalBoard(Board horizontalBoard, Rack horizontalRack) {
        horizontalBoard.copyBoardCells(BotLogic.scrabblePlay(horizontalBoard, horizontalRack), true);
    }

    //tilts then mirrors board, writes a horizontal word to it, then mirrors and tilts it back
    private void makeVerticalBoard(Board verticalBoard, Rack verticalRack) {
        UnsafeBoardMutations.tiltPiHalf(verticalBoard);
        UnsafeBoardMutations.mirrorAroundCol7(verticalBoard);
        verticalBoard.copyBoardCells(BotLogic.scrabblePlay(verticalBoard, verticalRack), true);
        UnsafeBoardMutations.mirrorAroundCol7(verticalBoard);
        UnsafeBoardMutations.tilt3PiHalf(verticalBoard);
    }

    /*
    //Prints the board for debugging purposes
    private void printBoard(Board board) {
        char[][] boardPrint = new char[15][15];
        for (int i = 0; i < board.getBoardCells().length; i++) {
            for (int j = 0; j < board.getBoardCells()[0].length; j++) {
                boardPrint[i][j] = board.getBoardCells()[i][j].getTileLetter();
            }
        }
        for (char[] row : boardPrint) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(" ");
    }

    //prints the rack for debugging purposes
    private void printRack(Rack rack) {
        System.out.println(BotLogic.getRackString(rack));
    }
    */




}
