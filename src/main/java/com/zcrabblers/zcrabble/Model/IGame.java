package com.zcrabblers.zcrabble.Model;

public interface IGame {
    void addSubscriber(ILetterObservable sub);
    void removeSubscriber(ILetterObservable sub);
}
