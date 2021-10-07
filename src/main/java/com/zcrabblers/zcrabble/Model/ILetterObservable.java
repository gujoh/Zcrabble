package com.zcrabblers.zcrabble.Model;

import java.util.ArrayList;

// Implemented by classes who wish to subscribe to a LetterObserver
public interface ILetterObservable {
    void update(ArrayList<LetterTuple> boardList, ArrayList<LetterTuple> rackList);
}
