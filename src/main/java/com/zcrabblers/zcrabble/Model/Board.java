package com.zcrabblers.zcrabble.Model;

public class Board {

    public Cell[][] boardCells = new Cell[15][15];

    public Board(Cell[][] boardCells){
        this.boardCells = boardCells;
    }

    public Board(Board board){
        Cell[][] cells = board.boardCells;
        Cell[][] newCells = new Cell[cells.length][cells.length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                newCells[i][j] = new Cell(cells[i][j]);
            }

        }
    }

    public Cell[][] Matrix(){
        return boardCells;
    }
    private Board PlaceTile(Board board, int i, int j, Cell cell){
            board.Matrix()[i][j] = cell;
        return board;
    }
}
