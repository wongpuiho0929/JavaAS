package BAMS.DAO;

import BAMS.Model.Model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {

    protected String dburl, dbUser, dbPassword;
    protected SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DAO(String dburl, String dbUser, String dbPassword) {
        this.dbPassword = dbPassword;
        this.dbUser = dbUser;
        this.dburl = dburl;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public abstract boolean insert(Model m);

    public abstract boolean delete(Model m);

    public abstract boolean update(Model m);

    public abstract ArrayList getById(String Id);

}
