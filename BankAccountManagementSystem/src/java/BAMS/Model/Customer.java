package BAMS.Model;

import BAMS.DAO.CustomerDAO;
import BAMS.DAO.DAO;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Customer extends Model {

    protected Hashtable<String, Model> accounts = new Hashtable<String, Model>();
    private String name, username, password, tel, address;
    private static CustomerDAO db = ((CustomerDAO) DAO.getDAO("Customer"));

    public Customer() {
    }

    public Customer(ArrayList<Account> list) {
        for (Account ac : list) {
            this.accounts.put(ac.getId(), ac);
        }
    }

    public Hashtable<String, Model> getAccounts() {
        return accounts;
    }

    public ArrayList<Model> getAccountList() {
        return new ArrayList<>(accounts.values());
    }

    public void setAccounts(Hashtable<String, Model> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account a) {
        accounts.put(a.accountNo, a);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        if (Model.checkTel(tel)) {
            this.tel = tel;
        }

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean checkPassword(String pwd) {
        return pwd.equals(this.getPassword());
    }

    public void save() {
        save(db);
    }

    public static Customer findByUsername(String username){
        return db.findByUsername(username);
    }
    
    public static Customer findById(String id) {
        return (Customer) findById(db, id);
    }
}
