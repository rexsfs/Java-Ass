import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderFile {

    public static void YourOrder(String filename) {
        List<OrderFunc> orders = new ArrayList<>();
        double totalAmount = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            System.out.println("Your Order: ");
            System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+");
            System.out.printf("| %-13s | %-48s | %-12s | %-13s | %-13s |%n", "Accessory ID", "Product", "Qty", "Price (RM)", "Amount (RM)");
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
    
    public static void YourReceipt(String filename) {
        List<OrderFunc> orders = new ArrayList<>();
        double totalAmount = 0.0;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename));
             BufferedWriter writer = new BufferedWriter(new FileWriter("receipt.txt"))) {
    
            String line;
    
            String header = "+---------------+--------------------------------------------------+--------------+---------------+---------------+\n";
            String point = String.format("| %-13s | %-48s | %-12s | %-13s | %-13s |%n", "Accessory ID", "Product", "Qty", "Price (RM)", "Amount (RM)");
    
            writer.write("Your Receipt: \n");
            writer.write(header);
            writer.write(point);
            writer.write(header);
    
            System.out.println("Your Receipt: ");
            System.out.print(header);
            System.out.printf(point);
            System.out.print(header);

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
    
                    String point1 = String.format("| %-13s | %-48s | %-12d | RM%-11.2f | RM%-11.2f |%n", 
                                                accessoryId, name, qty, price, qty * price);

                    writer.write(point1);
                    writer.write(header);
    
                    System.out.print(point1);
                    System.out.print(header);
                }
            }

            if (!orders.isEmpty()) {
                totalAmount = OrderFunc.calculateTotalAmount(orders);
                String totalStr = String.format("Total Amount: RM%.2f\n", totalAmount);
                String orderIdStr = "Order ID: " + orders.get(0).getOrderID() + "\n";
                String orderDateStr = "Order Date: " + orders.get(0).getOrderDate() + "\n";
                String paymentMethod = Payment.getPaymentMethod();
                String paymentStr = "Payment Method: " + paymentMethod + "\n";

                writer.write(totalStr);
                writer.write(orderIdStr);
                writer.write(orderDateStr);
                writer.write(paymentStr);

                System.out.print(totalStr);
                System.out.print(orderIdStr);
                System.out.print(orderDateStr);
                System.out.print(paymentStr);
            }
    
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }
    }
    
   
}
