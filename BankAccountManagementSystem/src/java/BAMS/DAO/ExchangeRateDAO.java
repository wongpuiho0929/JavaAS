/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.DAO;

import static BAMS.DAO.DAO.getConnection;
import BAMS.Model.Account;
import BAMS.Model.ExchangeRate;
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
public class ExchangeRateDAO extends DAO {

    public ExchangeRateDAO() {
        table = "History";
        data = new Hashtable<>();
        getData();
    }

    @Override
    public  synchronized boolean create(Model m) {
        ExchangeRate model = (ExchangeRate) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "Insert Into ExchangeRate values(?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            String nextId = getNextId();
            p.setString(index++, nextId);
            p.setString(index++, model.getCurrency1());
            p.setString(index++, model.getCurrency2());
            p.setDouble(index++, model.getRate());
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

        ExchangeRate model = (ExchangeRate) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update ExchangeRate set deletedAt=? where id=?";
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
    public synchronized  boolean update(Model m) {
        ExchangeRate model = (ExchangeRate) m;
        boolean success = false;
        try {
            Connection conn = getConnection();
            String sql = "update ExchangeRate set currency1=?,currency2=?,rate=?,createdAt=?,updatedAt=?,deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, model.getCurrency1());
            p.setString(index++, model.getCurrency2());
            p.setDouble(index++, model.getRate());
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

            ResultSet rs = conn.createStatement().executeQuery("select * from exchangeRate where deletedAt != 'N/A';");
            while (rs.next()) {
                ExchangeRate er = new ExchangeRate();
                er.setId(rs.getString("id"));
                er.setCurrency1(rs.getString("currency1"));
                er.setCurrency2(rs.getString("currency2"));
                er.setRate(rs.getDouble("rate"));
                er.setCreatedAt(stringToDate(rs.getString("createdAt")));
                er.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                er.setDeletedAt(stringToDate(rs.getString("deletedAt")));
                data.put(er.getId(), er);
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected String getNextId() {
        return "ER" + data.size();
    }

}
