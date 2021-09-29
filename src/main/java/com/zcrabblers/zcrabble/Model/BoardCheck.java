package com.zcrabblers.zcrabble.Model;

public class BoardCheck {
    private static final Dictionary dict = Dictionary.getInstance();

    public boolean checkBoard(Board board) {
       return (checkRow(board)&&checkCol(board)&& checkCoherence(board));
    }

    /*--- Method for checking that all columns are h with correct words ---*/
    private static boolean checkCol(Board board) {
        boolean colAreIndeedValid = true;
            StringBuilder word = new StringBuilder();

            for (int col = 0; col < board.matrix().length; col++) {
                if (word.length() > 0) {
                    colAreIndeedValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                for (int row = 0; row < board.matrix().length; row++) {

                    //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                    if (containsLetter(board, row, col) && containsLetter(board, row==0?row:row-1, col ) || containsLetter(board, row==14?row:row+1, col)) {
                        word.append(board.matrix()[col][row].getPlacedTile().getLetter());
                    }
                    //If there already is a String in word and there is no letter on the current cell,
                    // the String is finished, and will be checked, then deleted from word
                    if (word.length() > 1 && !containsLetter(board, col, row)) {
                        colAreIndeedValid = dict.checkWord(word.toString());
                        word.delete(0, word.length());
                    }
                    if (!colAreIndeedValid) {
                        return false;
                    }
                }
            }return colAreIndeedValid;
        }


    private static boolean checkRow(Board board){
        boolean rowAreIndeedValid = true;
        StringBuilder word = new StringBuilder();

        for (int row = 0; row < board.matrix().length; row++) {
            if (word.length() > 0) {
                rowAreIndeedValid = dict.checkWord(word.toString());
                word.delete(0, word.length());
            }
            for (int col = 0; col < board.matrix().length; col++) {

                //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                if (containsLetter(board, row, col) && containsLetter(board, row, col==0?col:col-1) || containsLetter(board, row, col==14?col:col+1)) {
                    word.append(board.matrix()[row][col].getPlacedTile().getLetter());
                }
                //If there already is a String in word and there is no letter on the current cell,
                // the String is finished, and will be checked, then deleted from word
                if (word.length() > 1 && !containsLetter(board, row, col)) {
                    rowAreIndeedValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                if (!rowAreIndeedValid) {
                    return false;
                }
            }
        }return rowAreIndeedValid;
    }

    //TODO checkCoherence
    /*--- Checks if all letters on the board are in contact with each other. ---*/
    private static boolean checkCoherence(Board board){
        /*
        check that the center is not empty.
        check that every tile is connected to the center
         */
        if (!containsLetter(board,7,7))
            return false;
        for (int i = 0; i <12 ; i++) {

        }










        return true;
    }

    /*--- Checks if a cell contains a letter tile ---*/
    private static boolean containsLetter (Board board,int i, int j){
        return board.matrix()[i][j].getPlacedTile().getLetter() != ' ';
    }

}

