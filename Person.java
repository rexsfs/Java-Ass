
import java.util.Scanner;


public class Person {

    private String personId;
    private String name;
    private String phoneNum;
    private String address;
    private String email;

    public Person() {
    }

    public Person(String personId, String name, String phoneNum, String address, String email) {
        this.personId = personId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.email = email;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return personId + "|" + name + "|" + phoneNum + "|" + address + "|" + email;
    }
    
    public void modifyDetails(){
        Scanner scanner = new Scanner(System.in);
    } 
    
}
