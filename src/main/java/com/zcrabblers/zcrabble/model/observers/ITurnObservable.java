package com.zcrabblers.zcrabble.model.observers;

/**
 * ITurnObserver is implemented by classes who wish to subscribe to a TurnObserver.
 * @author Gustaf Jonasson, Niklas Axelsson.
 */
public interface ITurnObservable {

    /**
     * Ends the current turn.
     * @return whether the turn was successfully ended.
     */
    boolean endTurn();
}
