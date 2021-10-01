package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;

public interface IGame {
    void start();
    void addSubscriber(ILetterObservable sub);
    void removeSubscriber(ILetterObservable sub);
    void removeAllSubscribers();
    Board getBoard();
    Board getTempBoard();
    boolean isCellEmpty(int x, int y);
    boolean isTempCellEmpty(int x, int y);
    void newGame();
    void endTurn();
    Rack getRack();
    boolean isRackEmpty(int x);

}
