package BAMS.DAO;

import BAMS.Model.Bank;
import BAMS.Model.Model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

public class BankDAO extends DAO {

    public BankDAO() {
        data = new Hashtable<String, Model>();
        table = "Bank";
        getData();
    }

    @Override
    public synchronized boolean create(Model m) {
        Bank model = (Bank) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "Insert Into bank values(?,?,?,?,?,?,?)";
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
            p.execute();
            success = true;
            System.out.println(nextId);
            if (success) {
                model.setId(nextId);
                System.out.println(model.getId() == null);
                data.put(model.getId(), model);
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return success;
        }

        return success;
    }

    @Override
    public synchronized boolean delete(Model m) {

        Bank model = (Bank) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update Bank set deletedAt=? where id=?";
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
        Bank model = (Bank) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update Bank set name=?,tel=?,address=?,createdAt=?,updatedAt=?,deletedAt=? where id=?";
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
        return "B" + data.size();
    }

    @Override
    protected void getData() {
        try {
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("select * from bank where deletedAt is not null;");
            while (rs.next()) {
                Bank b = new Bank();
                b.setAddress(rs.getString("address"));
                b.setId(rs.getString("id"));
                b.setName(rs.getString("name"));
                b.setTel(rs.getString("tel"));
                b.setCreatedAt(stringToDate(rs.getString("createdAt")));
                b.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                b.setDeletedAt(stringToDate(rs.getString("deletedAt")));
                data.put(b.getId(), b);
            }
            conn.close();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    
    
}
