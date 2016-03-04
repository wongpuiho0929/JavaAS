/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.Model;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Currency extends Model implements Comparable<Currency> {

    private ArrayList<ExchangeRate> erList;
    private String name;
    private String prefix;

    public Currency() {
        super();
        erList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ArrayList<ExchangeRate> getExchangeRateList() {
        return erList;
    }

    public void addExchangeRate(ExchangeRate er) {
        erList.add(er);
    }

    @Override
    public int compareTo(Currency o) {
        return this.getName().compareTo(o.getName());
    }

}
