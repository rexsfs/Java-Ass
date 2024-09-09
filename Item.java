import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Item {
    private String item;
    private int qty;
    private float price;

    public Item(String item, int qty, float price) {
        this.item = item;
        this.qty = qty;
        this.price = price;
    }

    // Getter methods
    public String getItem() {
        return item;
    }

    public int getQty() {
        return qty;
    }

    public float getPrice() {
        return price;
    }

    // Method to create the file if it does not exist
    public static void createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }

    // Method to write item details to the file using a for loop
    public static void writeToFile(String fileName, int numItems) {
        Scanner scanner = new Scanner(System.in);

        try (FileWriter writer = new FileWriter(fileName, true)) { // 'true' enables appending
            for (int i = 0; i < numItems; i++) {
                System.out.print("Enter item name: ");
                String itemName = scanner.nextLine(); // Use nextLine() to capture full input

                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();

                System.out.print("Enter amount: ");
                float price = scanner.nextFloat();
                scanner.nextLine(); // Consume newline character

                // Save item, qty, and amount to file using | as a delimiter
                writer.write(itemName + qty + " RM" + price + System.lineSeparator());
                System.out.println("Item details saved to " + fileName);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileName = "item.txt";

        // Step 1: Create the file if it doesn't exist
        createFile(fileName);

        // Step 2: Ask user how many items they want to enter
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many items do you want to add? ");
        int numItems = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Step 3: Write the specified number of items to the file
        writeToFile(fileName, numItems);
    }
}
