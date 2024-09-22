import java.util.Scanner;

public class MainStaffOrderMenu {
    public static void StaffMenu(int foundIndex) {
        Scanner choices = new Scanner(System.in);

        System.out.println("\nOrder:");
        System.out.println("1. Start Order");
        System.out.println("2. Customer Order");
        System.out.println("3. Generate Report");
        System.out.println("4. Return");
        System.out.print("Enter Your Option: ");
        int choice = choices.nextInt();

        switch (choice) {
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
            case 4:
                StaffMain.staffTypeRecognize(foundIndex);
                break;
            default:
                System.out.println("Invalid Option.");
                break;
        }

        choices.close();
    }
}
