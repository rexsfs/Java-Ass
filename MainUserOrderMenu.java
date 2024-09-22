import java.util.Scanner;

public class MainUserOrderMenu {
    public static void main(String[] args) {
        Scanner choices = new Scanner(System.in);  

        System.out.println("Order:");
        System.out.println("1. Start Order");
        System.out.println("2. History");
        System.out.print("Enter Your Option: ");
        int choice = choices.nextInt();
        
        switch(choice) {
            case 1:
                OrderMenu menu = new OrderMenu();
                menu.OrderAccessories();
                break;
            case 2:
                History history = new History();
                history.displayHistory();
                break;
        }
        
        choices.close(); 
    }
}

