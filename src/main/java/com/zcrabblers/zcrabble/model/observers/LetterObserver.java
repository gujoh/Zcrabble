package com.zcrabblers.zcrabble.model.observers;

import com.zcrabblers.zcrabble.model.gameBoard.CellTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * LetterObserver handles ILetterObservable subscribers and notifies them of changes.
 */
public class LetterObserver {

    private final List<ILetterObservable> subscribers = new ArrayList<>();

    /**
     * Adds as subscriber to the list of subscribers.
     * @param sub the subscriber.
     */
    public void addSubscriber(ILetterObservable sub){
        subscribers.add(sub);
    }

    /**
     * Removes a subscriber from the list of subscribers.
     * @param sub the subscriber.
     */
    public void removeSubscriber(ILetterObservable sub){
        subscribers.remove(sub);
    }

    /**
     * Removes all subscribers.
     */
    public void removeAllSubscribers(){
        for (int i = subscribers.size() - 1; i >= 0; i--) {
            removeSubscriber(subscribers.get(i));
        }
    }

    /**
     * Returns the amount of subscribers.
     * @return the amount of subscribers in the subscribers list.
     */
    public int getSubscriberCount(){
        return subscribers.size();
    }

    /**
     * Notifies subscribers of changes.
     * @param boardList a list of new cells and their position.
     * @param isGameOver whether the current game is over.
     */
    public void notifySubscribers(List<CellTuple> boardList, boolean isGameOver){
        subscribers.forEach(x -> x.updateState(boardList,isGameOver));
    }

}
