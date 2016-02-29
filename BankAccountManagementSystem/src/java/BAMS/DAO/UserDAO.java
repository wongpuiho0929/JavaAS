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
import BAMS.Model.UserType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author User
 */
public class UserDAO extends DAO {

    public UserDAO() {
        this.table = "User";
        data = new Hashtable<>();
    }

    @Override
    public boolean create(Model m) throws Exception {
        User model = (User) m;

        boolean success = false;
        try {

            String sql = "Insert Into User values(?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            String nextId = getNextId();
            p.setString(index++, nextId);
            p.setString(index++, model.getUsername());
            p.setString(index++, model.getPassword());
            DAO.customerDB.create(model.getCustomer());
            p.setString(index++, model.getCustomer().getId());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(model.getUpdatedAt()));
            p.setString(index++, dateToString(model.getDeletedAt()));

            success = p.execute();

            model.setId(nextId);
            data.put(model.getId(), model);
//            System.out.println("User added.");

        } catch (SQLException e) {
            e.printStackTrace();
            return success;
        }
        return success;
    }

    @Override
    public boolean update(Model m) throws Exception {
        User model = (User) m;
        boolean success = false;
        try {

            String sql = "update Customer set username=?,password=?,customerId=?,createdAt=?,updatedAt=?,deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, model.getUsername());
            p.setString(index++, model.getPassword());
            p.setString(index++, model.getCustomer().getId());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(now));
            p.setString(index++, dateToString(model.getDeletedAt()));
            p.setString(index++, model.getId());
            success = p.execute();

            model.setUpdatedAt(now);

        } catch (SQLException e) {
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

            ResultSet rs = conn.createStatement().executeQuery("select * from user where deletedAt = 'null';");
            while (rs.next()) {
                User u = new User();
                Customer c = (Customer) DAO.customerDB.findById(rs.getString("customerId"));
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
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected String getNextId() {
        return "U"+data.size();
    }

}
