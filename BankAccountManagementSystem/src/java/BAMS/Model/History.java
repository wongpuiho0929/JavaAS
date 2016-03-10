package BAMS.Model;

import BAMS.DAO.DAO;
import BAMS.DAO.HistoryDAO;

public class History extends Model {

    private String customerId;
    private String bankId;
    private String accountId;
    private String action;
    private static HistoryDAO db = DAO.historyDB;

    public History() {
    }

    public History(String customer, String bank, String account, String action) {
        this.customerId = customer;
        this.bankId = bank;
        this.accountId = account;
        this.action = action;
    }

    public Customer getCustomer() {
        return DAO.customerDB.findById(this.customerId);
    }

    public void setCustomer(Customer customer) {
        this.customerId = customer.getId();
    }

    public Bank getBank() {
        return DAO.bankDB.findById(this.bankId);
    }

    public void setBank(Bank bank) {
        this.bankId = bank.getId();
    }

    public Account getAccount() {
        return DAO.accountDB.findById(this.accountId);
    }

    public void setAccount(Account account) {
        this.accountId = account.getId();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "History (" + id + "): " + getCustomer().getName() + "(" + getCustomer().getId() + ")" + ": account(" + getAccount().getAccountNo() + "): " + action;
    }

    public static History findById(String id) {
        return (History) findById(db, id);
    }
}
