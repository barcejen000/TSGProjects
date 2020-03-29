/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author jenni
 */
public class VendingMachine {
    private String itemId;
    private String itemName;
    private BigDecimal itemPrice;
    private int itemCount;
    
    public VendingMachine(String itemId, String itemName, BigDecimal itemPrice, int itemCount){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
    }
    
    public VendingMachine(){
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.itemId);
        hash = 11 * hash + Objects.hashCode(this.itemName);
        hash = 11 * hash + Objects.hashCode(this.itemPrice);
        hash = 11 * hash + this.itemCount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VendingMachine other = (VendingMachine) obj;
        if (this.itemCount != other.itemCount) {
            return false;
        }
        if (!Objects.equals(this.itemId, other.itemId)) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.itemPrice, other.itemPrice)) {
            return false;
        }
        return true;
    }
    
    
}
