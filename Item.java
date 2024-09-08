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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (FileWriter writer = new FileWriter("item.txt", true)) { // 'true' enables appending
            while (true) {
                System.out.print("Enter item name (or type 'exit' to stop): ");
                String itemName = scanner.nextLine(); // Use nextLine() to capture full input

                if (itemName.equalsIgnoreCase("exit")) {
                    break; // Exit loop if user types 'exit'
                }

                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();

                System.out.print("Enter amount: ");
                float price = scanner.nextFloat();
                scanner.nextLine(); // Consume newline character

                // Save item, qty, and amount to file using | as a delimiter
                writer.write(itemName + qty + "RM" +  price + System.lineSeparator());
                System.out.println("Item details saved to item.txt");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
