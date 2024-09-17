import java.io.*;
import java.util.*;

public class OrderFunc extends Order {
    private String name;
    private String accessoryId; 
    private static Double totalAmount = 0.0; 
    private static Map<String, String[]> accessoriesMap = new HashMap<>();
    private static List<OrderFunc> orders = new ArrayList<>();  

    public OrderFunc(String orderId, String accessoryId, String name, Integer qty) {
        super(orderId, totalAmount, qty, name); 
        this.name = name;
        this.accessoryId = accessoryId; 
    }

    public String getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(String accessoryId) {
        this.accessoryId = accessoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Double calculateTotalAmount(List<OrderFunc> orders) {
        totalAmount = 0.0;
        for (OrderFunc order : orders) {
            totalAmount += order.getAmount(); 
        }
        return totalAmount;
    }
    
    public static void updateOrderFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("order.txt"))) {
            for (OrderFunc order : orders) {
                writer.write(order.getAccessoryId() + "," + order.getName() + "," + order.getQty() + "," + (order.getAmount() / order.getQty()) + "," + order.getAmount());
                writer.newLine();
            }
            System.out.println("Order file updated.");
        } catch (IOException e) {
            System.out.println("Error updating order file: " + e.getMessage());
        }
    }
    
    public static void addMoreOrders(Map<String, String[]> accessoriesMap) {
        Scanner scanner = new Scanner(System.in);  
        String accessoryId;
        int orderQty;
    
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
                    int qty = Integer.parseInt(parts[3]);
    
                    accessoriesMap.put(id, parts);
    
                    System.out.printf("| %-13s | %-48s | %-12d | RM%-11.2f |%n", id, name, qty, price);            
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
                            String name = accessoryDetails[1];
                            double price = Double.parseDouble(accessoryDetails[2]);
                            int availableQuantity = Integer.parseInt(accessoryDetails[3]);
    
                            if (orderQty <= availableQuantity) {
                                System.out.println("You ordered " + name + " successfully.");
    
                                OrderFunc order = new OrderFunc(null, accessoryId, name, orderQty);
                                order.calculateAmount(price);
                                orders.add(order);
    
                                writer.write(accessoryId + "," + name + "," + orderQty + "," + price + "\n");
                            } else {
                                System.out.println("Insufficient stock for " + name + ". Only " + availableQuantity + " available.");
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
            System.out.println("3. Cancel Order");
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
                        OrderFile.YourOrder("order.txt");
                        validChoice = true; 
                        break;
                    case 3:
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
        return String.format("| %-13s | %-48s | %-12d | RM%-11.2f | RM%-11.2f |",
                accessoryId, name, getQty(), (getAmount() / getQty()), getAmount());
    }
}
