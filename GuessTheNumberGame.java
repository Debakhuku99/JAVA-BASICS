package project;

import java.util.Random;
import javax.swing.JOptionPane;

public class GuessTheNumberGame {
    public static void main(String[] args) {
        // Set the range for the random number
        int minNumber = 1;
        int maxNumber = 100;

        // Initialize variables
        int totalRounds = 3;
        int score = 0;

        // Start the game loop for multiple rounds
        for (int round = 1; round <= totalRounds; round++) {
            // Generate a random number between minNumber and maxNumber (inclusive)
            Random random = new Random();
            int randomNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;

            // Initialize variables for each round
            int userGuess;
            int attempts = 0;
            int maxAttempts = 7; // Limiting the number of attempts to 7 in this example

            // Start the round loop
            while (attempts < maxAttempts) {
                // Prompt the user for their guess using a dialogue box
                String userInput = JOptionPane.showInputDialog(null,
                        "Round " + round + ": Guess the number between " + minNumber + " and " + maxNumber + ":");
                
                // Check if the user clicks "Cancel" or closes the dialogue box
                if (userInput == null) {
                    JOptionPane.showMessageDialog(null, "Thanks for playing! Your final score is: " + score);
                    return;
                }

                // Convert the user input to an integer
                try {
                    userGuess = Integer.parseInt(userInput);
                } catch (NumberFormatException e) {
                    // If the input cannot be converted to an integer, show an error message
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
                    continue;
                }

                // Check if the user's guess is correct, too low, or too high
                if (userGuess == randomNumber) {
                    attempts++;
                    score += calculateScore(maxAttempts, attempts);
                    JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number " + randomNumber +
                            " in " + attempts + " attempts. Your current score is: " + score);
                    break;
                } else if (userGuess < randomNumber) {
                    attempts++;
                    JOptionPane.showMessageDialog(null, "Too low! Try again.");
                } else {
                    attempts++;
                    JOptionPane.showMessageDialog(null, "Too high! Try again.");
                }
            }

            // Inform the user if they couldn't guess the number in the current round
            if (attempts >= maxAttempts) {
                JOptionPane.showMessageDialog(null, "Sorry, you couldn't guess the number. The correct number was: "
                        + randomNumber + ". Your current score is: " + score);
            }
        }

        // Game over after all rounds are completed
        JOptionPane.showMessageDialog(null, "Thanks for playing! Your final score is: " + score);
    }

    // Calculate the score based on the number of attempts
    private static int calculateScore(int maxAttempts, int attempts) {
        int maxScore = 100;
        int score = maxScore * (maxAttempts - attempts + 1) / maxAttempts;
        return score;
    }
}
