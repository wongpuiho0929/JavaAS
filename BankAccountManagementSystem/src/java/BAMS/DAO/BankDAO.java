package BAMS.DAO;

import BAMS.Model.Bank;
import BAMS.Model.Model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

public class BankDAO extends DAO {

    private Hashtable<String, Bank> dataByName;

    public BankDAO() {
        data = new Hashtable<String, Model>();
        table = "Bank";
    }

    @Override
    public synchronized boolean create(Model m) throws Exception {
        Bank model = (Bank) m;
        if (super.create(m)) {
            data.put(model.getId(), model);
            System.out.println("data count:" + data.size());
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean update(Model m) throws Exception {
        Bank model = (Bank) m;
        boolean success = false;
        if (model.isDeleted()) {
            return success;
        }
        Date now = new Date();
        model.setUpdatedAt(now);
        rs.absolute(model.getIndex());
        rs.updateString("id", model.getId());
        rs.updateString("name", model.getName());
        rs.updateString("tel", model.getTel());
        rs.updateString("address", model.getAddress());
        rs.updateString("createdAt", dateToString(model.getCreatedAt()));
        rs.updateString("updatedAt", dateToString(model.getUpdatedAt()));
        rs.updateString("deletedAt", dateToString(model.getDeletedAt()));
        rs.updateRow();
        System.out.println(model.getName());
        success = true;

        return success;
    }

    @Override
    protected String getNextId() {
        return "B" + data.size();
    }

    @Override
    protected void getData() {
        try {

            ResultSet rs = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select * from bank");
            this.rs = rs;
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
