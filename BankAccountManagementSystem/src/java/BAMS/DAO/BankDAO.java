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

public class BankDAO extends DAO {

    public BankDAO() {
        data = new Hashtable<String, Model>();
        table = "Bank";
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
//            System.out.println(nextId);
           
                model.setId(nextId);
//                System.out.println(model.getId() == null);
                data.put(model.getId(), model);
            
            conn.close();
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

                model.setDeletedAt(now);
            
            conn.close();
        } catch (IOException | SQLException e) {
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

                model.setUpdatedAt(now);
            
            conn.close();
        } catch (IOException | SQLException e) {
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
            Connection conn = getConnection();
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
            }
            conn.close();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean create(ArrayList<Model> m) {

        boolean success = false;
        try {
            Connection conn = getConnection();
            for (int i = 0; i < m.size(); i++) {
                Bank model = (Bank) m.get(i);
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
                
                    model.setId(nextId);
//                System.out.println(model.getId() == null);
                    data.put(model.getId(), model);
                
            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return success;
        }

        return success;
    }

    @Override
    public boolean delete(ArrayList<Model> m) {

        boolean success = false;
        try {
            Connection conn = getConnection();
            for (int i = 0; i < m.size(); i++) {
                Bank model = (Bank) m.get(i);

                String sql = "update Bank set deletedAt=? where id=?";
                PreparedStatement p = conn.prepareStatement(sql);
                int index = 1;
                Date now = new Date();
                p.setString(index++, dateToString(now));
                p.setString(index++, model.getId());

                success = p.execute();

                    model.setDeletedAt(now);
                
            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return success;
        }
        return success;
    }

    @Override
    public boolean update(ArrayList<Model> m) {

        boolean success = false;
        try {
            Connection conn = getConnection();
            for (int i = 0; i < m.size(); i++) {
                Bank model = (Bank) m.get(i);
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
                
            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return success;
        }
        return success;
    }

    @Override
    public Bank findById(String Id) {
        return (Bank)data.get(Id);
    }

}
