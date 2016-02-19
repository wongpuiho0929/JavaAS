package BAMS.Model.Bank;

import BAMS.Model.Account.Account;
import BAMS.Model.Account.HangSengAccount;

public class HangSengBank extends Bank{

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAccount(Account ac) {
        boolean success = false;
        if(ac.getClass().getName().equals(HangSengAccount.class.getName())){
            this.accounts.add(ac);
            success = true;
        }else
            success = false;
        return success;
    }
    
}
