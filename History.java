import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class History {
    public static void history() {
        String ANSI_RESET = "\u001B[0m"; 
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
         try (BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"))) {
            String line;
            
            System.out.println("Order History: ");
            System.out.println(ANSI_BOLD_YELLOW + "-------------------------------------------------------------------------------------------------------------------" + ANSI_RESET);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the receipt history file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        history();
    }
}
