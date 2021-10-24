import com.zcrabblers.zcrabble.model.gameBoard.CellTuple;
import com.zcrabblers.zcrabble.model.observers.ILetterObservable;
import com.zcrabblers.zcrabble.model.observers.LetterObserver;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.util.List;

/**
 * Tests for methods in LetterObserver.
 * @author Gustaf Jonasson.
 */
public class LetterObserverTests {

    LetterObserver observer = new LetterObserver();

    private static class ObserverTest implements ILetterObservable {

        @Override
        public void updateState(List<CellTuple> boardList, boolean isGameOver) {

        }
    }

    @Test
    public void testAddSub(){
        observer.addSubscriber(new ObserverTest());
        assertEquals(1, observer.getSubscriberCount());
    }

    @Test
    public void testRemoveSub(){
        ObserverTest obs = new ObserverTest();
        observer.addSubscriber(obs);
        for(int i = 0; i < 10; i++){
            observer.addSubscriber(new ObserverTest());
        }
        int count = observer.getSubscriberCount();
        observer.removeSubscriber(obs);
        assertTrue(observer.getSubscriberCount() < count);

        observer.removeAllSubscribers();

        assertEquals(0, observer.getSubscriberCount());
    }
}
