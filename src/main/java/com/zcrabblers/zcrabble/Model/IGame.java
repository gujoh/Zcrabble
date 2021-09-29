package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;

public interface IGame {
    void addSubscriber(ILetterObservable sub);
    void removeSubscriber(ILetterObservable sub);
    void removeAllSubscribers();
    Board getBoard();
    boolean isCellEmpty(int x, int y);
    void newGame() throws FileNotFoundException;
}
