import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Order {
    private String orderID;
    private Float amount;
    private Integer qty;
    private String orderDate;
    private static Float totalAmount = 0.0f;

    public Order(String orderID, Float amount, Integer qty, String orderDate) {
        this.orderID = orderID;
        this.qty = qty;
        this.amount = amount;
        this.orderDate = orderDate;
    }
//ensure that order id is unique, create duplicate func 
    public void randomOrderID() {
        Random random = new Random();
        int number = 1000000 + random.nextInt(9000000);
        this.orderID = "ORD" + number;
    }

    public void calculateAmount(Float price) {
        amount = price * qty;
    }

    public void dateOrder() {
        Date orderDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        this.orderDate = dateForm.format(orderDate);
    }

    public static void calculateTotalAmount(Order orders) {
        totalAmount += orders.getAmount();
    }

    public String getOrderID() {
        return orderID;
    }

    public Integer getQty() {
        return qty;
    }

    public Float getAmount() {
        return amount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderID(String orderID) {
        this.orderID= orderID;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public static void readOrdersFromFile(String filename) {
        List<Order> orders = new ArrayList<>(); 

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.printf("%-20s %-5s %-15s %-15s%n", "Product", "Qty", "Price", "Amount");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("RM");

                if (parts.length == 2) {
                    String itemDetails = parts[0];
                    float price = Float.parseFloat(parts[1].trim());

                    String itemName = itemDetails.replaceAll("\\d", "").trim();  
                    int qty = Integer.parseInt(itemDetails.replaceAll("\\D", "").trim());  

                    // Create new Order
                    Order order = new Order("", null, qty, null);
                    order.calculateAmount(price);
                    order.randomOrderID();
                    order.dateOrder();
                    orders.add(order);  

                    System.out.printf("%-20s %-5d RM%-13.2f RM%-8.2f%n", itemName, qty, price, order.getAmount());
                }
            }

            if (!orders.isEmpty()) {
                System.out.println("----------------------------------------------------------");
                System.out.println("Order ID: " + orders.get(0).getOrderID());
                System.out.printf("Total Amount: RM%.2f\n", calculateTotalAmount(orders));
                System.out.println("Order Date: " + orders.get(0).getOrderDate() + "\n");
                showMenu(orders);
            }

        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }
    }

    public static Float calculateTotalAmount(List<Order> orders) {
        Float totalAmount = 0.0f;
        for (Order order : orders) {
            totalAmount += order.getAmount();
        }
        return totalAmount;
    }

    public static void showMenu(List<Order> orders) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_YELLOW = "\u001B[33m";

        Scanner choices = new Scanner(System.in);
        System.out.println(ANSI_YELLOW + "\n----------------------------------------------------------");
        System.out.println("Choose Your Option");
        System.out.println("----------------------------------------------------------");

        System.out.println("1. Proceed to Payment");
        System.out.println("2. Change Qty");
        System.out.println("3. Remove Order");
        System.out.println("4. Cancel Order");

        System.out.println(ANSI_YELLOW + "----------------------------------------------------------");
        System.out.print("Enter Your option: " + ANSI_RESET);
        int choice = choices.nextInt();

        switch (choice) {
            case 1:
                    clearScreen();
                    Payment payment = new Payment();
                    payment.processPayment(orders);
                break;
            case 2:
               
                break;
            case 3:

                break;
            case 4:
                
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMenu(orders);
                break;
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        readOrdersFromFile("item.txt");
    }
}
