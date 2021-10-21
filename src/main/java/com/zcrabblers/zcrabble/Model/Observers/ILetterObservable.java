package com.zcrabblers.zcrabble.Model.Observers;

import com.zcrabblers.zcrabble.Model.CellTuple;

import java.util.List;

// Implemented by classes who wish to subscribe to a LetterObserver
public interface ILetterObservable {
    void updateState(List<CellTuple> boardList, boolean isGameOver);
}
