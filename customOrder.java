import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class customOrder {
    public void displayCusOrder() {
        String ANSI_RESET = "\u001B[0m"; 
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";

        try (BufferedReader reader = new BufferedReader(new FileReader("customOrder.txt"))) {
            String line;

            System.out.println(ANSI_BOLD_YELLOW + "  ____             _                                   ___            _             ");
            System.out.println(" / ___|_   _  ___ | |_  ___   _ __ ___    ___  _ __   / _ \\  _ __  __| |  ___  _ __ ");
            System.out.println("| |   | | | |/ __|| __|/ _ \\ | '_ ` _ \\  / _ \\| '__| | | | || '__|/ _` | / _ \\| '__|");
            System.out.println("| |___| |_| |\\__ \\| |_| (_) || | | | | ||  __/| |    | |_| || |  | (_| ||  __/| |   ");
            System.out.println(" \\____|\\__,_||___/ \\__|\\___/ |_| |_| |_| \\___||_|     \\___/ |_|   \\__,_| \\___||_|   " + ANSI_RESET);
            System.out.println("\nCustomer Order:");
            System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading the history file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        customOrder cusOrder = new customOrder();
        cusOrder.displayCusOrder();
    }
}
