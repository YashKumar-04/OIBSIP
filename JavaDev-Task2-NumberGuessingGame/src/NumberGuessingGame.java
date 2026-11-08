import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        final int MAX_ATTEMPTS = 7;
        final int MIN_NUMBER = 1;
        final int MAX_NUMBER = 100;

        boolean playAgain = true;

        int totalRounds = 0;
        int roundsWon = 0;
        int totalScore = 0;

        System.out.println("=========================================");
        System.out.println("       NUMBER GUESSING GAME");
        System.out.println("=========================================");

        while (playAgain) {

            totalRounds++;

            int targetNumber = random.nextInt(MAX_NUMBER) + MIN_NUMBER;
            int attemptsLeft = MAX_ATTEMPTS;
            int attemptsUsed = 0;

            boolean hasWon = false;

            System.out.println("\n-- Round " + totalRounds + " --");
            System.out.println("I have selected a number between 1 and 100.");
            System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it.");

            while (attemptsLeft > 0) {

                System.out.print("\nEnter your guess: ");

                // checking input
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.next();
                    continue;
                }

                int guess = scanner.nextInt();

                // checking guess does it is in the valid range
                if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                    System.out.println("Please enter a number between 1 and 100.");
                    continue;
                }

                attemptsUsed++;

                if (guess == targetNumber) {

                    hasWon = true;
                    roundsWon++;
                    totalScore += 10;

                    System.out.println("\nCongratulations! You guessed correctly.");
                    System.out.println("Number of attempts used: " + attemptsUsed);
                    System.out.println("You earned 10 points!");

                    break;

                } else if (guess > targetNumber) {

                    System.out.println("Too High! Try a smaller number.");

                } else {

                    System.out.println("Too Low! Try a larger number.");

                }

                attemptsLeft--;

                System.out.println("Attempts remaining: " + attemptsLeft);
            }

            // message displayed if the player losses
            if (!hasWon) {
                System.out.println("\nGame Over! You have used all your attempts.");
                System.out.println("The correct number was: " + targetNumber);
            }

            // display current score
            System.out.println("\n---------- SCOREBOARD ----------");
            System.out.println("Total rounds played: " + totalRounds);
            System.out.println("Rounds won: " + roundsWon);
            System.out.println("Total score: " + totalScore);
            System.out.println("--------------------------------");

            //ask for another round from the player
            while (true) {

                System.out.print("\nDo you want to play again? (yes/no): ");

                String response = scanner.next();

                if (response.equalsIgnoreCase("yes")
                        || response.equalsIgnoreCase("y")) {

                    break;

                } else if (response.equalsIgnoreCase("no")
                        || response.equalsIgnoreCase("n")) {

                    playAgain = false;
                    break;

                } else {

                    System.out.println("Invalid choice! Please enter yes or no.");
                }
            }
        }

        System.out.println("\n=========================================");
        System.out.println("              FINAL SUMMARY");
        System.out.println("=========================================");
        System.out.println("Total rounds played: " + totalRounds);
        System.out.println("Rounds won: " + roundsWon);
        System.out.println("Rounds lost: " + (totalRounds - roundsWon));
        System.out.println("Final score: " + totalScore);
        System.out.println("Thanks for playing!");
        System.out.println("=========================================");

        scanner.close();
    }
}
