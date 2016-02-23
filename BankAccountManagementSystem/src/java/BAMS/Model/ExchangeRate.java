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

    private Currency currency1, currency2;
    private double rate;
    private static ExchangeRateDAO db = ((ExchangeRateDAO) DAO.getDAO("ExchangeRate"));

    public ExchangeRate() {
    }

    public ExchangeRate(Currency currency1, Currency currency2, double rate) {
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.rate = rate;
    }

    public Currency getCurrency1() {
        return currency1;
    }

    public void setCurrency1(Currency currency1) {
        this.currency1 = currency1;
    }

    public Currency getCurrency2() {
        return currency2;
    }

    public void setCurrency2(Currency currency2) {
        this.currency2 = currency2;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void save() {
        save(db);
    }

    public static Customer findById(String id) {
        return (Customer) findById(db, id);
    }
}
