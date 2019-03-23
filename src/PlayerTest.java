import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void testNegativePlayer(){
        // Check that an exception is thrown when an attempt is made to create a card deck with a negative index
        try {
            Player player = new Player(-1);
            fail();
        } catch (InvalidPlayerException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testZeroPlayer(){
        // Check that an exception is thrown when an attempt is made to create a card deck with a negative index
        try {
            Player player = new Player(0);
            fail();
        } catch (InvalidPlayerException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testEdgeCasePlayer(){
        // Check that no exception is thrown when an attempt is made to create a card deck with the minimum allowed 
        // index value, 1
        try {
            Player player = new Player(1);
            assertTrue(true);
        } catch (InvalidPlayerException e) {
            fail();
        }
    }

    @Test
    public void testUpdateHand() {
        // Creates a new player and adds cards to its hand

        Player player2 = null;
        try {
            player2 = new Player(2);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            player2.updateHand(new Card(2));
            player2.updateHand(new Card(3));
            player2.updateHand(new Card(1));
            player2.updateHand(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // player2 should have 1 card (the 2 card) in its wanted hand and 3 cards in its unwanted
        assertEquals(1, player2.wanted.size());
        assertEquals(3, player2.unwanted.size());

        // The top of the unwanted hand should be the card with value 3
        assertEquals(3, player2.unwanted.peek().value);
    }

    @Test
    public void testGetIndex() {
        // Test getIndex method with a player of index 2, should return 2
        // this.index = 2;
        try {
            assertEquals(2, new Player(2).getIndex());
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNumberOfTurns(){
        // Create a player
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            player1.updateHand(new Card(1));
            player1.updateHand(new Card(2));
            player1.updateHand(new Card(3));
            player1.updateHand(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Create a left and a right deck
        CardDeck deck1 = null;
        try {
            deck1 = new CardDeck(1);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(2));
            deck1.updateDeck(new Card(3));
            deck1.updateDeck(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }
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

        // player1 takes 2 turns
        player1.takeTurn(deck1,deck2);
        player1.takeTurn(deck1,deck2);

        // Check that number of turns is 2
        assertEquals(2, player1.getNumberOfTurns());

    }

    @Test
    public void testRemoveCardFromUnwanted() {
        // Test removeCardFromUnwanted to ensure the method removes a correct card from the unwanted hand

        // Create a new player with a hand of 2,3,1,4
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            player1.updateHand(new Card(2));
            player1.updateHand(new Card(3));
            player1.updateHand(new Card(1));
            player1.updateHand(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Method should return a card that isn't a 1 and the unwanted queue should now hold 2 cards
        assertNotEquals(1, player1.removeCardFromUnwanted().value);
        assertEquals(2, player1.unwanted.size());
    }

    @Test
    public void testDrawCardFromDeck() {
        // Create a new deck containing cards 1,2,3,4 and a player
        CardDeck deck1 = null;
        try {
            deck1 = new CardDeck(1);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(2));
            deck1.updateDeck(new Card(3));
            deck1.updateDeck(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Check that drawCardFromDeck returns a value
        assertNotNull(player1.drawCardFromDeck(deck1));
        // Check that drawCardFromDeck returns a card
        assertTrue(player1.drawCardFromDeck(deck1) instanceof Card);
    }

    @Test
    public void testDiscardCardToDeck() {
        // Create a new deck containing 3 cards: 1,2,3 and a player
        CardDeck deck1 = null;
        try {
            deck1 = new CardDeck(1);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(2));
            deck1.updateDeck(new Card(3));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        try {
            player1.discardCardToDeck(new Card(4), deck1);
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Deck1 should now contain 4 cards
        assertEquals(4, deck1.getDeckSize());

    }

    @Test
    public void testCheckWanted(){
        // Create a player with a hand of 4 cards of its preferred denomination
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        for(int i=0; i<4; i++){
            try {
                player1.updateHand(new Card(1));
            } catch (InvalidCardException e) {
                e.printStackTrace();
            }
        }

        // Check that checkWanted returns true
        assertTrue(player1.checkWanted());
    }

    @Test
    public void testCheckUnwanted(){
        // Create player 1 with a hand of 4 cards with a value not of its preferred denomination
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            player1.updateHand(new Card(2));
            player1.updateHand(new Card(2));
            player1.updateHand(new Card(2));
            player1.updateHand(new Card(2));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Check that checkUnwanted returns true
        assertTrue(player1.checkUnwanted());
    }

    @Test
    public void testWon() {
        // Allocate player 2 with 4 different cards
        Player player2 = null;
        try {
            player2 = new Player(2);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            player2.updateHand(new Card(1));
            player2.updateHand(new Card(2));
            player2.updateHand(new Card(3));
            player2.updateHand(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        Player player3 = null;
        Player player4 = null;
        try {
            player3 = new Player(3);
            player4 = new Player(4);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        // Allocate player3 with 4 cards of its preferred value (3)
        // Allocate player4 with 4 cards of value 4, not of its preferred value (4)
        for(int i=0; i<4; i++){
            try {
                player3.updateHand(new Card(3));
                player4.updateHand(new Card(3));
            } catch (InvalidCardException e) {
                e.printStackTrace();
            }
        }
        // won() should return false as player2 does not have 4 cards of the same value
        assertFalse(player2.won());
        // won() should return true as player3 has 4 cards in its wanted hand
        assertTrue(player3.won());
        // won() should return true as player4 has 4 cards of the same value (3)
        assertTrue(player4.won());


    }

    @Test
    public void testTakeTurn() {
        // Create a new player with hand 1,2,3,4
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            player1.updateHand(new Card(1));
            player1.updateHand(new Card(2));
            player1.updateHand(new Card(3));
            player1.updateHand(new Card(4));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }


        // Create a left deck with 4 cards of value 1
        CardDeck leftDeck = null;
        try {
            leftDeck = new CardDeck(1);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            leftDeck.updateDeck(new Card(1));
            leftDeck.updateDeck(new Card(1));
            leftDeck.updateDeck(new Card(1));
            leftDeck.updateDeck(new Card(1));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Create a right deck with 4 cards
        CardDeck rightDeck = null;
        try {
            rightDeck = new CardDeck(2);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            rightDeck.updateDeck(new Card(1));
            rightDeck.updateDeck(new Card(4));
            rightDeck.updateDeck(new Card(3));
            rightDeck.updateDeck(new Card(2));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Check that the card with value 2 is at the top of the unwanted hand
        assertEquals(2, player1.unwanted.peek().value);

        player1.takeTurn(leftDeck, rightDeck);

        // player1 numberOfTurns should have equal 1
        assertEquals(1, player1.getNumberOfTurns());

        // player1 should still hold 4 cards in total
        assertEquals(4,player1.wanted.size() + player1.unwanted.size());

        // player1 should now have 2 cards in its wanted hand: 2 cards of value 1
        assertEquals(2, player1.wanted.size());

        // player1 should now have 2 cards in its unwanted hand after discarding one
        assertEquals(player1.unwanted.size(), 2);

        // leftDeck should now contain 3 cards
        assertEquals(leftDeck.getDeckSize(), 3);

        // rightDeck should now contain 5 cards
        assertEquals(rightDeck.getDeckSize(), 5);

        // Check that player1 has discarded the card with value 2
        assertNotEquals(2, player1.unwanted.peek().value);
    }

    @Test
    public void testRunWin(){
        // Set CardGame variables for a game with 1 player
        CardGame.hasWon = false;
        CardGame.playerWon = 0;
        CardGame.playerNumber = 1;
        CardGame.highestTurns = 0;
        CardGame.playersFinished = 0;
        CardGame.decks = new ArrayList<>();

        // Set up game by creating player1, 2 decks and assigning them cards
        CardDeck deck1 = null;
        try {
            deck1 = new CardDeck(1);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(1));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }
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
        CardGame.decks.add(deck1);
        CardGame.decks.add(deck2);
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            player1.updateHand(new Card(1));
            player1.updateHand(new Card(1));
            player1.updateHand(new Card(3));
            player1.updateHand(new Card(2));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        // Check that "player 1 wins" is output
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        player1.run();
        assertEquals("Player 1 wins\r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that the game has been won
        assertTrue(CardGame.hasWon);

        // Check that playerWon is 1
        assertEquals(1,CardGame.playerWon);

        // player1 should have taken 2 turns
        assertEquals(2, player1.getNumberOfTurns());

        // highestTurns should be 2
        assertEquals(2, CardGame.highestTurns);
    }

    @Test
    public void testRunWinLogs(){
        // Set CardGame variables for a game with 1 player
        CardGame.hasWon = false;
        CardGame.playerWon = 0;
        CardGame.playerNumber = 1;
        CardGame.highestTurns = 0;
        CardGame.playersFinished = 0;
        CardGame.decks = new ArrayList<>();

        // Set up game by creating player1, 2 decks and assigning them cards
        CardDeck deck1 = null;
        try {
            deck1 = new CardDeck(1);
        } catch (InvalidCardDeckException e) {
            e.printStackTrace();
        }
        try {
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(1));
            deck1.updateDeck(new Card(1));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }
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
        CardGame.decks.add(deck1);
        CardGame.decks.add(deck2);
        Player player1 = null;
        try {
            player1 = new Player(1);
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
        try {
            player1.updateHand(new Card(1));
            player1.updateHand(new Card(1));
            player1.updateHand(new Card(3));
            player1.updateHand(new Card(2));
        } catch (InvalidCardException e) {
            e.printStackTrace();
        }

        player1.run();
        // Check that player1 has items in its log array
        assertNotNull(player1.log);
    }
}