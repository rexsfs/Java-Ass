import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class History {
    public void displayHistory() {
        String ANSI_RESET = "\u001B[0m"; 
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";

        try (BufferedReader reader = new BufferedReader(new FileReader("history.txt"))) {
            String line;

            System.out.println(ANSI_BOLD_YELLOW + " _   _  _       _                      ");
            System.out.println("| | | |(_) ___ | |_  ___   _ __  _   _ ");
            System.out.println("| |_| || |/ __|| __|/ _ \\ | '__|| | | |");
            System.out.println("|  _  || |\\__ \\| |_| (_) || |   | |_| |");
            System.out.println("|_| |_||_||___/ \\__|\\___/ |_|    \\__, |");
            System.out.println("                                 |___/ " + ANSI_RESET);
            System.out.println("\nYour History:");
            System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading the history file.");
            e.printStackTrace();
        }
    }

}
