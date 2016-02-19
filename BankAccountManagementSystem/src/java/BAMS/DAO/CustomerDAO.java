package BAMS.DAO;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO extends DAO {

    public CustomerDAO() {
        data = new Hashtable<>();
        table = "Customer";
        getData();
    }

    @Override
    public synchronized boolean create(Model m) {
        Customer c = (Customer) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "Insert Into Customer values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            String nextId = getNextId();
            p.setString(index++, nextId);
            p.setString(index++, c.getName());
            p.setString(index++, c.getUsername());
            p.setString(index++, c.getPassword());
            p.setString(index++, c.getTel());
            p.setString(index++, c.getAddress());
            p.setString(index++, dateToString(c.getCreatedAt()));
            p.setString(index++, dateToString(c.getUpdatedAt()));
            p.setString(index++, dateToString(c.getDeletedAt()));

            success = p.execute();

            if (success) {
                c.setId(nextId);
                data.put(c.getId(), c);
            }

        } catch (IOException | SQLException e) {
            return success;
        }
        return success;
    }

    @Override
    public synchronized boolean delete(Model m) {

        Customer c = (Customer) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update Customer set deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, dateToString(now));
            p.setString(index++, c.getId());

            success = p.execute();

            if (success) {
                c.setDeletedAt(now);
            }

        } catch (IOException | SQLException e) {
            return success;
        }
        return success;
    }

    @Override
    public synchronized boolean update(Model m) {
        Customer c = (Customer) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update Customer set name=?,username=?,password=?,tel=?,address=?,createdAt=?,updatedAt=?,deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, c.getName());
            p.setString(index++, c.getUsername());
            p.setString(index++, c.getPassword());
            p.setString(index++, c.getTel());
            p.setString(index++, c.getAddress());
            p.setString(index++, dateToString(c.getCreatedAt()));
            p.setString(index++, dateToString(now));
            p.setString(index++, dateToString(c.getDeletedAt()));
            p.setString(index++, c.getId());
            success = p.execute();

            if (success) {
                c.setUpdatedAt(now);
            }

        } catch (IOException | SQLException e) {
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
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from customer where deletedAt != 'N/A';");
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getString("id"));
                c.setAddress(rs.getString("address"));
                c.setName(rs.getString("name"));
                c.setPassword(rs.getString("password"));
                c.setUsername(rs.getString("username"));
                c.setTel(rs.getString("tel"));
                c.setCreatedAt(stringToDate(rs.getString("createdAt")));
                c.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                c.setDeletedAt(stringToDate(rs.getString("deletedAt")));
                data.put(c.getId(), c);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
