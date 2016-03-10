/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.DAO;

import static BAMS.DAO.DAO.conn;
import BAMS.Model.Customer;
import BAMS.Model.Model;
import BAMS.Model.User;
import BAMS.Enum.UserType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author User
 */
public class UserDAO extends DAO {

    private Hashtable<String, User> dataByUsername;

    public UserDAO() {
        this.table = "User";
        data = new Hashtable<>();
        dataByUsername = new Hashtable<>();
    }

    @Override
    public boolean create(Model m) throws Exception {
        User model = (User) m;
        if (super.create(m)) {
            DAO.customerDB.putCustomerByUsername(model);
            data.put(model.getId(), model);
            dataByUsername.put(model.getUsername(), model);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Model m) throws Exception {
        User model = (User) m;
        boolean success = false;
        if (model.isDeleted()) {
            return success;
        }
        try {

            model.setUpdatedAt(new Date());
            rs.absolute(model.getIndex());
            rs.updateString("id", model.getId());
            rs.updateString("username", model.getUsername());
            rs.updateString("password", model.getPassword());
            rs.updateString("customerId", model.getCustomerId());
            rs.updateString("type", model.getType().name());
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
    public Model findById(String Id) {
        return data.get(Id);
    }

    @Override
    protected void getData() {
        try {

            ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select * from user");
            this.rs = rs;
            while (rs.next()) {
                User u = new User();
                Customer c = (Customer) DAO.customerDB.findById(rs.getString("customerId"));
                u.setIndex(rs.getRow());
                u.setId(rs.getString("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                if (c == null) {
                    u.setType(UserType.Staff);
                } else {
                    u.setType(UserType.Customer);
                    u.setCustomer(c);
                    DAO.customerDB.putCustomerByUsername(u);
                }
                u.setCreatedAt(stringToDate(rs.getString("createdAt")));
                u.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                u.setDeletedAt(stringToDate(rs.getString("deletedAt")));

                data.put(u.getId(), u);
                dataByUsername.put(u.getUsername(), u);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isUsernameExist(String username) {
        return dataByUsername.containsKey(username);
    }

    public User findByUsername(String username) {
        return dataByUsername.get(username);
    }

    @Override
    protected String getNextId() {
        return "U" + data.size();
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

        User model = (User) m;
        rs.absolute(model.getIndex());
        rs.refreshRow();
        dataByUsername.remove(model.getUsername());
        model.setUsername(rs.getString("username"));
        model.setPassword(rs.getString("password"));
        model.setCustomer(DAO.customerDB.findById(rs.getString("customerId")));
        model.setType(UserType.valueOf(rs.getString("type")));
        dataByUsername.put(model.getUsername(), model);
        model.setCreatedAt(stringToDate(rs.getString("createdAt")));
        model.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
        model.setDeletedAt(stringToDate(rs.getString("deletedAt")));
    }
}
