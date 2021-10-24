package com.zcrabblers.zcrabble.model.gameBoard;

/**
 * a simple class to group a cell with its position
 * @author Martin Björklund.
 */
public class CellTuple{
    private final int i;
    private final int j;
    private final Cell cell;

    /**
     * a simple class to group a cell with its position
     * @param i i corresponds to the position in the first list
     * @param j j corresponds to the position in the second list
     * @param cell the cell of a board
     * @see Cell
     */
    public  CellTuple(int i, int j, Cell cell){
        this.i = i;
        this.j = j;
        this.cell = cell;
    }

    /**
     * @return row coordinate of CellTuple
     */
    public int getI(){return i;}

    /**
     * @return column coordinate of CellTuple
     */
    public int getJ(){return j;}

    /**
     * @return cell of CellTuple
     */
    public Cell getCell(){return cell;}

    /**
     * @return letter on tile on cell of CellTuple
     */
    public char getTileLetter(){return cell.getTileLetter();}

    /**
     * @return score of tile on cell of CellTuple
     */
    public int getTileScore(){return cell.getTileScore();}

}
