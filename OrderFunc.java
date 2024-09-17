import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OrderFunc extends Order {
    private String item;
    private String accessoryId; 
    private static Double totalAmount = 0.0; 
    private static Map<String, String[]> accessoriesMap = new HashMap<>();
    private static List<OrderFunc> orders = new ArrayList<>();  

    public OrderFunc(String orderId, String accessoryId, String item, Integer qty) {
        super(orderId, totalAmount, qty, item); 
        this.item = item;
        this.accessoryId = accessoryId; 
    }

    public String getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(String accessoryId) {
        this.accessoryId = accessoryId;
    }

    public String getName() {
        return item;
    }

    public void setName(String item) {
        this.item = item;
    }

    public static Double calculateTotalAmount(List<OrderFunc> orders) {
        totalAmount = 0.0;
        for (OrderFunc order : orders) {
            totalAmount += order.getAmount(); 
        }
        return totalAmount;
    }
    
    public static void addMoreOrders(Map<String, String[]> accessoriesMap) {
        Scanner scanner = new Scanner(System.in);  
        String accessoryId;
        int orderQty;
    
        System.out.println("\nAll Accessories Details:");
        System.out.println("+---------------+--------------------------------------------------+--------------+---------------+");
        System.out.printf("| %-13s | %-48s | %-12s | %-13s |%n", "ID", "Item", "Price (RM)", "Stock");
        System.out.println("+---------------+--------------------------------------------------+--------------+---------------+");
    
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
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+");
                }
            }
    
            try (FileWriter writer = new FileWriter("order.txt", true)) {
                System.out.println("What You Want To Add On, Please Enter Accessory ID");
    
                while (true) {
                    System.out.printf("Enter Accessory ID (format: accs-): accs-");
                    accessoryId = "accs-" + scanner.next();
    
                    if (accessoryId.equalsIgnoreCase("accs-stop")) {
                        break;
                    }
    
                    if (accessoriesMap.containsKey(accessoryId)) {
                        System.out.print("Enter Quantity: ");
                        if (scanner.hasNextInt()) {
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
                            } else {
                                System.out.println("Insufficient stock for " + item + ". Only " + availableQuantity + " available.");
                            }
                        } else {
                            System.out.println("Invalid quantity. Please enter a number.");
                            scanner.next();  
                        }
                    } else {
                        System.out.println("Cannot find accessory ID: " + accessoryId);
                    }
                }
    
                System.out.println("Your Order Add On.\n");
                return;
            } catch (IOException e) {
                System.out.println("Error saving order.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error loading accessories.");
            e.printStackTrace();
        }
    }    

    public static void changeQty() {
        List<OrderFunc> orders = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Your Current Orders: ");
        System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");
        System.out.printf("| %-13s | %-48s | %-13s | %-12s | %-13s |%n", "Accessory ID", "Item", "Price (RM)", "Qty", "Amount (RM)");
        System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");
        
        try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String accessoryId = parts[0];
                    String item = parts[1];
                    double price = Double.parseDouble(parts[2].trim());
                    int qty = Integer.parseInt(parts[3].trim());
        
                    OrderFunc order = new OrderFunc(null, accessoryId, item, qty);
                    order.calculateAmount(price);
                    orders.add(order);
        
                    System.out.println(order.toString());
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }
        
        System.out.print("Enter the Accessory ID you want to change the quantity for (format: accs-): accs-");
        String accessoryIdToUpdate = "accs-" + scanner.next();
        
        boolean found = false;
        
        for (OrderFunc order : orders) {
            if (order.getAccessoryId().equalsIgnoreCase(accessoryIdToUpdate)) {
                found = true;
                System.out.print("Enter the new quantity: ");
                if (scanner.hasNextInt()) {
                    int newQty = scanner.nextInt();
        
                    if (newQty > 0) {
                        order.setQty(newQty);
                        String[] accessoryDetails = accessoriesMap.get(order.getAccessoryId());
                        double price = Double.parseDouble(accessoryDetails[2]);
                        order.calculateAmount(price);
        
                        System.out.println("Quantity updated successfully for " + order.getName());
                    } else {
                        System.out.println("Invalid quantity. Quantity cannot be 0.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); 
                }
                break;
            }
        }
        
        if (!found) {
            System.out.println("Accessory ID not found.");
        }
        
        try (FileWriter writer = new FileWriter("order.txt", false)) {
            for (OrderFunc order : orders) {
                writer.write(order.getAccessoryId() + "," + order.getName() + "," + (order.getAmount() / order.getQty()) + "," + order.getQty() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error updating the file.");
            e.printStackTrace();
        }
    }

    public static void cancelOrder() {
        System.out.println("Are you sure you want to cancel your order?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Enter Your Choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice == 1) {
            try {
                FileWriter orderWriter = new FileWriter("order.txt", false);
                orderWriter.write("");
                orderWriter.close();
                FileWriter summaryWriter = new FileWriter("receipt.txt", false);
                summaryWriter.write("");
                summaryWriter.close();
                orders.clear();  
            } catch (IOException e) {
                System.out.println("An error occurred while clearing the order file.");
                e.printStackTrace();
            }
        } else if (choice == 2) {
            checkOut(orders);  
        } else {
            System.out.println("Invalid option. Please try again.");
        }
        scanner.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void checkOut(List<OrderFunc> orders) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_YELLOW = "\u001B[33m";

        Scanner scanner = new Scanner(System.in);
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println(ANSI_YELLOW + "\n----------------------------------------------------------");
            System.out.println("Choose Your Option");
            System.out.println("----------------------------------------------------------");
            System.out.println("1. Proceed to Payment");
            System.err.println("2. Add More Order");
            System.out.println("3. Change Quantity");
            System.out.println("4. Cancel Order");
            System.out.println("----------------------------------------------------------");
            System.out.print("Enter Your option: " + ANSI_RESET);

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();


                switch (choice) {
                    case 1:
                        Payment.processPayment(orders, choice);
                        validChoice = true;  
                        break;
                    case 2:
                        addMoreOrders(accessoriesMap);
                        urOrder.main(null);
                        validChoice = true; 
                        break;
                    case 3:
                        changeQty();
                        urOrder.main(null);
                        validChoice = true; 
                        break;
                    case 4:
                        cancelOrder();
                        System.out.println("Your order has been canceled.");
                        validChoice = true;  
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
    }

    @Override
    public String toString() {
        return String.format("| %-13s | %-48s | RM%-11.2f | %-12d | RM%-11.2f |",
                accessoryId, item, (getAmount() / getQty()), getQty(), getAmount());
    }
}
