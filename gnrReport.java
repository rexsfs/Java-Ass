import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class gnrReport {
    public void generateReport() {
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
        String ANSI_RESET = "\u001B[0m";
        
        double totalRevenue = 0;
        int totalItemsSold = 0;
        int numberOfOrders = 0;
        Map<String, Integer> paymentMethodCount = new HashMap<>();
        Map<String, Integer> accessoryIdSold = new HashMap<>();
        Map<String, String> accessoryIdToItem = new HashMap<>();
        Map<String, Double> itemRevenue = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",\\s*");

                if (parts.length == 8) {
                    String accessoryId = parts[0].trim(); 
                    String item = parts[1].trim();       
                    int qty = Integer.parseInt(parts[3].trim());        
                    double total = Double.parseDouble(parts[4].trim()); 
                    String paymentMethod = parts[7].trim(); 

                    totalRevenue += total;
                    totalItemsSold += qty;
                    numberOfOrders++;

                    accessoryIdToItem.put(accessoryId, item);
                    paymentMethodCount.put(paymentMethod, paymentMethodCount.getOrDefault(paymentMethod, 0) + 1);
                    accessoryIdSold.put(accessoryId, accessoryIdSold.getOrDefault(accessoryId, 0) + qty);
                    itemRevenue.put(accessoryId, itemRevenue.getOrDefault(accessoryId, 0.0) + total);
                }
            }

            // Generate the report
            System.out.println("\nAccessories Sold Report:");
            System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+---------------+");
            System.out.printf("| %-13s | %-48s | %-13s |%n", "Accessory ID", "Item", "Sold");
            System.out.println("+---------------+--------------------------------------------------+---------------+" + ANSI_RESET);

            for (Map.Entry<String, Integer> entry : accessoryIdSold.entrySet()) {
                String accessoryId = entry.getKey();
                String item = accessoryIdToItem.get(accessoryId);
                int soldQty = entry.getValue();
                System.out.printf(ANSI_BOLD_YELLOW + "| %-13s | %-48s | %-13d |%n", accessoryId, item, soldQty);
                System.out.println("+---------------+--------------------------------------------------+---------------+" + ANSI_RESET);
            }

            System.out.printf("Total Revenue: RM%.2f%n", totalRevenue);
            System.out.printf("Total Items Sold: %d%n", totalItemsSold);
            System.out.printf("Number of Orders: %d%n", numberOfOrders);

            System.out.println("\nPayment Method Breakdown:");
            for (Map.Entry<String, Integer> entry : paymentMethodCount.entrySet()) {
                System.out.printf("%s: %d orders%n", entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error reading the receipt history file.");
            e.printStackTrace();
        }
    }
}
