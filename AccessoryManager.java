import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccessoryManager {
    private static Map<String, Accessory> accessories;
    private static Scanner scanner;

    static {
        accessories = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public AccessoryManager() {
        loadAccessories(); // Load accessories on initialization
    }

    public static void displayMenu(int staffIndex) {
        while (true) {
            System.out.println("\nAccessory Management System");
            System.out.println("1. Add New Accessory");
            System.out.println("2. Update Accessory Details");
            System.out.println("3. View Accessory Details");
            System.out.println("4. Delete Accessory");
            System.out.println("5. Stock In Accessory");
            System.out.println("6. Stock Out Accessory");
            System.out.println("7. Display All Accessories");
            System.out.println("8. Return");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addAccessory();
                    break;
                case 2:
                    updateAccessory();
                    break;
                case 3:
                    showDetails();
                    break;
                case 4:
                    deleteAccessory();
                    break;
                case 5:
                    stockInAccessory();
                    break;
                case 6:
                    stockOutAccessory();
                    break;
                case 7:
                    displayAllAccessories();
                    break;
                case 8:
                    // Assuming StaffMain.staffTypeRecognize exists
                    StaffMain.staffTypeRecognize(staffIndex);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }


    private static void displayAllAccessories() {
        System.out.println("\nAll Accessories Details:");
        try (BufferedReader br = new BufferedReader(new FileReader("accessories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);
                    String supplierId = parts[4];
                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Price: RM" + price);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Supplier ID: " + supplierId);
                    System.out.println("------------------------");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No accessories found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addAccessory() {
        ArrayList<Supplier> supplierList = SupplierMain.readSupplierToArray();
        System.out.print("Enter supplier ID: ");
        String supplierId = scanner.nextLine();

        boolean supplierFound = false;
        for (Supplier supplier : supplierList) {
            if (supplierId.equals(supplier.getPersonId())) {
                supplierFound = true;
                break;
            }
        }
        if (!supplierFound) {
            System.out.println("Invalid Supplier ID");
            return; // Exit the method
        }

        System.out.print("Enter Accessory Name: ");
        String name = scanner.nextLine();
        String id = generateUniqueId(); // Generate ID
        System.out.print("Enter Accessory Price: ");
        double price = scanner.nextDouble();

        int quantity = -1;
        while (true) {
            System.out.print("Enter Accessory Quantity: ");
            quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (quantity >= 0) {
                break;
            } else {
                System.out.println("Quantity cannot be negative. Please enter a valid number.");
            }
        }

        Accessory accessory = new Accessory(id, name, price, quantity, supplierId);
        accessories.put(id, accessory);
        saveSingleAccessory(accessory);
        System.out.println("Accessory added with ID: " + id);
    }

    private static String generateUniqueId() {
        String prefix = "accs-";
        String lastId = prefix + "0000"; // Default start if file is empty
    
        try (Scanner scanner = new Scanner(new File("accessories.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(","); // Correct delimiter is ','
                if (split.length > 0) {
                    lastId = split[0]; // Extract the accessory ID (first field)
                }
            }
        } catch (IOException e) { // File not found or does not exist
            System.out.println("Error reading accessory file.");
            e.printStackTrace(); // Use for debugging: locates the error
        }
    
        // Check if lastId is empty or not correctly formatted
        if (lastId.length() < 5 || !lastId.startsWith(prefix)) {
            return prefix + "0001"; // Return default ID if lastId is invalid
        }
    
        // Remove the prefix and parse the number
        String numberPart = lastId.substring(prefix.length()); // Remove the 'accs-' prefix
        int newIdNumber = Integer.parseInt(numberPart) + 1; // Auto increment the number part
    
        // Format the new ID with the prefix
        return String.format("%s%04d", prefix, newIdNumber);
    }
    
    private static void saveSingleAccessory(Accessory accessory) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("accessories.txt", true))) { // Append mode
            bw.write(accessory.getAccessoryId() + "," + accessory.getName() + "," +
                      accessory.getPrice() + "," + accessory.getQuantity() + "," + accessory.getSupplierId());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateAccessory() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        
        // Load all accessories into a list
        ArrayList<Accessory> accessoryList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("accessories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String accessoryId = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);
                    String supplierId = parts[4];
                    accessoryList.add(new Accessory(accessoryId, name, price, quantity, supplierId));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if there was an error reading the file
        }
        
        // Find and update the accessory
        boolean accessoryFound = false;
        for (Accessory accessory : accessoryList) {
            if (accessory.getAccessoryId().equals(id)) {
                accessoryFound = true;
                System.out.print("Enter new Name for Accessory (current: " + accessory.getName() + "): ");
                String name = scanner.nextLine();
                if (!name.isEmpty()) {
                    accessory.setName(name);
                }
    
                System.out.print("Enter new Price for Accessory (current: RM" + accessory.getPrice() + "): ");
                String priceInput = scanner.nextLine();
                if (!priceInput.isEmpty()) {
                    accessory.setPrice(Double.parseDouble(priceInput));
                }
    
                System.out.print("Enter new Quantity for Accessory (current: " + accessory.getQuantity() + "): ");
                String quantityInput = scanner.nextLine();
                if (!quantityInput.isEmpty()) {
                    int quantity = Integer.parseInt(quantityInput);
                    if (quantity >= 0) {
                        accessory.setQuantity(quantity);
                    } else {
                        System.out.println("Quantity cannot be negative. Keeping current quantity.");
                    }
                }
    
                break; // Exit the loop once the accessory is found and updated
            }
        }
    
        if (!accessoryFound) {
            System.out.println("Accessory ID not found.");
            return;
        }
    
        // Write the updated list back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("accessories.txt"))) {
            for (Accessory acc : accessoryList) {
                bw.write(acc.getAccessoryId() + "," + acc.getName() + "," +
                         acc.getPrice() + "," + acc.getQuantity() + "," + acc.getSupplierId());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        System.out.println("Accessory updated.");
    }
    
    private static void showDetails() {
        loadAccessories(); // Reload accessories from file to ensure the latest data is in memory
        
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);
    
        if (accessory != null) {
            displayDetails(accessory);
        } else {
            System.out.println("Accessory ID not found.");
        }
    }
    
    private static void displayDetails(Accessory accessory) {
        System.out.println("Accessory Details:");
        System.out.println("ID: " + accessory.getAccessoryId());
        System.out.println("Name: " + accessory.getName());
        System.out.println("Price: RM" + accessory.getPrice());
        System.out.println("Quantity: " + accessory.getQuantity());
    }

    private static void deleteAccessory() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine().trim(); // Trim spaces around the input
        Accessory accessory = accessories.get(id);
    
        if (accessory != null) {
            // Remove the accessory from the in-memory map
            accessories.remove(id);
    
            // Rewrite the file with the remaining accessories, sorted
            saveAccessories(); // Now this will handle sorting
    
            System.out.println("Accessory with ID " + id + " has been deleted.");
        } else {
            System.out.println("Accessory ID not found.");
        }
    }
    
    
    
    private static void stockInAccessory() {
        loadAccessories(); // Ensure the map is populated with current data
        
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine().trim(); // Trim spaces around the input
        Accessory accessory = accessories.get(id);
        
        if (accessory != null) {
            System.out.print("Enter quantity to stock in: ");
            int amount = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
        
            accessory.stockIn(amount); // Stock in the amount
            saveAccessories(); // Save updated map to the file
            System.out.println("Stock updated. New quantity: " + accessory.getQuantity());
        } else {
            System.out.println("Accessory ID not found.");
        }
    }
    
    private static void stockOutAccessory() {
        loadAccessories(); // Ensure the map is populated with current data
        
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine().trim(); // Trim spaces around the input
        Accessory accessory = accessories.get(id);
        
        if (accessory != null) {
            System.out.print("Enter quantity to stock out: ");
            int amount = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
        
            if (accessory.stockOut(amount)) { // Stock out the amount
                saveAccessories(); // Save updated map to the file
                System.out.println("Stock updated. New quantity: " + accessory.getQuantity());
            } else {
                System.out.println("Insufficient stock.");
            }
        } else {
            System.out.println("Accessory ID not found.");
        }
    }
    
    private static void loadAccessories() {
        accessories.clear(); // Clear the map before loading
        try (BufferedReader br = new BufferedReader(new FileReader("accessories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    Accessory accessory = new Accessory(id, parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), parts[4]);
                    accessories.put(id, accessory);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No accessories file found. Starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void saveAccessories() {
        // Create a list from the map entries
        List<Map.Entry<String, Accessory>> accessoryList = new ArrayList<>(accessories.entrySet());
    
        // Sort the list by accessory ID
        accessoryList.sort(Comparator.comparing(Map.Entry::getKey));
    
        // Write sorted accessories back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("accessories.txt", false))) { // Overwrite mode
            for (Map.Entry<String, Accessory> entry : accessoryList) {
                Accessory accessory = entry.getValue();
                bw.write(accessory.getAccessoryId() + "," + accessory.getName() + "," +
                          accessory.getPrice() + "," + accessory.getQuantity() + "," + accessory.getSupplierId());
                bw.newLine(); // Ensure each accessory is written on a new line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        AccessoryManager manager = new AccessoryManager();
        manager.displayMenu(0); // Pass in any staff index for the demo
    }
}

