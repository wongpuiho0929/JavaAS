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
public class Currency extends Model {

    private ArrayList<ExchangeRate> erList;
    private String name;
    
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
    
    public ArrayList<ExchangeRate> getExchangeRateList(){
        return erList;
    }
    
    public void addExchangeRate(ExchangeRate er){
        erList.add(er);
    }
    
    

}
