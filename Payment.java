import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Payment {

    private static String paymentMethod;  

    public Payment(String paymentMethod) {
        Payment.paymentMethod = paymentMethod;
    }

    public static String getPaymentMethod() {
        return paymentMethod;  
    }

    public static void processPayment(List<OrderFunc> orders, double totalAmount) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("\nPlease Select Your Payment Method: ");
    System.out.println("1. Credit Card");
    System.out.println("2. Debit Card");
    System.out.println("3. Touch'N Go");
    System.out.print("Enter Your Option: ");
    int option = scanner.nextInt();
    System.out.println("");

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
            return;
    }

    OrderFile.YourReceipt("order.txt");

    System.out.printf("\nAre You Want To Quit(y/n): ");
    String yn = scanner.next();
    switch (yn.toLowerCase()) {
        case "y":
            clearOrderFile("order.txt");
            break;
        case "n":
            return;
        default:
            System.out.println("Invalid Option. Please Try Again.");
            return;
    }
}

    public static void clearOrderFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error clearing the order file.");
            e.printStackTrace();
        }
    }
   
}
