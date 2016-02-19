package BAMS.DAO;

import BAMS.Model.Model;
import java.util.ArrayList;

public class BankDAO extends DAO{

    public BankDAO(String dburl, String dbUser, String dbPassword) {
        super(dburl, dbUser, dbPassword);
    }

    @Override
    public boolean insert(Model m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Model m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Model m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList getById(String Id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
