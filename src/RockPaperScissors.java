import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Game loop
        while (true) {
            // Display choices
            System.out.println("Enter your move (rock, paper, scissors). To exit the game, type \"exit\":");
            String userMove = scanner.nextLine().toLowerCase();

            // Exit condition
            if (userMove.equals("exit")) {
                System.out.println("Thanks for playing!");
                break;
            }

            // Validate user input
            if (!userMove.equals("rock") && !userMove.equals("paper") && !userMove.equals("scissors")) {
                System.out.println("Invalid move. Please try again.");
                continue;
            }

            // Generate computer's move
            int computerMoveInt = random.nextInt(3);
            String computerMove = "";
            if (computerMoveInt == 0) {
                computerMove = "rock";
            } else if (computerMoveInt == 1) {
                computerMove = "paper";
            } else if (computerMoveInt == 2) {
                computerMove = "scissors";
            }

            // Show computer's move
            System.out.println("Computer played: " + computerMove);

            // Determine the winner
            if (userMove.equals(computerMove)) {
                System.out.println("It's a tie!");
            } else if ((userMove.equals("rock") && computerMove.equals("scissors")) ||
                    (userMove.equals("scissors") && computerMove.equals("paper")) ||
                    (userMove.equals("paper") && computerMove.equals("rock"))) {
                System.out.println("You win!");
            } else {
                System.out.println("You lose!");
            }

            System.out.println();
        }

        scanner.close();
    }
}