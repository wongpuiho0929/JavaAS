/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.DAO;

import static BAMS.DAO.DAO.getConnection;
import BAMS.Model.Account;
import BAMS.Model.Customer;
import BAMS.Model.ExchangeRate;
import BAMS.Model.History;
import BAMS.Model.Model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author User
 */
public class HistoryDAO extends DAO {

    public HistoryDAO(){
        table = "History";
        data = new Hashtable<>();
        getData();
    }

    @Override
    public synchronized  boolean create(Model m) {
        History model = (History) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "Insert Into History values(?,?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            String nextId = getNextId();
            p.setString(index++, nextId);
            p.setString(index++, model.getCustomer().getId());
            p.setString(index++, model.getBank().getId());
            p.setString(index++, model.getAccount().getId());
            p.setString(index++, model.getAction());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(model.getUpdatedAt()));
            p.setString(index++, dateToString(model.getDeletedAt()));

            success = p.execute();

            if (success) {
                model.setId(nextId);
            }

        } catch (IOException | SQLException e) {
            return success;
        }
        data.put(model.getId(), model);
        return success;
    }

    @Override
    public synchronized  boolean delete(Model m) {

        History model = (History) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update History set deletedAt=? where id=?";
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
    public  synchronized boolean update(Model m) {
        History model = (History) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update History set customerId=?,bankId=?,accountId=?,action=?,createdAt=?,updatedAt=?,deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, model.getCustomer().getId());
            p.setString(index++, model.getBank().getId());
            p.setString(index++, model.getAccount().getId());
            p.setString(index++, model.getAction());
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
    protected void getData() {
        try {
            Connection conn = getConnection();

            ResultSet rs = conn.createStatement().executeQuery("select * from history where deletedAt != 'N/A';");
            while (rs.next()) {
                History h = new History();
                Account ac = (Account) getDAO("Account").findById(rs.getString("accountId"));
                Customer c = (Customer) getDAO("Customer").findById(rs.getString("customerId"));
                h.setId(rs.getString("id"));
                h.setAccount(ac);
                h.setAction(rs.getString("action"));
                h.setCustomer(c);
                h.setCreatedAt(stringToDate(rs.getString("createdAt")));
                h.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                h.setDeletedAt(stringToDate(rs.getString("deletedAt")));

                data.put(h.getId(), h);
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected String getNextId() {
        return "H" + data.size();
    }

}
