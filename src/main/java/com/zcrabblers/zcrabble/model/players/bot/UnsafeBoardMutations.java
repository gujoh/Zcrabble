package com.zcrabblers.zcrabble.model.players.bot;

import com.zcrabblers.zcrabble.model.gameBoard.Cell;
import com.zcrabblers.zcrabble.model.gameBoard.Board;


/**
 * UnsafeBoardMutations contains methods for mirroring and flipping a Board.
 * These are definitely Board operations but none but Bot will or should ever have access to them.
 * To fix this the method writeScrabbleWord in BotLogic should be rewritten to allow it to check in both directions.
 * If there was no deadline tomorrow that is what would be done.
 */
    //TODO Make above suggested changes, then delete this whole class.

class UnsafeBoardMutations {


    //TODO the next two methods should definitively be private
    /**
     * Copies a board's cell matrix flipped 3pi/2 = -pi/2 radians
     * First row is first column, first column is last row
     * @param board the Board to be tilted.
     */
    static void tilt3PiHalf(Board board){
        Board tempBoard = new Board();
        for (int i = 0; i <board.getBoardCells().length ; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {

                tempBoard.getBoardCells()[j][board.getBoardCells().length-i-1] = new Cell(board.getCellWordMultiplier(i,j),board.getCellLetterMultiplier(i,j),board.getTile(i,j));

            }
        }
        board.copyBoardCells(tempBoard,true);
    }

    /**
     * Copies a board's cell matrix flipped pi/2 radians
     * First row is first column, first column is last row
     * @param board the Board to be tilted.
     */
    static void tiltPiHalf(Board board){

        Board tempBoard = new Board();
        for (int i = 0; i <board.getBoardCells().length ; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {

                tempBoard.getBoardCells()[board.getBoardCells()[0].length-j-1][i] = new Cell(board.getCellWordMultiplier(i,j),board.getCellLetterMultiplier(i,j),board.getTile(i,j));

            }
        }
        board.copyBoardCells(tempBoard, true);
    }

    /**
     * Mirrors the board around row 7 (horizontally)
     * first row is last row, last row is first row, col is col
     * @param board the Board to be mirrored
     */
    static void mirrorAroundCol7(Board board){

        Board tempBoard = new Board();
        for (int i = 0; i <board.getBoardCells().length ; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {

                tempBoard.getBoardCells()[board.getSize()-1-i][j] = new Cell(board.getCellWordMultiplier(i,j), board.getCellLetterMultiplier(i,j), board.getTile(i,j));

            }
        }
        board.copyBoardCells(tempBoard,true);
    }



}
