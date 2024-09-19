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

    public void OrderAccessories() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BOLD_YELLOW = "\u001B[1;33m"; 
    
        System.out.println("\nAll Accessories Details:");
        System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+---------------+--------------+");
        System.out.printf("| %-13s | %-48s | %-13s | %-12s |%n", "ID", "Item", "Price (RM)", "Stock");
        System.out.println("+---------------+--------------------------------------------------+---------------+--------------+" + ANSI_RESET);
    
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
                    System.out.printf(ANSI_BOLD_YELLOW + "| %-13s | %-48s | RM%-11.2f | %-12d |%n", id, item, price, qty);
                    System.out.println("+---------------+--------------------------------------------------+---------------+--------------+" + ANSI_RESET);
                } 
            }
            
            StartOrder(); 
    
        } catch (IOException e) {
            System.out.println("Error loading accessories.");
            e.printStackTrace();
        }
    
    }
    
    private void StartOrder() {
        String ANSI_RESET = "\u001B[0m";
        String DEEP_GREEN = "\u001B[38;5;28m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
        Scanner scanner = new Scanner(System.in);
        String accessoryId;
        int orderQty;
    
        List<OrderFunc> orders = new ArrayList<>();
    
        try (FileWriter writer = new FileWriter("order.txt", true)) {
            System.out.println("Start Placing Your Order (Type '0' To Finish):");
    
            while (true) {
                System.out.printf("Enter Accessory ID: accs-");
                accessoryId = "accs-" + scanner.next();
    
                if (accessoryId.equalsIgnoreCase("accs-0")) {
                    break;
                }
    
                boolean exists = false;
                for (OrderFunc order : orders) {
                    if (order.getAccessoryId().equalsIgnoreCase(accessoryId)) {
                        exists = true;
                        break;
                    }
                }
    
                if (exists) {
                    System.out.println("This" + ANSI_BOLD_YELLOW + " Accessory" + ANSI_RESET + " Is Already In Your Order.\n");
                    continue; 
                }
    
                if (accessoriesMap.containsKey(accessoryId)) {
                    System.out.print("Enter Quantity: ");
                    orderQty = scanner.nextInt();
    
                    String[] accessoryDetails = accessoriesMap.get(accessoryId);
                    String item = accessoryDetails[1];
                    double price = Double.parseDouble(accessoryDetails[2]);  
                    int availableStock = Integer.parseInt(accessoryDetails[3]); 
                     
                    if(orderQty < 1) {
                        System.out.println(ANSI_RED + "Invalid" + ANSI_RESET + " Quantity. Quantity Cannot Be 0.\n");
                        continue;
                    }

                    if (orderQty <= availableStock) {
                        System.out.println("You Ordered " + item + DEEP_GREEN + " Successfully" + ANSI_RESET + ".\n");
                        OrderFunc order = new OrderFunc(null, accessoryId, item, orderQty);
                        order.calculateAmount(price);
                        orders.add(order);
                        writer.write(accessoryId + "," + item + "," + price + "," + orderQty + "\n");
                        writer.flush();  
                    }else {
                        System.out.println("Insufficient Stock For " + item + ". Only " + DEEP_GREEN + availableStock + ANSI_RESET + " Available.\n");
                    }
                } else {
                    System.out.println("Cannot Find accessory ID: " + ANSI_RED + accessoryId + ANSI_RESET);
                }
            }
    
            if (orders.isEmpty()) {
                System.out.println("No Items Were Ordered. Returning To The Menu...");
                return;
            } else {
                System.out.println("Order" + DEEP_GREEN + " Completed" + ANSI_RESET + ". Thank You!\n");
            }
    
        } catch (IOException e) {
            System.out.println("Error Saving Order.");
            e.printStackTrace();
        }

        urOrder.main(null);
        scanner.close();
    }
    

    public static void main(String[] args) {
        OrderMenu menu = new OrderMenu();
        menu.OrderAccessories();
    }
}
