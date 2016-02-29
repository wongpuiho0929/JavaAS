package BAMS.Model;

import BAMS.DAO.BankDAO;
import BAMS.DAO.DAO;
import BAMS.Model.Account;
import BAMS.Model.Model;
import java.util.ArrayList;
import java.util.Hashtable;

public class Bank extends Model {

    protected String name, tel, address;
    protected Hashtable<String, Model> accounts = new Hashtable<String, Model>();
    private static BankDAO db = ((BankDAO) DAO.getDAO("Bank"));

    public Bank(String name, String tel, String address) {
        this.name = name;
        this.tel = tel;
        this.address = address;
    }

    public Bank() {
        name = "";
        tel = "";
        address = "";
    }

    public void addAccount(Account ac) {
        accounts.put(ac.getId(), ac);
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

    public Hashtable<String, Model> getAccounts() {
        return accounts;
    }

    public void setAccounts(Hashtable<String, Model> accounts) {
        this.accounts = accounts;
    }

    public void save() {
        save(db);
    }

    public static Bank findById(String id) {
        return (Bank) findById(db, id);
    }

    public static Bank findByName(String name) {
        return db.findByName(name);
    }

}
