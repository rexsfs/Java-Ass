
// Item class for the shipment
public class Item {
    private String productId;
    private String name;
    private int quantity;
    private double price;

    public Item(String name, int quantity, double price, String productId) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter method
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getProductId() {
        return productId;
    }



    
}

