package BAMS.Model;

import BAMS.DAO.CustomerDAO;
import BAMS.DAO.DAO;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Customer extends Model {

    protected Hashtable<String, Model> accounts = new Hashtable<String, Model>();
    private String name, tel, address;
    private static CustomerDAO db = DAO.customerDB;
    private User user;

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

    public static Customer findByUsername(String username) {
        return db.findByUsername(username);
    }

    public static Customer findById(String id) {
        return (Customer) findById(db, id);
    }

    public String getUsername(){
//        System.out.println(user == null);
        return user.getUsername();
    }
    
    public String getPassword(){
        return user.getPassword();
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
