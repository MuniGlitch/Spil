import java.sql.Time;
import java.util.Scanner;

public class Guess10seconds {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Press enter to start...");
        scanner.nextLine(); //The user presses enter to start the game

        long startTime = System.currentTimeMillis(); //Recorded start time

        System.out.println("Press enter to stop the timer");
        scanner.nextLine(); //the user stops the timer

        long endTime = System.currentTimeMillis(); //record end time

        long elapsedTime = endTime - startTime;  // Calculate the elapsed time in milliseconds
        double elapsedSeconds = elapsedTime / 1000.0;  // Convert milliseconds to seconds
        double difference = Math.abs(elapsedSeconds - 10.0);

        System.out.printf("You guessed: %.2f seconds.\n", elapsedSeconds);
        System.out.printf("You were %.2f seconds off from 10 seconds.\n", difference);

        if (difference <= 0.5) {
            System.out.println("Great job! You were very close!");
        } else

            if (difference <= 1.0) {
            System.out.println("Not bad! You were fairly close.");
        } else {
            System.out.println("Better luck next time!");
        }

        scanner.close();
    }
}
