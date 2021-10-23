package com.zcrabblers.zcrabble.model.gameBoard.board;

import com.zcrabblers.zcrabble.model.gameBoard.Cell;

public class BoardMutations {

    /*

    //TODO the next two methods should definitively be private
    /**
     * Copies a board's cell matrix flipped 3pi/2 = -pi/2 radians
     * First row is first column, first column is last row
     * @param board the Board to be tilted.
     *//*
    public void tilt3PiHalf(Board board){
        Cell[][] tempCell = new Cell[board.getBoardCells()[0].length][board.getBoardCells().length];
        for (int i = 0; i <board.getBoardCells().length ; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {
                Cell newCell = board.getBoardCells()[i][j];
                tempCell[j][board.getBoardCells().length-i-1] = new Cell(newCell.GetCellWordMultiplier(),newCell.GetCellLetterMultiplier(),newCell.getPlacedTile());
            }
        }
        boardCells = tempCell;
    }

    /**
     * Copies a board's cell matrix flipped pi/2 radians
     * First row is first column, first column is last row
     * @param board the Board to be tilted.
     *//*
    public void tiltPiHalf(Board board){

        Cell[][] tempCell = new Cell[board.getBoardCells()[0].length][board.getBoardCells().length];
        for (int i = 0; i <board.getBoardCells().length ; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {
                Cell newCell = board.getBoardCells()[i][j];
                tempCell[board.getBoardCells()[0].length-j-1][i] = new Cell(newCell.GetCellWordMultiplier(),newCell.GetCellLetterMultiplier(),newCell.getPlacedTile());
            }
        }
        boardCells = tempCell;
    }

    /**
     * Mirrors the board around row 7 (horizontally)
     * first row is last row, last row is first row, col is col
     * @param board the Board to be mirrored
     *//*
    public void mirrorAroundCol7(Board board){

        Cell[][] tempCell = new Cell[board.getBoardCells()[0].length][board.getBoardCells().length];
        for (int i = 0; i <board.getBoardCells().length ; i++) {
            for (int j = 0; j <board.getBoardCells()[0].length ; j++) {
                Cell newCell = board.getBoardCells()[i][j];
                tempCell[board.getSize()-1-i][j] = new Cell(newCell.GetCellWordMultiplier(),newCell.GetCellLetterMultiplier(),newCell.getPlacedTile());
            }
        }
        boardCells = tempCell;
    }

    */
}
