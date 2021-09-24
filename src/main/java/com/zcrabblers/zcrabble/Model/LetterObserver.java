package com.zcrabblers.zcrabble.Model;

import java.util.ArrayList;
import java.util.List;

// Handles ILetterObservable subscribers and notifies them of changes
public class LetterObserver {
    private final List<ILetterObservable> subscribers = new ArrayList<>();

    public void addSubscriber(ILetterObservable sub){
        subscribers.add(sub);
    }

    public void removeSubscriber(ILetterObservable sub){
        subscribers.remove(sub);
    }

    public void notifySubscribers(Letter letter){
        subscribers.forEach(x -> x.update(letter));
    }

}
