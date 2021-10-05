package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.List;

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
    List<IPlayers> getPlayers();
    int getPlayerScore(int index);
}
