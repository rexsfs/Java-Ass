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
 
 /**
  *
  * @author TUF A15
  */
 public class StaffMain {
 
     public static void main(String[] args) {
 
         Login();
     }
 
     public static void menu(int foundIndex) {
 
         ArrayList<Staff> staffList = readStaffToArray();
 
         int choice = 0;
         Scanner scanner = new Scanner(System.in);
 
         while (true) {
             System.out.println("1. Supplier Function");
             System.out.println("2. Pesonal information");
             System.out.println("3. Modify Personal Information");
             System.out.println("4. Cancel Account");
             System.out.println("5. Exit");
             System.out.print("Select your choice : ");
 
             try {
                 choice = scanner.nextInt();
 
                 if (choice >= 1 && choice <= 5) {
                     break;
                 } else {
                     System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                 }
             } catch (InputMismatchException e) {
                 System.out.println("Invalid input. Please enter an integer between 1 and 4.");
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
         }
     }
 
     public static void adminMenu(int foundIndex) {
         int choice = 0;
 
         ArrayList<Staff> staffList = readStaffToArray();
 
         Scanner scanner = new Scanner(System.in);
         while (true) {
             System.out.println("1. Supplier Function");
             System.out.println("2. Pesonal information");
             System.out.println("3. Modify Personal Information");
             System.out.println("4. Modify Other Staff");
             System.out.println("5. Add Staff");
             System.out.println("6. Cancel Account");
             System.out.println("7. Remove Staff");
             System.out.println("8. Search Staff");
             System.out.println("9. Display All Staff");
             System.out.println(". Exit");
             System.out.print("Select your choice : ");
 
             try {
                 choice = scanner.nextInt();
 
                 if (choice >= 1 && choice <= 5) {
                     break;
                 } else {
                     System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                 }
             } catch (InputMismatchException e) {
                 System.out.println("Invalid input. Please enter an integer between 1 and 4.");
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
                 modifyStaff(foundIndex);
                 break;
             case 4:
 
             case 5:
                 addStaff();
                 break;
             case 6:
                 cancelAccount(foundIndex);
                 break;
             case 7:
                 removeStaff();
                 break;
             case 8:
                 searchStaff(foundIndex);
                 break;
             case 9:
                 displayStaff(staffList);
                 break;
         }
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
 
                 Staff staffTemp = new Staff(staffId, name, phoneNum, address, email, position, salary, staffType, password, answer);
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
 
     public static void addStaff() {
         Scanner scanner = new Scanner(System.in);
 
         String staffId = generateNewStaffId();
 
         System.out.print("Enter Staff Name: ");
         String name = scanner.nextLine();
 
         String phoneNumFormat1 = "01\\d-\\d{7}";
         String phoneNumFormat2 = "01\\d-\\d{8}";
 
         System.out.print("Enter Staff Phone Number(eg: 01x-xxxxxxxx): ");
         String phoneNum = scanner.nextLine();
 
         while (!phoneNum.matches(phoneNumFormat1) && !phoneNum.matches(phoneNumFormat2)) {
             System.out.println("Invalid input!");
             System.out.print("Enter Staff Phone Number: ");
             phoneNum = scanner.nextLine();
         }
 
         System.out.print("Enter Staff Address: ");
         String address = scanner.nextLine();
 
         String emailFormat = "^(.+)@(.+)$";
 
         System.out.print("Enter Staff Email: ");
         String email = scanner.nextLine();
 
         while (!email.matches(emailFormat)) {
             System.out.println("Invalid input!");
             System.out.print("Enter Staff Email: ");
             email = scanner.nextLine();
         }
 
         String positionFormat = "^[a-zA-Z ]+$";
 
         System.out.print("Enter Staff Position: ");
         String position = scanner.nextLine();
 
         while (!position.matches(positionFormat)) {
             System.out.println("Invalid input!");
             System.out.print("Enter Staff Position: ");
             position = scanner.nextLine();
         }
 
         double salary = 0.0;
         boolean validInput = false;
 
         while (!validInput) {
             System.out.print("Enter Staff Salary: ");
             try {
                 salary = scanner.nextDouble();
                 scanner.nextLine(); // Consume newline left-over
 
                 if (salary < 1000 || salary > 100000) {
                     System.out.println("Please enter salary between 1000 to 100000!");
                 } else {
                     validInput = true; // Exit loop if salary is valid
                 }
             } catch (InputMismatchException e) {
                 System.out.println("Invalid input! Please enter a valid number for salary.");
                 scanner.nextLine(); // Clear the invalid input
             }
         }
 
         System.out.print("Enter Staff Type(staff/admin): ");
         String staffType = scanner.nextLine();
 
         while (!staffType.equals("staff") && !staffType.equals("admin")) {
             System.out.println("Invalid input!");
             System.out.print("Enter Staff Type(staff/admin): ");
             staffType = scanner.nextLine();
         }
 
         System.out.print("Enter Password: ");
         String password1 = scanner.nextLine();
 
         System.out.print("Confirm Password: ");
         String password2 = scanner.nextLine();
 
         // Check if both passwords match
         while (!password1.equals(password2)) {
             System.out.println("Passwords do not match. Please try again.");
 
             // Re-enter passwords
             System.out.print("Enter Password: ");
             password1 = scanner.nextLine();
 
             System.out.print("Confirm Password: ");
             password2 = scanner.nextLine();
         }
 
         System.out.println("Enter answer for sequrity question.");
         System.out.print("Childhood nickname : ");
         String answer = scanner.nextLine();
 
         Staff staff = new Staff(staffId, name, phoneNum, address, email, position, salary, staffType, password1, answer);
 
         System.out.println("Are you confirm to add this staff? (Y): ");
         char message = scanner.nextLine().charAt(0);
 
         if (Character.toUpperCase(message) == 'Y') {
 
             System.out.println("Staff Details");
             System.out.println("Staff Id: " + staff.getPersonId());
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
                 System.out.println("Staff Added Successfully");
             } catch (IOException e) {
                 System.out.println("File Opening error.");
                 e.printStackTrace();
             }
         } else {
             System.out.println("Staff added not successfully.");
             addStaff();
         }
 
     }
 
     public static void displayPersonalInfo(int foundIndex) {
         Scanner scanner = new Scanner(System.in);
         ArrayList<Staff> staffList = readStaffToArray(); // Assume this method reads staff from the file into an ArrayList
 
         System.out.println("\n\nStaff Details");
         System.out.println("Staff ID: " + staffList.get(foundIndex).getPersonId());
         System.out.println("Staff Name: " + staffList.get(foundIndex).getName());
         System.out.println("Staff Phone Number: " + staffList.get(foundIndex).getPhoneNum());
         System.out.println("Staff Address: " + staffList.get(foundIndex).getAddress());
         System.out.println("Staff Email: " + staffList.get(foundIndex).getEmail());
         System.out.println("Staff Password: " + staffList.get(foundIndex).getPassword());
         System.out.println("Staff Security Answer: " + staffList.get(foundIndex).getAnswer());
 
         System.out.println("Back to menu (Y = yes)? ");
         char confirmation = scanner.nextLine().charAt(0);
         
         if (Character.toUpperCase(confirmation) == 'Y'){
             if (staffList.get(foundIndex).getStaffType().equals("staff")){
                 menu(foundIndex);
             }else{
                 adminMenu(foundIndex);
             }
         }
         
     }
 
     public static void removeStaff() {
         Scanner scanner = new Scanner(System.in);
 
         ArrayList<Staff> staffList = readStaffToArray();
 
         System.out.print("Enter Staff ID to remove (9999 to exit): ");
         String staffId = scanner.nextLine();
 
     }
 
     public static void displayStaff(ArrayList<Staff> staffList) {
         if (staffList.isEmpty()) {
             System.out.println("No staff records available.");
             return;
         }
 
         System.out.println("Staff List:");
         System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
         System.out.printf("%-10s %-20s %-15s %-20s %-25s %-15s %-10s %-10s%n",
                 "Staff ID", "Name", "Phone Number", "Address", "Email", "Position", "Salary", "Staff Type");
         System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
 
         for (Staff staff : staffList) {
             System.out.printf("%-10s %-20s %-15s %-20s %-25s %-15s %-10.2f %-10s%n",
                     staff.getPersonId(),
                     staff.getName(),
                     staff.getPhoneNum(),
                     staff.getAddress(),
                     staff.getEmail(),
                     staff.getPosition(),
                     staff.getSalary(),
                     staff.getStaffType());
         }
 
         System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
         
         
     }
 
     public static void searchStaff(int temp) {
         Scanner scanner = new Scanner(System.in);
 
         ArrayList<Staff> staffList = readStaffToArray();
 
         System.out.print("Enter Staff ID (9999 to exit) : ");
         String staffId = scanner.nextLine();
 
         if (staffId.equals("9999")) {
             //menu();
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
 
         System.out.print("Do you want to search more staff (Y = yes)?");
         char confirmation = scanner.nextLine().charAt(0);
 
         if (Character.toUpperCase(confirmation) == 'Y') {
             searchStaff(temp);
         } else {
             adminMenu(temp);
         }
 
     }
 
     public static void cancelAccount(int foundIndex) {
 
         Scanner scanner = new Scanner(System.in);
 
         ArrayList<Staff> staffList = readStaffToArray();
 
         System.out.print("Confirm to cancel (Y = yes)? ");
         char confirmation = scanner.nextLine().charAt(0);
         
         if (Character.toUpperCase(confirmation) == 'Y'){
             staffList.remove(foundIndex);
             writeArrayToFile(staffList);
         }
         
         System.out.println("Back to menu (Y = yes)? ");
         confirmation = scanner.nextLine().charAt(0);
         
         if (Character.toUpperCase(confirmation) == 'Y'){
             if (staffList.get(foundIndex).getStaffType().equals("staff")){
                 menu(foundIndex);
             }else{
                 adminMenu(foundIndex);
             }
         }
 
     }
 
     public static void Login() {
         Scanner input = new Scanner(System.in);
         ArrayList<Staff> staffList = readStaffToArray();
         boolean found = false;  // Variable to track if login is successful
         int foundIndex = -1;
         String loggedInStaffRole = null;  // Variable to hold the role after successful login
 
         System.out.println("\n\nStaff Login");
         System.out.println("-----------");
         System.out.print("Please enter your id (9999 to reset password): ");
         String id = input.nextLine();
 
         if (id.equals("9999")) {
             resetPassword();
             Login();
         }
 
         for (int i = 0; i < staffList.size(); i++) {
             if (id.equals(staffList.get(i).getPersonId())) {
                 System.out.print("Please enter your password: ");
                 String password = input.nextLine();
 
                 if (password.equals(staffList.get(i).getPassword())) {
                     loggedInStaffRole = staffList.get(i).getStaffType();  // Store the role after successful login
                     foundIndex = i;
                     found = true;  // Mark login as successful
                     break;  // Exit the loop after successful login
                 } else {
                     System.out.println("Wrong Password");
                     Login();  // Exit method for unsuccessful login
                 }
             }
         }
 
         if (found) {
             // Check the role and direct to appropriate menu
             if ("admin".equalsIgnoreCase(loggedInStaffRole)) {
                 adminMenu(foundIndex);  // Call admin menu if the role is 'admin'
             } else {
                 menu(foundIndex);  // Call normal menu for other staff roles
             }
         }
     }
 
     public static void resetPassword() {
 
         Scanner scanner = new Scanner(System.in);
 
         ArrayList<Staff> staffList = readStaffToArray();
 
         boolean found = false;
         int foundIndex = -1;
 
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
         System.out.println("-----------------");
         System.out.print("Enter your childhood nickname : ");
         String answer = scanner.nextLine();
 
         if (answer.equals(staffList.get(foundIndex).getAnswer())) {
             System.out.print("Enter new password : ");
             String password = scanner.nextLine();
             staffList.get(foundIndex).setPassword(password);
         } else {
             System.out.println("Wrong answer.");
             resetPassword();
         }
 
     }
 
     public static void modifyPersonal(int foundIndex) {
         Scanner scanner = new Scanner(System.in);
         ArrayList<Staff> staffList = readStaffToArray();
 
         int choice = 0;
         boolean validChoice = false;
 
         while (!validChoice) {
             try {
                 System.out.println("\n\nSelect the information to modify:");
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
                 System.out.println("Invalid input. Please enter a valid number.");
                 scanner.next(); // Clear the invalid input
             }
         }
 
         scanner.nextLine(); // Consume the newline character
 
         switch (choice) {
             case 1:
                 System.out.print("Enter New Name: ");
                 staffList.get(foundIndex).setName(scanner.nextLine());
                 break;
             case 2:
                 System.out.print("Enter New Phone Number: ");
                 staffList.get(foundIndex).setPhoneNum(scanner.nextLine());
                 break;
             case 3:
                 System.out.print("Enter New Address: ");
                 staffList.get(foundIndex).setAddress(scanner.nextLine());
                 break;
             case 4:
                 System.out.print("Enter New Email: ");
                 staffList.get(foundIndex).setEmail(scanner.nextLine());
                 break;
             case 5:
                 System.out.print("Enter Your Current Password: ");
                 String currentPassword = scanner.nextLine();
                 if (staffList.get(foundIndex).getPassword().equals(currentPassword)) {
                     System.out.print("Enter New Password: ");
                     String newPassword = scanner.nextLine();
 
                     System.out.print("Confirm New Password: ");
                     String confirmPassword = scanner.nextLine();
                     if (newPassword.equals(confirmPassword)) {
                         staffList.get(foundIndex).setPassword(newPassword);
                         System.out.println("Password updated successfully.");
                     } else {
                         System.out.println("Passwords do not match.");
                     }
                 } else {
                     System.out.println("Incorrect current password.");
                 }
                 break;
             case 6:
                 System.out.print("Enter New Security Question Answer: ");
                 staffList.get(foundIndex).setAnswer(scanner.nextLine());
                 break;
             default:
                 System.out.println("Invalid choice.");
         }
 
         writeArrayToFile(staffList);
         
         System.out.println("Back to menu (Y = yes)? ");
         char confirmation = scanner.nextLine().charAt(0);
         
         if (Character.toUpperCase(confirmation) == 'Y'){
             if (staffList.get(foundIndex).getStaffType().equals("staff")){
                 menu(foundIndex);
             }else{
                 adminMenu(foundIndex);
             }
         }
         
         
     }
 
     public static void modifyStaff(int foundIndex) {
         Scanner scanner = new Scanner(System.in);
         ArrayList<Staff> staffList = readStaffToArray();
 
         int choice = 0;
         boolean validChoice = false;
 
         while (!validChoice) {
             try {
                 System.out.println("\n\nSelect the information to modify:");
                 System.out.println("1. Name");
                 System.out.println("2. Phone Number");
                 System.out.println("3. Address");
                 System.out.println("4. Email");
                 System.out.println("5. Position");
                 System.out.println("6. Salary");
                 System.out.println("7. Staff Type");
                 System.out.print("Enter Choice: ");
                 choice = scanner.nextInt();
                 validChoice = true;
             } catch (InputMismatchException e) {
                 System.out.println("Invalid input. Please enter a valid number.");
                 scanner.next(); // Clear the invalid input
             }
         }
 
         scanner.nextLine(); // Consume the newline character
 
         switch (choice) {
             case 1:
                 System.out.print("Enter New Staff Name: ");
                 staffList.get(foundIndex).setName(scanner.nextLine());
                 break;
             case 2:
                 System.out.print("Enter New Staff Phone Number: ");
                 staffList.get(foundIndex).setPhoneNum(scanner.nextLine());
                 break;
             case 3:
                 System.out.print("Enter New Staff Address: ");
                 staffList.get(foundIndex).setAddress(scanner.nextLine());
                 break;
             case 4:
                 System.out.print("Enter New Staff Email: ");
                 staffList.get(foundIndex).setEmail(scanner.nextLine());
                 break;
             case 5:
                 System.out.print("Enter New Staff Position: ");
                 staffList.get(foundIndex).setPosition(scanner.nextLine());
                 break;
             case 6:
                 System.out.print("Enter New Staff Salary: ");
                 staffList.get(foundIndex).setSalary(scanner.nextDouble());
                 break;
             case 7:
                 System.out.print("Enter New Staff Type(admin/staff): ");
                 staffList.get(foundIndex).setPosition(scanner.nextLine());
                 break;
             default:
                 System.out.println("Invalid choice.");
         }
 
         writeArrayToFile(staffList);
         modifyStaff(foundIndex);
     }
 
 }
 