import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BranchManager {

    private static Map<String, Branch> branches;
    private static Scanner scanner;
    private static Map<String, Integer> idCounters;

    static {
        branches = new HashMap<>();
        scanner = new Scanner(System.in);
        idCounters = new HashMap<>();
    }

    public BranchManager() {
        loadBranches();
    }

    public static void displayMenu(int staffIndex) {
        while (true) {
            System.out.println("+=========================================================================+");
            System.out.println("|                    ____                       _                         |");
            System.out.println("|                   | __ ) _ __ __ _ _ __   ___| |__                      |");
            System.out.println("|                   |  _ \\| '__/ _` | '_ \\ / __| '_ \\                     |");
            System.out.println("|                   | |_) | | | (_| | | | | (__| | | |                    |");
            System.out.println("|                   |____/|_|  \\__,_|_| |_|\\___|_| |_|                    |");
            System.out.println("+=========================================================================+");

            System.out.println("\nBranch Management System");
            System.out.println("1. Add New Branch");
            System.out.println("2. Update Branch Details");
            System.out.println("3. View Branch Details");
            System.out.println("4. Delete Branch");
            System.out.println("5. Return");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addBranch();
                    break;
                case 2:
                    updateBranch();
                    break;
                case 3:
                    showDetails();
                    break;
                case 4:
                    deleteBranch();
                    break;
                case 5:
                    StaffMain.staffTypeRecognize(staffIndex);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }

    private static void addBranch() {
        System.out
                .println("\n+=======================================================================================+");
        System.out.println("                        _       _     _ ");
        System.out.println("                       / \\   __| | __| |");
        System.out.println("                      / _ \\ / _` |/ _` |");
        System.out.println("                     / ___ \\ (_| | (_| |");
        System.out.println("                    /_/   \\_\\__,_|\\__,_|");
        System.out.println("+=======================================================================================+");
        System.out.print("Enter Branch Name: ");
        String name = scanner.nextLine();
        String id = generateId(name);
        String phoneNum;

        // Validate phone number
        while (true) {
            System.out.print("Enter Branch Phone Number: ");
            phoneNum = scanner.nextLine();

            // Check phone number validity
            if (phoneNum.length() > 11) {
                System.out.println("Phone number is too long!");
            } else if (phoneNum.startsWith("011") && phoneNum.length() == 11) {
                break; // Valid phone number starting with 011
            } else if ((phoneNum.startsWith("01") || phoneNum.startsWith("03")) && phoneNum.length() == 10) {
                break; // Valid phone number starting with 01 or 03
            } else {
                System.out.println("Invalid phone number! Please ensure the phone number follows the format:");
                System.out.println("- Starts with 011");
                System.out.println("- Starts with 01 or 03");
            }
        }

        System.out.print("Enter Branch Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Branch Manager Name: ");
        String managerName = scanner.nextLine();

        int employeeCount = -1;
        // Validate employee count
        while (true) {
            System.out.print("Enter Branch Employees Count: ");
            if (scanner.hasNextInt()) {
                employeeCount = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Check if employee count is positive and does not exceed 60
                if (employeeCount > 0 && employeeCount <= 60) {
                    break; // Valid employee count
                } else {
                    System.out.println("Invalid employee count! Please enter a positive number not exceeding 60.");
                }
            } else {
                System.out.println("Invalid input! Please enter a numeric value.");
                scanner.next(); // Clear invalid input
            }
        }

        Branch branch = new Branch(id, name, phoneNum, address, employeeCount, managerName);
        branches.put(id, branch);
        System.out.println("Branch added with ID: " + id);

        // Save branches to file
        saveBranches();
    }

    private static void updateBranch() {
        System.out
                .println("\n+=======================================================================================+");
        System.out.println("                     _   _           _       _       ");
        System.out.println("                    | | | |_ __   __| | __ _| |_ ___ ");
        System.out.println("                    | | | | '_ \\ / _` |/ _` | __/ _ \\");
        System.out.println("                    | |_| | |_) | (_| | (_| | ||  __/");
        System.out.println("                     \\___/| .__/ \\__,_|\\__,_|\\__\\___|");
        System.out.println("                          |_|                        ");
        System.out.println("+=======================================================================================+");
        System.out.print("Enter Branch ID: ");
        String id = scanner.nextLine();
        Branch branch = branches.get(id);

        if (branch != null) {
            // Update Branch Name
            System.out.print("Enter new Name for Branch (current: " + branch.getName() + "): ");
            String name = scanner.nextLine();

            // Update Branch Phone Number with validation
            String phoneNum;
            while (true) {
                System.out.print("Enter new Phone Number for Branch (current: " + branch.getPhoneNum() + "): ");
                phoneNum = scanner.nextLine();

                // Check phone number validity
                if (phoneNum.length() > 11) {
                    System.out.println("Phone number is too long!");
                } else if (phoneNum.startsWith("011") && phoneNum.length() == 11) {
                    break; // Valid phone number starting with 011
                } else if ((phoneNum.startsWith("01") || phoneNum.startsWith("03")) && phoneNum.length() == 10) {
                    break; // Valid phone number starting with 01 or 03
                } else {
                    System.out.println("Invalid phone number! Please ensure the phone number follows the format:");
                    System.out.println("- Starts with 011");
                    System.out.println("- Starts with 01 or 03");
                }
            }

            // Update Branch Address
            System.out.print("Enter new Address for Branch (current: " + branch.getAddress() + "): ");
            String address = scanner.nextLine();

            // Update Branch Manager Name
            System.out.print("Enter new Manager for Branch (current: " + branch.getManagerName() + "): ");
            String managerName = scanner.nextLine();

            // Update Branch Employee Count with validation
            int employeeCount = -1;
            while (true) {
                System.out.print("Enter new Employee Count (current: " + branch.getEmployeeCount() + "): ");
                if (scanner.hasNextInt()) {
                    employeeCount = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Check if employee count is positive and does not exceed 60
                    if (employeeCount > 0 && employeeCount <= 60) {
                        break; // Valid employee count
                    } else {
                        System.out.println("Invalid employee count! Please enter a positive number not exceeding 60.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a numeric value.");
                    scanner.next(); // Clear invalid input
                }
            }

            // Update branch information
            branch.updateBranch(name, phoneNum, address, employeeCount, managerName);
            System.out.println("Branch updated.");

            // Save branches to file
            saveBranches();
        } else {
            System.out.println("Branch ID not found.");
        }
    }

    private static void showDetails() {
        System.out.println("\n+=======================================================================================+");
        System.out.println("                     ____                      _     ");
        System.out.println("                    / ___|  ___  __ _ _ __ ___| |__  ");
        System.out.println("                    \\___ \\ / _ \\/ _` | '__/ __| '_ \\ ");
        System.out.println("                     ___) |  __/ (_| | | | (__| | | |");
        System.out.println("                    |____/ \\___|\\__,_|_|  \\___|_| |_|");
        System.out.println("+=======================================================================================+");
        loadBranches(); // Reload branches from file to ensure the latest data is in memory
        
        System.out.print("Enter Branch ID: ");
        String id = scanner.nextLine();
        System.out.println("Looking for Branch ID: " + id); // Debug line
        Branch branch = branches.get(id);
        
        if (branch != null) {
            displayDetails(branch);
        } else {
            System.out.println("Branch ID not found.");
        }
    }
    
    
    
    

    private static void deleteBranch() {
        System.out.println("\n+=======================================================================================+");
        System.out.println("                     ____       _      _       ");
        System.out.println("                    |  _ \\  ___| | ___| |_ ___ ");

        System.out.println("                    | | | |/ _ \\ |/ _ \\ __/ _ \\");
        System.out.println("                    | |_| |  __/ |  __/ ||  __/");
        System.out.println("                    |____/ \\___|_|\\___|\\__\\___|");
        System.out.println("+=======================================================================================+");
        System.out.print("Enter Branch ID: ");
        String id = scanner.nextLine().trim(); // Trim spaces around the input
        Branch branch = branches.get(id);
    
        if (branch != null) {
            // Remove the branch from the in-memory map
            branches.remove(id);
            
            // Rewrite the file with the remaining branches, sorted
            saveBranches(); // Now this will handle sorting
    
            System.out.println("Branch with ID " + id + " has been deleted.");
        } else {
            System.out.println("Branch ID not found.");
        }
    }
    

    private static String generateId(String name) {
        String prefix = "br-";
        String lastId = prefix + "0000"; // Default start if file is empty

        try (Scanner scanner = new Scanner(new File("branches.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(","); // Assuming the ID is the first field in a CSV format
                if (split.length > 0) {
                    lastId = split[0]; // Extract the branch ID (first field)
                }
            }
        } catch (IOException e) { // File not found or does not exist
            System.out.println("Error reading branches file.");
            e.printStackTrace(); // Use for debugging: locates the error
        }

        // Check if lastId is empty or not correctly formatted
        if (lastId.length() < 5 || !lastId.startsWith(prefix)) {
            return prefix + "0001"; // Return default ID if lastId is invalid
        }

        // Remove the prefix and parse the number
        String numberPart = lastId.substring(prefix.length()); // Remove the 'br-' prefix
        int newIdNumber = Integer.parseInt(numberPart) + 1; // Auto increment the number part

        // Format the new ID with the prefix
        return String.format("%s%04d", prefix, newIdNumber);
    }


    private static void displayDetails(Branch branch) {
        System.out
                .println("\n+=======================================================================================+");
        System.out.println("                     ____  _           _             ");
        System.out.println("                    |  _ \\(_)___ _ __ | | __ _ _   _ ");
        System.out.println("                    | | | | / __| '_ \\| |/ _` | | | |");
        System.out.println("                    | |_| | \\__ \\ |_) | | (_| | |_| |");
        System.out.println("                    |____/|_|___/ .__/|_|\\__,_|\\__, |");
        System.out.println("                                |_|            |___/ ");
        System.out.println("+=======================================================================================+");
        System.out.println("Branch Details:");
        System.out.println("ID: " + branch.getBranchId());
        System.out.println("Name: " + branch.getName());
        System.out.println("Phone Number: " + branch.getPhoneNum());
        System.out.println("Address: " + branch.getAddress());
        System.out.println("Manager Name: " + branch.getManagerName());
        System.out.println("Employee Count: " + branch.getEmployeeCount());
    }

    private static void loadBranches() {
        try (BufferedReader br = new BufferedReader(new FileReader("branches.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0];
                    Branch branch = new Branch(id, parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5]);
                    branches.put(id, branch);
                    // System.out.println("Loaded branch: " + branch.getName()); // Debug line removed
                } else {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No branches file found. Starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    private static void saveBranches() {
    List<Branch> branchList = new ArrayList<>(branches.values());
    
    // Sort the list based on branch IDs
    branchList.sort(Comparator.comparing(Branch::getBranchId));
    
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("branches.txt"))) { // Overwrite mode
        for (Branch branch : branchList) {
            bw.write(branch.getBranchId() + "," + branch.getName() + "," +
                      branch.getPhoneNum() + "," + branch.getAddress() + "," +
                      branch.getEmployeeCount() + "," + branch.getManagerName());
            bw.newLine(); // Adds a newline after each record
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    
    
    public static void main(String[] args) {
        // Example usage
        String branchId = generateId("SomeBranchName");
        System.out.println("Generated Branch ID: " + branchId);
    }

}
