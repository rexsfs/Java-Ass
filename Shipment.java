import java.util.ArrayList;

public class Shipment { 
    private String shipmentId;
    private String destination;
    private ArrayList<Item> items;
    private double shippingCost;

    // Default constructor
    public Shipment() {
        this.shipmentId = "";
        this.destination = "";
        this.items = new ArrayList<>();
        this.shippingCost = 0.0;
    }

    // Parameterized constructor
    public Shipment(String shipmentId, String destination, ArrayList<Item> items) {
        this.shipmentId = shipmentId;
        this.destination = destination;
        this.items = items;
        this.shippingCost = calculateShippingCost(calculateTotalItemCost());
    }

    // Getters
    public String getShipmentId() {
        return shipmentId;
    }

    public String getDestination() {
        return destination;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    // Setters
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    // Calculate total item cost
    public double calculateTotalItemCost() {
        double totalItemCost = 0.0;
        for (Item currentItem : items) {
            totalItemCost += currentItem.getPrice() * currentItem.getQuantity();
        }
        return totalItemCost;
    }

    // Calculate shipping cost based on total item cost
    public double calculateShippingCost(double totalCost) {
        return totalCost < 100 ? 10.00 : 0.00;
    }

    // Print invoice method
    public void printInvoice() {
        double totalItemCost = calculateTotalItemCost();
        double shippingCost = calculateShippingCost(totalItemCost); // Calculate shipping cost here
        double tax = totalItemCost * 0.05;
        double totalCost = totalItemCost + tax + shippingCost;

        System.out.println("Order ID: " + shipmentId);
        System.out.println("Destination: " + destination);
        System.out.println("--------------------------------------------------");
        System.out.println("No.  | Item                  | Quantity | Total(RM)");

        for (int i = 0; i < items.size(); i++) {
            Item currentItem = items.get(i);
            double itemCost = currentItem.getPrice() * currentItem.getQuantity();
            System.out.printf("%-4d | %-21s | %-8d | %-8.2f\n", i + 1, currentItem.getName(), currentItem.getQuantity(), itemCost);
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Tax (5%%)                           : %-8.2f\n", tax);
        System.out.printf("Shipping Cost                      : %-8.2f\n", shippingCost);
        System.out.printf("TOTAL (RM)                         : %-8.2f\n", totalCost);
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        // Example data for testing
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Claw clip", 1, 20.70));
        itemList.add(new Item("Shirt", 1, 10.00));

        // Create a Shipment object and print the invoice
        Shipment shipment = new Shipment("P1000", "Jalan34/70a,50480, Kulala Lumpur, Malaysia ", itemList);
        shipment.printInvoice();
    }
}

// Item class for the shipment
class Item {
    private String name;
    private int quantity;
    private double price;

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}