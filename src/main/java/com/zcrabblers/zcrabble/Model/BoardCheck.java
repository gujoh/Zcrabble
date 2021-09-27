package com.zcrabblers.zcrabble.Model;

public class BoardCheck {
    private static final Dictionary dict = Dictionary.getInstance();

    private boolean checkBoard(Board board) {
       return (checkRow(board)&&checkCol(board)&& checkCoherence(board));
    }

    private static boolean checkRow(Board board){
        return true;
    }

    /*--- Method for checking that all columns are h with correct words ---*/
    private static boolean checkCol(Board board) {

       return true;
        /*
        boolean columnsAreIndeedValid = true;
        StringBuilder word = new StringBuilder();

        for (int col = 0; col < board.matrix().length; col++) {
            if (word.length() > 0) {
                columnsAreIndeedValid = dict.checkWord(word.toString());
                word.delete(0, word.length());
            }
            for (int row = 0; row < board.matrix().length; row++) {

                //if there is a letter on the current cell and that letter is part of a vertical word, add the letter to word.
                if (containsLetter(board, row, col) && containsLetter(board, row == 0 ? row : row - 1, col) || containsLetter(board, row == 15 ? row : row + 1, col)) {
                    word.append(board.matrix()[row][col].getPlacedTile().getLetter());
                }
                //If there already is a String in word and there is no letter on the current cell,
                // the String is finished, and will be checked, then deleted from word
                if (word.length() > 1 && !containsLetter(board, row, col)) {
                    columnsAreIndeedValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                if (!columnsAreIndeedValid) {
                    return false;
                }
            }
        }
        return columnsAreIndeedValid;*/
    }

    private static boolean checkRowCol(Board board){
        boolean rowColAreIndeedValid = true;
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < board.matrix().length; i++) {
            if (word.length() > 0) {
                rowColAreIndeedValid = dict.checkWord(word.toString());
                word.delete(0, word.length());
            }
            for (int j = 0; j < board.matrix().length; j++) {

                //if there is a letter on the current cell and that letter is part of a word, add the letter to word.
                if (containsLetter(board, i, j) && containsLetter(board, i, j == 0 ? j : j - 1) || containsLetter(board, i, j == 0 ? j : j + 1)) {
                    word.append(board.matrix()[i][j].getPlacedTile().getLetter());
                }
                //If there already is a String in word and there is no letter on the current cell,
                // the String is finished, and will be checked, then deleted from word
                if (word.length() > 1 && !containsLetter(board, i, j)) {
                    rowColAreIndeedValid = dict.checkWord(word.toString());
                    word.delete(0, word.length());
                }
                if (!rowColAreIndeedValid) {
                    return false;
                }
            }
        }return rowColAreIndeedValid;
    }

        private static boolean checkCoherence(Board board){
            return true;
        }

        private static boolean containsLetter (Board board,int row, int col){
            return board.matrix()[row][col].getPlacedTile().getLetter() != ' ';
        }

    }

