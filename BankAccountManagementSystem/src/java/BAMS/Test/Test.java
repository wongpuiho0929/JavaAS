package BAMS.Test;

import BAMS.DAO.*;
import BAMS.Model.*;
import java.util.ArrayList;
import java.util.Random;

public class Test {

    private static DAO bankDB, accountDB, exchangeRateDB, customerDB, historyDB;
    private static DAO[] DAOlist;

    public static void main(String[] args) {
        try {
            setting();
//            createTable();
//            createData();
            refreshData();
            
            Customer c = (Customer)((CustomerDAO)customerDB).findByUsername("Cust0");
            System.out.println("username:"+c.getUsername());
            System.out.println("password:"+c.getPassword());
//            Bank b = (Bank) bankDB.findById("B1");
//            bankDB.delete(b);
//            printTablesSize();
//            Account a = (Account) accountDB.findById("A31");
//            Account a2 = (Account) accountDB.findById("A34");
//            System.out.println("Account a: balance = " + a.getBalance());
//            a.setBalance(9999);
//            accountDB.update(a);
//            refreshData();
//            System.out.println("Account a(" + a.getAccountNo() + "): balance = " + a.getBalance());
//            System.out.println("Account a2(" + a2.getAccountNo() + "): balance = " + a2.getBalance());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable()  throws Exception{
        DAO.rebuild();
        refreshData();
    }

    private static void refreshData() throws Exception {
        for (DAO d : DAOlist) {
            d.refresh();
            printTablesSize();
        }
    }

    private static void createData()  throws Exception{

        Bank b = new Bank();
        b.setName("HangSeng Bank");
        b.setAddress("G/F, Empire Centre, 68 Mody Road, Tsim Sha Tsui");
        b.setTel("28220228");
        bankDB.create(b);
        DAO.printAllCount();
        Bank b2 = new Bank();
        b2.setName("Standard Chartered Bank");
        b2.setAddress("4-4A Des Voeux Road Central, Hong Kong");
        b2.setTel("28868868");
        bankDB.create(b2);

        ArrayList<Model> customerList = new ArrayList<>();
        ArrayList<Model> accountList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Customer c = new Customer();
            c.setName("Cust" + i);
            c.setUsername("Cust" + i);
            c.setPassword("password");
            c.setTel(20022202 + i + "");
            c.setAddress("IVE");
            System.out.println("adding Customer...");
            customerList.add(c);
        }
        customerDB.create(customerList);
        int i = 0;
        for (Model cust : customerList) {
            String acNo = (i++ + "").hashCode() + "";
            double balance = (new Random()).nextDouble() * 10000;
            Account ac = new Account((Customer) cust, b, acNo, balance);
            System.out.println("adding Account...");
            accountList.add(ac);
        }
        accountDB.create(accountList);
        System.out.println("Data Create Finish");
    }

    private static void printTablesSize() throws Exception {
        System.out.println("\n" + DAO.printAllCount());
    }

    private static void setting() throws Exception {
        String url = "jdbc:mysql://localhost:3306/BAMS_db";
        String user = "root";
        String pwd = "";
        DAO.setting(url, user, pwd);

        bankDB = DAO.getDAOs().get("Bank");
        accountDB = DAO.getDAOs().get("Account");
        exchangeRateDB = DAO.getDAOs().get("ExchangeRate");
        customerDB = DAO.getDAOs().get("Customer");
        historyDB = DAO.getDAOs().get("History");
        DAOlist = new DAO[]{bankDB, customerDB, accountDB, exchangeRateDB, historyDB
        };
        System.out.println("Setting Finish");
    }

}
