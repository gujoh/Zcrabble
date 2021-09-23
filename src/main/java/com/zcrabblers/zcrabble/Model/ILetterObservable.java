package com.zcrabblers.zcrabble.Model;

// Implemented by classes who which to subscribe to a LetterObserver
public interface ILetterObservable {
    void update(Letter letter);
}
