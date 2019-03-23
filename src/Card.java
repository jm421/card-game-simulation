class Card {
    int value;

    // Stores a positive integer
    Card(int value) throws InvalidCardException {
        if (value <= 0) {
            throw new InvalidCardException("Invalid Card value: " + Integer.toString(this.value));
        } else {
            this.value = value;
        }
    }
}
