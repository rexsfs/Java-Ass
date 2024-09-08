import java.util.List;
import java.util.Scanner;

public class Payment {
    public void processPayment(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders to process.");
            return;
        }

        Float totalAmount = Order.calculateTotalAmount(orders);
        System.out.println("\nProcessing Payment for Order(s):");
        System.out.println("Amount to Pay: RM" + String.format("%.2f", totalAmount));
        
    }
}
