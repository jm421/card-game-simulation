import static org.junit.Assert.*;
import org.junit.Test;

public class CardTest {
    @Test
    public void testNegativeCard(){
        // Check that an exception is thrown when an attempt is made to create a card with a negative index
        try {
            Card card = new Card(-3);
            fail();
        } catch (InvalidCardException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testZeroCard(){
        // Check that an exception is thrown when an attempt is made to create a card with a index of 0
        try {
            Card card = new Card(0);
            fail();
        } catch (InvalidCardException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testEdgeCaseCard(){
        // Check that no exception is made when creating a card of the minimum value index, 1
        try {
            Card card = new Card(1);
            assertTrue(true);
        } catch (InvalidCardException e) {
            fail();
        }
    }
}
