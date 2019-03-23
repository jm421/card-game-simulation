import java.util.*;
import java.io.*;

public class CardGame {
    static ArrayList<Player> players = new ArrayList<>();
    static Queue<Card> cards = new LinkedList<>();
    static ArrayList<CardDeck> decks = new ArrayList<>();
    static int playerNumber = 0;
    static int playerWon;
    static boolean hasWon = false;
    static int highestTurns = 0;
    static int playersFinished = 0;
    static String filePath = "";
    static boolean validInt = false;
    static boolean validPath = false;
    static boolean correctPack = false;
    static Scanner reader = new Scanner(System.in);

    static String takeInput(){
        //Takes user keyboard input
        String input = "";

        input = reader.nextLine();
        return(input);
    }

    static void readPlayerNumber(String input){
        // Reading player number and checking it's a positive integer
        String stringInt = input;
        try {
            playerNumber = Integer.parseInt(stringInt);
            if (playerNumber > 0) {
                validInt = true;
            } else {
                System.out.println("Invalid input. Please enter a positive integer:");
                validInt = false;
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a positive integer:");
            validInt = false;
        }
    }

    static void readFilePath(String input){
        // Reading file path from user input and checking it meets the constraints
        String trialFilePath = input;
        try {
            File file1 = new File(trialFilePath);
            BufferedReader br = new BufferedReader(new FileReader(file1));
            int lineCount = 0;

            while (br.readLine() != null) {
                lineCount++;
            }
            if (lineCount == 8 * playerNumber) {
                validPath = true;
                filePath = trialFilePath;
            } else {
                System.out.println("File has an invalid number of cards. Try again: ");
            }
        } catch (Exception e) {
            System.out.println("File path is invalid. Try again: ");
        }
    }

    static void createCards(){
        // Reading file and creating new cards
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            while ((st = br.readLine()) != null) {
                try {
                    cards.add(new Card(Integer.parseInt(st)));
                } catch(InvalidCardException e) {
                    System.out.println(e.getMessage());
                }
            }
            correctPack = true;
        } catch (Exception e) {
            System.out.println("Error reading pack file. Enter a file path to valid pack: ");
            correctPack = false;
            validPath = false;
        }
    }

    static void setUp(){
        // Building players and decks
        for (int i = 1; i <= playerNumber; i++) {
            try {
                players.add(new Player(i));
                decks.add(new CardDeck(i));
            } catch (InvalidPlayerException | InvalidCardDeckException e){
                System.out.println(e.getMessage());
            }
        }

        // Round robin to players
        for (int i = 0; i < 4 * playerNumber; i++) {
            players.get(i % playerNumber).updateHand(cards.poll());
        }

        // Round robin to decks
        for (int i = 0; i < 4 * playerNumber; i++) {
            decks.get(i % playerNumber).updateDeck(cards.poll());
        }

        // Logging initial hands of each player
        for (Player player: players) {
            ArrayList<String> handArray = new ArrayList<>();
            for (Card card: player.wanted) {
                handArray.add(Integer.toString(card.value));
            }
            for (Card card: player.unwanted) {
                handArray.add(Integer.toString(card.value));
            }

            String message = "player " + Integer.toString(player.getIndex()) + " initial hand ";
            for (String string: handArray) {
                message = message.concat(string + " ");
            }
            player.log.add(message);
        }

        // Checking if any player won before the game started
        for (Player i: players) {
            if (i.won()) {
                System.out.println("Player " + Integer.toString(i.getIndex()) + " wins");
                CardGame.hasWon = true;
                CardGame.playerWon = i.getIndex();
                break;
            }
        }

        // Checking which player won, and updating logs of winner/losers, then writing player logs to file
        if (CardGame.hasWon) {
            for (Player player: players) {

                // If this player has won, update his logs accordingly
                if (CardGame.playerWon == player.getIndex()) {
                    player.log.add("player " + Integer.toString(player.getIndex()) + " wins");
                    player.log.add("player " + Integer.toString(player.getIndex()) + " exits");
                    ArrayList<String> handArray = new ArrayList<>();
                    if (player.wanted.size() > 0) {
                        for (Card card: player.wanted) {
                            handArray.add(Integer.toString(card.value));
                        }
                    } else {
                        for (Card card: player.unwanted) {
                            handArray.add(Integer.toString(card.value));
                        }
                    }
                    String message = "player " + Integer.toString(player.getIndex()) + " final hand: ";
                    for (String string: handArray) {
                        message = message.concat(string + " ");
                    }
                    player.log.add(message);
                }

                // Else this player has lost, update his logs accordingly
                else {
                    player.log.add("player " + Integer.toString(CardGame.playerWon) + " has informed player " +
                                   Integer.toString(player.getIndex()) + " that player " +
                                   Integer.toString(CardGame.playerWon) + " has won");
                    player.log.add("player " + Integer.toString(player.getIndex()) + " exits");
                    ArrayList<String> handArray = new ArrayList<> ();
                    for (Card card: player.wanted) {
                        handArray.add(Integer.toString(card.value));
                    }
                    for (Card card: player.unwanted) {
                        handArray.add(Integer.toString(card.value));
                    }
                    String message = "player " + Integer.toString(player.getIndex()) + " hand: ";
                    for (String string: handArray) {
                        message = message.concat(string + " ");
                    }
                    player.log.add(message);
                }

                // Writing player log to file
                String filename = "player" + Integer.toString(player.getIndex()) + "_output.txt";
                try {
                    FileWriter writer = new FileWriter(filename);
                    for (String string: player.log) {
                        writer.write(string + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    static void playGame(){
        // Building Thread array of appropriate size
        Thread threads[] = new Thread[playerNumber];

        // If no player wins initially, let the players play (i.e. start the game)
        if (!CardGame.hasWon) {
            for (int x = 0; x < playerNumber; x++) {
                threads[x] = new Thread(players.get(x));
                threads[x].start();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter number of players: ");
        while(!validInt) {
            readPlayerNumber(takeInput());
        }
        System.out.println("Enter file path to valid pack: ");
        while(!correctPack) {
            while(!validPath) {
                readFilePath(takeInput());
            }
            createCards();
        }
        reader.close();
        setUp();
        playGame();

    }

}
