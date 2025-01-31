import java.io.Serializable;

public class Accessory implements Serializable {
    private static final long serialVersionUID = 1L;  // For serialization
    private String accessoryId;
    private String name;
    private double price;
    private int quantity;
    private String supplierId;

    // Constructor with parameters
    public Accessory(String accessoryId, String name, double price, int quantity, String supplierId) {
        this.accessoryId = accessoryId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplierId = supplierId;
    }

    // No-argument constructor
    public Accessory() {
        this.accessoryId = "UNKNOWN";
        this.name = "Unnamed Accessory";
        this.price = 0.0;
        this.quantity = 0;
    }

    public String getSupplierId() {
        return supplierId;
    }

    // Method to add stock
    public void stockIn(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }

    // Method to remove stock
    public boolean stockOut(int amount) {
        if (amount > 0 && this.quantity >= amount) {
            this.quantity -= amount;
            return true;
        }
        return false;  // Return false if there isn't enough stock
    }

    // Method to update the accessory details
    public void updateAccessory(String name, double price, int quantity) {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    // Getters and setters
    public String getAccessoryId() {
        return accessoryId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setAccessoryId(String accessoryId) {
        this.accessoryId = accessoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
