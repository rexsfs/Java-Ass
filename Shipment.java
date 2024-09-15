import java.util.ArrayList;

public class Shipment {
    private String shipmentId;
    private String destination;
    private ArrayList<Item>item;
    private double shippingCost;

    public Shipment() {
        this.shipmentId = "";
        this.destination = "";
        this.item = new ArrayList <>();
        this.shippingCost = 0.0;
    }
    
    public Shipment(String shipmentId, String destination, ArrayList<Item>item, double shippingCost) {
        this.shipmentId = shipmentId;
        this.destination = destination;
        this.item = item;
        this.shippingCost = shippingCost;
    }

    public String getshipmentId() {
        return shipmentId;
    }

    public String getdestination() {
        return destination;
    }

    public ArrayList <Item> getArrayList() {
        return item;
    }

    public double shippingCost() {
        return shippingCost;
    }

    public void setshipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setdestination(String destination) {
        this.destination = destination;
    }

    public void setArrayList(ArrayList <Item> myArrayList) {
        this.item = myArrayList;
    }

    public void setshippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public void calcShippingCost(double totalCost) {
        if (totalCost  <= 100) {
            shippingCost = 10.00;
        }
        else if (totalCost > 100) {
            shippingCost = 0.00;
        }
    }
    
    




}

