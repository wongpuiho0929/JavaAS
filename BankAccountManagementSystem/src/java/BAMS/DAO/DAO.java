package BAMS.DAO;

import BAMS.Model.Model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {

    protected String table;
    protected static String dburl, dbUser, dbPassword;
    protected SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected static Hashtable<String, DAO> DAObyName = new Hashtable<>();
    protected Hashtable<String, Model> data;

    public static void setting(String dburl, String dbUser, String dbPassword) {
        DAO.dbPassword = dbPassword;
        DAO.dbUser = dbUser;
        DAO.dburl = dburl;
        DAObyName.put("Bank", new BankDAO());
        DAObyName.put("Customer", new CustomerDAO());
        DAObyName.put("ExchangeRate", new ExchangeRateDAO());
        DAObyName.put("Account", new AccountDAO());
        DAObyName.put("History", new HistoryDAO());
    }

    public static Hashtable<String, DAO> getDAOs() {
        return DAObyName;
    }

    public static DAO getDAO(String name) {
        return DAObyName.get(name);
    }

    public Date stringToDate(String s) {
        try {
            if (s.equals("N/A")) {
                return null;
            }
            return formatter.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    public String dateToString(Date d) {
        if (d == null) {
            return "null";
        }
        return formatter.format(d);

    }

    protected static Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public abstract boolean create(Model m);
    public abstract boolean create(ArrayList<Model> m);

    public abstract boolean delete(Model m);
    public abstract boolean delete(ArrayList<Model>  m);

    public abstract boolean update(Model m);
    public abstract boolean update(ArrayList<Model>  m);

    public abstract Model findById(String Id);

    public String printCount() {
        return table + ": " + getCount();
    }

    public int getCount() {
        return data.size();
    }

    public static String printAllCount() {
        Enumeration<DAO> e = DAObyName.elements();
        String s = "";
        while (e.hasMoreElements()) {
            s += e.nextElement().printCount() + "\n";
        }
        return s;
    }

    protected abstract void getData();

    public String getTableName() {
        return table;
    }

    public static void rebuild() {
        dropTable();
        createTable();
    }

    public synchronized static void dropTable() {
        dropTable("Account");
        dropTable("Customer");
        dropTable("Bank");
        dropTable("History");
        dropTable("ExchangeRate");

        System.out.println("Drop Finish");
    }

    private synchronized static void dropTable(String tableName) {
        try {
            Connection conn = getConnection();
            String preQueryStatement = "DROP TABLE IF EXISTS " + tableName + ";";
            conn.createStatement().execute(preQueryStatement);
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected abstract String getNextId();

    private void clearData() {
        data = new Hashtable<>();
    }

    public void refresh() {
        clearData();
        getData();
        System.out.println(getTableName()+" finish refresh.");
    }

    public synchronized static void createTable() {
        try {
            Connection conn = getConnection();
            Statement stmnt = conn.createStatement();
            String sql
                    = "CREATE TABLE `customer` ("
                    + "  `id` varchar(30) NOT NULL,"
                    + "  `name` varchar(30) DEFAULT NULL,"
                    + "  `username` varchar(30) DEFAULT NULL,"
                    + "  `password` varchar(50) DEFAULT NULL,"
                    + "  `tel` varchar(8) DEFAULT NULL,"
                    + "  `address` varchar(50) DEFAULT NULL,"
                    + "  `createdAt` char(19) DEFAULT NULL,"
                    + "  `updatedAt` char(19) DEFAULT NULL,"
                    + "  `deletedAt` char(19) DEFAULT NULL,"
                    + "  PRIMARY KEY (`id`)"
                    + ");";
            stmnt.execute(sql);

            sql
                    = "CREATE TABLE `bank` ("
                    + "  `id` varchar(30) NOT NULL,"
                    + "  `name` varchar(30) DEFAULT NULL,"
                    + "  `tel` varchar(8) DEFAULT NULL,"
                    + "  `address` varchar(50) DEFAULT NULL,"
                    + "  `createdAt` char(19) DEFAULT NULL,"
                    + "  `updatedAt` char(19) DEFAULT NULL,"
                    + "  `deletedAt` char(19) DEFAULT NULL,"
                    + "  PRIMARY KEY (`id`)"
                    + ");";
            stmnt.execute(sql);

            sql
                    = "CREATE TABLE `Account` ("
                    + "  `id` varchar(30) NOT NULL,"
                    + "  `customerId` varchar(30) DEFAULT NULL,"
                    + "  `bankId` varchar(30) DEFAULT NULL,"
                    + "  `accountNo` varchar(30) DEFAULT NULL,"
                    + "  `balance` int DEFAULT 0,"
                    + "  `createdAt` char(19) DEFAULT NULL,"
                    + "  `updatedAt` char(19) DEFAULT NULL,"
                    + "  `deletedAt` char(19) DEFAULT NULL,"
                    + "  PRIMARY KEY (`id`)"
                    + ");";
            stmnt.execute(sql);

            sql
                    = "CREATE TABLE `History` ("
                    + "  `id` varchar(30) NOT NULL,"
                    + "  `customerId` varchar(30) DEFAULT NULL,"
                    + "  `bankId` varchar(30) DEFAULT NULL,"
                    + "  `accountId` varchar(30) DEFAULT NULL,"
                    + "  `action` varchar(200) DEFAULT NULL,"
                    + "  `createdAt` char(19) DEFAULT NULL,"
                    + "  `updatedAt` char(19) DEFAULT NULL,"
                    + "  `deletedAt` char(19) DEFAULT NULL,"
                    + "  PRIMARY KEY (`id`)"
                    + ");";
            stmnt.execute(sql);

            sql
                    = "CREATE TABLE `ExchangeRate` ("
                    + "  `id` varchar(30) NOT NULL,"
                    + "  `currency1` varchar(30) DEFAULT NULL,"
                    + "  `currency2` varchar(30) DEFAULT NULL,"
                    + "  `rate` int DEFAULT NULL,"
                    + "  `createdAt` char(19) DEFAULT NULL,"
                    + "  `updatedAt` char(19) DEFAULT NULL,"
                    + "  `deletedAt` char(19) DEFAULT NULL,"
                    + "  PRIMARY KEY (`id`)"
                    + ");";
            stmnt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Model> getDataList() {
        return new ArrayList(data.values());

    }

}
