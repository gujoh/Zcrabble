package com.zcrabblers.zcrabble.Model;

public class CellTuple{
    private int i;
    private int j;
    private Cell cell;

    public CellTuple(int i, int j, Cell cell){
        this.i = i;
        this.j = j;
        this.cell = cell;
    }
    public int getI(){return i;}
    public int getJ(){return j;}
    public Cell getCell(){return cell;}

}
