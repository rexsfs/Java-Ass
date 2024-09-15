import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderFile {

    public static void YourOrder(String filename) {
        List<OrderFunc> orders = new ArrayList<>();
        String orderId = null;
        double totalAmount = 0.0;
        String orderDate = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            System.out.println("Your Order: ");
            System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");
            System.out.printf("| %-13s | %-48s | %-12s | %-13s | %-13s |%n", "Order ID", "Accessory ID", "Product", "Qty", "Price (RM)", "Amount (RM)");
            System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String accessoryId = parts[0];
                    String name = parts[1];
                    int qty = Integer.parseInt(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());

                    OrderFunc order = new OrderFunc(null, accessoryId, name, qty);
                    order.calculateAmount(price);
                    order.randomOrderID();
                    order.dateOrder();
                    orders.add(order);

                    System.out.println(order.toString());
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");
                }
            }

            if (!orders.isEmpty()) {
                totalAmount = OrderFunc.calculateTotalAmount(orders);
                System.out.printf("Total Amount: RM%.2f\n", totalAmount);
            }

        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }
    }
}
