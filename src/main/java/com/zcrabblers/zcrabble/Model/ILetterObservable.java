package com.zcrabblers.zcrabble.Model;

import java.util.ArrayList;

// Implemented by classes who wish to subscribe to a LetterObserver
public interface ILetterObservable {
    void updateState(ArrayList<LetterTuple> boardList);
}
