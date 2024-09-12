import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Order {

    private Map<String, String[]> accessoriesMap = new HashMap<>();

    public void OrderAccessories() {
        System.out.println("\nAll Accessories Details:");

        System.out.println("+---------------+--------------------------------------------------+--------------+---------------+");
        System.out.printf("| %-13s | %-48s | %-12s | %-13s |%n", "ID", "Name", "Stock", "Price (RM)");
        System.out.println("+---------------+--------------------------------------------------+--------------+---------------+");

        try (BufferedReader br = new BufferedReader(new FileReader("accessories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];  
                    String name = parts[1];
                    
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);

                    accessoriesMap.put(id, parts);

                    System.out.printf("| %-13s | %-48s | %-12d | RM%-11.2f |%n", id, name, quantity, price);
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+");
                } else {
                    System.out.println("Invalid data format. Skipping line.");
                }
            }
            StartOrder();
            
        } catch (IOException e) {
            System.out.println("Error loading accessories.");
            e.printStackTrace();
        }
    }

    private void StartOrder() {
        Scanner scanner = new Scanner(System.in);
        String accessoryId;
        int orderQuantity;

        try (FileWriter writer = new FileWriter("order.txt", true)) {
            System.out.println("Start placing your order (type 'stop' to finish):");

            while (true) {
                System.out.print("Enter Accessory ID: ");
                accessoryId = scanner.next();

                if (accessoryId.equalsIgnoreCase("stop")) {
                    break;
                }

                if (accessoriesMap.containsKey(accessoryId)) {
                    System.out.print("Enter Quantity: ");
                    orderQuantity = scanner.nextInt();

                    String[] accessoryDetails = accessoriesMap.get(accessoryId);
                    String name = accessoryDetails[1];
                    double price = Double.parseDouble(accessoryDetails[2]);
                    int availableQuantity = Integer.parseInt(accessoryDetails[3]);

                    if (orderQuantity <= availableQuantity) {
                        System.out.println("You ordered " + name + " successfully.");
                        writer.write(accessoryId + "," + name + "," + orderQuantity + "," + price + "\n");
                    } else {
                        System.out.println("Insufficient stock for " + name + ". Only " + availableQuantity + " available.");
                    }
                } else {
                    System.out.println("Cannot find accessory ID: " + accessoryId);
                }
            }

            System.out.println("Order completed. Thank you!");

        } catch (IOException e) {
            System.out.println("Error saving order.");
            e.printStackTrace();
        }
    }
}
