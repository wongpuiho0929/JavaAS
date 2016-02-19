package BAMS.Model;

import BAMS.Model.Customer;
import BAMS.Model.Model;

public class Account extends Model {

    protected String accountNo;
    protected Customer customer;
    protected Bank bank;
    protected double balance;

    public Account() {
    }

    public Account(Customer c, Bank b, String accountNo, double balance) {
        setBank(b);
        setCustomer(c);
        setAccountNo(accountNo);
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setBank(Bank b) {
        this.bank = b;
        b.addAccount(this);
    }

    public void setCustomer(Customer c) {
        this.customer = c;
        c.addAccount(this);

    }

    public Customer getCustomer() {
        return customer;
    }

    public Bank getBank() {
        return bank;
    }

}
