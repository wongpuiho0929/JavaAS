package BAMS.Model;

import BAMS.DAO.AccountDAO;
import BAMS.DAO.DAO;

public class Account extends Model {

    protected String accountNo;
    protected Customer customer;
    protected Bank bank;
    protected double balance;
    private static AccountDAO db = DAO.accountDB;
    private Currency currency;

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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setBank(Bank b) {
        this.bank = b;
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

    public static Account findById(String id) {
        return (Account) findById(db, id);
    }

    public synchronized boolean transfer(Account ac, double money) {
        if (this.balance < money) {
            return false;
        }
        if (this.currency.compareTo(ac.currency) == 0) {
            ac.increaseBalance(money);
            this.decreaseBalance(money);
        }
        return true;
    }

    public synchronized void increaseBalance(double money) {
        this.balance += money;
    }

    public synchronized void decreaseBalance(double money) {
        this.balance -= money;
    }

}
