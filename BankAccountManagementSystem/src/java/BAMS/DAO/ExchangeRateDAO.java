/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.DAO;

import BAMS.Model.Currency;
import BAMS.Model.ExchangeRate;
import BAMS.Model.Model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author User
 */
public class ExchangeRateDAO extends DAO {

    private Hashtable<String, ExchangeRate> dataByPrefix = new Hashtable<>();

    public ExchangeRateDAO() {
        table = "ExchangeRate";
        data = new Hashtable<>();
    }

    @Override
    public synchronized boolean create(Model m) {
        ExchangeRate model = (ExchangeRate) m;
        boolean success = false;
        try {

            String sql = "Insert Into ExchangeRate values(?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            String nextId = getNextId();
            p.setString(index++, nextId);
            p.setString(index++, model.getCurrency1().getId());
            p.setString(index++, model.getCurrency2().getId());
            p.setDouble(index++, model.getRate());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(model.getUpdatedAt()));
            p.setString(index++, dateToString(model.getDeletedAt()));

            success = p.execute();

            model.setId(nextId);
            data.put(model.getId(), model);

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    @Override
    public synchronized boolean update(Model m) {
        ExchangeRate model = (ExchangeRate) m;
        boolean success = false;
        try {

            String sql = "update ExchangeRate set currency1Id=?,currency2Id=?,rate=?,createdAt=?,updatedAt=?,deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, model.getCurrency1().getId());
            p.setString(index++, model.getCurrency2().getId());
            p.setDouble(index++, model.getRate());
            p.setString(index++, dateToString(model.getCreatedAt()));
            p.setString(index++, dateToString(now));
            p.setString(index++, dateToString(model.getDeletedAt()));
            p.setString(index++, model.getId());
            success = p.execute();

            model.setUpdatedAt(now);

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    @Override
    protected void getData() {
        try {
            CurrencyDAO currencyDAO = DAO.currencyDB;
            ResultSet rs = conn.createStatement().executeQuery("select * from exchangeRate where deletedAt = 'null';");
            while (rs.next()) {
                ExchangeRate er = new ExchangeRate();
                Currency c1 = (Currency) currencyDAO.findById(rs.getString("currency1Id"));
                Currency c2 = (Currency) currencyDAO.findById(rs.getString("currency2Id"));

                er.setId(rs.getString("id"));
                er.setCurrency1(c1);
                er.setCurrency2(c2);
                er.setRate(rs.getDouble("rate"));
                er.setCreatedAt(stringToDate(rs.getString("createdAt")));
                er.setUpdatedAt(stringToDate(rs.getString("updatedAt")));
                er.setDeletedAt(stringToDate(rs.getString("deletedAt")));
                data.put(er.getId(), er);
                dataByPrefix.put(er.getCurrency1().getPrefix() + er.getCurrency2().getPrefix(), er);
                c1.addExchangeRate(er);
                c2.addExchangeRate(er);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected String getNextId() {
        return "ER" + data.size();
    }

    public ExchangeRate findByPrefix(Currency c1, Currency c2) {
        if (dataByPrefix.containsKey(c1.getPrefix() + c2.getPrefix())) {
            return dataByPrefix.get(c1.getPrefix() + c2.getPrefix());
        }
        if (dataByPrefix.containsKey(c2.getPrefix() + c1.getPrefix())) {
            return dataByPrefix.get(c2.getPrefix() + c1.getPrefix());
        }
        return null;
    }

    @Override
    public ExchangeRate findById(String Id) {
        if (data.contains(Id)) {
            return (ExchangeRate) data.get(Id);
        }
        return null;
    }

    public ArrayList<ExchangeRate> findByCurrency(Currency currency) {
        return currency.getExchangeRateList();
    }

    @Override
    protected void clearData() {
        super.clearData(); //To change body of generated methods, choose Tools | Templates.
        dataByPrefix = new Hashtable<>();
    }

}
