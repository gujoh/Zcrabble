import com.zcrabblers.zcrabble.model.Letter;
import com.zcrabblers.zcrabble.model.LetterTuple;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LetterTests {
    @Test
    public void TestLetter(){
        Letter letter = Letter.A;
        assertEquals("A",letter.getLetter());
        LetterTuple letterTuple = new LetterTuple(letter, 1, 2);
        assertEquals(Letter.A, letterTuple.getLetter());
        assertEquals(1, letterTuple.getX());
        assertEquals(2, letterTuple.getY());

    }
}
