package BAMS.Model;

public enum UserType {

    Staff("Staff"), Customer("Customer");

    private String type;
    
    private UserType(String t){
        this.type = t;
    }
            
    public String toString(){
        return this.type;
    }       
    
}
