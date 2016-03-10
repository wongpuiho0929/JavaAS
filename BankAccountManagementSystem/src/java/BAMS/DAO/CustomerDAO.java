package BAMS.DAO;

import BAMS.Exception.SameUsernameException;
import BAMS.Model.Customer;
import BAMS.Model.Model;
import BAMS.Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

public class CustomerDAO extends DAO {

    private Hashtable<String, Customer> dataByUsername;

    public CustomerDAO() {
        data = new Hashtable<>();
        dataByUsername = new Hashtable<>();
        table = "Customer";
    }

    @Override
    public synchronized boolean create(Model m) throws Exception {
        Customer model = (Customer) m;
        if (super.create(m)) {
            data.put(model.getId(), model);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean update(Model m) {
        Customer model = (Customer) m;
        boolean success = false;
        if (model.isDeleted()) {
            return success;
        }
        try {
            Date now = new Date();
            rs.absolute(model.getIndex());
            rs.updateString("id", model.getId());
            rs.updateString("name", model.getName());
            rs.updateString("tel", model.getTel());
            rs.updateString("address", model.getAddress());
            rs.updateString("createdAt", dateToString(model.getCreatedAt()));
            rs.updateString("updatedAt", dateToString(now));
            rs.updateString("deletedAt", dateToString(model.getDeletedAt()));
            rs.updateRow();
            success = true;

            model.setUpdatedAt(now);

        } catch (SQLException e) {
            return success;
        }
        return success;
    }

    @Override
    protected String getNextId() {
        return "C" + data.size();
    }

    @Override
    protected void getData() {
        try {

            ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select * from customer;");
            this.rs = rs;
            while (rs.next()) {
                Customer c = new Customer();
                c.setIndex(rs.getRow());
                c.setId(rs.getString("id"));
                c.setAddress(rs.getString("address"));
                c.setName(rs.getString("name"));
                c.setTel(rs.getString("tel"));
                c.setCreatedAt(stringToDate(rs.getString("createdAt")));
                c.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                c.setDeletedAt(stringToDate(rs.getString("deletedAt")));
                data.put(c.getId(), c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putCustomerByUsername(User u) {
        dataByUsername.put(u.getUsername(), u.getCustomer());
//        System.out.println(u.getCustomer().getId());
    }

    @Override
    public Customer findById(String Id) {
        return (Customer) data.get(Id);
    }

    public boolean isUsernameExist(String username) {
        return dataByUsername.containsKey(username);
    }

    public Customer findByUsername(String username) {
        return (Customer) dataByUsername.get(username);
    }

    protected void clearData() {
        super.clearData();
        dataByUsername = new Hashtable<>();
    }

    @Override
    public void getUpdateFromResultSet(Model m) throws Exception {
        if (m.getId() == null) {
            throw new Exception("Please save the object before get update.");
        }

        Customer model = (Customer) m;
        rs.absolute(model.getIndex());
        rs.refreshRow();
        model.setName(rs.getString("name"));
        model.setAddress(rs.getString("address"));
        model.setTel(rs.getString("tel"));
        model.setCreatedAt(stringToDate(rs.getString("createdAt")));
        model.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
        model.setDeletedAt(stringToDate(rs.getString("deletedAt")));
    }
}
