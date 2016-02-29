package BAMS.DAO;

import BAMS.Model.Model;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    protected static Connection conn;
    public static CustomerDAO customerDB;
    public static CurrencyDAO currencyDB;
    public static AccountDAO accountDB;
    public static BankDAO bankDB;
    public static ExchangeRateDAO exchangeRateDB;
    public static HistoryDAO historyDB;

    public static void setting(String dburl, String dbUser, String dbPassword) {
        try {
            DAO.dbPassword = dbPassword;
            DAO.dbUser = dbUser;
            DAO.dburl = dburl;

            conn = getConnection();

            DAObyName.put("Bank", new BankDAO());
            DAObyName.put("Customer", new CustomerDAO());
            DAObyName.put("ExchangeRate", new ExchangeRateDAO());
            DAObyName.put("Account", new AccountDAO());
            DAObyName.put("History", new HistoryDAO());
            DAObyName.put("Currency", new CurrencyDAO());

            DAObyName.get("Bank").refresh();
            DAObyName.get("Customer").refresh();
            DAObyName.get("ExchangeRate").refresh();
            DAObyName.get("Account").refresh();
            DAObyName.get("History").refresh();
            DAObyName.get("Currency").refresh();

            accountDB = (AccountDAO)getDAO("Account");
            bankDB = (BankDAO)getDAO("Bank");
            currencyDB = (CurrencyDAO)getDAO("Currency");
            customerDB = (CustomerDAO)getDAO("Customer");
            exchangeRateDB = (ExchangeRateDAO)getDAO("ExchangeRate");
            historyDB = (HistoryDAO)getDAO("History");
            
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Hashtable<String, DAO> getDAOs() {
        return DAObyName;
    }

    public static DAO getDAO(String name) {
        return DAObyName.get(name);
    }

    public Date stringToDate(String s) {
        try {
            if (s.equals("null")) {
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

    public abstract boolean create(Model m) throws Exception;

    public boolean create(ArrayList<Model> m) throws Exception {
        boolean success = true;
        for (Model model : m) {
            if (create(model) == false) {
                success = false;
            }
        }

        return success;
    }

    public boolean delete(Model m) throws Exception {
        boolean success = false;
        try {

            String sql = "update " + this.table + " set deletedAt=? where id=?";
            PreparedStatement p = conn.prepareStatement(sql);
            int index = 1;
            Date now = new Date();
            p.setString(index++, dateToString(now));
            p.setString(index++, m.getId());

            success = p.execute();

            m.setDeletedAt(now);

        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }

    public boolean delete(ArrayList<Model> m) throws Exception {
        boolean success = true;
        for (Model model : m) {
            if (delete(model) == false) {
                success = false;
            }
        }

        return success;
    }

    public abstract boolean update(Model m) throws Exception;

    public boolean update(ArrayList<Model> m) throws Exception {
        boolean success = true;
        for (Model model : m) {
            if (update(model) == false) {
                success = false;
            }
        }

        return success;
    }

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
        dropTable("Currency");

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
        System.out.println(getTableName() + " finish refresh.");
    }

    public synchronized static void createTable() {
        try {
            Connection conn = getConnection();
            Statement stmnt = conn.createStatement();
            String sql
                    = "CREATE TABLE `customer` ("
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
                    + "  `currency1Id` varchar(30) DEFAULT NULL,"
                    + "  `currency2Id` varchar(30) DEFAULT NULL,"
                    + "  `rate` int DEFAULT NULL,"
                    + "  `createdAt` char(19) DEFAULT NULL,"
                    + "  `updatedAt` char(19) DEFAULT NULL,"
                    + "  `deletedAt` char(19) DEFAULT NULL,"
                    + "  PRIMARY KEY (`id`)"
                    + ");";
            stmnt.execute(sql);

            sql
                    = "CREATE TABLE `User` ("
                    + "  `id` varchar(30) NOT NULL,"
                    + "  `username` varchar(30) DEFAULT NULL,"
                    + "  `password` varchar(30) DEFAULT NULL,"
                    + "  `type` varchar(30) DEFAULT NULL,"
                    + "  `customerId` varchar(30) DEFAULT NULL,"
                    + "  `createdAt` char(19) DEFAULT NULL,"
                    + "  `updatedAt` char(19) DEFAULT NULL,"
                    + "  `deletedAt` char(19) DEFAULT NULL,"
                    + "  PRIMARY KEY (`id`)"
                    + ");";
            stmnt.execute(sql);
            sql
                    = "CREATE TABLE `Currency` ("
                    + "  `id` varchar(30) NOT NULL,"
                    + "  `name` varchar(30) DEFAULT NULL,"
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

    protected void finalize() throws Throwable {
        conn.close();
        System.out.println("Connection closed.");
        super.finalize();
    }
}
