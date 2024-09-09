import java.util.List;
import java.util.Scanner;

public class Payment {
    
    public void processPayment(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders to process.");
            return;
        }

        clearScreen();
        Float totalAmount = Order.calculateTotalAmount(orders);
        System.out.println("Processing Payment for Order(s):");
        System.out.println("Amount to Pay: RM" + String.format("%.2f", totalAmount) + "\n");
        System.out.println("----------------------------------------------------------");
        System.out.println("Payment Method");
        System.out.println("----------------------------------------------------------");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Touch'n Go");
        System.out.println("----------------------------------------------------------");
        System.out.printf("Enter Your Payment Method:");
        Scanner chooses = new Scanner(System.in);
        int choose = chooses.nextInt(); 

        chooses.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
