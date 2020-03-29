/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dto;

/**
 *
 * @author jenni
 */
public class Change {
    
    private int quarters;
    private int dimes;
    private int nickels;
    private int pennies;
    
    public Change (int quarters, int dimes, int nickels, int pennies) {
        this.quarters = quarters;
        this.dimes = dimes;
        this.nickels = nickels; 
        this.pennies = pennies;
    
}

    public Change() {
    }

    public int getQuarters() {
        return quarters;
    }

    public void setQuarters(int quarters) {
        this.quarters = quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public void setDimes(int dimes) {
        this.dimes = dimes;
    }

    public int getNickels() {
        return nickels;
    }

    public void setNickels(int nickels) {
        this.nickels = nickels;
    }

    public int getPennies() {
        return pennies;
    }

    public void setPennies(int pennies) {
        this.pennies = pennies;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.quarters;
        hash = 61 * hash + this.dimes;
        hash = 61 * hash + this.nickels;
        hash = 61 * hash + this.pennies;
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
        final Change other = (Change) obj;
        if (this.quarters != other.quarters) {
            return false;
        }
        if (this.dimes != other.dimes) {
            return false;
        }
        if (this.nickels != other.nickels) {
            return false;
        }
        if (this.pennies != other.pennies) {
            return false;
        }
        return true;
    }

    
}
