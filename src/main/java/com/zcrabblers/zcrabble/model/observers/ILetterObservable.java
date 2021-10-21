package com.zcrabblers.zcrabble.model.observers;

import com.zcrabblers.zcrabble.model.gameBoard.CellTuple;

import java.util.List;

// Implemented by classes who wish to subscribe to a LetterObserver
public interface ILetterObservable {
    void updateState(List<CellTuple> boardList, boolean isGameOver);
}
