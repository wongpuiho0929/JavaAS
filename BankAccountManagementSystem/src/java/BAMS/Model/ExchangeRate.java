/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.Model;

import BAMS.DAO.DAO;
import BAMS.DAO.ExchangeRateDAO;

/**
 *
 * @author User
 */
public class ExchangeRate extends Model {

    private String currency1Id, currency2Id;
    private double rate;
    private static ExchangeRateDAO db = DAO.exchangeRateDB;
            
    public ExchangeRate() {
    }

    public ExchangeRate(String currency1, String currency2, double rate) {
        this.currency1Id = currency1;
        this.currency2Id = currency2;
        this.rate = rate;
    }

    public Currency getCurrency1() {
        return DAO.currencyDB.findById(this.currency1Id);
    }

    public void setCurrency1(Currency currency1) {
        this.currency1Id = currency1.getId();
    }

    public Currency getCurrency2() {
       return DAO.currencyDB.findById(this.currency2Id);
    }

    public void setCurrency2(Currency currency2) {
        this.currency2Id = currency2.getId();
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    public static ExchangeRate findById(String id) {
        return (ExchangeRate) findById(db, id);
    }
}
