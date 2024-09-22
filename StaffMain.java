/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class StaffMain {
    public static void main(String[] args) {

        Login();
    }

    public static ArrayList<Staff> readStaffToArray() {
        ArrayList<Staff> staff = new ArrayList<>();

        try (Scanner staffList = new Scanner(new File("staff.txt"))) {

            while (staffList.hasNextLine()) {
                String line = staffList.nextLine();
                String[] split = line.split("\\|"); // Corrected split method

                String staffId = split[0];
                String name = split[1];
                String phoneNum = split[2];
                String address = split[3];
                String email = split[4];
                String position = split[5];
                double salary = Double.parseDouble(split[6]);
                String staffType = split[7];
                String password = split[8];
                String answer = split[9];

                Staff staffTemp = new Staff(staffId, name, phoneNum, address, email, position, salary, staffType,
                        password, answer);
                staff.add(staffTemp);
            }

        } catch (IOException e) {
            System.out.println("\t\t\t\tError occur");
            e.printStackTrace();
        }

        return staff;
    }

    public static void writeArrayToFile(ArrayList<Staff> staffList) {
        try (FileWriter orderFile = new FileWriter("staff.txt", false)) {
            for (Staff staff : staffList) {

                orderFile.write(staff.toString());
            }
        } catch (IOException e) {
            System.out.println("\t\t\t\tError occurred while writing to file.");
            e.printStackTrace();
        }
    }

    public static String generateNewStaffId() {
        String lastId = "1000"; // Default start if file is empty

        try (Scanner scanner = new Scanner(new File("staff.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split("\\|");
                if (split.length > 0) {
                    lastId = split[0]; // Extract the staff ID (first field)
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading staff file.");
            e.printStackTrace();
        }

        int newId = Integer.parseInt(lastId) + 1; // Increment the latest ID by 1
        return String.valueOf(newId);
    }

    public static void Login() {
        Scanner input = new Scanner(System.in);
        ArrayList<Staff> staffList = readStaffToArray();
        boolean found = false; // Variable to track if login is successful
        int foundIndex = -1;
        String loggedInStaffRole = null; // Variable to hold the role after successful login

        while (!found) { // Loop until a valid ID is found or password is entered correctly

            System.out.println(
                    " _____                   ____             _____         ____________  _____    _____    ");
            System.out.println(
                    "|\\    \\              ____\\_  \\__     _____\\    \\_      /            \\|\\    \\   \\    \\   ");
            System.out.println(
                    " \\\\    \\            /     /     \\   /     /|     |    |\\___/\\  \\\\___/|\\\\    \\   |    |  ");
            System.out.println(
                    "  \\\\    \\          /     /\\      | /     / /____/|     \\|____\\  \\___|/ \\\\    \\  |    |  ");
            System.out.println(
                    "   \\|    | ______ |     |  |     ||     | |_____|/           |  |       \\|    \\ |    |  ");
            System.out.println(
                    "    |    |/      \\|     |  |     ||     | |_________    __  /   / __     |     \\|    |  ");
            System.out.println(
                    "    /            ||     | /     /||\\     \\|\\        \\  /  \\/   /_/  |   /     /\\      \\ ");
            System.out.println(
                    "   /_____\\/_____/||\\     \\_____/ || \\_____\\|    |\\__/||____________/|  /_____/ /______/|");
            System.out.println(
                    "  |      | |    ||| \\_____\\   | / | |     /____/| | |||           | / |      | |     | |");
            System.out.println(
                    "  |______|/|____|/ \\ |    |___|/   \\|_____|     |\\|_|/|___________|/  |______|/|_____|/ ");
            System.out.println(
                    "                    \\|____|               |____/                                        ");

            System.out.print("\nPlease enter your id (9999 to reset password): ");
            String id = input.nextLine();

            if (id.equals("9999")) {
                resetPassword();
                Login(); // Restart login after password reset
                return; // Ensure the method exits after calling Login() again
            }

            // Search for the ID in the staff list
            for (int i = 0; i < staffList.size(); i++) {
                if (id.equals(staffList.get(i).getPersonId())) {
                    System.out.print("Please enter your password: ");
                    String password = input.nextLine();

                    if (password.equals(staffList.get(i).getPassword())) {
                        loggedInStaffRole = staffList.get(i).getStaffType(); // Store the role after successful login
                        foundIndex = i;
                        found = true; // Mark login as successful
                        break; // Exit the loop after successful login
                    }
                }
            }

            // If the ID was not found, display a message and loop again
            if (!found) {
                System.out.println("\n\nWrong ID or Password. Please try again.");
            }
        }

        // After successful login, check the role and direct to appropriate menu
        if ("admin".equalsIgnoreCase(loggedInStaffRole)) {
            adminMenu(foundIndex); // Call admin menu if the role is 'admin'
        } else {
            menu(foundIndex); // Call normal menu for other staff roles
        }
    }

    public static void menu(int foundIndex) {

        ArrayList<Staff> staffList = readStaffToArray();

        int choice = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println(
                    "\n+=========================================================================================+");
            System.out.println(
                    "|           __        __   _                            ____  _         __  __            |");
            System.out.println(
                    "|           \\ \\      / /__| | ___ ___  _ __ ___   ___  / ___|| |_ __ _ / _|/ _|           |");
            System.out.println(
                    "|            \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_  _ \\ / _ \\ \\___ \\| __/ _` | |_| |_             |");
            System.out.println(
                    "|             \\ V  V /  __/ | (_| (_) | | | | | |  __/  ___) | || (_| |  _|  _|           |");
            System.out.println(
                    "|              \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___| |____/ \\__\\__,_|_| |_|             |");
            System.out.println(
                    "+=========================================================================================+");

            System.out.println("\n1. Supplier Function");
            System.out.println("2. Personal information");
            System.out.println("3. Modify Personal Information");
            System.out.println("4. Cancel Account");
            System.out.println("5. Accessory Management");
            System.out.println("6. Branch Function");
            System.out.println("7. Exit");
            System.out.println(
                    "--------------------------------------------------------------------------------------------");
            System.out.print("Select your choice : ");

            try {
                choice = scanner.nextInt();

                if (choice >= 1 && choice <= 7) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer between 1 and 7!!!");
                scanner.next();
            }
        }

        switch (choice) {
            case 1:
                SupplierMain.menu(foundIndex);
                break;
            case 2:
                displayPersonalInfo(foundIndex);
                break;
            case 3:
                modifyPersonal(foundIndex);
                break;
            case 4:
                cancelAccount(foundIndex);
                break;
            case 5:
                AccessoryManager.displayMenu(foundIndex);
                break;
            case 6:
                BranchManager.displayMenu(foundIndex); // Pass the foundIndex
                break;
            case 7:
                System.out.print("exit...");
                break;
        }
    }

    public static void adminMenu(int foundIndex) {
        int choice = 0;

        ArrayList<Staff> staffList = readStaffToArray();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "\n+=========================================================================================+");
            System.out.println(
                    "|       __        __   _                               _       _           _              |");
            System.out.println(
                    "|       \\ \\      / /__| | ___ ___  _ __ ___   ___     / \\   __| |_ __ ___ (_)_ __         |");
            System.out.println(
                    "|        \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\   / _ \\ / _` | '_ ` _ \\| | '_ \\        |");
            System.out.println(
                    "|         \\ V  V /  __/ | (_| (_) | | | | | |  __/  / ___ \\ (_| | | | | | | | | | |       |");
            System.out.println(
                    "|          \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___| /_/   \\_\\__,_|_| |_| |_|_|_| |_|       |");
            System.out.println(
                    "|                                                                                         |");
            System.out.println(
                    "+=========================================================================================+");
            System.out.println("\n1. Supplier Function");
            System.out.println("2. Personal information");
            System.out.println("3. Modify Personal Information");
            System.out.println("4. Modify Other Staff");
            System.out.println("5. Add Staff");
            System.out.println("6. Cancel Account");
            System.out.println("7. Remove Staff");
            System.out.println("8. Search Staff");
            System.out.println("9. Display All Staff");
            System.out.println("10. Accessory Management");
            System.out.println("11. Branch Function");
            System.out.println("12. Exit");
            System.out.println(
                    "--------------------------------------------------------------------------------------------");
            System.out.print("Select your choice : ");

            try {
                choice = scanner.nextInt();

                if (choice >= 1 && choice <= 12) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 12!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer between 1 and 12!!!");
                scanner.next();
            }
        }

        switch (choice) {
            case 1:
                SupplierMain.menu(foundIndex);
                break;
            case 2:
                displayPersonalInfo(foundIndex);
                break;
            case 3:
                modifyPersonal(foundIndex);
                break;
            case 4:
                modifyStaff(foundIndex);
                break;
            case 5:
                addStaff(foundIndex);
                break;
            case 6:
                cancelAccount(foundIndex);
                break;
            case 7:
                removeStaff(foundIndex);
                break;
            case 8:
                searchStaff(foundIndex);
                break;
            case 9:
                displayStaff(staffList, foundIndex);
                break;
            case 10:
                AccessoryManager.displayMenu(foundIndex);
                break;
            case 11:
                BranchManager.displayMenu(foundIndex); // Pass the foundIndex
                break;
            case 12:
                System.out.println("        _____                                                          ");
                System.out.println("   _____\\    \\ _____       _____ ____________    ________    ________  ");
                System.out.println("  /    / |    |\\    \\     /    //            \\  /        \\  /        \\ ");
                System.out.println(" /    /  /___/| \\    |   |    /|\\___/\\  \\\\___/||\\         \\/         /|");
                System.out.println("|    |__ |___|/  \\    \\ /    /  \\|____\\  \\___|/| \\            /\\____/ |");
                System.out.println("|       \\         \\    |    /         |  |     |  \\______ /\\   \\     | |");
                System.out.println("|     __/ __      /    |    \\    __  /   / __   \\ |      | \\   \\____|/ ");
                System.out.println("|\\    \\  /  \\    /    /|\\    \\  /  \\/   /_/  |   \\|______|  \\   \\      ");
                System.out.println("| \\____\\/    |  |____|/ \\|____||____________/|            \\  \\___\\    ");
                System.out.println("| |    |____/|  |    |   |    ||           | /             \\ |   |    ");
                System.out.println(" \\|____|   | |  |____|   |____||___________|/               \\|___|    ");
                System.out.println("       |___|/                                                          ");
                System.out.println("\nexit...");
                break;
        }
    }

    public static void displayPersonalInfo(int foundIndex) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Staff> staffList = readStaffToArray(); // Assume this method reads staff from the file into an
                                                         // ArrayList

        System.out.println(
                "\n+=========================================================================================+");
        System.out
                .println("|                ____  _         __  __   ____       _        _ _                         |");
        System.out.println(
                "|               / ___|| |_ __ _ / _|/ _| |  _ \\  ___| |_ __ _(_) |___                     |");
        System.out.println(
                "|               \\___ \\| __/ _` | |_| |_  | | | |/ _ \\ __/ _` | | / __|                    |");
        System.out.println(
                "|                ___) | || (_| |  _|  _| | |_| |  __/ || (_| | | \\__ \\                    |");
        System.out.println(
                "|               |____/ \\__\\__,_|_| |_|   |____/ \\___|\\__\\__,_|_|_|___/                    |");
        System.out
                .println("|                                                                                         |");
        System.out
                .println("+=========================================================================================+");
        System.out.println("Staff ID: " + staffList.get(foundIndex).getPersonId());
        System.out.println("Staff Name: " + staffList.get(foundIndex).getName());
        System.out.println("Staff Phone Number: " + staffList.get(foundIndex).getPhoneNum());
        System.out.println("Staff Address: " + staffList.get(foundIndex).getAddress());
        System.out.println("Staff Email: " + staffList.get(foundIndex).getEmail());
        System.out.println("Staff Password: " + staffList.get(foundIndex).getPassword());
        System.out.println("Staff Security Answer: " + staffList.get(foundIndex).getAnswer());

        System.out.print("\nBack to (admin/staff) menu (Y = yes)? : ");
        char confirmation = scanner.nextLine().charAt(0);

        while (Character.toUpperCase(confirmation) != 'Y'){
            System.out.println("Invalid input.Please enter letter 'y' return to menu!!!");
            System.out.print("\nBack to (admin/staff) menu (Y = yes)? : ");
            confirmation = scanner.nextLine().charAt(0);
        }

        staffTypeRecognize(foundIndex);

    }

    public static void modifyPersonal(int foundIndex) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Staff> staffList = readStaffToArray();

        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            try {
                System.out.println(
                        "\n+=========================================================================================+");
                System.out.println(
                        "|        __  __           _ _  __         ____                                 _          |");
                System.out.println(
                        "|       |  \\/  | ___   __| (_)/ _|_   _  |  _ \\ ___ _ __ ___  ___  _ __   __ _| |         |");
                System.out.println(
                        "|       | |\\/| |/ _ \\ / _` | | |_| | | | | |_) / _ \\ '__/ __|/ _ \\| '_ \\ / _` | |         |");
                System.out.println(
                        "|       | |  | | (_) | (_| | |  _| |_| | |  __/  __/ |  \\__ \\ (_) | | | | (_| | |         |");
                System.out.println(
                        "|       |_|  |_|\\___/ \\__,_|_|_|  \\__, | |_|   \\___|_|  |___/\\___/|_| |_|\\__,_|_|         |");
                System.out.println(
                        "|             |_ _|_ __  / _| ___ |___/ _ __ ___   __ _| |_(_) ___  _ __                  |");
                System.out.println(
                        "|              | || '_ \\| |_ / _ \\| '__| '_ ` _ \\ / _` | __| |/ _ \\| '_ \\                 |");
                System.out.println(
                        "|              | || | | |  _| (_) | |  | | | | | | (_| | |_| | (_) | | | |                |");
                System.out.println(
                        "|             |___|_| |_|_|  \\___/|_|  |_| |_| |_|\\__,_|\\__|_|\\___/|_| |_|                |");
                System.out.println(
                        "|                                                                                         |");
                System.out.println(
                        "+=========================================================================================+");
                System.out.println("Select the information to modify:");
                System.out.println("1. Name");
                System.out.println("2. Phone Number");
                System.out.println("3. Address");
                System.out.println("4. Email");
                System.out.println("5. Password");
                System.out.println("6. Security question answer");
                System.out.print("Enter Choice: ");
                choice = scanner.nextInt();
                validChoice = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number!!!");
                scanner.next(); // Clear the invalid input
            }
        }

        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                System.out.println(
                        "\n------------------------------------------------------------------------------------------");
                System.out.println("Current Staff name: " + staffList.get(foundIndex).getName());
                System.out.print("Enter new Staff name: ");
                staffList.get(foundIndex).setName(scanner.nextLine());
                break;
            case 2:
                System.out.println(
                        "\n------------------------------------------------------------------------------------------");
                String phoneNumFormat1 = "01\\d-\\d{7}";
                String phoneNumFormat2 = "01\\d-\\d{8}";

                System.out.println("Current Phone Number: " + staffList.get(foundIndex).getPhoneNum());
                System.out.print("Enter new Phone Number(01X-XXXXXXX/ 01X-XXXXXXXX): ");
                String phoneNum = scanner.nextLine();

                while (!phoneNum.matches(phoneNumFormat1) && !phoneNum.matches(phoneNumFormat2)) {
                    System.out.println("Invalid input!!!");
                    System.out.print("Enter new Phone Number(01X-XXXXXXX/ 01X-XXXXXXXX): ");
                    phoneNum = scanner.nextLine();
                }
                break;
            case 3:
                System.out.println(
                        "\n------------------------------------------------------------------------------------------");
                System.out.println("Current Address: " + staffList.get(foundIndex).getAddress());
                System.out.print("Enter New Address: ");
                staffList.get(foundIndex).setAddress(scanner.nextLine());
                break;
            case 4:
                String emailFormat = "^(.+)@(.+)$";

                System.out.println(
                        "\n------------------------------------------------------------------------------------------");
                System.out.println("Current Email: " + staffList.get(foundIndex).getEmail());
                System.out.print("Enter new Email (must include @): ");
                String email = scanner.nextLine();

                while (!email.matches(emailFormat)) {
                    System.out.println("Invalid input!!!");
                    System.out.print("Enter new Email (must include @): ");
                    email = scanner.nextLine();
                }
                break;
            case 5:
                System.out.println(
                        "\n------------------------------------------------------------------------------------------");
                System.out.print("Enter Your Current Password: ");
                String currentPassword = scanner.nextLine();
                if (staffList.get(foundIndex).getPassword().equals(currentPassword)) {
                    System.out.print("Enter New Password: ");
                    String newPassword = scanner.nextLine();

                    System.out.print("Confirm New Password: ");
                    String confirmPassword = scanner.nextLine();
                    if (newPassword.equals(confirmPassword)) {
                        staffList.get(foundIndex).setPassword(newPassword);
                        System.out.println("Password updated successfully!!!");
                    } else {
                        System.out.println("Passwords do not match!!!");
                    }
                } else {
                    System.out.println("Incorrect current password!!!");
                }
                break;
            case 6:
                System.out.println(
                        "\n------------------------------------------------------------------------------------------");
                System.out.println("What is your childhood nickname?");
                System.out.print("Enter your current Security Question answer: ");
                String answer = scanner.nextLine();
                if (answer.equals(staffList.get(foundIndex).getAnswer())) {
                    System.out.print("Enter your new Security Question answer: ");
                    staffList.get(foundIndex).setAnswer(scanner.nextLine());
                    break;
                } else {
                    System.out.println("Wrong answer!!!");
                    resetPassword();
                }
            default:
                System.out.println("Invalid choice!!!");
        }

        writeArrayToFile(staffList);

        System.out.print("Back to (admin/staff) menu (Y = yes)?: ");
        char confirmation = scanner.nextLine().charAt(0);

        if (Character.toUpperCase(confirmation) == 'Y') {
            if (staffList.get(foundIndex).getStaffType().equals("staff")) {
                menu(foundIndex);
            } else {
                adminMenu(foundIndex);
            }
        }

    }

    public static void modifyStaff(int foundIndex) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Staff> staffList = readStaffToArray();

        System.out.print("\nEnter staff ID to modify (9999 to exit): ");
        String staffId = scanner.nextLine();

        if (staffId.equals("9999")) {
            adminMenu(foundIndex); // Assuming this method exists in your system
            return; // Exit from the function after calling adminMenu
        }

        boolean found = false;

        // Search for the staff member by ID
        for (int i = 0; i < staffList.size(); i++) {
            if (staffId.equals(staffList.get(i).getPersonId())) {
                foundIndex = i;
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Staff not found!!!");
            modifyStaff(foundIndex); // Be careful to avoid infinite recursion here
        } else {
            int choice = -1;
            boolean validChoice = false;

            while (!validChoice) {
                try {
                    System.out.println(
                            "\n+=========================================================================================+");
                    System.out.println(
                            "|                __  __           _ _  __         ____  _         __  __                  |");
                    System.out.println(
                            "|               |  \\/  | ___   __| (_)/ _|_   _  / ___|| |_ __ _ / _|/ _|                 |");
                    System.out.println(
                            "|               | |\\/| |/ _ \\ / _ \\| | |_| | | | \\___ \\| __/ _` | |_| |_                  |");
                    System.out.println(
                            "|               | |  | | (_) | (_| | |  _| |_| |  ___) | || (_| |  _|  _|                 |");
                    System.out.println(
                            "|               |_|  |_\\___/ \\__,_|_|_|  \\__, | |____/ \\__\\__,_|_| |_|                    |");
                    System.out.println(
                            "|                ___        __            |___/           _   _                           |");
                    System.out.println(
                            "|               |_ _|_ __  / _| ___  _ __ _ __ ___   __ _| |_(_) ___  _ __                |");
                    System.out.println(
                            "|                | || '_ \\| |_ / _ \\| '__| '_ ` _ \\ / _` | __| |/ _ \\| '_ \\               |");
                    System.out.println(
                            "|                | || | | |  _| (_) | |  | | | | | | (_| | |_| | (_) | | | |              |");
                    System.out.println(
                            "|               |___|_| |_|_|  \\___/|_|  |_| |_| |_|\\__,_|\\__|_|\\___/|_| |_|              |");
                    System.out.println(
                            "|                                                                                         |");
                    System.out.println(
                            "+=========================================================================================+");
                    System.out.println("\nSelect the information to modify:");
                    System.out.println("1. Name");
                    System.out.println("2. Phone Number");
                    System.out.println("3. Address");
                    System.out.println("4. Email");
                    System.out.println("5. Position");
                    System.out.println("6. Salary");
                    System.out.println("7. Staff Type");
                    System.out.println("8. Exit");
                    System.out.println(
                            "------------------------------------------------------------------------------------------");
                    System.out.print("Enter Choice: ");
                    choice = scanner.nextInt();
                    validChoice = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Clear the invalid input
                }
            }

            scanner.nextLine(); // Consume the newline character after `nextInt()`

            switch (choice) {
                case 1:
                    System.out.println(
                            "\n------------------------------------------------------------------------------------------");
                    System.out.println("Current Staff name: " + staffList.get(foundIndex).getName());
                    String name;
                    boolean isValidName = false;

                    while (!isValidName) {
                        System.out.print("\nEnter Staff Name: ");
                        name = scanner.nextLine(); // Assign input to the name variable

                        // Check if name contains any digits
                        if (name.matches(".*\\d.*")) {
                            System.out.println("Error: Name cannot contain numbers.Please enter a valid name!!!");
                        } else if (name.length() > 30) {
                            System.out.println(
                                    "Error: Name cannot be more than 30 characters.Please enter a shorter name!!!");
                        } else {
                            // If both checks pass, set the valid name to the staff object
                            staffList.get(foundIndex).setName(name);
                            System.out.println("Name updated successfully!!!");
                            isValidName = true;
                        }
                    }
                    break;
                case 2:
                    System.out.println(
                            "\n------------------------------------------------------------------------------------------");
                    System.out.println("Current Phone Number: " + staffList.get(foundIndex).getPhoneNum());
                    String phoneNum;
                    String phoneNumFormat1 = "01\\d-\\d{7}";
                    String phoneNumFormat2 = "01\\d-\\d{8}";
                    do {
                        System.out.print("Enter new Phone Number (01X-XXXXXXX/ 01X-XXXXXXXX): ");
                        phoneNum = scanner.nextLine();
                        if (!phoneNum.matches(phoneNumFormat1) && !phoneNum.matches(phoneNumFormat2)) {
                            System.out.println("Invalid input!!!");
                        }
                    } while (!phoneNum.matches(phoneNumFormat1) && !phoneNum.matches(phoneNumFormat2));

                    staffList.get(foundIndex).setPhoneNum(phoneNum); // Set the validated phone number
                    break;

                case 3:
                    System.out.println(
                            "\n------------------------------------------------------------------------------------------");
                    System.out.println("Current Address: " + staffList.get(foundIndex).getAddress());
                    System.out.print("Enter new Address: ");
                    staffList.get(foundIndex).setAddress(scanner.nextLine());
                    break;

                case 4:
                    System.out.println(
                            "\n------------------------------------------------------------------------------------------");
                    System.out.println("Current Email: " + staffList.get(foundIndex).getEmail());
                    String email;
                    String emailFormat = "^(.+)@(.+)$";
                    do {
                        System.out.print("Enter new Email (must include @): ");
                        email = scanner.nextLine();
                        if (!email.matches(emailFormat)) {
                            System.out.println("Invalid email format!!!");
                        }
                    } while (!email.matches(emailFormat));

                    staffList.get(foundIndex).setEmail(email); // Set the validated email
                    break;

                case 5:
                    System.out.println(
                            "\n------------------------------------------------------------------------------------------");
                    System.out.print("Enter new Staff Position: ");
                    String position;
                    String positionFormat = "^[a-zA-Z ]+$";
                    do {
                        position = scanner.nextLine();
                        if (!position.matches(positionFormat)) {
                            System.out.println("Invalid position format!!!");
                        }
                    } while (!position.matches(positionFormat));

                    staffList.get(foundIndex).setPosition(position); // Set the validated position
                    break;

                case 6:
                    double salary = 0.0;
                    boolean validInput = false;
                    while (!validInput) {
                        System.out.println(
                                "\n------------------------------------------------------------------------------------------");
                        System.out.print("Enter new Staff Salary: RM");
                        try {
                            salary = scanner.nextDouble();
                            validInput = true;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input.Please enter a valid salary!!!");
                            scanner.next(); // Clear the invalid input
                        }
                    }
                    staffList.get(foundIndex).setSalary(salary); // Set the validated salary
                    scanner.nextLine(); // Consume leftover newline character
                    break;

                case 7:
                    System.out.println(
                            "\n------------------------------------------------------------------------------------------");
                    System.out.print("Enter new Staff Type (staff/admin): ");
                    String staffType;
                    do {
                        staffType = scanner.nextLine().toLowerCase();
                        if (!staffType.equals("staff") && !staffType.equals("admin")) {
                            System.out.println("Invalid input! Please enter 'staff' or 'admin'.");
                        }
                    } while (!staffType.equals("staff") && !staffType.equals("admin"));

                    staffList.get(foundIndex).setStaffType(staffType); // Set the validated staff type
                    break;
                case 8:
                    adminMenu(foundIndex);
                default:
                    System.out.println("Invalid choice!!!");
            }

            // Save updated staff list to file
            writeArrayToFile(staffList);
        }

        System.out.print("\nDo you want to modify more Staff? (y = Yes): ");
        char confirmation = scanner.nextLine().charAt(0);

        if (Character.toUpperCase(confirmation) == 'Y') {
            modifyStaff(foundIndex);
        } else {
            adminMenu(foundIndex);
        }
    }

    public static void addStaff(int foundIndex) {
        Scanner scanner = new Scanner(System.in);

        String staffId = generateNewStaffId();
        String name = "";

        System.out.println(
                "\n+=========================================================================================+");
        System.out
                .println("|                         _       _     _   ____  _         __  __                        |");
        System.out.println(
                "|                        / \\   __| | __| | / ___|| |_ __ _ / _|/ _|                       |");
        System.out.println(
                "|                       / _ \\ / _` |/ _` | \\___ \\| __/ _` | |_| |_                        |");
        System.out.println(
                "|                      / ___ \\ (_| | (_| |  ___) | || (_| |  _|  _|                       |");
        System.out.println(
                "|                     /_/   \\_\\__,_|\\__,_| |____/ \\__\\__,_|_| |_|                         |");
        System.out
                .println("|                                                                                         |");
        System.out
                .println("+=========================================================================================+");

        while (true) {
            System.out.print("Enter Staff Name: ");
            name = scanner.nextLine();

            // Check if name contains any digits
            if (name.matches(".*\\d.*")) {
                System.out.println("Name cannot contain numbers. Please enter a valid name!!!");
            }
            // Check if the name exceeds 30 characters
            else if (name.length() > 30) {
                System.out.println("Name cannot be more than 30 characters. Please enter a shorter name!!!\n");
            }
            // If both checks pass, break the loop
            else {
                break;
            }
        }

        String phoneNumFormat1 = "01\\d-\\d{7}";
        String phoneNumFormat2 = "01\\d-\\d{8}";

        System.out.print("Enter Staff Phone Number (01X-XXXXXXX/ 01X-XXXXXXXX): ");
        String phoneNum = scanner.nextLine();

        while (!phoneNum.matches(phoneNumFormat1) && !phoneNum.matches(phoneNumFormat2)) {
            System.out.println("Invalid input!!!");
            System.out.print("Enter Staff Phone Number (01X-XXXXXXX/ 01X-XXXXXXXX): ");
            phoneNum = scanner.nextLine();
        }

        System.out.print("\n");
        System.out.print("Enter Staff Address: ");
        String address = scanner.nextLine();

        String emailFormat = "^(.+)@(.+)$";

        System.out.print("Enter Staff Email (must include @): ");
        String email = scanner.nextLine();

        while (!email.matches(emailFormat)) {
            System.out.println("Invalid input!!!");
            System.out.print("Enter Staff Email (must include @): ");
            email = scanner.nextLine();
        }

        String positionFormat = "^[a-zA-Z ]+$";

        System.out.print("\n");
        System.out.print("Enter Staff Position: ");
        String position = scanner.nextLine();

        while (!position.matches(positionFormat)) {
            System.out.println("Invalid input!!!");
            System.out.print("Enter Staff Position: ");
            position = scanner.nextLine();
        }

        double salary = 0.0;
        boolean validInput = false;

        System.out.print("\n");
        while (!validInput) {
            System.out.print("Enter Staff Salary: RM");
            try {
                salary = scanner.nextDouble();
                scanner.nextLine(); // Consume newline left-over
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.Please enter a valid number for salary!!!");
                System.out.print("\n");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        System.out.print("\n");
        System.out.print("Enter Staff Type (staff/admin): ");
        String staffType = scanner.nextLine();

        staffType = staffType.toLowerCase();

        while (!staffType.equals("staff") && !staffType.equals("admin")) {
            System.out.println("Invalid input!!!");
            System.out.print("Enter Staff Type (staff/admin): ");
            staffType = scanner.nextLine();
            staffType = staffType.toLowerCase();
        }

        System.out.println(
                "--------------------------------------------------------------------------------------------");
        System.out.print("Enter Password: ");
        String password1 = scanner.nextLine();

        System.out.print("Confirm Password: ");
        String password2 = scanner.nextLine();

        // Check if both passwords match
        while (!password1.equals(password2)) {
            System.out.println("Passwords do not match.Please try again!!!");

            // Re-enter passwords
            System.out.print("Enter Password: ");
            password1 = scanner.nextLine();

            System.out.print("Confirm Password: ");
            password2 = scanner.nextLine();
        }

        String answer;

        while (true) {
            System.out.println("Enter answer for security question.");
            System.out.print("Childhood nickname: ");
            answer = scanner.nextLine();

            if (answer.length() < 30) {
                break; // Exit the loop if the input is valid
            } else {
                System.out.println("The answer cannot more than 30 characters.Please try again!!!");
            }
        }

        Staff staff = new Staff(staffId, name, phoneNum, address, email, position, salary, staffType, password1,
                answer);

        System.out.print("Are you confirm to add this staff? (Y): ");
        char message = scanner.nextLine().charAt(0);

        if (Character.toUpperCase(message) == 'Y') {

            System.out.println(
                    "\n+=========================================================================================+");
            System.out.println(
                    "|                ____  _         __  __   ____       _        _ _                         |");
            System.out.println(
                    "|               / ___|| |_ __ _ / _|/ _| |  _ \\  ___| |_ __ _(_) |___                     |");
            System.out.println(
                    "|               \\___ \\| __/ _` | |_| |_  | | | |/ _ \\ __/ _` | | / __|                    |");
            System.out.println(
                    "|                 ___) | || (_| |  _|  _| | |_| |  __/ || (_| | | \\__ \\                   |");
            System.out.println(
                    "|               |____/ \\__\\__,_|_| |_|   |____/ \\___|\\__\\__,_|_|_|___/                    |");
            System.out.println(
                    "|                                                                                         |");
            System.out.println(
                    "+=========================================================================================+");
            System.out.println("\nStaff Id: " + staff.getPersonId());
            System.out.println("Staff Name: " + staff.getName());
            System.out.println("Staff Phone Number: " + staff.getPhoneNum());
            System.out.println("Staff Address: " + staff.getAddress());
            System.out.println("Staff Email: " + staff.getEmail());
            System.out.println("Staff Position: " + staff.getPosition());
            System.out.println("Staff Salary: " + staff.getSalary());
            System.out.println("Staff Type: " + staff.getStaffType());

            try {
                FileWriter staffFile = new FileWriter("staff.txt", true);
                staffFile.write(staff.toString());
                staffFile.close();
                System.out.println("Staff Added Successfully!!!");
                adminMenu(foundIndex);
            } catch (IOException e) {
                System.out.println("File Opening error!!!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Staff added not successfully!!!");
            addStaff(foundIndex);
        }

    }

    public static void cancelAccount(int foundIndex) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Staff> staffList = readStaffToArray();

        System.out.println(
                "\n+=========================================================================================+");
        System.out
                .println("|            ____                     _      _                             _              |");
        System.out.println(
                "|           / ___|__ _ _ __   ___ ___| |    / \\   ___ ___ ___  _   _ _ __ | |_            |");
        System.out.println(
                "|          | |   / _` | '_ \\ / __/ _ \\ |   / _ \\ / __/ __/ _ \\| | | | '_ \\| __|           |");
        System.out.println(
                "|          | |__| (_| | | | | (_|  __/ |  / ___ \\ (_| (_| (_) | |_| | | | | |_            |");
        System.out.println(
                "|           \\____\\__,_|_| |_|\\___\\___|_| /_/   \\_\\___\\___\\___/ \\__,_|_| |_|\\__|           |");
        System.out
                .println("|                                                                                         |");
        System.out
                .println("+=========================================================================================+");

        System.out.print("Confirm to cancel (Y = yes)?: ");
        System.out.println("exit...");
        char confirmation = scanner.nextLine().charAt(0);

        if (Character.toUpperCase(confirmation) == 'Y') {
            staffList.remove(foundIndex);
            writeArrayToFile(staffList);
        } else {
            System.out.print("Back to (staff/admin) menu (Y = yes)?: ");
            confirmation = scanner.nextLine().charAt(0);

            if (Character.toUpperCase(confirmation) == 'Y') {
                if (staffList.get(foundIndex).getStaffType().equals("staff")) {
                    menu(foundIndex);
                } else {
                    adminMenu(foundIndex);
                }
            }
        }

    }

    public static void removeStaff(int staffIndex) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Staff> staffList = readStaffToArray();
        System.out.println(
                "\n+=========================================================================================+");
        System.out
                .println("|               ____                                 ____  _         __  __               |");
        System.out.println(
                "|              |  _ \\ ___ _ __ ___   _____   _____  / ___|| |_ __ _ / _|/ _|              |");
        System.out.println(
                "|              | |_) / _ \\ '_ ` _ \\ / _ \\ \\ / / _ \\ \\___ \\| __/ _` | |_| |_               |");
        System.out.println(
                "|              |  _ <  __/ | | | | | (_) \\ V /  __/  ___) | || (_| |  _|  _|              |");
        System.out.println(
                "|              |_| \\_\\___|_| |_| |_|\\___/ \\_/ \\___| |____/ \\__\\__,_|_| |_|                |");
        System.out
                .println("|                                                                                         |");
        System.out
                .println("+=========================================================================================+");

        System.out.print("Enter Staff ID to remove (9999 to exit): ");
        String staffId = scanner.nextLine();

        if (staffId.equals("9999")) {
            adminMenu(staffIndex);
        }

        int indexFound = -1;
        boolean found = false;

        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getPersonId().equals(staffId)) {
                indexFound = i;
                found = true;
                System.out.println("Staff ID: " + staffList.get(i).getPersonId());
                System.out.println("Staff Name: " + staffList.get(i).getName());
                System.out.println("Staff Phone Number: " + staffList.get(i).getPhoneNum());
                System.out.println("Staff Address: " + staffList.get(i).getAddress());
                System.out.println("Staff Email: " + staffList.get(i).getEmail());
                break;
            }
        }

        if (!found) {
            System.out.println("Staff not found...");
            removeStaff(staffIndex);
        }

        System.out.print("\nSure to remove this staff (Y = yes) ?: ");
        String confirmation = scanner.nextLine();

        if (confirmation.equals("Y") || confirmation.equals("y")) {
            System.out.println("Staff removed successfully!!!");
            staffList.remove(indexFound);
        } else {
            System.out.println("Staff removed unsuccessfully!!!");
        }

        writeArrayToFile(staffList);

        System.out.print("Back to main (Y = yes) ? ");
        confirmation = scanner.nextLine();
        if (confirmation.equals("Y") || confirmation.equals("y")) {
            adminMenu(staffIndex);
        } else {
            removeStaff(staffIndex);
        }

    }

    public static void searchStaff(int temp) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Staff> staffList = readStaffToArray();

        System.out.println(
                "\n+=========================================================================================+");
        System.out
                .println("|                             ____                      _                                 |");
        System.out
                .println("|                            / ___|  ___  __ _ _ __ ___| |__                              |");
        System.out.println(
                "|                             \\___ \\ / _ \\/ _` | '__/ __| '_ \\                            |");
        System.out
                .println("|                             ___) |  __/ (_| | | | (__| | | |                            |");
        System.out.println(
                "|                            |____/ \\___|\\__,_|_|  \\___|_| |_|                            |");
        System.out
                .println("|                                                                                         |");
        System.out
                .println("+=========================================================================================+");
        System.out.print("\nEnter Staff ID to search (9999 to exit) : ");
        String staffId = scanner.nextLine();

        if (staffId.equals("9999")) {
            // menu();
        }

        boolean found = false;
        int foundIndex = -1;

        for (int i = 0; i < staffList.size(); i++) {

            if (staffId.equals(staffList.get(i).getPersonId())) {
                found = true;
                foundIndex = i;
            }

        }

        if (found) {
            System.out.println("Staff ID : " + staffList.get(foundIndex).getPersonId());
            System.out.println("Name : " + staffList.get(foundIndex).getName());
            System.out.println("Number : " + staffList.get(foundIndex).getPhoneNum());
            System.out.println("Email : " + staffList.get(foundIndex).getEmail());
            System.out.println("Address : " + staffList.get(foundIndex).getAddress());
            System.out.println("Position : " + staffList.get(foundIndex).getPosition());
            System.out.println("Salary : " + staffList.get(foundIndex).getSalary());
            System.out.println("Staff Type : " + staffList.get(foundIndex).getStaffType());
        } else {
            System.out.println("Staff Not Exist");
        }

        System.out.print("\nDo you want to search more staff (Y = yes)?: ");
        char confirmation = scanner.nextLine().charAt(0);

        if (Character.toUpperCase(confirmation) == 'Y') {
            searchStaff(temp);
        } else {
            adminMenu(temp);
        }

    }

    public static void displayStaff(ArrayList<Staff> staffList, int indexFound) {
        Scanner scanner = new Scanner(System.in);

        if (staffList.isEmpty()) {
            System.out.println("No staff records available.");
            return;
        }

        System.out.println(
                "\n+=========================================================================================+");
        System.out
                .println("|                         ____  _         __  __   _     _     _                          |");
        System.out
                .println("|                       / ___|| |_ __ _ / _|/ _| | |   (_)___| |_                         |");
        System.out.println(
                "|                       \\___ \\| __/ _` | |_| |_  | |   | / __| __|                        |");
        System.out.println(
                "|                        ___) | || (_| |  _|  _| | |___| \\__ \\ |_                         |");
        System.out.println(
                "|                       |____/ \\__\\__,_|_| |_|   |_____|_|___/\\__|                        |");
        System.out
                .println("|                                                                                         |");
        System.out.println(
                "+==========================================================================================+");
        System.out.println(
                "+------------+----------------------+-----------------+------------------------------------------------+----------------------------+-----------------+-----------------+-----------------+");
        System.out.printf("| %-10s | %-20s | %-15s | %-45s  | %-26s | %-15s | %-15s | %-15s |%n",
                "Staff ID", "Name", "Phone Number", "Address", "Email", "Position", "Salary", "Staff Type");
        System.out.println(
                "+------------+----------------------+-----------------+------------------------------------------------+----------------------------+-----------------+-----------------+-----------------+");

        for (Staff staff : staffList) {
            System.out.printf("| %-10s | %-20s | %-15s | %-45s  | %-26s | %-15s | %-15s | %-15s |%n",
                    staff.getPersonId(),
                    staff.getName(),
                    staff.getPhoneNum(),
                    staff.getAddress(),
                    staff.getEmail(),
                    staff.getPosition(),
                    staff.getSalary(),
                    staff.getStaffType());
        }

        System.out.println(
                "+------------+----------------------+-----------------+------------------------------------------------+----------------------------+-----------------+-----------------+-----------------+");
        System.out.print("Back to Admin menu (Y = yes): ");
        char choice = scanner.nextLine().charAt(0);

        while (Character.toUpperCase(choice) != 'Y') {

            System.out.print("Back to Admin menu (Y = yes): ");
            choice = scanner.nextLine().charAt(0);

        }

        if (Character.toUpperCase(choice) == 'Y') {
            adminMenu(indexFound);
        }
    }

    public static void resetPassword() {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Staff> staffList = readStaffToArray();

        boolean found = false;
        int foundIndex = -1;

        System.out.println(
                "\n                         _____                            _____                                                                                 ");
        System.out.println(
                "___________         _____\\    \\            _____     _____\\    \\   ________    ________                                                         ");
        System.out.println(
                "\\          \\       /    / |    |      _____\\    \\   /    / |    | /        \\  /        \\                                                        ");
        System.out.println(
                " \\    /\\    \\     /    /  /___/|     /    / \\    | /    /  /___/||\\         \\/         /|                                                       ");
        System.out.println(
                "  |   \\_\\    |   |    |__ |___|/    |    |  /___/||    |__ |___|/| \\            /\\____/ |                                                       ");
        System.out.println(
                "  |      ___/    |       \\       ____\\    \\ |   |||       \\      |  \\______\\/   \\     | |                                                       ");
        System.out.println(
                "  |      \\  ____ |     __/ __   /    /\\    \\|___|/|     __/ __    \\ |      | \\   \\____|/                                                        ");
        System.out.println(
                " /     /\\ \\/    \\|\\    \\  /  \\ |    |/ \\    \\     |\\    \\  /  \\    \\|______|  \\   \\                                                             ");
        System.out.println(
                "/_____/ |\\______|| \\____\\/    ||\\____\\ /____/|    | \\____\\/    |            \\  \\___\\                                                            ");
        System.out.println(
                "|     | | |     || |    |____/|| |   ||    | |    | |    |____/|             \\ |   |                                                            ");
        System.out.println(
                "|_____|/ \\|_____| \\|____|   | | \\|___||____|/      \\|____|   | |              \\|___|                                                            ");
        System.out.print("Enter Staff ID (9999 to exit): ");
        String staffId = scanner.nextLine();

        for (int i = 0; i < staffList.size(); i++) {
            if (staffId.equals(staffList.get(i).getPersonId())) {
                found = true;
                foundIndex = i;
            }
        }

        if (!found) {
            System.out.println("\n\nStaff Id not found");
            resetPassword();
        }

        System.out.println("Security Question");
        System.out.println(
                "\n------------------------------------------------------------------------------------------");
        System.out.print("Enter your childhood nickname : ");
        String answer = scanner.nextLine();

        if (answer.equals(staffList.get(foundIndex).getAnswer())) {
            System.out.print("Enter new password : ");
            String password = scanner.nextLine();
            staffList.get(foundIndex).setPassword(password);
            System.out.println("Passowrd changed successfully!!!");
        } else {
            System.out.println("Wrong answer!!!");
            resetPassword();
        }

        writeArrayToFile(staffList);

    }

    public static void staffTypeRecognize(int foundIndex) {

        ArrayList<Staff> staffList = readStaffToArray();

        if (staffList.get(foundIndex).getStaffType().equals("staff")) {
            menu(foundIndex);
        } else {
            adminMenu(foundIndex);
        }

    }

}
