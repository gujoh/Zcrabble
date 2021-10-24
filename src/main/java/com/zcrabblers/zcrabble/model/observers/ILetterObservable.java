package com.zcrabblers.zcrabble.model.observers;

import com.zcrabblers.zcrabble.model.gameBoard.CellTuple;

import java.util.List;

/**
 * ILetterObserver is implemented by classes who wish to subscribe to a LetterObserver.
 * @author Gustaf Jonasson, Niklas Axelsson.
 */
public interface ILetterObservable {

    /**
     * Updates the current state of the Game using boardList and isGameOver.
     * @param boardList a list of CellTuples containing new cells and their positions.
     * @param isGameOver whether the current game is over.
     */
    void updateState(List<CellTuple> boardList, boolean isGameOver);
}
