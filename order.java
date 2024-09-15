import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Order {
    private String orderID;
    private Double amount;
    private Integer qty;
    private String orderDate;

    public Order(String orderID, Double amount, Integer qty, String orderDate) {
        this.orderID = orderID;
        this.amount = amount;
        this.qty = qty;
        this.orderDate = orderDate;
    }

    public void randomOrderID() {
        Random random = new Random();
        int number = 1000000 + random.nextInt(9000000);
        this.orderID = "ORD" + number;
    }

    public void calculateAmount(double price) {
        amount = price * qty;
    }

    public void dateOrder() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        this.orderDate = dateFormat.format(date);
    }

    public String getOrderID() {
        return orderID;
    }

    public Integer getQty() {
        return qty;
    }

    public double getAmount() {
        return amount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
