package BAMS.Model.Bank;

import BAMS.Model.Account.Account;
import BAMS.Model.Model;
import java.util.ArrayList;

public abstract class Bank implements Model{
    
    protected String name;
    protected int id;
    protected ArrayList<Account> accounts = new ArrayList<>();
    
    public abstract boolean addAccount(Account ac);
    
}
