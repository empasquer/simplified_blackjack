import java.util.Random;
import java.util.Scanner;

public class BlackJack {
    Random random = new Random();

    public static void main(String[] args) {
        new BlackJack().run();
    }

    public void run() {
        Scanner userInput = new Scanner(System.in);
        String playerUserName;
        char player = 'p';
        int playerHits = 0;
        boolean gameOver = false;

        int card;
        int playerPoints = 0;
        int dealerPoints = 0;

        while (!gameOver) {
            System.out.println("Greetings player. Welcome to BlackJack for dummies.");
            System.out.println("What is your name?");
            playerUserName = userInput.nextLine().trim().toUpperCase();
            System.out.printf("%s, you start.%n", playerUserName);

            while (playerHits < 3 && playerPoints <= 21) {
                playerHits++;
                System.out.printf("%d hit:%n", playerHits);
                card = chooseRandomCard();
                System.out.printf("The card is: %d%n", card);
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
                    card = chooseRandomCard();
                    System.out.printf("The dealer draws: %d%n", card);
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

    int chooseRandomCard() {
        return random.nextInt(13) + 1;
    }

    int getCardValue(int card) {
        if (card >= 10 && card <= 13) {
            return 10;
        } else {
            return card;
        }
    }
}
