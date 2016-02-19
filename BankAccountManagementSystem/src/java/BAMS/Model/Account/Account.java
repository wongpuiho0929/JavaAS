package BAMS.Model.Account;

import BAMS.Model.Customer;
import BAMS.Model.Bank.Bank;
import BAMS.Model.Model;

public abstract class Account implements Model{
    
    protected Customer customer;
    protected Bank bank;
    
    public Account(){
        
    }
    
    public Account(Customer c, Bank b){
        setBank(b);
        setCustomer(c);
    }
    
    public void setBank(Bank b){
        this.bank = b;
    } 
    
    public void setCustomer(Customer c){
        this.customer = c;
    }
}
