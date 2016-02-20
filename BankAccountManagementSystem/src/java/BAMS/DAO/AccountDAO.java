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
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class AccountDAO extends DAO {

    public AccountDAO() {
        data = new Hashtable<String, Model>();
        table = "Account";

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

            model.setId(nextId);
            model.getBank().addAccount(model);
            model.getCustomer().addAccount(model);
            data.put(model.getId(), model);
            System.out.println("Account added.");

            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
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

            model.setDeletedAt(now);

            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
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

            model.setUpdatedAt(now);

            conn.close();
        } catch (IOException | SQLException e) {
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
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from account where deletedAt  = 'null';");
            while (rs.next()) {
                Account ac = new Account();
                Customer c = (Customer) getDAO("Customer").findById(rs.getString("customerId"));
                Bank b = (Bank) getDAO("Bank").findById(rs.getString("bankId"));
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
            conn.close();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean create(ArrayList<Model> m) {

        boolean success = false;
        try {
            Connection conn = getConnection();
            for (int i = 0; i < m.size(); i++) {
                Account model = (Account) m.get(i);
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
                model.setId(nextId);
                model.getBank().addAccount(model);
                model.getCustomer().addAccount(model);
                data.put(model.getId(), model);
                System.out.println("Account added.");

            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return success;
        }

        return success;
    }

    @Override
    public boolean delete(ArrayList<Model> m) {

        boolean success = false;
        try {
            Connection conn = getConnection();
            for (int i = 0; i < m.size(); i++) {
                Account model = (Account) m.get(i);

                String sql = "update Account set deletedAt=? where id=?";
                PreparedStatement p = conn.prepareStatement(sql);
                int index = 1;
                Date now = new Date();
                p.setString(index++, dateToString(now));
                p.setString(index++, model.getId());

                success = p.execute();

                model.setDeletedAt(now);

            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return success;
        }
        return success;
    }

    @Override
    public boolean update(ArrayList<Model> m) {

        boolean success = false;
        try {
            Connection conn = getConnection();
            for (int i = 0; i < m.size(); i++) {
                Account model = (Account) m.get(i);
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

                model.setUpdatedAt(now);

            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return success;
        }
        return success;
    }

    @Override
    public Model findById(String Id) {
        return (Account) data.get(Id);
    }

}
