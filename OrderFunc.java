import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
    String ANSI_RESET = "\u001B[0m"; 
    String DEEP_GREEN = "\u001B[38;5;28m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_BOLD_YELLOW = "\u001B[1;33m";
    Scanner scanner = new Scanner(System.in);  
    String accessoryId;
    int orderQty;

    System.out.println("\nAll Accessories Details:");
    System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+--------------+---------------+");
    System.out.printf("| %-13s | %-48s | %-12s | %-13s |%n", "ID", "Item", "Price (RM)", "Stock");
    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+" + ANSI_RESET);

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
                System.out.println("+---------------+--------------------------------------------------+--------------+---------------+" + ANSI_RESET);
            }
        }

        Set<String> exist = new HashSet<>();
        try (BufferedReader orderReader = new BufferedReader(new FileReader("order.txt"))) {
            while ((line = orderReader.readLine()) != null) {
                String[] orderParts = line.split(",");
                if (orderParts.length > 0) {
                    exist.add(orderParts[0]); 
                }
            }
        }

        try (FileWriter writer = new FileWriter("order.txt", true)) {
            System.out.println("What You Want To Add On (Type '0' to Finish):");

            while (true) {
                System.out.printf("Enter Accessory ID: accs-");
                accessoryId = "accs-" + scanner.next();

                if (accessoryId.equalsIgnoreCase("accs-0")) {
                    break;
                }

                if (exist.contains(accessoryId)) {
                    System.out.println("This" + ANSI_BOLD_YELLOW + " Accessory" + ANSI_RESET + " Is Already In Your Order.\n");
                    continue; 
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
                            System.out.println("You Ordered " + item + DEEP_GREEN + " Successfully" + ANSI_RESET + ".\n");

                            OrderFunc order = new OrderFunc(null, accessoryId, item, orderQty);
                            order.calculateAmount(price);
                            orders.add(order);

                            writer.write(accessoryId + "," + item + "," + price + "," + orderQty + "\n");
                        } else {
                            System.out.println("Insufficient Stock For " + item + ". Only " + DEEP_GREEN + availableQuantity + ANSI_RESET + " Available.\n");
                        }
                    } else {
                        System.out.println(ANSI_RED + "Invalid" + ANSI_RESET + " Quantity. Please Enter A Number.");
                        scanner.next();  
                    }
                } else {
                    System.out.println("Cannot Find Accessory ID: " + ANSI_RED + accessoryId + ANSI_RESET + "\n");
                }
            }

            System.out.println("Your Order Add On " + DEEP_GREEN + "Successfully" + ANSI_RESET + ".\n");
            return;
        } catch (IOException e) {
            System.out.println("Error Saving Order.");
            e.printStackTrace();
        }
    } catch (IOException e) {
        System.out.println("Error Loading Accessories.");
        e.printStackTrace();
    }
}


    public static void changeQty() {
        String ANSI_RESET = "\u001B[0m"; 
        String DEEP_GREEN = "\u001B[38;5;28m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
    
        List<OrderFunc> orders = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nYour Current Orders: ");
        System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+--------------+---------------+---------------+");
        System.out.printf("| %-13s | %-48s | %-13s | %-12s | %-13s |%n", "Accessory ID", "Item", "Price (RM)", "Qty", "Amount (RM)");
        System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);
        
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
    
                    System.out.println(ANSI_BOLD_YELLOW + order.toString());
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);
                }
            }
        } catch (IOException e) {
            System.out.println("Error Reading The File.");
            e.printStackTrace();
        }
    
        String accessoryIdToUpdate = "";
        System.out.println("Change Quantity(Type '0' To Finish):");
        while (true) {
            System.out.print("Enter Accessory ID: accs-");
            accessoryIdToUpdate = "accs-" + scanner.next();
    
            if (accessoryIdToUpdate.equalsIgnoreCase("accs-0")) {
                break; 
            }
    
            boolean found = false;
    
            for (OrderFunc order : orders) {
                if (order.getAccessoryId().equalsIgnoreCase(accessoryIdToUpdate)) {
                    found = true;
                    System.out.print("Enter New Quantity: ");
                    if (scanner.hasNextInt()) {
                        int newQty = scanner.nextInt();
    
                        if (newQty > 0) {
                            order.setQty(newQty);
                            String[] accessoryDetails = accessoriesMap.get(order.getAccessoryId());
                            double price = Double.parseDouble(accessoryDetails[2]);
                            order.calculateAmount(price);
    
                            System.out.println("Quantity Updated Successfully For " + DEEP_GREEN + order.getName() + ANSI_RESET + "\n");
                        } else {
                            System.out.println(ANSI_RED + "Invalid" + ANSI_RESET + " Quantity. Quantity Cannot Be 0.");
                        }
                    } else {
                        System.out.println(ANSI_RED + "Invalid" + ANSI_RESET + " Input. Please Enter A Valid Number.");
                        scanner.next(); 
                    }
                }
            }
    
            if (!found) {
                System.out.println("Accessory ID " + ANSI_RED + "Not Found.\n" + ANSI_RESET);
            }
        }

        try (FileWriter writer = new FileWriter("order.txt", false)) {
            for (OrderFunc order : orders) {
                writer.write(order.getAccessoryId() + "," + order.getName() + "," + (order.getAmount() / order.getQty()) + "," + order.getQty() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error Updating The File.");
            e.printStackTrace();
        }
     
    }    

    public static void removeOrder() {
        String ANSI_RESET = "\u001B[0m"; 
        String DEEP_GREEN = "\u001B[38;5;28m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
    
        List<OrderFunc> orders = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nYour Orders: ");
        System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+--------------+---------------+---------------+");
        System.out.printf("| %-13s | %-48s | %-13s | %-12s | %-13s |%n", "Accessory ID", "Item", "Price (RM)", "Qty", "Amount (RM)");
        System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);
        
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
    
                    System.out.println(ANSI_BOLD_YELLOW + order.toString());
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);
                }
            }
        } catch (IOException e) {
            System.out.println("Error Reading The File.");
            e.printStackTrace();
        }
    
        String accessoryIdToRemove = "";
        System.out.println("Remove Order(Type '0' To Stop):");
        while (true) {
            System.out.print("Enter Accessory ID: accs-");
            accessoryIdToRemove = "accs-" + scanner.next();
    
            if (accessoryIdToRemove.equalsIgnoreCase("accs-0")) {
                break; 
            }
    
            boolean found = false;
    
            for (OrderFunc order : orders) {
                if (order.getAccessoryId().equalsIgnoreCase(accessoryIdToRemove)) {
                    orders.remove(order); 
                    found = true;
                    System.out.println("Order For " + order.getAccessoryId() + " Removed " + DEEP_GREEN + "Successfully" + ANSI_RESET + ".\n");
                    break;
                }
            }
    
            if (!found) {
                System.out.println("Accessory ID " + ANSI_RED + "Not Found" + ANSI_RESET + ".\n");
            }
        }
    
        try (FileWriter writer = new FileWriter("order.txt", false)) {
            for (OrderFunc order : orders) {
                writer.write(order.getAccessoryId() + "," + order.getName() + "," + (order.getAmount() / order.getQty()) + "," + order.getQty() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error Updating The File.");
            e.printStackTrace();
        }
        
    }
    
    public static void cancelOrder() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";

        System.out.println("\nAre You Sure You Want To Cancel Your Order?");
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
                System.out.println("An" + ANSI_RED + " Error" + ANSI_RESET + " Occurred While Clearing The Order File.");
                e.printStackTrace();
            }
        } else if (choice == 2) {
            checkOut(orders);  
        } else {
            System.out.println(ANSI_RED + "Invalid" + ANSI_RESET + " Option. Please Try Again.");
        }
        scanner.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void checkOut(List<OrderFunc> orders) {
        String ANSI_RESET = "\u001B[0m";
        String DEEP_GREEN = "\u001B[38;5;28m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";

        Scanner scanner = new Scanner(System.in);
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("\n-------------------------------------");
            System.out.println("|                                   |");
            System.out.println("|       Choose Your Option          |");
            System.out.println("|                                   |");
            System.out.println("-------------------------------------");
            System.out.println("|       1. Proceed to Payment       |");
            System.err.println("|       2. Add More Order           |");
            System.out.println("|       3. Change Quantity          |");
            System.out.println("|       4. Remove Order             |");
            System.out.println("|       5. Cancel Order             |");
            System.out.println("-------------------------------------");
            System.out.print(ANSI_BOLD_YELLOW + "Enter Your Option: " + ANSI_RESET);

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
                        removeOrder();
                        urOrder.main(null);
                        validChoice = true; 
                        break;
                    case 5:
                        cancelOrder();
                        System.out.println("\nYour Order Has Been Cancelled" + DEEP_GREEN + " Successfully" + ANSI_RESET + ".");
                        validChoice = true;  
                        break;
                    default:
                        System.out.println(ANSI_RED + "Invalid" + ANSI_RESET + " Choice. Please Try Again.");
                        break;
                }
            } 
        }
        scanner.close();
    }

    @Override
    public String toString() {
        return String.format("| %-13s | %-48s | RM%-11.2f | %-12d | RM%-11.2f |",
                accessoryId, item, (getAmount() / getQty()), getQty(), getAmount());
    }
}
