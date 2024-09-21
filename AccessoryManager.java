import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AccessoryManager {
    private static Map<String, Accessory> accessories;
    private static Scanner scanner;
    private static int idCounter; // Keep track of the next available ID number

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
        String id = String.format("%s%04d", prefix, idCounter);
        idCounter++; // Increment for the next unique ID
        return id;
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
        Accessory accessory = accessories.get(id);

        if (accessory != null) {
            System.out.print("Enter new Name for Accessory (current: " + accessory.getName() + "): ");
            String name = scanner.nextLine();
            if (name.isEmpty()) name = accessory.getName();

            System.out.print("Enter new Price for Accessory (current: RM" + accessory.getPrice() + "): ");
            String priceInput = scanner.nextLine();
            double price = accessory.getPrice();
            if (!priceInput.isEmpty()) {
                price = Double.parseDouble(priceInput);
            }

            System.out.print("Enter new Quantity for Accessory (current: " + accessory.getQuantity() + "): ");
            String quantityInput = scanner.nextLine();
            int quantity = accessory.getQuantity();
            if (!quantityInput.isEmpty()) {
                quantity = Integer.parseInt(quantityInput);
                if (quantity < 0) {
                    System.out.println("Quantity cannot be negative. Keeping current quantity.");
                    quantity = accessory.getQuantity();
                }
            }

            accessory.updateAccessory(name, price, quantity);
            saveAccessories();
            System.out.println("Accessory updated.");
        } else {
            System.out.println("Accessory ID not found.");
        }
    }

    private static void showDetails() {
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
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);

        if (accessory != null) {
            accessories.remove(id);
            saveAccessories();
            System.out.println("Accessory with ID " + id + " has been deleted.");
        } else {
            System.out.println("Accessory ID not found.");
        }
    }

    private static void stockInAccessory() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);

        if (accessory != null) {
            System.out.print("Enter quantity to stock in: ");
            int amount = scanner.nextInt();
            accessory.stockIn(amount);
            saveAccessories();
            System.out.println("Stock updated. New quantity: " + accessory.getQuantity());
        } else {
            System.out.println("Accessory ID not found.");
        }
    }

    private static void stockOutAccessory() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);

        if (accessory != null) {
            System.out.print("Enter quantity to stock out: ");
            int amount = scanner.nextInt();
            if (accessory.stockOut(amount)) {
                saveAccessories();
                System.out.println("Stock updated. New quantity: " + accessory.getQuantity());
            } else {
                System.out.println("Insufficient stock.");
            }
        } else {
            System.out.println("Accessory ID not found.");
        }
    }

    private static void loadAccessories() {
        try (BufferedReader br = new BufferedReader(new FileReader("accessories.txt"))) {
            String line;
            int highestIdNumber = 0; // Track the highest numeric part of the IDs
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    Accessory accessory = new Accessory(id, parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), parts[4]);
                    accessories.put(id, accessory);

                    // Extract numeric part of ID to find the highest
                    int idNumber = Integer.parseInt(id.substring(5)); // "accs-xxxx"
                    if (idNumber > highestIdNumber) {
                        highestIdNumber = idNumber;
                    }
                }
            }
            // Set the counter to the next available ID
            idCounter = highestIdNumber + 1; // Ensure the next ID will be unique
            System.out.println("Initialized idCounter to: " + idCounter); // Debug output
        } catch (FileNotFoundException e) {
            System.out.println("No accessories file found. Starting fresh.");
            idCounter = 1; // Optional: Start with ID 1 if the file does not exist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveAccessories() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("accessories.txt"))) { // Overwrite mode
            for (Accessory accessory : accessories.values()) {
                bw.write(accessory.getAccessoryId() + "," + accessory.getName() + "," +
                          accessory.getPrice() + "," + accessory.getQuantity() + "," + accessory.getSupplierId());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
