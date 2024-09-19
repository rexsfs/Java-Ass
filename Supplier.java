public class Supplier extends Person{
    private String companyName;
    
    public Supplier(String personId, String name, String phoneNum, String address, String email,String companyName){
        super(personId, name, phoneNum, address, email);
        this.companyName = companyName;
    }
    
    public String getCompanyName(){
        return companyName;
    }
    
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    
    @Override
    public String toString(){
        return super.toString() + "|" + getCompanyName() + "\n";
    }
}