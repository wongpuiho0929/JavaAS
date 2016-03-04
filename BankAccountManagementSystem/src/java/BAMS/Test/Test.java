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
            createTable();
            createData();
            refreshData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable() throws Exception {
        DAO.rebuild();
        refreshData();
    }

    private static void refreshData() throws Exception {
        for (DAO d : DAO.DBs) {
            d.refresh();
            printTablesSize();
        }
    }

    private static void createData() throws Exception {
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

        ArrayList<Model> userList = new ArrayList<>();
        ArrayList<Model> accountList = new ArrayList<>();
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
        int i = 0;
        for (Model user : userList) {
            String acNo = (i++ + "").hashCode() + "";
            double balance = (new Random()).nextDouble() * 10000;
            Account ac = new Account(((User)user).getCustomer(), b, acNo, balance);
            System.out.println("adding Account...");
            accountList.add(ac);
        }
        DAO.accountDB.create(accountList);
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

}
