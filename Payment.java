import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Payment {
    public static void processPayment(List<OrderFunc> orders) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please Select Your Payment Method: ");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Touch'N Go");
        System.out.printf("Enter Your Option: ");
        int option = scanner.nextInt();

        String paymentMethod;

        switch (option) {
            case 1:
                paymentMethod = "Credit Card";
                break;
            case 2:
                paymentMethod = "Debit Card";
                break;
            case 3:
                paymentMethod = "Touch'N Go";
                break;
            default:
                System.out.println("Invalid Option. Please Try Again.");
                scanner.close();
                return;
        }

        double totalAmount = OrderFunc.calculateTotalAmount(orders);
        String orderID = orders.get(0).getOrderID(); // Assuming all orders share the same orderID
        String orderDate = orders.get(0).getOrderDate(); // Assuming all orders share the same orderDate

        // Save the receipt details to a file
        try (FileWriter writer = new FileWriter("receipt.txt", true)) {
            writer.write("Order ID: " + orderID + "\n");
            writer.write("Order Date: " + orderDate + "\n");
            writer.write("Payment Method: " + paymentMethod + "\n");
            writer.write("Items:\n");

            for (OrderFunc order : orders) {
                writer.write(order.toString() + "\n");
            }

            writer.write("Total Amount: RM" + String.format("%.2f", totalAmount) + "\n");
            writer.write("---------------------------------\n");
            System.out.println("Payment processed successfully. Receipt saved.");
        } catch (IOException e) {
            System.out.println("Error writing receipt.");
            e.printStackTrace();
        }

        scanner.close();
    }
}
