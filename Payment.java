import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Payment {
    public static void processPayment() {
        List<OrderFunc> orders = new ArrayList<>();
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
        
        scanner.close();
        writeReceipt(paymentMethod);
    }

    private static void writeReceipt(String paymentMethod) {
        try (FileWriter receiptWriter = new FileWriter("receipt.txt", true)) {
            receiptWriter.write("Receipt Details:\n");
            receiptWriter.write("-----------------\n");

            receiptWriter.write("Order Details:\n");
            appendFileContent("order.txt", receiptWriter);

            receiptWriter.write("\nOrder Summary:\n");
            appendFileContent("orderSum.txt", receiptWriter);

            receiptWriter.write("Payment Method: " + paymentMethod + "\n");
            System.out.println("");

        } catch (IOException e) {
            System.out.println("Error writing to receipt.txt.");
            e.printStackTrace();
        }
    }

    private static void appendFileContent(String fileName, FileWriter writer) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading from " + fileName);
            e.printStackTrace();
        }
    }
}
