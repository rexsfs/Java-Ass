import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class History {
    public static void history() {
         try (BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"))) {
            String line;
            
            System.out.println("Order History: ");
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
