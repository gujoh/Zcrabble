package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;

public class BoardCheck {




    private static boolean checkBoard(Board board) {
       return (checkRow(board)&&checkCol(board)&&checkCoherhence(board));
    }

    private static boolean checkRow(Board board){
        return true;
    }

    /*--- Method for checking that all columns are filled with correct words ---*/
    private static boolean checkCol(Board board)  {

        for (int col = 0; col < board.Matrix().length ; col++) {
            for (int row = 0; row < board.Matrix().length; row++) {

                StringBuilder word = new StringBuilder();
                if (containsLetter(board,row,col) && containsLetter(board,row==0?row:row-1,col) || containsLetter(board,row==15?row:row+1,col) ){
                    word.append(board.Matrix()[row][col].getPlacedTile().getLetter());
                }
                if(word.length()>1 && !containsLetter(board,row,col)){
                    Dictionary.checkWord(word.toString());
                    word.delete(0,word.length());
                }

            /*
            StringBuffer word = new StringBuffer.

            Check cells on board [row][col]
            if:
                there is a letter on the cell && ( the next || last cell contains a letter)
                word.append(letter[row][col]
            if:
                (StringBuffer.length > 1) && (there is no letter on the cell)
                checkWord;
                word.delete(0, str.length());
             */
            }
            //checkWord
        }
        return true;
    }

    private static boolean checkCoherhence(Board board) {
        return true;
    }

    private static boolean containsLetter(Board board,int row, int col){
        return board.Matrix()[row][col].getPlacedTile().getLetter() != ' ';
    }

}
