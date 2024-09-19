import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderFile {

    public static void YourOrder(String filename) {
        String ANSI_RESET = "\u001B[0m"; 
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
        List<OrderFunc> orders = new ArrayList<>();
        double totalAmount = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            System.out.println("Your Order: ");
            System.out.println(ANSI_BOLD_YELLOW + "+---------------+--------------------------------------------------+--------------+---------------+---------------+");
            System.out.printf("| %-13s | %-48s | %-13s | %-12s | %-13s |%n", "Accessory ID", "Item", "Price (RM)", "Qty", "Amount (RM)");
            System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
            
                if (parts.length == 4) {
                    String accessoryId = parts[0];
                    String item = parts[1];
                    double price = Double.parseDouble(parts[2].trim()); 
                    int qty = (int) Double.parseDouble(parts[3].trim()); 
            
                    OrderFunc order = new OrderFunc(null, accessoryId, item, qty);
                    order.calculateAmount(price);
                    orders.add(order);
            
                    System.out.println(ANSI_BOLD_YELLOW + order.toString());
                    System.out.println("+---------------+--------------------------------------------------+--------------+---------------+---------------+" + ANSI_RESET);
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
        String ANSI_RESET = "\u001B[0m"; 
        String ANSI_BOLD_YELLOW = "\u001B[1;33m";
        List<OrderFunc> orders = new ArrayList<>();
        double totalAmount = 0.0;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename));
             BufferedWriter writer = new BufferedWriter(new FileWriter("receipt.txt", true))) { 
    
            String line;
    
            String header = "+---------------+--------------------------------------------------+--------------+---------------+---------------+\n";
            String point = String.format("| %-13s | %-48s | %-12s | %-13s | %-13s |%n", "Accessory ID", "Item", "Price (RM)", "Qty", "Amount (RM)");
    
            System.out.println("Your Receipt: ");
            System.out.print(ANSI_BOLD_YELLOW + header);
            System.out.printf(point);
            System.out.print(header + ANSI_RESET);
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
    
                if (parts.length == 4) {
                    String accessoryId = parts[0];
                    String item = parts[1];
                    double price = Double.parseDouble(parts[2].trim());
                    int qty = Integer.parseInt(parts[3].trim());
    
                    OrderFunc order = new OrderFunc(null, accessoryId, item, qty);
                    order.calculateAmount(price);
                    order.randomOrderID();
                    order.dateOrder();
                    orders.add(order);
    
                    totalAmount = OrderFunc.calculateTotalAmount(orders);
                    String orderID = order.getOrderID();
                    String orderDt = order.getOrderDate();
                    String paymentMtd = Payment.getPaymentMethod();
    
                    String write = String.format("%s, %s, %.2f, %d, %.2f, %.2f, %s, %s, %s%n", 
                                                 accessoryId, item, price, qty, qty * price, totalAmount, orderID, orderDt, paymentMtd);
                    writer.write(write);
    
                    String show = String.format("| %-13s | %-48s | RM%-11.2f | %-12d | RM%-11.2f |%n", 
                                                 accessoryId, item, price, qty, qty * price);
    
                    System.out.print(ANSI_BOLD_YELLOW + show);
                    System.out.print(header + ANSI_RESET);
                }
            }
    
            if (!orders.isEmpty()) {
                totalAmount = OrderFunc.calculateTotalAmount(orders);
                String orderID = orders.get(0).getOrderID();
                String orderDt = orders.get(0).getOrderDate();
                String paymentMtd = Payment.getPaymentMethod();
    
                String total = String.format("Total Amount: RM%.2f\n", totalAmount);
                String id = "Order ID: " + orderID + "\n";
                String date = "Order Date: " + orderDt + "\n";
                String method = "Payment Method: " + paymentMtd + "\n";
    
                System.out.print(total);
                System.out.print(id);
                System.out.print(date);
                System.out.print(method + "\n");
            }
    
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }
    }    
    
}
