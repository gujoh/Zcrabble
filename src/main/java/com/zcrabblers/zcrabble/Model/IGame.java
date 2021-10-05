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
    boolean isBoardCellEmpty(int x, int y);
    boolean isTempCellEmpty(int x, int y);
    void switchBoardCells(int x1, int y1, int x2, int y2);
    void switchTempCells(int x1, int y1, int x2, int y2);
    void switchRackCells(int x1, int x2);
    void switchRackBoardCells(int rackX, int boardX, int boardY);
    void newGame();
    void endTurn();
    Rack getRack();
    boolean isRackEmpty(int x);
    List<IPlayers> getPlayers();
    int getPlayerScore(int index);
}
