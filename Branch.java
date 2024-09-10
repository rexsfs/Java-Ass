public class Branch {

    // Attributes
    private String branchId;
    private String name;
    private String phoneNum;
    private String address;
    private int employeeCount;
    private String managerName;

    // Constructor
    public Branch(String branchId, String name, String phoneNum, String address, int employeeCount, String managerName) {
        this.branchId = branchId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.employeeCount = employeeCount;
        this.managerName = managerName;
    }

    // Getter and Setter methods
    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    // Method to update branch details
    public void updateBranch(String name, String phoneNum, String address, int employeeCount, String managerName) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.employeeCount = employeeCount;
        this.managerName = managerName;
    }

    // Overriding toString() method to format branch details
    @Override
    public String toString() {
        return "Branch ID: " + branchId +
               "\nName: " + name +
               "\nPhone Number: " + phoneNum +
               "\nAddress: " + address +
               "\nEmployee Count: " + employeeCount +
               "\nManager: " + managerName;
    }
}
