import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class gameController {
    private static final int code_length = 4;
    private static final int max_attempts = 10;
    private List<String> Colors = Arrays.asList("RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "PINK");
    private List<String> secretcode;
    private int attempts; // to count number of attempts
    private String[] guess = new String[code_length];

    public gameController() {
        attempts = 0;
        generatesecretcode();
    }

    // Method to generate the secret code
    private void generatesecretcode() {
        secretcode = new ArrayList<>(Colors);  // Initialize with Colors list
        Collections.shuffle(secretcode);      // Shuffle the list
        secretcode = secretcode.subList(0, code_length);  // Select only the first `code_length` colors
    }

    // Method to submit a guess
    public void submitGuess() {
        if (attempts < max_attempts) {
            Scanner scanner = new Scanner(System.in);
            boolean guessed = true;

            // Take input for guess
            System.out.println("Enter your guess (4 colors from RED, GREEN, BLUE, YELLOW, ORANGE, PINK):");
            for (int i = 0; i < code_length; i++) {
                guess[i] = scanner.nextLine().trim().toUpperCase();  // Ensure we handle spaces and case properly
                if (!Colors.contains(guess[i])) {
                    guessed = false;
                    System.out.println("Invalid color, please choose from the following: RED, GREEN, BLUE, YELLOW, ORANGE, PINK");
                    break;
                }
            }

            if (!guessed) {
                return;  // Exit if any guess is invalid
            }

            // Provide feedback on the guess
            String feedback = giveFeedback(guess);
            System.out.println(feedback);
            attempts++;

            if (isCorrectGuess(guess)) {
                System.out.println("Congratulations! You guessed the correct code!");
                end(true);
            } else if (attempts == max_attempts) {
                System.out.println("Sorry, you've used all attempts. Try again.");
                end(false);
            }
        }
    }

    // Check if the guess matches the secret code
    private boolean isCorrectGuess(String[] guess) {
        return Arrays.equals(guess, secretcode.toArray());
    }

    // Provide feedback on the guess
    private String giveFeedback(String[] guess) {
        int correct_pos = 0;
        int correct_col = 0;
        for (int i = 0; i < code_length; i++) {
            if (guess[i] != null && guess[i].equals(secretcode.get(i))) {
                correct_pos++;
            } else if (guess[i] != null && secretcode.contains(guess[i])) {
                correct_col++;
            }
        }
        return "Correct position: " + correct_pos + " Correct color but wrong position: " + correct_col;
    }

    // End the game
    private void end(boolean success) {
        if (success) {
            System.out.println("You won the game!");
        } else {
            System.out.println("Game over! The secret code was: " + secretcode);
        }
    }

    // Main method to run the game
    public static void main(String[] args) {
        // Welcome message and game instructions
        System.out.println("Welcome to Master Mind!");
        System.out.println("================================");
        System.out.println("In this game, you have to guess a secret code.");
        System.out.println("The code is made up of 4 colors chosen from the following list:");
        System.out.println("RED, GREEN, BLUE, YELLOW, ORANGE, PINK");
        System.out.println("You have 10 attempts to guess the correct code.");
        System.out.println("After each guess, you will be given feedback in the form of:");
        System.out.println(" - Correct position: Number of colors that are in the correct position.");
        System.out.println(" - Correct color but wrong position: Number of colors that are correct but in the wrong position.");
        System.out.println("Let's start!");
        System.out.println("================================");

        // Start the game
        gameController game = new gameController();
        while (game.attempts < max_attempts) {
            game.submitGuess();
            if (game.isGameEnded()) {
                break;
            }
        }
    }

    public boolean isGameEnded() {
        return attempts >= max_attempts || isCorrectGuess(guess);
    }
}


