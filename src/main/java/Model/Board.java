package Model;

public class Board {

    public Cell[][] boardCells = new Cell[15][15];

    public Board(Cell[][] boardCells){
        this.boardCells = boardCells;
    }
    public Cell[][] Matrix(){
        return boardCells;
    }
    private Board PlaceTile(Board board, int i, int j, Cell cell){
            board.Matrix()[i][j] = cell;
        return board;
    }
}
