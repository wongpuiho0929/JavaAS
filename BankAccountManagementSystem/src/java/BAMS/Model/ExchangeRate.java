/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.Model;

/**
 *
 * @author User
 */
public class ExchangeRate extends Model{
    
    private String currency1,currency2;
    private double rate;

    public ExchangeRate() {
    }
    
    public ExchangeRate(String currency1, String currency2, double rate) {
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.rate = rate;
    }

    public String getCurrency1() {
        return currency1;
    }

    public void setCurrency1(String currency1) {
        this.currency1 = currency1;
    }

    public String getCurrency2() {
        return currency2;
    }

    public void setCurrency2(String currency2) {
        this.currency2 = currency2;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
}
