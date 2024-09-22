import java.util.Scanner;

public class MainStaffOrderMenu {
    public static void main(String[] args) {
        Scanner choices = new Scanner(System.in);  

        System.out.println("Order:");
        System.out.println("1. Start Order");
        System.out.println("2. Customer Order");
        System.out.println("3. Generate Report");
        System.out.print("Enter Your Option: ");
        int choice = choices.nextInt();
        
        switch(choice) {
            case 1:
                OrderMenu menu = new OrderMenu();
                menu.OrderAccessories();
                break;
            case 2:
                customOrder cusOrder = new customOrder();
                cusOrder.displayCusOrder();
                break;
            case 3:
                gnrReport report = new gnrReport();
                report.generateReport();
                break;
            default:
                System.out.println("Invalid Option.");
                break;
        }
        
        choices.close(); 
    }
}
