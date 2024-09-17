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
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nPlease Select Your Payment Method: ");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Touch'N Go");
        System.out.print("Enter Your Option: ");
        int option = scanner.nextInt();
        System.out.println("");

        switch (option) {
            case 1:
                paymentMethod = "Credit Card";
                break;
            case 2:
                paymentMethod = "Debit Card";
                break;
            case 3:
                paymentMethod = "Touch'N Go";
                break;
            default:
                System.out.println("Invalid Option. Please Try Again.");
                return;
        }

        OrderFile.YourReceipt("order.txt");
        updateAccessoryQuantities("order.txt", "accessories.txt");
        quit();
    }

    public static void quit() {
        Scanner scanner =  new Scanner(System.in);

        System.out.printf("\nAre You Want To Quit(y/n): ");
        String yn = scanner.next();
        switch (yn.toLowerCase()) {
            case "y":
                clearOrderFile("order.txt");
                break;
            case "n":
                return;
            default:
                System.out.println("Invalid Option. Please Try Again.");
                return;
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
