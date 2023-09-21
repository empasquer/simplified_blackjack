import java.util.*;

public class BlackJack2 {
    public static void main(String[] args) {
        new BlackJack2().run();
    }

    public void run() {
        Scanner userInput = new Scanner(System.in);
        String playerUserName;
        int playerHits = 0;
        boolean gameOver = false;

        List<Card> deck = createDeck();
        List<Card> playerHand = new ArrayList<>();
        int playerPoints = 0;
        int dealerPoints = 0;

        while (!gameOver) {
            System.out.println("Greetings player. Welcome to BlackJack for dummies.");
            System.out.println("What is your name?");
            playerUserName = userInput.nextLine().trim();
            playerUserName = playerUserName.substring(0,1).toUpperCase().concat(playerUserName.substring(1));
            System.out.printf("%s, you start.%n", playerUserName);

            while (playerHits < 3 && playerPoints <= 21) {
                playerHits++;
                System.out.printf("%d hit:%n", playerHits);
                Card card = drawCard(deck);
                playerHand.add(card);
                System.out.printf("The card is: %s of %s%n", card.name(), card.suit());
                playerPoints += getCardValue(card);
                System.out.printf("You have %d points.%n", playerPoints);

                if (playerPoints > 21 || playerHits >= 3) {
                    // Exit the loop if conditions are met
                    playerHits = 3;
                } else {
                    System.out.print("Type 'hit' to get another card. Type anything else to pass: ");
                    String input = userInput.nextLine();
                    if (!input.equalsIgnoreCase("hit")) {
                        // Exit the loop if input is not "hit"
                        playerHits = 3;
                    }
                }
            }

            if (!gameOver) {
                // Dealer's turn
                int dealerHits = 0;
                while (dealerHits < 3 && dealerPoints < 17) {
                    dealerHits++;
                    Card card = drawCard(deck);
                    System.out.printf("The dealer draws: %s of %s%n", card.name(), card.suit());
                    dealerPoints += getCardValue(card);
                    System.out.printf("Dealer has %d points.%n", dealerPoints);
                }

                // Determine the winner
                if (dealerPoints > 21 || (playerPoints <= 21 && playerPoints > dealerPoints)) {
                    System.out.println("You win!");
                } else if (playerPoints == dealerPoints) {
                    System.out.println("It's a draw!");
                } else {
                    System.out.println("Dealer wins!");
                }

                gameOver = true;
            }
        }
    }

    List<Card> createDeck() {
        // Represents a deck of 52 cards with named values and suit symbols
        List<Card> deck = new ArrayList<>();
        for (int suit = 0; suit < 4; suit++) {
            for (int value = 1; value <= 13; value++) {
                String cardName = getCardName(value);
                String suitSymbol = getSuitSymbol(suit);
                deck.add(new Card(cardName, suitSymbol));
            }
        }
        shuffleDeck(deck);
        return deck;
    }

    void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }

    Card drawCard(List<Card> deck) {
        if (deck.isEmpty()) {
            // Reshuffle the deck if it's empty
            deck.addAll(createDeck());
            shuffleDeck(deck);
        }
        return deck.remove(deck.size() - 1);
    }

    int getCardValue(Card card) {
        int value = card.getValue();
        if (value >= 10) {
            return 10;
        } else {
            return value;
        }
    }

    String getCardName(int value) {
        return switch (value) {
            case 1 -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> String.valueOf(value);
        };
    }

    String getSuitSymbol(int suit) {
        return switch (suit) {
            case 0 -> "\u2660"; // Spades (♠)
            case 1 -> "\u2663"; // Clubs (♣)
            case 2 -> "\u2665"; // Hearts (♥)
            case 3 -> "\u2666"; // Diamonds (♦)
            default -> "Unknown";
        };
    }
}

record Card(String name, String suit) {

    public int getValue() {
        if (name.equals("Ace")) {
            return 1; // Ace has a value of 1
        } else if (name.equals("Jack") || name.equals("Queen") || name.equals("King")) {
            return 10; // Face cards have a value of 10
        } else {
            return Integer.parseInt(name);
        }
    }
}
