import java.util.*;

class CardDeck {
    private int index;
    private Queue<Card> state = new LinkedList<>();

    CardDeck(int index) throws InvalidCardDeckException {
        if (index < 1){
            throw new InvalidCardDeckException("Invalid CardDeck index: " + Integer.toString(index));
        } else {
            this.index = index;
        }
    }

    // Getter method for deck index
    int getIndex() {
        return this.index;
    }

    // Getter method for deck state
    Queue<Card> getState() {
        return this.state;
    }

    // Returns deck size
    synchronized int getDeckSize() {
        return this.state.size();
    }

    // Puts given card to the bottom of the deck
    synchronized void updateDeck(Card card) {
        this.state.offer(card);
    }

    // Returns a card from the top of the deck
    synchronized Card takeCard() {
        return this.state.poll();
    }
}
