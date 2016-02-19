package BAMS.Test;

import BAMS.DAO.*;
import BAMS.Model.*;

public class Test {

    private static DAO bankDB, accountDB, exchangeRateDB, customerDB, historyDB;
    private static DAO[] DAOlist;

    public static void main(String[] args) {
        try {
            setting();
            createTable();
            createData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable() {

        DAO.rebuild();
        for(DAO d : DAOlist)
            d.refresh();
        DAO.printAllCount();

    }

    private static void createData() {
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
        DAO.printAllCount();
    }

    private static void printTableSize() {
        System.out.println(DAO.printAllCount());
    }

    private static void setting() {
        String url = "jdbc:mysql://localhost:3306/BAMS_db";
        String user = "root";
        String pwd = "";
        DAO.setting(url, user, pwd);

        bankDB = DAO.getDAOs().get("Bank");
        accountDB = DAO.getDAOs().get("Account");
        exchangeRateDB = DAO.getDAOs().get("ExchangeRate");
        customerDB = DAO.getDAOs().get("Customer");
        historyDB = DAO.getDAOs().get("History");
        DAOlist = new DAO[]{bankDB, accountDB, exchangeRateDB, customerDB, historyDB
        };
        System.out.println("Setting Finish");
    }

}
