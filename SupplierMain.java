/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.File;
import java.io.FileWriter; //IOException e
import java.io.IOException;
import java.util.InputMismatchException; //catching hte exception data type
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class SupplierMain {

    public static void main(String[] args) {
        menu(1);
    }

    public static void menu(int foundIndex) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            System.out.println("1. Add supplier");
            System.out.println("2. Modify supplier");
            System.out.println("3. Delete supplier");
            System.out.println("4. Display supplier");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-4): ");

            try {
                choice = scanner.nextInt();

                if (choice >= 1 && choice <= 5) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer between 1 and 4.");
                scanner.next(); //rewind stdin = clear
            }
        }

        switch (choice) {
            case 1:
                addSupplier(foundIndex);
                break;
            case 2:
                modifySupplier(foundIndex);
                break;
            case 3:
                removeSupplier(foundIndex);
                break;
            case 4:
                displaySupplier(foundIndex);
            case 5:
                StaffMain.menu(foundIndex);
                break;
        }

    }

    public static String generateNewSupplierId() {
        String lastId = "S1000"; // Default start if file is empty

        try (Scanner scanner = new Scanner(new File("supplier.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split("\\|");
                if (split.length > 0) {
                    lastId = split[0]; // Extract the supplier ID (first field)
                }
            }
        } catch (IOException e) { //file not found or does not exist
            System.out.println("Error reading supplier file.");
            e.printStackTrace(); //use for debugging: locates the error
        }

        // Check if lastId is empty or not correctly formatted
        if (lastId.length() < 2 || !lastId.startsWith("S")) {
            return "S1000"; // Return default ID if lastId is invalid
        }

        // Remove the 'S' prefix and parse the number
        String numberPart = lastId.substring(1); // Remove the 'S' //remove the character part
        int newIdNumber = Integer.parseInt(numberPart) + 1; // auto increaase 1 in the number part like 1001

        // Format the new ID with 'S' prefix
        return "S" + newIdNumber;
    }

    public static void WriteSupplierToFile(ArrayList<Supplier> supplierList) {
        try (FileWriter supplierFile = new FileWriter("supplier.txt", false)) {

            for (Supplier supplier : supplierList) {
                supplierFile.write(supplier.toString());
            }

        } catch (IOException e) {
            System.out.println("Error occured while writing to the file.");
            e.printStackTrace();
        }
    }

    public static ArrayList<Supplier> readSupplierToArray() {
        ArrayList<Supplier> supplierList = new ArrayList<>();   //no need initialise array size

        try (Scanner scanner = new Scanner(new File("supplier.txt"))) {
            while (scanner.hasNextLine()) {  //has next line = continue loop
                String line = scanner.nextLine();
                String[] split = line.split("\\|");

                if (split.length >= 6) {
                    String supplierId = split[0];
                    String name = split[1];
                    String phoneNum = split[2];
                    String address = split[3];
                    String email = split[4];
                    String companyName = split[5];

                    Supplier supplier = new Supplier(supplierId, name, phoneNum, address, email, companyName);
                    supplierList.add(supplier);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading supplier file.");
            e.printStackTrace();
        }

        return supplierList;
    }

    public static void addSupplier(int foundIndex) {
        Scanner scanner = new Scanner(System.in);

        String supplierId = generateNewSupplierId();

        System.out.print("Enter Supplier Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Supplier Phone Number: ");
        String phoneNum = scanner.nextLine();

        while (!isValidPhoneNum(phoneNum)) {
            System.out.println("Invalid input!");
            System.out.print("Enter Supplier Phone Number: ");
            phoneNum = scanner.nextLine();
        }

        System.out.print("Enter Supplier Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Supplier Email: ");
        String email = scanner.nextLine();

        while (!isValidEmail(email)) {
            System.out.println("Invalid email");
            System.out.println("Enter Supplier Email");
            email = scanner.nextLine();
        }

        System.out.print("Enter Supplier's Company Name : ");
        String companyName = scanner.nextLine();

        Supplier supplier = new Supplier(supplierId, name, phoneNum, address, email, companyName);

        System.out.println("Are you confirm to add this supplier? (Y): ");
        char message = scanner.nextLine().charAt(0);

        if (Character.toUpperCase(message) == 'Y') {

            System.out.println("Staff Details");
            System.out.println("Staff Id: " + supplier.getPersonId());
            System.out.println("Staff Name: " + supplier.getName());
            System.out.println("Staff Phone Number: " + supplier.getPhoneNum());
            System.out.println("Staff Address: " + supplier.getAddress());
            System.out.println("Staff Email: " + supplier.getEmail());
            System.out.println("Staff Company Name : " + supplier.getCompanyName());

            try {
                FileWriter supplierFile = new FileWriter("supplier.txt", true);
                supplierFile.write(supplier.toString());
                supplierFile.close();
                System.out.println("Staff Added Successfully");
            } catch (IOException e) {
                System.out.println("File Opening error.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Staff added not successfully.");
            addSupplier(foundIndex);
        }

    }

    public static void modifySupplier(int staffFound) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Supplier> supplierList = readSupplierToArray();

        System.out.print("Enter supplier ID to modify (9999 to exit) : ");
        String supplierId = scanner.nextLine();

        if (supplierId.equals("9999")) {
            menu(staffFound);
        }

        int foundIndex = 0;
        boolean found = false;

        for (int i = 0; i < supplierList.size(); i++) {

            if (supplierId.equals(supplierList.get(i).getPersonId())) {
                foundIndex = i;
                found = true;
                break;
            }

        }

        if (!found) {

            System.out.println("Supplier not found.");
            modifySupplier(staffFound);

        } else {
            int choice = -1;
            boolean validChoice = false;

            while (!validChoice) {
                try {
                    System.out.println("1. Supplier Name");
                    System.out.println("2. Supplier Phone Number");
                    System.out.println("3. Supplier Email");
                    System.out.println("4. Supplier Address");
                    System.out.println("5. Supplier Company");
                    System.out.println("6. Exit");
                    System.out.print("Select the information to modify: ");

                    // Attempt to read the user's choice
                    choice = scanner.nextInt();
                    validChoice = true;  // if the input is valid, exit the loop
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();  // clear the invalid input
                }
            }

            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new Supplier name: ");
                    supplierList.get(foundIndex).setName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new Phone Number : ");
                    supplierList.get(foundIndex).setPhoneNum(scanner.nextLine());

                    while (!isValidPhoneNum(supplierList.get(foundIndex).getPhoneNum())) {
                        System.out.println("Invalid Phone Number");
                        System.out.print("Enter new Phone Number : ");
                        supplierList.get(foundIndex).setPhoneNum(scanner.nextLine());
                    }
                    break;
                case 3:
                    System.out.print("Enter new email : ");
                    supplierList.get(foundIndex).setEmail(scanner.nextLine());

                    while (!isValidEmail(supplierList.get(foundIndex).getEmail())) {
                        System.out.println("Invalid Email");
                        System.out.print("Enter new Email : ");
                        supplierList.get(foundIndex).setEmail(scanner.nextLine());
                    }
                    break;
                case 4:
                    System.out.println("Enter new address : ");
                    supplierList.get(foundIndex).setAddress(scanner.nextLine());
                    break;
                case 5:
                    System.out.println("Enter new Company : ");
                    supplierList.get(foundIndex).setCompanyName(scanner.nextLine());
                    break;
                case 6:
                    menu(staffFound);
            }
        }

        WriteSupplierToFile(supplierList);

        System.out.print("Do you want to modify more supplier ?");
        char confirmation = scanner.nextLine().charAt(0);

        if (Character.toUpperCase(confirmation) == 'Y') {
            modifySupplier(staffFound);
        } else {
            menu(staffFound);
        }

    }

    public static boolean isValidPhoneNum(String phoneNum) {
        String phoneNumFormat1 = "01\\d-\\d{7}";
        String phoneNumFormat2 = "01\\d-\\d{8}";

        if (!phoneNum.matches(phoneNumFormat1) && !phoneNum.matches(phoneNumFormat2)) {
            return false;
        }

        return true;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";  // Email format pattern
        return email.matches(regex);
    }

    public static void removeSupplier(int staffFound) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Supplier> supplierList = readSupplierToArray();
        System.out.print("Enter Supplier ID to remove (9999 to exit): ");
        String supplierId = scanner.nextLine();

        if (supplierId.equals("9999")) {
            menu(staffFound);
        }
        int foundIndex = 0;
        boolean found = false;

        for (int i = 0; i < supplierList.size(); i++) {
            if (supplierId.equals(supplierList.get(i).getPersonId())) {
                found = true;
                foundIndex = i;
                break;
            }
        }

        if (!found) {
            System.out.print("Supplier Id not found");
            removeSupplier(staffFound);
        }

        System.out.println("\n\nSupplier Details");
        System.out.println("Supplier ID: " + supplierList.get(foundIndex).getPersonId());
        System.out.println("Supplier Name: " + supplierList.get(foundIndex).getName());
        System.out.println("Supplier Phone Number: " + supplierList.get(foundIndex).getPhoneNum());
        System.out.println("Supplier Address: " + supplierList.get(foundIndex).getAddress());
        System.out.println("Supplier Email: " + supplierList.get(foundIndex).getEmail());
        System.out.println("Supplier Company Name: " + supplierList.get(foundIndex).getCompanyName());

        System.out.println("Are you sure to remove this supplier details(Y): ");
        char choice = scanner.nextLine().charAt(0); //character at the position 0

        if (Character.toUpperCase(choice) == 'Y') {
            System.out.println("Remove successfully");
            supplierList.remove(foundIndex);
            WriteSupplierToFile(supplierList);
        } else {
            System.out.println("Remove unsuccessfully");
            System.out.println("Do you want to remove other supplier(Y) ? ");
            choice = scanner.nextLine().charAt(0);
        }
        //if yes, removeSupplier
        //if no, menu
        if (Character.toUpperCase(choice) == 'Y') {
            removeSupplier(staffFound);
        } else {
            menu(staffFound);
        }
    }

    public static void displaySupplier(int staffFound) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Supplier> supplierList = readSupplierToArray();

        if (supplierList.isEmpty()) {
            System.out.println("No supplier records available.");
            return;
        }

        System.out.println("Supplier List:");
        System.out.println("+------------+-----------------+--------------+----------------------+---------------------------+---------------------------+");
        System.out.printf("| %-10s | %-15s | %-12s | %-20s | %-25s | %-25s |%n",
                "ID", "Name", "Phone", "Address", "Email", "Company");
        System.out.println("+------------+-----------------+--------------+----------------------+---------------------------+---------------------------+");

        for (Supplier supplier : supplierList) {
            System.out.printf("| %-10s | %-15s | %-12s | %-20s | %-25s | %-25s |%n",
                    supplier.getPersonId(),
                    supplier.getName(),
                    supplier.getPhoneNum(),
                    supplier.getAddress(),
                    supplier.getEmail(),
                    supplier.getCompanyName());
        }

        System.out.println("+------------+-----------------+--------------+----------------------+---------------------------+---------------------------+");

        System.out.println("Back to main (Y = yes): ");
        char choice = scanner.nextLine().charAt(0);

        while (Character.toUpperCase(choice) != 'Y') {

            System.out.println("Back to main (Y = yes): ");
            choice = scanner.nextLine().charAt(0);

        }

        if (Character.toUpperCase(choice) == 'Y') {
            menu(staffFound);
        }

    }

}
