package com.zcrabblers.zcrabble.Model;

import java.io.FileNotFoundException;
import java.util.List;

public interface IGame {
    void addSubscriber(ILetterObservable sub);
    void removeSubscriber(ILetterObservable sub);
    void removeAllSubscribers();
    Board getBoard();
    Board getTempBoard();
    boolean isBoardCellEmpty(int y, int x);
    boolean isTempCellEmpty(int y, int x);
    int getBoardSize();
    void switchBoardCells(int y1, int x1, int y2, int x2);
    void switchTempCells(int y1, int x1, int y2, int x2);
    void switchRackCells(int x1, int x2);
    void switchRackBoardCells(int rackX, int boardY, int boardX);
    void newGame();
    boolean endTurn();
    Rack getRack();
    boolean isRackEmpty(int x);
    List<IPlayers> getPlayers();
    int getPlayerScore(int index);
    int getRemainingTiles();
    void shuffleCurrentRack();
    char getRackLetter(int index);
    int getWinner();
    void returnTilesToRack();
    void fromRackToBag(int i);
}
