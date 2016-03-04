package BAMS.Test;

import BAMS.DAO.*;
import BAMS.Enum.UserType;
import BAMS.Model.*;
import java.util.ArrayList;
import java.util.Random;

public class Test {

//    private static DAO bankDB, accountDB, exchangeRateDB, customerDB, historyDB;
    private static DAO[] DAOlist;

    public static void main(String[] args) {
        try {
            setting();
//            createTable();
//            createData();
            refreshData();
            ArrayList<Model> acList = DAO.accountDB.getDataList();
            Account ac = (Account)acList.get(0);
            System.out.println("Currency : "+ac.getCurrency().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable() throws Exception {
        DAO.rebuild();
        refreshData();
    }

    private static void refreshData() throws Exception {
        DAO.refreshAll();
        System.out.println("refreshData() finish");
    }

    private static void createBank() throws Exception {
        Bank b = new Bank();
        b.setName("HangSeng Bank");
        b.setAddress("G/F, Empire Centre, 68 Mody Road, Tsim Sha Tsui");
        b.setTel("28220228");
        DAO.bankDB.create(b);
        DAO.printAllCount();
        Bank b2 = new Bank();
        b2.setName("Standard Chartered Bank");
        b2.setAddress("4-4A Des Voeux Road Central, Hong Kong");
        b2.setTel("28868868");
        DAO.bankDB.create(b2);
        
        System.out.println("createBank() finish.");
    }
    
    private static void createUserCustomer() throws Exception {
        
        ArrayList<Model> userList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            User u = new User();
            Customer c = new Customer();
            u.setUsername("Cust" + i);
            u.setPassword("password");
            c.setName("Cust" + i);
            c.setTel(20022202 + i + "");
            c.setAddress("IVE");
            c.setUser(u);
            u.setType(UserType.Customer);
            u.setCustomer(c);
//            System.out.println("adding Customer...");
            userList.add(u);
        }
        DAO.userDB.create(userList);
        System.out.println("createUserCustomer() finish.");
    }
    
    private static void createAccount() throws Exception{
        int i = 0;
        ArrayList<Model> currencyList = DAO.currencyDB.getDataList();
        ArrayList<Model> bankList = DAO.bankDB.getDataList();
        ArrayList<Model> userList = DAO.userDB.getDataList();
        ArrayList<Model> accountList = new ArrayList<>();
        for (Model user : userList) {
            String acNo = (i++ + "").hashCode() + "";
            double balance = (new Random()).nextDouble() * 10000;
            Account ac = new Account();
            ac.setBalance(balance);
            ac.setAccountNo(acNo);
            ac.setCustomer(((User)user).getCustomer());
            ac.setBank((Bank)bankList.get(((int)(Math.random()*100) % 2)));
            ac.setCurrency((Currency)currencyList.get(((int)(Math.random()*100) % currencyList.size())));
//            System.out.println("adding Account...");
            accountList.add(ac);
        }
        DAO.accountDB.create(accountList);
        System.out.println("createAccount() finish.");
    }
    
    private static void createData() throws Exception {
        createBank();
        createUserCustomer();
        createCurrency();
        createAccount();
        
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
        
        System.out.println("Setting Finish");
    }

    private static void createCurrency() throws Exception{
        Currency c = new Currency();
        c.setName("Hong Kong Dollar"); //
        c.setPrefix("HKD");
        DAO.currencyDB.create(c);
        c.setName("Swiss Franc");
        c.setPrefix("CHF");
        DAO.currencyDB.create(c);
        c.setName("Singapore Dollar");
        c.setPrefix("SGD");
        DAO.currencyDB.create(c);
        c.setName("US Dollar");
        c.setPrefix("USD");
        DAO.currencyDB.create(c);
        c.setName("Australian Dollar");
        c.setPrefix("AUD");
        DAO.currencyDB.create(c);
        c.setName("Swedish Krone");
        c.setPrefix("SEK");
        DAO.currencyDB.create(c);
        c.setName("British Pound");
        c.setPrefix("GBP");
        DAO.currencyDB.create(c);
        c.setName("Canadian Dollar");
        c.setPrefix("CAD");
        DAO.currencyDB.create(c);
        c.setName("Danish Krone");
        c.setPrefix("DKK");
        DAO.currencyDB.create(c);
        c.setName("Japanese Yen");
        c.setPrefix("JPY");
        DAO.currencyDB.create(c);
        c.setName("New Zealand Dollar");
        c.setPrefix("NZD");
        DAO.currencyDB.create(c);
        c.setName("Renminbi");
        c.setPrefix("RMB");
        DAO.currencyDB.create(c);
        c.setName("Euro");
        c.setPrefix("EUR");
        DAO.currencyDB.create(c);
        System.out.println("createCurrency() finish.");
    }

}
