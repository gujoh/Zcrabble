package com.zcrabblers.zcrabble.model.observers;

import java.util.ArrayList;
import java.util.List;

/**
 * TurnObserver handles ITurnObservable subscribers and notifies them of changes.
 * @author Niklas Axelsson
 * used by: Bot
 * uses: ITurnObservable
 */
public class TurnObserver {

    private final List<ITurnObservable> subscribers = new ArrayList<>();

    /**
     * Adds as subscriber to the list of subscribers.
     * @param sub the subscriber.
     */
    public void addSubscriber(ITurnObservable sub){
        subscribers.add(sub);
    }

    /**
     * Removes a subscriber from the list of subscribers.
     * @param sub the subscriber.
     */
    public void removeSubscriber(ITurnObservable sub){
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
     * Calls endTurn on all subscribers.
     */
    public void notifySubscribers(){
        subscribers.forEach(ITurnObservable::endTurn);
    }
}
