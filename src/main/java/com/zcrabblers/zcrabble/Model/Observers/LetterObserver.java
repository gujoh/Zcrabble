package com.zcrabblers.zcrabble.Model.Observers;

import com.zcrabblers.zcrabble.Model.GameBoard.CellTuple;

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

    public void removeAllSubscribers(){
        for (int i = subscribers.size() - 1; i >= 0; i--) {
            removeSubscriber(subscribers.get(i));
        }
    }

    public void notifySubscribers(List<CellTuple> boardList, boolean isGameOver){
        subscribers.forEach(x -> x.updateState(boardList,isGameOver));
    }

}
