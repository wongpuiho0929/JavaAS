package BAMS.DAO;

import static BAMS.DAO.DAO.getConnection;
import BAMS.Model.Account;
import BAMS.Model.Bank;
import BAMS.Model.Customer;
import BAMS.Model.Model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

public class AccountDAO extends DAO {

    public AccountDAO() {
        data = new Hashtable<String, Model>();
        table = "Account";
        getData();
    }

    @Override
    public synchronized boolean create(Model m) {
        Account model = (Account) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "Insert Into Account values(?,?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            String nextId = getNextId();
            p.setString(index++, nextId);
            p.setString(index++, model.getCustomer().getId());
            p.setString(index++, model.getBank().getId());
            p.setString(index++, model.getAccountNo());
            p.setDouble(index++, model.getBalance());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(model.getUpdatedAt()));
            p.setString(index++, dateToString(model.getDeletedAt()));

            success = p.execute();

            if (success) {
                model.setId(nextId);
                data.put(model.getId(), model);
            }
        } catch (IOException | SQLException e) {
            return success;
        }

        return success;
    }

    @Override
    public synchronized boolean delete(Model m) {

        Account model = (Account) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update Account set deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, dateToString(now));
            p.setString(index++, model.getId());

            success = p.execute();

            if (success) {
                model.setDeletedAt(now);
            }

        } catch (IOException | SQLException e) {
            return success;
        }
        return success;
    }

    @Override
    public synchronized boolean update(Model m) {
        Account model = (Account) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update Account set customerId=?,bankId=?,accountNo=?,balance=?,createdAt=?,updatedAt=?,deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, model.getCustomer().getId());
            p.setString(index++, model.getBank().getId());
            p.setString(index++, model.getAccountNo());
            p.setDouble(index++, model.getBalance());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(now));
            p.setString(index++, dateToString(model.getDeletedAt()));
            p.setString(index++, model.getId());
            success = p.execute();

            if (success) {
                model.setUpdatedAt(now);
            }

        } catch (IOException | SQLException e) {
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
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from account where deletedAt != 'N/A';");
            while (rs.next()) {
                Account ac = new Account();
                Customer c = (Customer) getDAO("Customer").findById(rs.getString("customerId"));
                Bank b = (Bank) getDAO("Customer").findById(rs.getString("bankId"));
                ac.setAccountNo(rs.getString("accountNo"));
                ac.setId(rs.getString("id"));
                ac.setBalance(rs.getDouble("balance"));
                ac.setCustomer(c);
                ac.setBank(b);
                ac.setCreatedAt(stringToDate(rs.getString("createdAt")));
                ac.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                ac.setDeletedAt(stringToDate(rs.getString("deletedAt")));
                data.put(ac.getId(), ac);
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

}
