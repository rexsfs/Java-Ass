import java.util.Scanner;

public class MainOrderMenu {
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
                back(foundIndex);
                break;
            case 2:
                customOrder cusOrder = new customOrder();
                cusOrder.displayCusOrder();
                back(foundIndex);
                break;
            case 3:
                gnrReport report = new gnrReport();
                report.generateReport();
                back(foundIndex);
                break;
            case 4:
                StaffMain.staffTypeRecognize(foundIndex);
                break;
            default:
                System.out.println("Invalid Option.");
                break;
        }

    }

    public static void UserMenu(int foundIndex) {
        Scanner choices = new Scanner(System.in);

        System.out.println("\nOrder:");
        System.out.println("1. Start Order");
        System.out.println("2. History");
        System.out.println("3. Return");
        System.out.print("Enter Your Option: ");
        int choice = choices.nextInt();

        switch (choice) {
            case 1:
                OrderMenu menu = new OrderMenu();
                menu.OrderAccessories();
                break;
            case 2:
                History history = new History();
                history.displayHistory();
                back(foundIndex);
                break;
            case 3:
                StaffMain.staffTypeRecognize(foundIndex);
                break;
        }

    }

    public static void back(int foundIndex) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        Scanner scanner = new Scanner(System.in);
        String yn = "";
        
        while (!yn.equalsIgnoreCase("y")) {
            System.out.printf("\nPlease Press y To Quit: ");
            yn = scanner.next();
            
            if (yn.equalsIgnoreCase("y")) {
                StaffMain.staffTypeRecognize(foundIndex);
            } else {
                System.out.println(ANSI_RED + "\nInvalid" + ANSI_RESET + " Option. Please Try Again.\n");
            }
        }
    }
}
