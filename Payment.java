import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Payment {

    private static String paymentMethod;  

    public Payment(String paymentMethod) {
        Payment.paymentMethod = paymentMethod;
    }

    public static String getPaymentMethod() {
        return paymentMethod;  
    }

    public static void processPayment(List<OrderFunc> orders, double totalAmount) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) { 
            System.out.println("\n----------------------------------------");
            System.out.println("|   Please Select Your Payment Method  |");
            System.out.println("----------------------------------------");
            System.out.println("|            1. Credit Card            |");
            System.out.println("|            2. Debit Card             |");
            System.out.println("|            3. Touch'N Go             |");
            System.out.println("----------------------------------------");
            System.out.print(ANSI_BOLD_YELLOW + "Enter Your Option: " + ANSI_RESET);
            int option = scanner.nextInt();
            System.out.println();

            switch (option) {
                case 1:
                    paymentMethod = "Credit Card";
                    isValid = true;
                    break;
                case 2:
                    paymentMethod = "Debit Card";
                    isValid = true;
                    break;
                case 3:
                    paymentMethod = "Touch'N Go";
                    isValid = true;
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid" + ANSI_RESET + " Option. Please Try Again.");
                    break;
            }
        }

        OrderFile.YourReceipt("order.txt");
        updateAccessoryQuantities("order.txt", "accessories.txt");
        quit();
        scanner.close();
    }

    public static void quit() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        Scanner scanner = new Scanner(System.in);
        String yn = "";
        
        while (!yn.equalsIgnoreCase("y")) {
            System.out.printf("Please Press y To Quit: ");
            yn = scanner.next();
            
            if (yn.equalsIgnoreCase("y")) {
                clearOrderFile("order.txt");
            } else {
                System.out.println(ANSI_RED + "Invalid" + ANSI_RESET + " Option. Please Try Again.");
            }
        }
        scanner.close();
    }

    public static void updateAccessoryQuantities(String orderFilename, String accessoriesFilename) {
        List<String> accessoryData = new ArrayList<>();
        Map<String, Integer> accessoryMap = new HashMap<>();
        
        try (BufferedReader accessoryReader = new BufferedReader(new FileReader(accessoriesFilename))) {
            String line;
            while ((line = accessoryReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String accessoryId = parts[0].trim();
                    String item = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());  
                    int qty = (int) Double.parseDouble(parts[3].trim()); 
    
                    accessoryMap.put(accessoryId, qty);
                    accessoryData.add(line); 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading accessories file.");
            e.printStackTrace();
            return;
        }
            
        try (BufferedReader orderReader = new BufferedReader(new FileReader(orderFilename))) {
            String orderLine;
            while ((orderLine = orderReader.readLine()) != null) {
                String[] orderParts = orderLine.split(",");
                if (orderParts.length == 4) {
                    String orderAccessoryId = orderParts[0].trim();
                    int orderQty = Integer.parseInt(orderParts[3].trim()); 
    
                    if (accessoryMap.containsKey(orderAccessoryId)) {
                        int availableQty = accessoryMap.get(orderAccessoryId);
                        int updatedQty = availableQty - orderQty;
    
                        accessoryMap.put(orderAccessoryId, updatedQty);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading order file.");
            e.printStackTrace();
            return;
        }
    
        try (BufferedWriter accessoryWriter = new BufferedWriter(new FileWriter(accessoriesFilename))) {
            for (String line : accessoryData) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String accessoryId = parts[0].trim();
                    String item = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim()); 
    
                    if (accessoryMap.containsKey(accessoryId)) {
                        int updatedQty = accessoryMap.get(accessoryId);
                        accessoryWriter.write(String.format("%s,%s,%.2f,%d\n", accessoryId, item, price, updatedQty));
                    } else {
                        accessoryWriter.write(line + "\n"); 
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to accessories file.");
            e.printStackTrace();
        }
    }    

    public static void clearOrderFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error clearing the order file.");
            e.printStackTrace();
        }
    }

}
