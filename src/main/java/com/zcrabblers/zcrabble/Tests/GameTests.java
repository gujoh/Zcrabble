package com.zcrabblers.zcrabble.Tests;

import com.zcrabblers.zcrabble.Model.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameTests {
    @Test
    public void testSwitchRackBoardTile(){
        Game game = new Game(2, 0);
        game.newGame();
        Board temp = game.getTempBoard();
        Rack rack = game.getRack();

        temp.placeTile(5, 5, new Tile('K', 5));

        char oBoard = temp.getTile(5, 5).getLetter();
        char oRack = rack.getTile(4).getLetter();

        game.switchRackBoardCells(4, 5, 5);

        Tile rackT = rack.getTile(4);
        Tile tempT = temp.getTile(5, 5);
        assertEquals(oBoard, rackT.getLetter());
        assertEquals(oRack, tempT.getLetter());
    }

    @Test
    public void testFromRackToBag(){
        Game game = new Game(2, 0);
        game.newGame();
        int temp = game.getRemainingTiles();
        game.fromRackToBag(0);
        assertEquals(game.getRemainingTiles(), temp + 1);
    }

    @Test
    public void testFromRackToBag2(){
        Game game = new Game(2, 0);
        game.newGame();
        int index = 2;
        game.fromRackToBag(index);
        assertEquals(index, game.getRack().getFirstFreeIndex());
    }

    @Test
    public void endTurnFailTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        assertFalse(game.endTurn());
    }

    @Test
    public void endTurnTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        Rack rack = game.getRack();
        for(int i = 0; i < 7; i++)
            rack.remove(i);
        rack.set(0, new Tile('L', 1));
        rack.set(1, new Tile('A', 1));
        game.switchRackBoardCells(0, 7, 7);
        game.switchRackBoardCells(1, 7, 8);
        assertTrue(game.endTurn());
    }

    @Test
    public void GetWinnerTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        List<IPlayers> players = game.getPlayers();
        players.get(0).addScore(44);
        players.get(1).addScore(99);
        assertEquals(1 + 1, game.getWinner());
    }

    @Test
    public void returnTilesToRackTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        for (int i = 0; i < 7; i++) {
            game.switchRackBoardCells(i, 0, i);
        }
        game.returnTilesToRack();
        boolean boardOK = true;
        boolean rackOK = true;
        for (int i = 0; i < 7; i++) {
            if(!game.isTempCellEmpty(0, i))
                boardOK = false;
            if(game.isRackEmpty(i))
                rackOK = false;
        }
        assertTrue(boardOK && rackOK);
    }
    @Test
    public void playerScoreTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        List<IPlayers> players = game.getPlayers();
        int score = game.getPlayerScore(0);
        players.get(0).addScore(44);
        assertEquals(score + 44, game.getPlayerScore(0));
    }

    @Test
    public void boardSizeTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        assertEquals(15, game.getBoardSize());
    }

    @Test
    public void isBoardCellEmptyTest() {
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        Rack rack = game.getRack();
        for (int i = 0; i < 7; i++)
            rack.remove(i);
        rack.set(0, new Tile('L', 1));
        rack.set(1, new Tile('A', 1));
        game.switchRackBoardCells(0, 7, 7);
        game.switchRackBoardCells(1, 7, 8);
        game.endTurn();
        assertTrue(!game.isBoardCellEmpty(7, 7) && !game.isBoardCellEmpty(7, 8));
    }

    @Test
    public void ShuffleCurrentRackTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        Rack rack = game.getRack();

        Tile[] preShuffle = new Tile[7];
        for (int i = 0; i < 7; i++) {
            Tile t = rack.getTile(i);
            preShuffle[i] = new Tile(t.getLetter(), t.getTileScore());
        }
        game.shuffleCurrentRack();
        boolean ok = false;
        for (int i = 0; i < 7; i++) {
            if(preShuffle[i].getLetter() != game.getRackLetter(i))
                ok = true;
        }
        assertTrue(ok);
    }

    @Test
    public void switchRackCellsTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        char two = game.getRackLetter(2);
        char four = game.getRackLetter(4);
        game.switchRackCells(2, 4);
        assertTrue(two == game.getRackLetter(4) && four == game.getRackLetter(2));
    }

    @Test
    public void switchTempCellsTest(){
        GameManager gm = GameManager.getInstance();
        gm.newGame(2, 0);
        Game game = gm.getCurrentGame();
        Rack rack = game.getRack();
        for (int i = 0; i < 7; i++)
            rack.remove(i);
        rack.set(0, new Tile('L', 1));
        rack.set(1, new Tile('A', 1));
        game.switchRackBoardCells(0, 7, 7);
        game.switchRackBoardCells(1, 7, 8);
        game.switchTempCells(7, 7, 7, 8);
        char one = game.getTempBoard().getTile(7, 7).getLetter();
        char two = game.getTempBoard().getTile(7, 8).getLetter();
        System.out.println(one + " " + two);
        assertTrue('L' == two && 'A' == one);
    }
}
