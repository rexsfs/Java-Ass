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
            System.out.println(ANSI_BOLD_YELLOW + " ____                                        _     __  __        _    _                 _ ");
            System.out.println("|  _ \\  __ _  _   _  _ __ ___    ___  _ __  | |_  |  \\/  |  ___ | |_ | |__    ___    __| |");
            System.out.println("| |_) |/ _` || | | || '_ ` _ \\  / _ \\| '_ \\ | __| | |\\/| | / _ \\| __|| '_ \\  / _ \\  / _` |");
            System.out.println("|  __/| (_| || |_| || | | | | ||  __/| | | || |_  | |  | ||  __/| |_ | | | || (_) || (_| |");
            System.out.println("|_|    \\__,_| \\__, ||_| |_| |_| \\___||_| |_| \\__| |_|  |_| \\___| \\__||_| |_| \\___/  \\__,_|");
            System.out.println("              |___/                                                                      ");
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.println("|                                    1. Credit Card                                       |");
            System.out.println("|                                    2. Debit Card                                        |");
            System.out.println("|                                    3. Touch'N Go                                        |");
            System.out.println("-------------------------------------------------------------------------------------------" + ANSI_RESET);
            System.out.print("Enter Your Option: ");
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
        clearOrderFile("order.txt");
        back(0);
    }

    public static void back(int foundIndex) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        Scanner scanner = new Scanner(System.in);
        String yn = "";
        
        while (!yn.equalsIgnoreCase("y")) {
            System.out.printf("\nPlease Press y To Quit: ");
            yn = scanner.next();
            
            if (yn.equalsIgnoreCase("y")) {
                StaffMain.staffTypeRecognize(foundIndex);
            } else {
                System.out.println(ANSI_RED + "\nInvalid" + ANSI_RESET + " Option. Please Try Again.\n");
            }
        }
    }

    public static void updateAccessoryQuantities(String orderFilename, String accessoriesFilename) {
        List<String> accessoryData = new ArrayList<>();
        Map<String, Integer> accessoryMap = new HashMap<>();

        try (BufferedReader accessoryReader = new BufferedReader(new FileReader(accessoriesFilename))) {
            String line;
            while ((line = accessoryReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { 
                    String accessoryId = parts[0].trim();
                    String item = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int qty = (int) Double.parseDouble(parts[3].trim()); 
                    String supplierId = parts[4].trim(); 

                    accessoryMap.put(accessoryId, qty);
                    accessoryData.add(String.format("%s,%s,%.2f,%d,%s", accessoryId, item, price, qty, supplierId));
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
            if (!accessoryMap.isEmpty() && !accessoryData.isEmpty()) {
                for (String line : accessoryData) {
                    String[] parts = line.split(",");
                    if (parts.length == 5) { 
                        String accessoryId = parts[0].trim();
                        String item = parts[1].trim();
                        double price = Double.parseDouble(parts[2].trim());
                        String supplierId = parts[4].trim(); 

                        if (accessoryMap.containsKey(accessoryId)) {
                            int updatedQty = accessoryMap.get(accessoryId);
                            accessoryWriter.write(String.format("%s,%s,%.2f,%d,%s\n", accessoryId, item, price, updatedQty, supplierId));
                        } else {
                            accessoryWriter.write(line + "\n"); 
                        }
                    }
                }
            } else {
                System.out.println("Accessory data is empty or invalid. No update to accessories.txt.");
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
