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

            System.out.println(ANSI_BOLD_YELLOW + "  ___            _             ");
            System.out.println(" / _ \\  _ __  __| |  ___  _ __ ");
            System.out.println("| | | || '__|/ _` | / _ \\| '__|");
            System.out.println("| |_| || |  | (_| ||  __/| |   ");
            System.out.println(" \\___/ |_|   \\__,_| \\___||_|   " + ANSI_RESET);
            System.out.println("\nYour Order: ");
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
        String generatedOrderID = null; 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename));
             BufferedWriter receipt = new BufferedWriter(new FileWriter("receipt.txt", true));
             BufferedWriter customOrder = new BufferedWriter(new FileWriter("customOrder.txt", true));
             BufferedWriter history = new BufferedWriter(new FileWriter("history.txt", true))) {
    
            String line;
    

            String header = "+---------------+--------------------------------------------------+--------------+---------------+---------------+\n";
            String point = String.format("| %-13s | %-48s | %-12s | %-13s | %-13s |%n", "Accessory ID", "Item", "Price (RM)", "Qty", "Amount (RM)");
            
            System.out.println(ANSI_BOLD_YELLOW + " ____                   _         _   ");
            System.out.println("|  _ \\  ___   ___  ___ (_) _ __  | |_ ");
            System.out.println("| |_) |/ _ \\ / __|/ _ \\| || '_ \\ | __|");
            System.out.println("|  _ <|  __/| (__|  __/| || |_) || |_ ");
            System.out.println("|_| \\_\\\\___| \\___|\\___||_|| .__/  \\__|");
            System.out.println("                          |_|         " + ANSI_RESET);
            System.out.println("\nYour Receipt: ");
            System.out.print(ANSI_BOLD_YELLOW + header);
            System.out.printf(point);
            System.out.print(header + ANSI_RESET);

            history.write("Your Receipt:\n");
            history.write(ANSI_BOLD_YELLOW + header);
            history.write(point);
            history.write(header + ANSI_RESET);

            customOrder.write("Order:\n");
            customOrder.write(ANSI_BOLD_YELLOW + header);
            customOrder.write(point);
            customOrder.write(header + ANSI_RESET);
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
    
                if (parts.length == 4) {
                    String accessoryId = parts[0];
                    String item = parts[1];
                    double price = Double.parseDouble(parts[2].trim());
                    int qty = Integer.parseInt(parts[3].trim());
    
                    OrderFunc order = new OrderFunc(null, accessoryId, item, qty);
                    order.calculateAmount(price);
    
                    if (generatedOrderID == null) {
                        order.randomOrderID();
                        generatedOrderID = order.getOrderID(); 
                    } else {
                        order.setOrderID(generatedOrderID); 
                    }
    
                    order.dateOrder();
                    orders.add(order);
    
                    totalAmount = OrderFunc.calculateTotalAmount(orders);
                    String orderID = order.getOrderID();
                    String orderDt = order.getOrderDate();
                    String paymentMtd = Payment.getPaymentMethod();
    
                    String receiptEntry = String.format("%s, %s, %.2f, %d, %.2f, %s, %s, %s%n", 
                                                        accessoryId, item, price, qty, qty * price, orderID, orderDt, paymentMtd);
                    receipt.write(receiptEntry);  

                    String historytEntry = String.format("| %-13s | %-48s | RM%-11.2f | %-12d | RM%-11.2f |%n", 
                                                        accessoryId, item, price, qty, qty * price);
    
                    System.out.print(ANSI_BOLD_YELLOW + historytEntry);
                    System.out.print(header + ANSI_RESET);
                    
                    history.write(ANSI_BOLD_YELLOW + historytEntry);  
                    history.write(header + ANSI_RESET);
                    customOrder.write(ANSI_BOLD_YELLOW + historytEntry);  
                    customOrder.write(header + ANSI_RESET);
                }
            }
    
            if (!orders.isEmpty()) {
                totalAmount = OrderFunc.calculateTotalAmount(orders);
                String orderID = orders.get(0).getOrderID();  
                String orderDt = orders.get(0).getOrderDate();
                String paymentMtd = Payment.getPaymentMethod();
    
                String totalReceipt = String.format("Total Amount: RM%.2f\n", totalAmount);
                String orID = "Order ID: " + orderID + "\n";
                String orDate = "Order Date: " + orderDt + "\n";
                String methodReceipt = "Payment Method: " + paymentMtd + "\n";
    
                System.out.print(totalReceipt);
                System.out.print(orID);
                System.out.print(orDate);
                System.out.print(methodReceipt + "\n");
    
                history.write(totalReceipt);
                history.write(orID);
                history.write(orDate);
                history.write(methodReceipt + "\n");
                history.write(ANSI_BOLD_YELLOW + header + ANSI_RESET);

                customOrder.write(totalReceipt);
                customOrder.write(orID);
                customOrder.write(orDate);
                customOrder.write(methodReceipt + "\n");
                customOrder.write(ANSI_BOLD_YELLOW + header + ANSI_RESET); 
            }
    
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }
    }
    
}
