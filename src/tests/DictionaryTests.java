import com.zcrabblers.zcrabble.model.Dictionary;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTests {
    @Test
    public void TestgetInstance(){
        Dictionary dictionary = Dictionary.getInstance();
        assertNotNull(dictionary.getDictSet());
        assertTrue(dictionary.checkWord("boat"));
    }
}
