package BAMS.Model;

import BAMS.Model.Account.Account;
import java.util.ArrayList;

public class Customer implements Model{
    
    protected ArrayList<Account> accounts = new ArrayList<>();

    public Customer(ArrayList<Account> list){
        for (Account ac : list) {
            this.accounts.add(ac);
        }
    }
    
    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
