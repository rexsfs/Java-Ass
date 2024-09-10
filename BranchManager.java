import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BranchManager {

    private Map<String, Branch> branches;
    private Scanner scanner;
    private Map<String, Integer> idCounters;

    public BranchManager() {
        branches = new HashMap<>();
        scanner = new Scanner(System.in);
        idCounters = new HashMap<>();
        loadBranches();
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nBranch Management System");
            System.out.println("1. Add New Branch");
            System.out.println("2. Update Branch Details");
            System.out.println("3. View Branch Details");
            System.out.println("4. Delete Branch");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

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
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void addBranch() {
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
                break;  // Valid phone number starting with 011
            } else if ((phoneNum.startsWith("01") || phoneNum.startsWith("03")) && phoneNum.length() == 10) {
                break;  // Valid phone number starting with 01 or 03
            } else {
                System.out.println("Invalid phone number! Please ensure the phone number follows the format:\n" +
                                   " - Starts with 011\n" +
                                   " - Starts with 01 or 03");
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
                scanner.nextLine();  // Consume newline
                
                // Check if employee count is positive and does not exceed 60
                if (employeeCount > 0 && employeeCount <= 60) {
                    break;  // Valid employee count
                } else {
                    System.out.println("Invalid employee count! Please enter a positive number not exceeding 60.");
                }
            } else {
                System.out.println("Invalid input! Please enter a numeric value.");
                scanner.next();  // Clear invalid input
            }
        }
    
        Branch branch = new Branch(id, name, phoneNum, address, employeeCount, managerName);
        branches.put(id, branch);
        System.out.println("Branch added with ID: " + id);
    
        // Save branches to file
        saveBranches();
    }
    

    private void updateBranch() {
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
                    break;  // Valid phone number starting with 011
                } else if ((phoneNum.startsWith("01") || phoneNum.startsWith("03")) && phoneNum.length() == 10) {
                    break;  // Valid phone number starting with 01 or 03
                } else {
                    System.out.println("Invalid phone number! Please ensure the phone number follows the format:\n" +
                                       " - Starts with 011 \n" +
                                       " - Starts with 01 or 03");
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
                    scanner.nextLine();  // Consume newline
                    
                    // Check if employee count is positive and does not exceed 60
                    if (employeeCount > 0 && employeeCount <= 60) {
                        break;  // Valid employee count
                    } else {
                        System.out.println("Invalid employee count! Please enter a positive number not exceeding 60.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a numeric value.");
                    scanner.next();  // Clear invalid input
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
    
    
    private void showDetails() {
        System.out.print("Enter Branch ID: ");
        String id = scanner.nextLine();
        Branch branch = branches.get(id);

        if (branch != null) {
            displayDetails(branch);
        } else {
            System.out.println("Branch ID not found.");
        }
    }

    private void deleteBranch() {
        System.out.print("Enter Branch ID: ");
        String id = scanner.nextLine();
        Branch branch = branches.get(id);

        if (branch != null) {
            branches.remove(id);
            System.out.println("Branch with ID " + id + " has been deleted.");

            // Save branches to file
            saveBranches();
        } else {
            System.out.println("Branch ID not found.");
        }
    }

    private String generateId(String name) {
        String prefix = "br-";

        // Get the current ID counter for this prefix
        int idNumber = idCounters.getOrDefault(prefix, 1);

        // Generate the ID
        String id = String.format("%s%04d", prefix, idNumber);

        // Increment and store the updated counter
        idCounters.put(prefix, idNumber + 1);

        return id;
    }

    private void displayDetails(Branch branch) {
        System.out.println("Branch Details:");
        System.out.println("ID: " + branch.getBranchId());
        System.out.println("Name: " + branch.getName());
        System.out.println("Phone Number: " + branch.getPhoneNum());
        System.out.println("Address: " + branch.getAddress());
        System.out.println("Manager Name: " + branch.getManagerName());
        System.out.println("Employee Count: " + branch.getEmployeeCount());
    }

    private void loadBranches() {
        try (BufferedReader br = new BufferedReader(new FileReader("branches.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 6) {
                    System.err.println("Skipping malformed line: " + line);
                    continue; // Skip this line if it doesn't have exactly 6 parts
                }

                String id = parts[0];
                String name = parts[1];
                String phoneNum = parts[2];
                String address = parts[3];
                int employeeCount = 0;

                try {
                    employeeCount = Integer.parseInt(parts[4]);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing employee count for line: " + line);
                    continue; // Skip this line if parsing fails
                }

                Branch branch = new Branch(id, name, phoneNum, address, employeeCount, parts[5]);
                branches.put(id, branch);

                // Update the ID counter based on the loaded branch ID
                String prefix = id.substring(0, id.indexOf('-') + 1);
                int idNumber = Integer.parseInt(id.substring(prefix.length()));
                int currentMax = idCounters.getOrDefault(prefix, 0);
                if (idNumber > currentMax) {
                    idCounters.put(prefix, idNumber + 1);
                }
            }
        } catch (FileNotFoundException e) {
            branches = new HashMap<>();
            idCounters = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBranches() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("branches.txt"))) {
            for (Branch branch : branches.values()) {
                bw.write(branch.getBranchId() + "," + branch.getName() + "," + branch.getPhoneNum() + "," + branch.getAddress() + "," + branch.getEmployeeCount() + "," + branch.getManagerName());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BranchManager manager = new BranchManager();
        manager.displayMenu();
    }
}
