import com.zcrabblers.zcrabble.model.observers.ITurnObservable;
import com.zcrabblers.zcrabble.model.observers.TurnObserver;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TurnObserverTests {

    TurnObserver observer = new TurnObserver();

    private static class ObserverTest implements ITurnObservable{

        @Override
        public boolean endTurn() {
            return false;
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
