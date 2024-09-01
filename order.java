import java.util.Scanner;

public class Order {
    private String orderId;
    private Integer qty;
    private String orderDate;
    private Float amount;
    private Payment payment;

    public static void main(String[] args) {
        System.out.println(String.format("| %-20s | %-20s | %-20s | %-20s |", "Id", "Qty", "Total", "Date"));
    }

    public Order(String orderId, Integer qty, String orderDate, Float amount, Payment payment) {
        this.orderId = orderId;
        this.qty = qty;
        this.orderDate = orderDate;
        this.amount = amount;
        this.payment = payment;
    }

    public void addOrder() {
        
    }
}
