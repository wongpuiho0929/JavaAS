/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.DAO;

import BAMS.Model.Currency;
import BAMS.Model.Model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 *
 * @author User
 */
public class CurrencyDAO extends DAO {

    private Hashtable<String, Currency> dataByPrefix;

    public CurrencyDAO() {
        this.table = "Currency";
        data = new Hashtable<>();
        dataByPrefix = new Hashtable<>();
    }

    @Override
    public boolean create(Model m) throws Exception {
        try {
            Currency model = (Currency) m;
            boolean success = false;
            String sql = "Insert Into Currency values(?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            String nextId = getNextId();
            p.setString(index++, nextId);
            p.setString(index++, model.getName());
            p.setString(index++, model.getPrefix());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(model.getUpdatedAt()));
            p.setString(index++, dateToString(model.getDeletedAt()));

            success = p.execute();

            model.setId(nextId);
            data.put(model.getId(), model);
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Model m) throws Exception {
        boolean success = false;
        try {
            Currency model = (Currency) m;
            String sql = "update Currency set name=?,prefix=?,updatedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            p.setString(index++, model.getName());
            p.setString(index++, model.getPrefix());
            p.setString(index++, dateToString(model.getUpdatedAt()));
            p.setString(index++, model.getId());
            p.execute();
            success = true;

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
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
            ResultSet rs = conn.createStatement().executeQuery("select * from Currency  where deletedAt = 'null'");
            while (rs.next()) {
                Currency c = new Currency();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                c.setPrefix(rs.getString("prefix"));
                c.setCreatedAt(stringToDate(rs.getString("createdAt")));
                c.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                c.setDeletedAt(stringToDate(rs.getString("deletedAt")));
                data.put(c.getId(), c);
                dataByPrefix.put(c.getPrefix(), c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected String getNextId() {
        return "CURR" + data.size();
    }

    @Override
    protected void clearData() {
        super.clearData(); //To change body of generated methods, choose Tools | Templates.
        dataByPrefix = new Hashtable<>();
    }

    public Currency findByPrefix(String prefix) {
        if (dataByPrefix.containsKey(prefix)) {
            return dataByPrefix.get(prefix);
        }
        return null;
    }
}
