import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AccessoryManager {

    private Map<String, Accessory> accessories;
    private Scanner scanner;
    private Map<String, Integer> idCounters;

    public AccessoryManager() {
        accessories = new HashMap<>();
        scanner = new Scanner(System.in);
        idCounters = new HashMap<>();
        loadAccessories();
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nAccessory Management System");
            System.out.println("1. Add New Accessory");
            System.out.println("2. Update Accessory Details");
            System.out.println("3. View Accessory Details");
            System.out.println("4. Delete Accessory");
            System.out.println("5. Stock In Accessory");
            System.out.println("6. Stock Out Accessory");
            System.out.println("7. Display All Accessories");
            System.out.println("8. Start Order");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
    
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
                    Order orderMenu = new Order();  
                    orderMenu.OrderAccessories();  
                    OrderReceipt.main(null);
                    OrderFunc.showMenu(null);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    

    private void displayAllAccessories() {
        System.out.println("\nAll Accessories Details:");
        try (BufferedReader br = new BufferedReader(new FileReader("accessories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);
                    
                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Price: RM" + price);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("------------------------");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No accessories found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private void addAccessory() {
        System.out.print("Enter Accessory Name: ");
        String name = scanner.nextLine();
        String id = generateId(); // Always generate "accs-xxxx" ID
        System.out.print("Enter Accessory Price: ");
        double price = scanner.nextDouble();

        // Validate accessory quantity
        int quantity = -1;
        while (true) {
            System.out.print("Enter Accessory Quantity: ");
            quantity = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            if (quantity >= 0) {
                break;
            } else {
                System.out.println("Quantity cannot be negative. Please enter a valid number.");
            }
        }

        Accessory accessory = new Accessory(id, name, price, quantity);
        accessories.put(id, accessory);
        saveAccessories();  // Auto-save
        System.out.println("Accessory added with ID: " + id);
    }

    private String generateId() {
        // Always use the "accs-" prefix
        String prefix = "accs-";

        // Get the current ID counter for this prefix, or start from 1 if not present
        int idNumber = idCounters.getOrDefault(prefix, 1);

        // Generate the ID with a zero-padded number (4 digits)
        String id = String.format("%s%04d", prefix, idNumber);

        // Increment and store the updated counter
        idCounters.put(prefix, idNumber + 1);

        return id;
    }

    private void updateAccessory() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);
    
        if (accessory != null) {
            // Update name: if user presses enter, keep the current name
            System.out.print("Enter new Name for Accessory (current: " + accessory.getName() + "): ");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                name = accessory.getName(); // Keep the current name if no input is given
            }
    
            // Update price: if user presses enter, keep the current price
            System.out.print("Enter new Price for Accessory (current: RM" + accessory.getPrice() + "): ");
            String priceInput = scanner.nextLine();
            double price = accessory.getPrice(); // Default to the current price
            if (!priceInput.isEmpty()) {
                try {
                    price = Double.parseDouble(priceInput); // Update if input is valid
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price entered, keeping current price.");
                }
            }
    
            // Update quantity: if user presses enter, keep the current quantity
            System.out.print("Enter new Quantity for Accessory (current: " + accessory.getQuantity() + "): ");
            String quantityInput = scanner.nextLine();
            int quantity = accessory.getQuantity(); // Default to the current quantity
            if (!quantityInput.isEmpty()) {
                try {
                    quantity = Integer.parseInt(quantityInput); // Update if input is valid
                    if (quantity < 0) {
                        System.out.println("Quantity cannot be negative. Keeping current quantity.");
                        quantity = accessory.getQuantity(); // Revert to current if input is invalid
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity entered, keeping current quantity.");
                }
            }
    
            // Update the accessory with new or unchanged values
            accessory.updateAccessory(name, price, quantity);
            saveAccessories();  // Auto-save
            System.out.println("Accessory updated.");
        } else {
            System.out.println("Accessory ID not found.");
        }
    }
    

    private void showDetails() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);

        if (accessory != null) {
            displayDetails(accessory);
        } else {
            System.out.println("Accessory ID not found.");
        }
    }

    private void displayDetails(Accessory accessory) {
        System.out.println("Accessory Details:");
        System.out.println("ID: " + accessory.getAccessoryId());
        System.out.println("Name: " + accessory.getName());
        System.out.println("Price: RM" + accessory.getPrice());
        System.out.println("Quantity: " + accessory.getQuantity());
    }

    private void deleteAccessory() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);

        if (accessory != null) {
            accessories.remove(id);
            saveAccessories();  // Auto-save
            System.out.println("Accessory with ID " + id + " has been deleted.");
        } else {
            System.out.println("Accessory ID not found.");
        }
    }

    private void stockInAccessory() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);

        if (accessory != null) {
            // Validate stock in quantity
            int amount = -1;
            while (true) {
                System.out.print("Enter amount to stock in: ");
                amount = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                if (amount >= 0) {
                    break;
                } else {
                    System.out.println("Stock in amount cannot be negative.");
                }
            }

            accessory.stockIn(amount);
            saveAccessories();  // Auto-save
            System.out.println("Stock updated. Current quantity: " + accessory.getQuantity());
        } else {
            System.out.println("Accessory ID not found.");
        }
    }

    private void stockOutAccessory() {
        System.out.print("Enter Accessory ID: ");
        String id = scanner.nextLine();
        Accessory accessory = accessories.get(id);

        if (accessory != null) {
            if (accessory.getQuantity() == 0) {
                System.out.println("The accessory is out of stock.");
                return;
            }

            // Validate stock out quantity
            int amount = -1;
            while (true) {
                System.out.print("Enter amount to stock out: ");
                amount = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                if (amount >= 0) {
                    break;
                } else {
                    System.out.println("Stock out amount cannot be negative.");
                }
            }

            if (accessory.stockOut(amount)) {
                saveAccessories();  // Auto-save
                System.out.println("Stock updated. Current quantity: " + accessory.getQuantity());
            } else {
                System.out.println("Not enough stock.");
            }
        } else {
            System.out.println("Accessory ID not found.");
        }
    }

    private void loadAccessories() {
        try (BufferedReader br = new BufferedReader(new FileReader("accessories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                String id = parts[0];
                String name = parts[1];
                double price = 0.0;
                int quantity = 0;

                try {
                    price = Double.parseDouble(parts[2]);
                    quantity = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing price/quantity for line: " + line);
                    continue;
                }

                Accessory accessory = new Accessory(id, name, price, quantity);
                accessories.put(id, accessory);

                String prefix = "accs-";
                int idNumber = Integer.parseInt(id.substring(prefix.length()));
                int currentMax = idCounters.getOrDefault(prefix, 0);
                if (idNumber > currentMax) {
                    idCounters.put(prefix, idNumber + 1);
                }
            }
        } catch (FileNotFoundException e) {
            accessories = new HashMap<>();
            idCounters = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAccessories() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("accessories.txt"))) {
            accessories.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey()) // Sort by accessory ID in ascending order
                .forEach(entry -> {
                    Accessory accessory = entry.getValue();
                    try {
                        bw.write(accessory.getAccessoryId() + "," + accessory.getName() + "," + accessory.getPrice() + "," + accessory.getQuantity());
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        AccessoryManager manager = new AccessoryManager();
        manager.displayMenu();
    }
}
