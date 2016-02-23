package BAMS.Model;

import BAMS.DAO.AccountDAO;
import BAMS.DAO.DAO;

public class Account extends Model {

    protected String accountNo;
    protected Customer customer;
    protected Bank bank;
    protected double balance;
    private static AccountDAO db = (AccountDAO)DAO.getDAO("Account");

    public Account() {
    }

    public Account(Customer c, Bank b, String accountNo, double balance) {
        this.bank = b;
        this.customer = c;
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

    public void save() {
        save(db);
    }

    public static Account findById(String id){
        return (Account)findById(db, id);
    }
        
}
