package com.zcrabblers.zcrabble.Model;

// Implemented by classes who wish to subscribe to a LetterObserver
public interface ILetterObservable {
    void update(LetterTuple[] boardList, LetterTuple[] rackList);
}
