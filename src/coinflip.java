import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class coinflip {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            // Ask user if they want to flip the coin
            System.out.println("Do you want to flip the coin? (yes/no):");
            String userInput = scanner.nextLine().toLowerCase();

            // Exit condition
            if (userInput.equals("no")) {
                System.out.println("Thanks for playing!");
                break;
            } else if (!userInput.equals("yes")) {
                System.out.println("Invalid input. Please type \"yes\" or \"no\".");
                continue;
            }

            // Flip the coin
            int coinFlip = random.nextInt(2); // 0 for heads, 1 for tails
            String result = (coinFlip == 0) ? "Heads" : "Tails";

            // Show the result
            System.out.println("The coin landed on: " + result);
        }

        scanner.close();
    }
}