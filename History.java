import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class History {
    public static void history() {
        String ANSI_RESET = "\u001B[0m"; 
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
        try (BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"))) {
            String line;
            
            System.out.println("Your Receipt: ");
            System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+--------------+---------------+---------------+");
            System.out.printf("| %-13s | %-48s | %-13s | %-12s | %-13s |%n", "Accessory ID", "Item", "Price (RM)", "Qty", "Amount (RM)");
            System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s*");

                if (parts.length == 9) {
                    String accessoryId = parts[0].trim();
                    String item = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int qty = Integer.parseInt(parts[3].trim());
                    double total = Double.parseDouble(parts[4].trim());
                    String orderId = parts[5].trim();
                    String orderDate = parts[6].trim() + " " + parts[7].trim(); 
                    String paymentMethod = parts[8].trim();

                    System.out.printf("| %-13s | %-48s | RM%-11.2f | %-12d | RM%-11.2f |%n", accessoryId, item, price, qty, total);
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);

                    System.out.printf("Total Amount: RM%.2f%n", total);
                    System.out.printf("Order ID: %s%n", orderId);
                    System.out.printf("Order Date: %s%n", orderDate);
                    System.out.printf("Payment Method: %s%n", paymentMethod);
                    System.out.println();
                }
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
