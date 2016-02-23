package BAMS.DAO;

import BAMS.Exception.SameUsernameException;
import BAMS.Model.Customer;
import BAMS.Model.Model;
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
        Customer c = (Customer) m;
        if (dataByUsername.containsKey(c.getUsername())) {
            throw new SameUsernameException(c.getUsername());
        }

        boolean success = false;
        try {
            
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

            c.setId(nextId);
            data.put(c.getId(), c);
            dataByUsername.put(c.getUsername(), c);
            System.out.println("Customer added.");
            
        } catch (SQLException e) {
            e.printStackTrace();
            return success;
        }
        return success;
    }

    @Override
    public synchronized boolean update(Model m) {
        Customer c = (Customer) m;
        boolean success = false;
        try {
            
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

            c.setUpdatedAt(now);

            
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
            
            ResultSet rs = conn.createStatement().executeQuery("select * from customer where deletedAt = 'null';");
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
                dataByUsername.put(c.getUsername(), c);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

}
