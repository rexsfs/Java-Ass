import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderFile {

    public static void YourOrder(String filename) {
        List<OrderFunc> orders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            clearScreen();
            System.out.println("\n\n\nYour Order: ");
            System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");
            System.out.printf("| %-13s | %-48s | %-12s | %-13s | %-13s |%n", "ID", "Product", "Qty", "Price (RM)", "Amount (RM)");
            System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    int qty = Integer.parseInt(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());

                    OrderFunc order = new OrderFunc(id, name, qty);
                    order.calculateAmount(price);
                    order.randomOrderID();
                    order.dateOrder();
                    orders.add(order);

                    System.out.printf("| %-13s | %-48s | %-12d | RM%-11.2f | RM%-11.2f |%n", id, name, qty, price, order.getAmount());
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");
                }
            }

            if (!orders.isEmpty()) {
                System.out.println("Order ID: " + orders.get(0).getOrderID());
                System.out.printf("Total Amount: RM%.2f\n", OrderFunc.calculateTotalAmount(orders));
                System.out.println("Order Date: " + orders.get(0).getOrderDate());
            }

        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
