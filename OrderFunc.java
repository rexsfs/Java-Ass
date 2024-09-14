import java.util.List;
import java.util.Scanner;

public class OrderFunc extends OrderBase {
    private String name;
    private static Double totalAmount = 0.0; 
    private Payment payment;

    public OrderFunc(String orderID, String name, Integer qty) {
        super(orderID, totalAmount, qty, name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Double calculateTotalAmount(List<OrderFunc> orders) {
        totalAmount = 0.0;
        for (OrderFunc order : orders) {
            totalAmount += order.getAmount(); 
        }
        return totalAmount;
    }

    private static void cancelOrder(List<OrderFunc> orders) {
        System.out.println("Are you sure you want to cancel your order?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Enter Your Choice: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            orders.clear();
            System.out.println("All orders have been canceled.");
        } else if (choice == 2) {
            checkOut(orders);
        } else {
            System.out.println("Invalid choice. Please try again.");
            checkOut(orders);
        }
        scanner.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void checkOut(List<OrderFunc> orders) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_YELLOW = "\u001B[33m";

        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_YELLOW + "\n----------------------------------------------------------");
        System.out.println("Choose Your Option");
        System.out.println("----------------------------------------------------------");
        System.out.println("1. Proceed to Payment");
        System.out.println("2. Change Qty");
        System.out.println("3. Remove Order");
        System.out.println("4. Cancel Order");
        System.out.println("----------------------------------------------------------");
        System.out.print("Enter Your option: " + ANSI_RESET);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                clearScreen();
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:
                cancelOrder(orders);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                checkOut(orders);
                break;
        }
        scanner.close();
    }
}
