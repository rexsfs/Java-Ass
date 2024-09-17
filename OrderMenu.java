import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OrderMenu {

    private Map<String, String[]> accessoriesMap = new HashMap<>();

    public boolean OrderAccessories() {
        boolean Ordering = false; 
    
        System.out.println("\nAll Accessories Details:");
        System.out.println("+---------------+--------------------------------------------------+---------------+--------------+");
        System.out.printf("| %-13s | %-48s | %-13s | %-12s |%n", "ID", "Item", "Price (RM)", "Stock");
        System.out.println("+---------------+--------------------------------------------------+---------------+--------------+");
    
        try (BufferedReader br = new BufferedReader(new FileReader("accessories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String item = parts[1];
                    double price = Double.parseDouble(parts[2]);  
                    int qty = Integer.parseInt(parts[3]);     
    
                    accessoriesMap.put(id, parts);
                    System.out.printf("| %-13s | %-48s | RM%-11.2f | %-12d |%n", id, item, price, qty);
                    System.out.println("+---------------+--------------------------------------------------+---------------+--------------+");
                } 
            }
            
            Ordering = StartOrder(); 
            urOrder.main(null);
    
        } catch (IOException e) {
            System.out.println("Error loading accessories.");
            e.printStackTrace();
        }
    
        return Ordering; 
    }
    
    private boolean StartOrder() {
        Scanner scanner = new Scanner(System.in);
        String accessoryId;
        int orderQty;
        boolean orderPlaced = false;
    
        List<OrderFunc> orders = new ArrayList<>();
    
        try (FileWriter writer = new FileWriter("order.txt", true)) {
            System.out.println("Start placing your order (type 'stop' to finish):");
    
            while (true) {
                System.out.printf("Enter Accessory ID (format: accs-): accs-");
                accessoryId = "accs-" + scanner.next();
    
                if (accessoryId.equalsIgnoreCase("accs-stop")) {
                    break;
                }
    
                if (accessoriesMap.containsKey(accessoryId)) {
                    System.out.print("Enter Quantity: ");
                    orderQty = scanner.nextInt();
    
                    String[] accessoryDetails = accessoriesMap.get(accessoryId);
                    String item = accessoryDetails[1];
                    double price = Double.parseDouble(accessoryDetails[2]);  
                    int availableQuantity = Integer.parseInt(accessoryDetails[3]); 
    
                    if (orderQty <= availableQuantity) {
                        System.out.println("You ordered " + item + " successfully.");
                        OrderFunc order = new OrderFunc(null, accessoryId, item, orderQty);
                        order.calculateAmount(price);
                        orders.add(order);
                        writer.write(accessoryId + "," + item + "," + price + "," + orderQty + "\n");
                        orderPlaced = true;
                    } else {
                        System.out.println("Insufficient stock for " + item + ". Only " + availableQuantity + " available.");
                    }
                } else {
                    System.out.println("Cannot find accessory ID: " + accessoryId);
                }
            }
    
            if (orders.isEmpty()) {
                System.out.println("No items were ordered.");
            } else {
                System.out.println("Order completed. Thank you!\n");
            }
    
        } catch (IOException e) {
            System.out.println("Error saving order.");
            e.printStackTrace();
        }
        scanner.close();
        return orderPlaced;
    }
}
