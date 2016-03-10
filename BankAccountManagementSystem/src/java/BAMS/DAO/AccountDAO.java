package BAMS.DAO;

import BAMS.Model.Account;
import BAMS.Model.Bank;
import BAMS.Model.Currency;
import BAMS.Model.Customer;
import BAMS.Model.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

public class AccountDAO extends DAO {

    public AccountDAO() {
        data = new Hashtable<String, Model>();
        table = "Account";
    }

    @Override
    public synchronized boolean create(Model m) throws Exception {
        Account model = (Account) m;
        if (super.create(m)) {
            model.getBank().addAccount(model);
            model.getCustomer().addAccount(model);
            data.put(model.getId(), model);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean update(Model m) {
        Account model = (Account) m;
        boolean success = false;
        if (model.isDeleted()) {
            return success;
        }
        try {
            Date now = new Date();
            model.setUpdatedAt(now);
            rs.absolute(model.getIndex());
            rs.updateString("id", model.getId());
            rs.updateString("customerId", model.getCustomer().getId());
            rs.updateString("bankId", model.getBank().getId());
            rs.updateString("accountNo", model.getAccountNo());
            rs.updateDouble("balance", model.getBalance());
            rs.updateString("currencyId", model.getCurrency().getId());
            rs.updateString("createdAt", dateToString(model.getCreatedAt()));
            rs.updateString("updatedAt", dateToString(model.getUpdatedAt()));
            rs.updateString("deletedAt", dateToString(model.getDeletedAt()));
            rs.updateRow();
            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            return success;
        }
        return success;
    }

    @Override
    protected String getNextId() {
        return "A" + data.size();
    }

    @Override
    protected void getData() {
        try {

            ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select * from account");
            this.rs = rs;
            while (rs.next()) {
                Account ac = new Account();
                Customer c = (Customer) DAO.customerDB.findById(rs.getString("customerId"));
                Bank b = (Bank) DAO.bankDB.findById(rs.getString("bankId"));
                Currency cu = (Currency) DAO.currencyDB.findById(rs.getString("currencyId"));
                ac.setAccountNo(rs.getString("accountNo"));
                ac.setId(rs.getString("id"));
                ac.setBalance(rs.getDouble("balance"));
                ac.setCustomer(c);
                ac.setBank(b);
                ac.setCurrency(cu);
                ac.setCreatedAt(stringToDate(rs.getString("createdAt")));
                ac.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                ac.setDeletedAt(stringToDate(rs.getString("deletedAt")));
                data.put(ac.getId(), ac);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Account findById(String Id) {
        return (Account) data.get(Id);
    }

    @Override
    public void getUpdateFromResultSet(Model m) throws Exception {
        if(m.getId()==null)
            throw new Exception("Please save the object before get update.");
        
        Account model = (Account)m;
        
        rs.absolute(model.getIndex());
        rs.refreshRow();
        model.setAccountNo(rs.getString("accountNo"));
        model.setBalance(rs.getDouble("balance"));
        model.setBank(DAO.bankDB.findById(rs.getString("bankId")));
        model.setCustomer(DAO.customerDB.findById(rs.getString("customerId")));
        model.setCurrency(DAO.currencyDB.findById(rs.getString("currencyId")));
        model.setCreatedAt(stringToDate(rs.getString("createdAt")));
        model.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
        model.setDeletedAt(stringToDate(rs.getString("deletedAt")));
    }

}
