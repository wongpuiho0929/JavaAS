/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools Templates
 * and open the template in the editor.
 */
package BAMS.DAO;

import BAMS.Model.Account;
import BAMS.Model.Customer;
import BAMS.Model.History;
import BAMS.Model.Model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author User
 */
public class HistoryDAO extends DAO {

    public HistoryDAO() {
        table = "History";
        data = new Hashtable<>();
    }

    @Override
    public synchronized boolean create(Model m) throws Exception {
        History model = (History) m;
        if (super.create(m)) {
            data.put(model.getId(), model);
            return true;
        }

        return false;
    }

    @Override
    public synchronized boolean update(Model m) {
        History model = (History) m;
        boolean success = false;
        if (model.isDeleted()) {
            return success;
        }
        try {
            Date now = new Date();
            model.setUpdatedAt(now);
            rs.absolute(model.getIndex());
            rs.updateString("id", model.getId());
            rs.updateString("customerId", model.getCustomer().getId());
            rs.updateString("bankId", model.getBank().getId());
            rs.updateString("accountId", model.getAccount().getId());
            rs.updateString("action", model.getAction());
            rs.updateString("createdAt", dateToString(model.getCreatedAt()));
            rs.updateString("updatedAt", dateToString(model.getUpdatedAt()));
            rs.updateString("deletedAt", dateToString(model.getDeletedAt()));
            rs.updateRow();
            success = true;

        } catch (SQLException e) {
            return success;
        }
        return success;
    }

    @Override
    protected void getData() {
        try {

            ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select * from history");
            this.rs = rs;
            while (rs.next()) {
                History h = new History();
                Account ac = (Account) DAO.accountDB.findById(rs.getString("accountId"));
                Customer c = (Customer) DAO.customerDB.findById(rs.getString("customerId"));
                h.setId(rs.getString("id"));
                h.setIndex(rs.getRow());
                h.setAccount(ac);
                h.setAction(rs.getString("action"));
                h.setCustomer(c);
                h.setCreatedAt(stringToDate(rs.getString("createdAt")));
                h.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                h.setDeletedAt(stringToDate(rs.getString("deletedAt")));

                data.put(h.getId(), h);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected String getNextId() {
        return "H" + data.size();
    }

    @Override
    public History findById(String Id) {
        return (History) data.get(Id);
    }

    @Override
    public void getUpdateFromResultSet(Model m) throws Exception {
        if (m.getId() == null) {
            throw new Exception("Please save the object before get update.");
        }

        History model = (History) m;
        rs.absolute(model.getIndex());
        rs.refreshRow();
        model.setAccount(DAO.accountDB.findById(rs.getString("accountId")));
        model.setBank(DAO.bankDB.findById(rs.getString("bankId")));
        model.setCustomer(DAO.customerDB.findById(rs.getString("customerId")));
        model.setAction(rs.getString("action"));
        model.setCreatedAt(stringToDate(rs.getString("createdAt")));
        model.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
        model.setDeletedAt(stringToDate(rs.getString("deletedAt")));
    }

}
