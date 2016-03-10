package BAMS.Model;

import BAMS.DAO.AccountDAO;
import BAMS.DAO.DAO;

public class Account extends Model {

    protected String accountNo;
    protected String customerId;
    protected String bankId;
    protected double balance;
    private static AccountDAO db = DAO.accountDB;
    protected String currencyId;

    public Account() {
    }

    public Account(String c, String b, String accountNo, double balance) {
        this.bankId = b;
        this.customerId = c;
        this.accountNo = accountNo;
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

    public Currency getCurrency() {
        return (Currency) DAO.currencyDB.findById(this.currencyId);
    }

    public void setCurrency(Currency currency) {
        this.currencyId = currency.getId();
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setBank(Bank b) {
        this.bankId = b.getId();
    }

    public void setCustomer(Customer c) {
        this.customerId = c.id;
        c.addAccount(this);

    }

    public Customer getCustomer() {
        return DAO.customerDB.findById(this.customerId);
    }

    public Bank getBank() {
        return DAO.bankDB.findById(this.bankId);
    }

    public static Account findById(String id) {
        return (Account) findById(db, id);
    }

    public synchronized boolean transfer(Account ac, double money) {
        if (this.balance < money) {
            return false;
        }
        
        ac.increaseBalance(money);
        this.decreaseBalance(money);

        return true;
    }

    public synchronized void increaseBalance(double money) {
        this.balance += money;
    }

    public synchronized void decreaseBalance(double money) {
        this.balance -= money;
    }

}
