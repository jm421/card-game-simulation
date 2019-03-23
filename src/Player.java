import java.io.IOException;
import java.io.FileWriter;
import java.util.*;

public class Player implements Runnable {
    ArrayList<Card> wanted  = new ArrayList<>();
    Queue<Card> unwanted = new LinkedList<>();
    ArrayList<String> log = new ArrayList<>();
    private int index;
    private int numberOfTurns = 0;


    Player(int index) throws InvalidPlayerException {
        if (index < 1){
            throw new InvalidPlayerException("Invalid Player index: " + Integer.toString(index));
        } else {
            this.index = index;
        }
    }

    // Adds given card to appropriate hand depending on card value and player index
    void updateHand(Card card) {
        if ((card.value == this.index) && this.wanted.size() != 4) {
            this.wanted.add(card);
        } else {
            this.unwanted.offer(card);
        }
    }

    // Getter method for player index
    int getIndex() {
        return this.index;
    }

    // Getter method for number of turns
    int getNumberOfTurns(){
        return this.numberOfTurns;
    }

    // Removes specified card from unwanted partition of player hand
    Card removeCardFromUnwanted() {
        return this.unwanted.remove();
    }

    // Removes a card from top of specified deck, and returns it
    synchronized Card drawCardFromDeck(CardDeck deck) {
        return deck.takeCard();
    }

    // Checks if a player was won via wanted cards
    boolean checkWanted(){
        return (this.wanted.size() == 4);
    }

    // Checks if an unwanted hand contains winning cards
    boolean checkUnwanted() {
        Card topCard = this.unwanted.element();
        if (this.unwanted.size() != 4) {
            return false;
        }
        for (Card card: this.unwanted) {
            if (card.value != topCard.value) {
                return false;
            }
        }
        return true;
    }

    // Puts given card to the bottom of given deck
    synchronized void discardCardToDeck(Card card, CardDeck deck) {
        deck.updateDeck(card);
    }

    // Checks if a player has won
    boolean won() {
        return (checkWanted() || checkUnwanted());
    }

    // Updates highest total number of turns taken in the game
    private synchronized void changeHighestTurns() {
        if (this.numberOfTurns > CardGame.highestTurns) {
            CardGame.highestTurns = this.numberOfTurns;
        }
    }

    // Takes players turn and updates logs
    synchronized void takeTurn(CardDeck leftDeck, CardDeck rightDeck) {
        // Updating player turn count and highest turn count
        this.numberOfTurns++;
        changeHighestTurns();

        // Drawing card from left deck and updating player hand, and updates player log
        Card newCard = this.drawCardFromDeck(leftDeck);
        updateHand(newCard);
        this.log.add("Player " + Integer.toString(this.index) + " draws a " + Integer.toString(newCard.value) +
                     " from deck " + Integer.toString(leftDeck.getIndex()));

        // Discards an unwanted card from player hand to right deck, and updates player log
        Card discarded = removeCardFromUnwanted();
        discardCardToDeck(discarded, rightDeck);
        this.log.add("Player " + Integer.toString(this.index) + " discards a " + Integer.toString(discarded.value) +
                     " to deck " + Integer.toString(rightDeck.getIndex()));

        // Checks players current hand (wanted and unwanted), and adds this to the player log
        ArrayList<String> handArray = new ArrayList<>();
        for (Card card: this.wanted) {
            handArray.add(Integer.toString(card.value));
        }
        for (Card card: this.unwanted) {
            handArray.add(Integer.toString(card.value));
        }
        String message = "player " + Integer.toString(this.index) + " current hand is: ";
        for (String string: handArray) {
            message = message.concat(string + " ");
        }
        this.log.add(message);
    }


    public void run() {
        // While the game has not been won and the left deck is non-empty, take a turn and check if won
        while (!CardGame.hasWon) {
            if (CardGame.decks.get(this.index - 1).getDeckSize() >= 1) {
                takeTurn(CardGame.decks.get(this.index - 1), CardGame.decks.get((this.index) % CardGame.playerNumber));
                synchronized (this) {
                    if (won()) {
                        CardGame.hasWon = true;
                        CardGame.playerWon = this.index;
                        System.out.println("Player " + Integer.toString(this.index) + " wins");
                    }
                    changeHighestTurns();
                }
            }
        }

        // If this player has won, update his logs accordingly
        if (CardGame.playerWon == this.index) {
            this.log.add("player " + Integer.toString(this.index) + " wins");
            this.log.add("player " + Integer.toString(this.index) + " exits");

            ArrayList<String> handArray = new ArrayList<>();
            if (this.wanted.size() > 0) {
                for (Card card: this.wanted) {
                    handArray.add(Integer.toString(card.value));
                }
            } else {
                for (Card card: this.unwanted) {
                    handArray.add(Integer.toString(card.value));
                }
            }
            String message = "player " + Integer.toString(this.index) + " final hand: ";
            for (String string: handArray) {
                message = message.concat(string + " ");
            }
            this.log.add(message);

        }

        // Else this player has lost, update his logs accordingly
        else {
            this.log.add("player " + Integer.toString(CardGame.playerWon) + " has informed player " +
                    Integer.toString(this.index) + " that player " + Integer.toString(CardGame.playerWon) +
                    " has won");
            this.log.add("player " + Integer.toString(this.index) + " exits");

            ArrayList<String> handArray = new ArrayList<>();
            for (Card card: this.wanted) {
                handArray.add(Integer.toString(card.value));
            }
            for (Card card: this.unwanted) {
                handArray.add(Integer.toString(card.value));
            }
            String message = "player " + Integer.toString(this.index) + " hand: ";
            for (String string: handArray) {
                message = message.concat(string + " ");
            }
            this.log.add(message);
        }

        // If the player has taken less turns than that of the quickest player, he must play on
        while (this.numberOfTurns < CardGame.highestTurns) {
            if (CardGame.decks.get(this.index - 1).getDeckSize() >= 1) {
                takeTurn(CardGame.decks.get(this.index - 1), CardGame.decks.get((this.index) % CardGame.playerNumber));
            }
            changeHighestTurns();
        }
        CardGame.playersFinished++;

        // Writing player logs to file
        String filename = "player" + Integer.toString(this.index) + "_output.txt";
        try {
            FileWriter writer = new FileWriter(filename);
            for (String string: this.log) {
                writer.write(string + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        // If players are still playing, wait for them to finish
        while (CardGame.playersFinished != CardGame.playerNumber) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Writing left-deck log to file
        String deckLog = "deck" + Integer.toString(CardGame.decks.get(this.index - 1).getIndex()) + " contents: ";
        String deckFileName = "deck" + Integer.toString(CardGame.decks.get(this.index - 1).getIndex()) + "_output.txt";
        for (Card card: CardGame.decks.get(this.index - 1).getState()) {
            deckLog = deckLog.concat(Integer.toString(card.value) + " ");
        }

        try {
            FileWriter writer = new FileWriter(deckFileName);
            writer.write(deckLog);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
