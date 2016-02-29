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
        if (dataByUsername.containsKey(model.getUser().getUsername())) {
            throw new SameUsernameException(model.getUser().getUsername());
        }

        boolean success = false;
        try {

            String sql = "Insert Into Customer values(?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            String nextId = getNextId();
            p.setString(index++, nextId);
            p.setString(index++, model.getName());
            p.setString(index++, model.getTel());
            p.setString(index++, model.getAddress());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(model.getUpdatedAt()));
            p.setString(index++, dateToString(model.getDeletedAt()));

            success = p.execute();

            model.setId(nextId);
            data.put(model.getId(), model);
            dataByUsername.put(model.getUser().getUsername(), model);
            System.out.println("Customer added.");

        } catch (SQLException e) {
            e.printStackTrace();
            return success;
        }
        return success;
    }

    @Override
    public synchronized boolean update(Model m) {
        Customer model = (Customer) m;
        boolean success = false;
        try {

            String sql = "update Customer set name=?,tel=?,address=?,createdAt=?,updatedAt=?,deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, model.getName());
            p.setString(index++, model.getTel());
            p.setString(index++, model.getAddress());
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
    
    public void putCustomerByUsername(User u){
        dataByUsername.put(u.getUsername(), u.getCustomer());
        System.out.println(u.getCustomer().getId());
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
