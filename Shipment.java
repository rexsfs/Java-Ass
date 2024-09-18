import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Shipment {
    private String shipmentStatus;
    private String date;
    private String address; 
    private ArrayList<Item> items;
    private double shipmentCost;
    private String invoiceId;


    // constructor
    public Shipment() {
        this.shipmentStatus = "";
        this.date = "";
        this.address = "";
        this.items = new ArrayList<>();
        this.shipmentCost = 0.0;
        this.invoiceId = "";
    }

    public Shipment(String shipmentStatus, String date, String address, ArrayList<Item> items) {
        this.shipmentStatus = shipmentStatus;
        this.date = date;
        this.address = address;
        this.items = items;
        this.shipmentCost = calculateShipmentCost(calculateTotalItemCost());
    }

    // Getter method

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getShipmentCost() {
        return shipmentCost;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    // Setter method

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setShipmentCost(double shipmentCost) {
        this.shipmentCost = shipmentCost;
    }

    public void setinvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    // Calculate total item cost
    public double calculateTotalItemCost() {
        double totalItemCost = 0.0;
        for (Item currentItem : items) {
            totalItemCost += currentItem.getPrice() * currentItem.getQuantity();
        }
        return totalItemCost;
    }

    // Calculate shipment cost based on total item cost
    public double calculateShipmentCost(double totalCost) {
        return totalCost < 100 ? 10.00 : 0.00;
    }

    public String generateRandomInvoiceId() {
        Random random = new Random();
        int randomNum = random.nextInt(900000) + 100000;  // Generates a 6-digit random number
        return "INV" + randomNum; // Prefix with "INV" for clarity
    }

    public void generateInvoice() {
        String invoiceId = generateRandomInvoiceId();  // Generate random invoice ID
        setinvoiceId(invoiceId);
        double totalItemCost = calculateTotalItemCost();
        double shipmentCost = calculateShipmentCost(totalItemCost); // Calculate shipment cost here
        double tax = totalItemCost * 0.05;
        double totalCost = totalItemCost + tax + shipmentCost;
    
        try (FileWriter writer = new FileWriter (invoiceId + ".txt")) {
            // Write invoice details to file
            writer.write("Invoice ID: " + invoiceId + "\n");
            writer.write("Shipment Date: " + date + "\n");
            writer.write("Address: " + address + "\n");
            writer.write("-------------------------------------------------------------\n");
            writer.write("No.  | Product ID | Item                  | Quantity | Total(RM)\n");
    
            for (int i = 0; i < items.size(); i++) {
                Item currentItem = items.get(i);
                double itemCost = currentItem.getPrice() * currentItem.getQuantity();
                writer.write(String.format("%-4d | %-10s | %-21s | %-8d | %-8.2f\n", i + 1, currentItem.getProductId(), currentItem.getName(), currentItem.getQuantity(), itemCost));
            }
    
            writer.write("-------------------------------------------------------------\n");
            writer.write(String.format("Tax (5%%)                                 : %-8.2f\n", tax));
            writer.write(String.format("Shipment Cost                            : %-8.2f\n", shipmentCost));
            writer.write(String.format("TOTAL (RM)                               : %-8.2f\n", totalCost));
            writer.write("-------------------------------------------------------------\n");
    
            System.out.println("Invoice generated and written to invoice.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the invoice to file.");
            e.printStackTrace();
        }
    }

    // Method to track the shipment status
    public void trackingShipment() {
        System.out.println("InvoiceId " + invoiceId + " status: " + shipmentStatus);
    }

    public static void main(String[] args) {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Phone case", 1, 50.00, "P1001"));
        itemList.add(new Item("Ear phone", 1, 10.00, "P1002"));

        Shipment shipment = new Shipment("In Transit", "2024-09-17", "Jalan34/70a,50480, Kuala Lumpur, Malaysia", itemList);
        shipment.generateInvoice();  // Generate  invoice
        shipment.trackingShipment();  // Track shipment status
    }
}



