import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

public class CardDeckTest {

    @Test
    public void testNegativeCardDeck(){
        // Check that an exception is thrown when an attempt is made to create a card deck with a negative index
        try {
            CardDeck deck = new CardDeck(-2);
            fail();
        } catch (InvalidCardDeckException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testZeroCardDeck(){
        // Check that an exception is thrown when an attempt is made to create a card deck with a index of 0
        try {
            CardDeck deck = new CardDeck(0);
            fail();
        } catch (InvalidCardDeckException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testEdgeCaseCardDeck(){
        // Check that no exception is thrown when an attempt is made to create a card deck with the minimum allowed index size, 1
        try {
            CardDeck deck = new CardDeck(1);
            assertTrue(true);
        } catch (InvalidCardDeckException e) {
            fail();
        }
    }

    @Test
    public void testGetIndex() {
        // Create new CardDeck with index 1
        CardDeck deck1 = null;
        try {
            deck1 = new CardDeck(1);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        // getIndex should return 1
        assertEquals(1, deck1.getIndex());
    }

    @Test
    public void testUpdateDeck() {
        // Create new CardDeck with index 2 and add 4 cards to it
        CardDeck deck2 = null;
        try {
            deck2 = new CardDeck(2);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            deck2.updateDeck(new Card(1));
            deck2.updateDeck(new Card(2));
            deck2.updateDeck(new Card(3));
            deck2.updateDeck(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // deck2 should contain 4 cards
        assertEquals(4, deck2.getState().size());

        // Card with value 1 should be at the top of the deck
        assertEquals(1, deck2.getState().peek().value);
    }

    @Test
    public void testGetDeckSize() {
        // Create a new CardDeck with index 5 and add 5 cards to it
        CardDeck deck4 = null;
        try {
            deck4 = new CardDeck(4);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            deck4.updateDeck(new Card(1));
            deck4.updateDeck(new Card(2));
            deck4.updateDeck(new Card(3));
            deck4.updateDeck(new Card(4));
            deck4.updateDeck(new Card(5));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }


        // Check that getDeckSize returns 5
        assertEquals(5, deck4.getDeckSize());

    }

    @Test
    public void testGetState() {
        // Create a new CardDeck with index 3 and add 4 cards to it
        CardDeck deck3 = null;
        try {
            deck3 = new CardDeck(3);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            deck3.updateDeck(new Card(1));
            deck3.updateDeck(new Card(2));
            deck3.updateDeck(new Card(3));
            deck3.updateDeck(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Check that get state doesn't return null
        assertNotNull(deck3.getState());
        // Check that getState returns a queue
        assertTrue(deck3.getState() instanceof Queue);
    }

    @Test
    public void testTakeCard() {
        // Create a new CardDeck with index 3 and add 4 cards to it
        CardDeck deck3 = null;
        try {
            deck3 = new CardDeck(3);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            deck3.updateDeck(new Card(1));
            deck3.updateDeck(new Card(2));
            deck3.updateDeck(new Card(3));
            deck3.updateDeck(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // check that takeCard returns card with value 1 - top of the deck
        assertEquals(1, deck3.takeCard().value);
    }
}