package com.zcrabblers.zcrabble.Model;

import java.util.ArrayList;
import java.util.List;

public class TurnObserver {

    private final List<ITurnObservable> subscribers = new ArrayList<>();

    public void addSubscriber(ITurnObservable sub){
        subscribers.add(sub);
    }

    public void removeSubscriber(ITurnObservable sub){
        subscribers.remove(sub);
    }

    public void removeAllSubscribers(){
        for (int i = subscribers.size() - 1; i >= 0; i--) {
            removeSubscriber(subscribers.get(i));
        }
    }

    public void notifySubscribers(){
        subscribers.forEach(ITurnObservable::endTurn);
    }
}
