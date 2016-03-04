package BAMS.DAO;

import BAMS.Model.Bank;
import BAMS.Model.Model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class BankDAO extends DAO {

    private Hashtable<String, Bank> dataByName;

    public BankDAO() {
        data = new Hashtable<String, Model>();
        table = "Bank";
    }

    @Override
    public synchronized boolean create(Model m) {
        Bank model = (Bank) m;
        boolean success = false;
        try {

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
//            System.out.println(nextId);

            model.setId(nextId);
//                System.out.println(model.getId() == null);
            data.put(model.getId(), model);

        } catch (SQLException e) {
            e.printStackTrace();
            return success;
        }

        return success;
    }

    @Override
    public synchronized boolean update(Model m) {
        Bank model = (Bank) m;
        boolean success = false;
        try {

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

            model.setUpdatedAt(now);

        } catch (SQLException e) {
            e.printStackTrace();
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

            ResultSet rs = conn.createStatement().executeQuery("select * from bank where deletedAt = 'null';");
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
                dataByName.put(b.getName(), b);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Bank findById(String Id) {
        return (Bank) data.get(Id);
    }

    public Bank findByName(String name) {
        if (dataByName == null) {
            dataByName = new Hashtable<>();

            for (Model m : getDataList()) {
                dataByName.put(((Bank) m).getName(), (Bank) m);
            }

        }

        return dataByName.get(name);
    }

    protected void clearData() {
        super.clearData();
        dataByName = new Hashtable<>();
    }
}
