/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.DAO;

import BAMS.Model.Currency;
import BAMS.Model.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
        Currency model = (Currency) m;
        if (super.create(m)) {
            data.put(model.getId(), model);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Model m) throws Exception {
        boolean success = false;
        Currency model = (Currency) m;
        if (model.isDeleted()) {
            return success;
        }
        Date now = new Date();
        model.setUpdatedAt(now);
        rs.absolute(model.getIndex());
        rs.updateString("id", model.getId());
        rs.updateString("name", model.getName());
        rs.updateString("prefix", model.getPrefix());
        rs.updateString("createdAt", dateToString(model.getCreatedAt()));
        rs.updateString("updatedAt", dateToString(model.getUpdatedAt()));
        rs.updateString("deletedAt", dateToString(model.getDeletedAt()));
        rs.updateRow();
        success = true;

        return success;
    }

    @Override
    public Currency findById(String Id) {
        return (Currency) data.get(Id);
    }

    @Override
    protected void getData() {
        try {
            ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select * from Currency");
            this.rs = rs;
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

    @Override
    public void getUpdateFromResultSet(Model m) throws Exception {
        if (m.getId() == null) {
            throw new Exception("Please save the object before get update.");
        }

        Currency model = (Currency) m;
        rs.absolute(model.getIndex());
        rs.refreshRow();
        dataByPrefix.remove(model.getPrefix());
        model.setName(rs.getString("name"));
        model.setPrefix(rs.getString("prefix"));
        dataByPrefix.put(model.getPrefix(), model);
        model.setCreatedAt(stringToDate(rs.getString("createdAt")));
        model.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
        model.setDeletedAt(stringToDate(rs.getString("deletedAt")));
    }

}
