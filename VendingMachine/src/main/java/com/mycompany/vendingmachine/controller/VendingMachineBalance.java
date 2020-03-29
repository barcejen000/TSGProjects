/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.controller;

import java.math.BigDecimal;

/**
 *
 * @author jenni
 */
public class VendingMachineBalance {
    
    private BigDecimal moneyBalance = new BigDecimal(0);
    
    
    public BigDecimal getCurrentBalance(){
        return this.moneyBalance;
    }
    
    public void returnBalance(BigDecimal money){
        moneyBalance = moneyBalance.subtract(money);
    }
    
    public void addToBalance(BigDecimal money){
        moneyBalance = moneyBalance.add(money);
    }
    
}
