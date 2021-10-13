package com.zcrabblers.zcrabble.Model;
/**
 * a simple class to group a cell with its position
 */
public class CellTuple{
    private int i;
    private int j;
    private Cell cell;

    /**
     * a simple class to group a cell with its position
     * @param i i corresponds to the position in the first list
     * @param j j corresponds to the position in the second list
     * @param cell the cell of a board
     */
    public CellTuple(int i, int j, Cell cell){
        this.i = i;
        this.j = j;
        this.cell = cell;
    }
    public int getI(){return i;}
    public int getJ(){return j;}
    public Cell getCell(){return cell;}

}
