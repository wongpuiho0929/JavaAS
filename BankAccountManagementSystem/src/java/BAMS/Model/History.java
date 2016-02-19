package BAMS.Model;

public class History extends Model{
    
    private Customer customer;
    private Bank bank;
    private Account account;
    private String action;

    public History() {
    }

    public History(Customer customer, Bank bank, Account account, String action) {
        this.customer = customer;
        this.bank = bank;
        this.account = account;
        this.action = action;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "History ("+ id + "): " + customer.getName() + "("+customer.getId()+")" + ": account(" + account.getAccountNo() + "): " + action;
    }
    
}
