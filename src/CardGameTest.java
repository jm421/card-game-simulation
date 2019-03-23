import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class CardGameTest {

    @Test
    public void testReadPlayerNumberString(){
        // Check that output is given when the user enters a string as a player number
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readPlayerNumber("abc");
        assertEquals("Invalid input. Please enter a positive integer:\r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        //  Check that validInt is still false
        assertFalse(CardGame.validInt);
    }

    @Test
    public void testReadPlayerNumberZero(){
        // Check that output is given when the user enters zero as a player number
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readPlayerNumber("0");
        assertEquals("Invalid input. Please enter a positive integer:\r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validInt is still false
        assertFalse(CardGame.validInt);
    }

    @Test
    public void testReadPlayerNumberNegative(){
        // Check that output is given when the user enters a negative player number
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readPlayerNumber("-2");
        assertEquals("Invalid input. Please enter a positive integer:\r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validInt is still false
        assertFalse(CardGame.validInt);
    }

    @Test
    public void testReadPlayerNumberEmpty(){
        // Check that output is given when the user enters no player number
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readPlayerNumber("");
        assertEquals("Invalid input. Please enter a positive integer:\r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validInt is still false
        assertFalse(CardGame.validInt);
    }

    @Test
    public void testReadPlayerNumber(){
        // Check that no output is given when the user enters a valid player number
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readPlayerNumber("3");
        assertNotEquals("Invalid input. Please enter a positive integer:\r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validInt has been set to true
        assertTrue(CardGame.validInt);

        // Check that playerNumber has been updated
        assertEquals(3, CardGame.playerNumber);
    }

    @Test
    public void testReadFilePathNumber(){
        // Check that an output message is given when the user enters an integer as a filepath
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readFilePath("2");
        assertEquals("File path is invalid. Try again: \r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validPath is still false
        assertFalse(CardGame.validPath);
    }

    @Test
    public void testReadFilePathString(){
        // Check that an output message is given when the user enters an invalid string as a filepath
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readFilePath("abc");
        assertEquals("File path is invalid. Try again: \r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validPath is still false
        assertFalse(CardGame.validPath);
    }

    @Test
    public void testReadFilePathWrongNumber(){
        CardGame.playerNumber = 4;
        // Check that an output message is given when the given file does not have the correct amount of numbers for the game (32)
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readFilePath("Sample Pack File Incorrect Number.txt");
        assertEquals("File has an invalid number of cards. Try again: \r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validPath is still false
        assertFalse(CardGame.validPath);
    }

    @Test
    public void testReadFilePathCorrect(){
        CardGame.playerNumber = 4;
        // Check that output not given when a file with the correct amount of numbers for the game (32) is given
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.readFilePath("Sample Pack File.txt");
        assertNotEquals("File has an invalid number of cards. Try again: \r\n", out.toString());
        assertNotEquals("File path is invalid. Try again: \r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validPath is true
        assertTrue(CardGame.validPath);

        // Check that filePath has been updated
        assertNotEquals("", CardGame.filePath);
    }

    @Test
    public void testCreateCardsIncorrectFile(){
        CardGame.filePath = "Sample Pack File Invalid.txt";

        // Check that the system outputs a message when a file is given that doesn't contain all integers
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.createCards();
        assertEquals("Error reading pack file. Enter a file path to valid pack: \r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validPath is false
        assertFalse(CardGame.validPath);

        // Check that correctPack is false;
        assertFalse(CardGame.correctPack);
    }

    @Test
    public void testCreateCardsIncorrectString(){
        CardGame.filePath = "abc";

        // Check that system outputs a message when an incorrect filepath is given
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.createCards();
        assertEquals("Error reading pack file. Enter a file path to valid pack: \r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validPath is false
        assertFalse(CardGame.validPath);

        // Check that correctPack is false;
        assertFalse(CardGame.correctPack);
    }

    @Test
    public void testCreateCardsInteger(){
        CardGame.filePath = "2";

        // Check that system outputs a message when an integer file path is given
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.createCards();
        assertEquals("Error reading pack file. Enter a file path to valid pack: \r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validPath is false
        assertFalse(CardGame.validPath);

        // Check that correctPack is false;
        assertFalse(CardGame.correctPack);
    }

    @Test
    public void testCreateCardsCorrect(){
        CardGame.filePath = "Sample Pack File.txt";
        CardGame.playerNumber = 4;
        CardGame.cards = new LinkedList<>();

        // Check that system does not output a message for a valid file pack with integers
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.createCards();
        assertNotEquals("Error reading pack file. Enter a file path to valid pack: \r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check that validPath is true
        assertTrue(CardGame.validPath);

        // Check that correctPack is false;
        assertTrue(CardGame.correctPack);

        // Check that 32 cards have been created
        assertEquals(32, CardGame.cards.size());
    }

    @Test
    public void testSetUpDecks(){
        CardGame.playerNumber = 4;
        CardGame.filePath = "Sample File Path.txt";
        CardGame.players = new ArrayList<>();
        CardGame.decks = new ArrayList<>();
        CardGame.cards = new LinkedList<>();
        CardGame.hasWon = false;
        CardGame.filePath = "Sample Pack File.txt";
        CardGame.createCards();
        CardGame.setUp();

        // Check that 4 decks have been created
        assertEquals(4, CardGame.decks.size());

        // Check that 4 players have been created
        assertEquals(4, CardGame.players.size());

        // Check that each player has 4 cards
        for(Player player: CardGame.players){
            assertEquals(4, player.wanted.size()+player.unwanted.size());
        }

        // Check that each deck has 4 cards
        for(CardDeck deck: CardGame.decks){

        }
    }

    @Test
    public void testSetUpPlayers(){
        CardGame.playerNumber = 4;
        CardGame.filePath = "Sample Pack File.txt";
        CardGame.players = new ArrayList<>();
        CardGame.decks = new ArrayList<>();
        CardGame.cards = new LinkedList<>();
        CardGame.hasWon = false;
        CardGame.createCards();
        CardGame.setUp();

        // Check that 4 players have been created
        assertEquals(4, CardGame.players.size());
    }

    @Test
    public void testSetUpPlayerCards() {
        CardGame.playerNumber = 4;
        CardGame.filePath = "Sample Pack File.txt";
        CardGame.players = new ArrayList<>();
        CardGame.decks = new ArrayList<>();
        CardGame.cards = new LinkedList<>();
        CardGame.hasWon = false;
        CardGame.createCards();
        CardGame.setUp();

        // Check that each player has 4 cards
        for (Player player : CardGame.players) {
            assertEquals(4, player.wanted.size() + player.unwanted.size());
        }
    }

    @Test
    public void testSetUpDeckCards() {
        CardGame.playerNumber = 4;
        CardGame.filePath = "Sample Pack File.txt";
        CardGame.players = new ArrayList<>();
        CardGame.decks = new ArrayList<>();
        CardGame.cards = new LinkedList<>();
        CardGame.hasWon = false;
        CardGame.createCards();
        CardGame.setUp();

        // Check that each deck has 4 cards
        for (CardDeck deck : CardGame.decks) {
            assertEquals(4, deck.getDeckSize());
        }
    }

    @Test
    public void testSetUpPlayerLogs() {
        CardGame.playerNumber = 4;
        CardGame.filePath = "Sample Pack File.txt";
        CardGame.players = new ArrayList<>();
        CardGame.decks = new ArrayList<>();
        CardGame.cards = new LinkedList<>();
        CardGame.hasWon = false;
        CardGame.createCards();
        CardGame.setUp();

        // Check message added to all players log files
        for(Player player: CardGame.players){
            assertNotEquals(0, player.log.size());
        }
    }

    @Test
    public void testSetUpImmediateWin() {
        CardGame.playerNumber = 4;
        // File set up so that player 2 should immediately win
        CardGame.filePath = "Sample_Pack_File_player2.txt";
        CardGame.players = new ArrayList<>();
        CardGame.decks = new ArrayList<>();
        CardGame.cards = new LinkedList<>();
        CardGame.hasWon = false;
        CardGame.createCards();

        // Check system prints that player 2 has won
        PrintStream ps = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        CardGame.setUp();
        assertEquals("Player 2 wins\r\n", out.toString());
        System.setOut(ps);
        PrintStream originalOut = System.out;
        System.setOut(originalOut);

        // Check hasWon is now true
        assertTrue(CardGame.hasWon);

        // Check playerWon is equal to 2
        assertEquals(2, CardGame.playerWon);
    }

    @Test
    public void testSetUpImmediateWinLogs() {
        CardGame.playerNumber = 4;
        // File set up so that player 2 should immediately win
        CardGame.filePath = "Sample_Pack_File_player2.txt";
        CardGame.players = new ArrayList<>();
        CardGame.decks = new ArrayList<>();
        CardGame.cards = new LinkedList<>();
        CardGame.hasWon = false;
        CardGame.createCards();

        CardGame.setUp();

        // Check that each player has 4 items in its log array
        for(Player player: CardGame.players){
            assertEquals(4, player.log.size());
        }
    }

    @Test
    public void testSetUpImmediateWinException() {
        CardGame.playerNumber = 4;
        // File set up so that player 2 should immediately win
        CardGame.filePath = "Sample_Pack_File_player2.txt";
        CardGame.players = new ArrayList<>();
        CardGame.decks = new ArrayList<>();
        CardGame.cards = new LinkedList<>();
        CardGame.hasWon = false;
        CardGame.createCards();

        // Check no exception is thrown when writing log files
        try {
            CardGame.setUp();
            assertTrue(true);
        }
        catch(Exception e){
            fail();
        }
    }




}