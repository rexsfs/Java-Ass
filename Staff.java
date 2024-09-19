public class Staff extends Person{
    private String position;
    private double salary;
    private String staffType;
    private String password;
    private String answer;
    
    public Staff(String personId, String name, String phoneNum, String address, String email, String position, double salary, String staffType, String password, String answer){
        super(personId, name, phoneNum, address, email);
        this.position = position;
        this.salary = salary;
        this.staffType = staffType;
        this.password = password;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getPosition(){
        return position;
    }
    
    public void setPosition(String position){
        this.position = position;
    }
    
    public double getSalary(){
        return salary;
    }
    
    public void setSalary(double salary){
        this.salary = salary;
    }
    
    public String getStaffType(){
        return staffType;
    }
    
    public void setStaffType(String staffType){
        this.staffType = staffType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString(){
        return super.toString() + "|" + getPosition() + "|" + getSalary() + "|" + getStaffType() + "|" + getPassword() + "|" + getAnswer() + "\n" ;
    }
    
    @Override
    public void modifyDetails(){
        
    } 
}

